/*
 * Copyright (c) 2024 LangChat. TyCoding All Rights Reserved.
 *
 * Licensed under the GNU Affero General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.tycoding.langchat.server.service.impl;

import cn.tycoding.langchat.biz.entity.AigcMessage;
import cn.tycoding.langchat.biz.service.AigcMessageService;
import cn.tycoding.langchat.common.constant.RoleEnum;
import cn.tycoding.langchat.common.dto.ChatReq;
import cn.tycoding.langchat.common.dto.ChatRes;
import cn.tycoding.langchat.common.utils.ServletUtil;
import cn.tycoding.langchat.common.utils.StreamEmitter;
import cn.tycoding.langchat.core.service.LangChatService;
import cn.tycoding.langchat.core.service.LangDocService;
import cn.tycoding.langchat.server.service.ChatService;
import dev.langchain4j.model.output.TokenUsage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author tycoding
 * @since 2024/1/4
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final LangChatService langChatService;
    private final LangDocService langDocService;
    private final AigcMessageService aigcMessageService;

    @Override
    public void chat(ChatReq req) {
        StreamEmitter emitter = req.getEmitter();
        long startTime = System.currentTimeMillis();
        StringBuilder text = new StringBuilder();

        // save user message
        req.setRole(RoleEnum.USER.getName());
        saveMessage(req, 0, 0);

        try {
            langChatService.chat(req)
                    .onNext(e -> {
                        text.append(e);
                        emitter.send(new ChatRes(e));
                    })
                    .onComplete((e) -> {
                        TokenUsage tokenUsage = e.tokenUsage();
                        emitter.send(new ChatRes(tokenUsage.totalTokenCount(), startTime));
                        emitter.complete();

                        // save message
                        if (req.getConversationId() != null) {
                            req.setMessage(text.toString());
                            req.setRole(RoleEnum.ASSISTANT.getName());
                            saveMessage(req, tokenUsage.inputTokenCount(),
                                    tokenUsage.outputTokenCount());
                        }
                    })
                    .onError((e) -> {
                        emitter.error(e.getMessage());
                        throw new RuntimeException(e.getMessage());
                    })
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
            emitter.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void docsChat(ChatReq req) {
        long startTime = System.currentTimeMillis();
        StreamEmitter emitter = req.getEmitter();
        StringBuilder text = new StringBuilder();

        // save user message
        req.setRole(RoleEnum.USER.getName());
        saveMessage(req, 0, 0);

        try {
            langDocService.chat(req)
                    .onNext(e -> {
                        text.append(e);
                        emitter.send(new ChatRes(e));
                    })
                    .onComplete(e -> {
                        TokenUsage tokenUsage = e.tokenUsage();
                        emitter.send(new ChatRes(tokenUsage.totalTokenCount(), startTime));
                        emitter.complete();

                        // save message
                        if (req.getConversationId() != null) {
                            req.setMessage(text.toString());
                            req.setRole(RoleEnum.ASSISTANT.getName());
                            saveMessage(req, tokenUsage.inputTokenCount(),
                                    tokenUsage.outputTokenCount());
                        }
                    })
                    .onError((e) -> {
                        e.printStackTrace();
                        emitter.error(e.getMessage());
                    })
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
            emitter.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveMessage(ChatReq req, Integer inputToken, Integer outputToken) {
        AigcMessage message = new AigcMessage();
        BeanUtils.copyProperties(req, message);
        message.setIp(ServletUtil.getIpAddr());
        message.setPromptTokens(inputToken);
        message.setTokens(outputToken);
        aigcMessageService.addMessage(message);
    }
}
