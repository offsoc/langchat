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

import { http } from '@/utils/http/axios';

export function page(params: any) {
  return http.request({
    url: '/aigc/model/page',
    method: 'get',
    params,
  });
}

export function list(params: any) {
  return http.request({
    url: '/aigc/model/list',
    method: 'get',
    params,
  });
}

export function getChatModels() {
  return http.request({
    url: '/aigc/model/getChatModels',
    method: 'get',
  });
}

export function getImageModels() {
  return http.request({
    url: '/aigc/model/getImageModels',
    method: 'get',
  });
}

export function getEmbeddingModels() {
  return http.request({
    url: '/aigc/model/getEmbeddingModels',
    method: 'get',
  });
}

export function getById(id: any) {
  return http.request({
    url: `/aigc/model/${id}`,
    method: 'get',
  });
}

export function add(params: any) {
  return http.request({
    url: '/aigc/model',
    method: 'post',
    params,
  });
}

export function update(params: any) {
  return http.request({
    url: '/aigc/model',
    method: 'put',
    params,
  });
}

export function del(id: string) {
  return http.request({
    url: `/aigc/model/${id}`,
    method: 'delete',
  });
}
