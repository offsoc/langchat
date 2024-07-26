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

package cn.tycoding.langchat.server.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Dict;
import cn.tycoding.langchat.biz.entity.AigcUser;
import cn.tycoding.langchat.biz.service.AigcUserService;
import cn.tycoding.langchat.biz.utils.ClientAuthUtil;
import cn.tycoding.langchat.common.annotation.ApiLog;
import cn.tycoding.langchat.common.utils.MybatisUtil;
import cn.tycoding.langchat.common.utils.QueryPage;
import cn.tycoding.langchat.common.utils.R;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tycoding
 * @since 2024/4/15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/aigc/user")
public class AigcUserController {

    private final AigcUserService userService;

    @GetMapping("/info")
    public R<AigcUser> info() {
        AigcUser userInfo = userService.info(ClientAuthUtil.getUsername());
        userInfo.setPassword(null);
        return R.ok(userInfo);
    }

    @GetMapping("/checkName")
    public R<Boolean> checkName(AigcUser sysUser) {
        return R.ok(userService.checkName(sysUser));
    }

    @GetMapping("/list")
    public R<List<AigcUser>> list(AigcUser data) {
        return R.ok(userService.list(Wrappers.lambdaQuery()));
    }

    @GetMapping("/page")
    public R<Dict> page(AigcUser user, QueryPage queryPage) {
        return R.ok(MybatisUtil.getData(userService.page(user, queryPage)));
    }

    @GetMapping("/{id}")
    public R<AigcUser> findById(@PathVariable Long id) {
        return R.ok(userService.getById(id));
    }

    @PostMapping
    @ApiLog("新增客户端用户")
    @SaCheckPermission("aigc:user:add")
    public R<AigcUser> add(@RequestBody AigcUser data) {
        userService.save(data);
        return R.ok();
    }

    @PutMapping
    @ApiLog("修改客户端用户")
    @SaCheckPermission("aigc:user:update")
    public R update(@RequestBody AigcUser data) {
        userService.updateById(data);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @ApiLog("删除客户端用户")
    @SaCheckPermission("aigc:user:delete")
    public R delete(@PathVariable Long id) {
        AigcUser user = userService.getById(id);
        if (user != null) {
            userService.removeById(id);
        }
        return R.ok();
    }
}
