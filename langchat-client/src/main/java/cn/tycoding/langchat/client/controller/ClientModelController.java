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

package cn.tycoding.langchat.client.controller;

import cn.tycoding.langchat.biz.entity.AigcModel;
import cn.tycoding.langchat.biz.entity.AigcPrompt;
import cn.tycoding.langchat.biz.service.AigcModelService;
import cn.tycoding.langchat.biz.service.AigcPromptService;
import cn.tycoding.langchat.common.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tycoding
 * @since 2024/1/19
 */
@RequestMapping("/client")
@RestController
@AllArgsConstructor
public class ClientModelController {

    private final AigcPromptService aigcPromptService;
    private final AigcModelService aigcModelService;

    @GetMapping("/prompt/list")
    public R list(AigcPrompt data) {
        List<AigcPrompt> list = aigcPromptService.list(Wrappers.<AigcPrompt>lambdaQuery().orderByDesc(AigcPrompt::getCreateTime));
        return R.ok(list);
    }

    @GetMapping("/getChatModels")
    public R<List<AigcModel>> getChatModels() {
        List<AigcModel> list = aigcModelService.getChatModels();
        list.forEach(i -> i.setApiKey(null));
        return R.ok(list);
    }
}
