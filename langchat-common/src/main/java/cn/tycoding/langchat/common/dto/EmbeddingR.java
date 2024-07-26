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

package cn.tycoding.langchat.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tycoding
 * @since 2024/4/26
 */
@Data
@Accessors(chain = true)
public class EmbeddingR {

    /**
     * 写入到vector store的ID
     */
    private String vectorId;

    /**
     * 文档ID
     */
    private String docsId;

    /**
     * 知识库ID
     */
    private String knowledgeId;

    /**
     * Embedding后切片的文本
     */
    private String text;
}
