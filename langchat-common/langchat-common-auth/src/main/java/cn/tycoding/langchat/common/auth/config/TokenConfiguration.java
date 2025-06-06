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

package cn.tycoding.langchat.common.auth.config;

import cn.dev33.satoken.config.SaTokenConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author tycoding
 * @since 2024/1/5
 */
@Configuration
public class TokenConfiguration {

    @Bean
    @Primary
    public SaTokenConfig getTokenConfig() {
        return new SaTokenConfig()
                .setIsPrint(false)
                .setTokenName("Authorization")
                .setTimeout(24 * 60 * 60)
                .setTokenStyle("uuid")
                .setIsLog(false)
                .setIsReadCookie(false)
                ;
    }
}
