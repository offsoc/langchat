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

import format from 'date-fns/format';

const DATE_TIME_FORMAT = 'yyyy-MM-dd HH:mm:ss';
const DATE_FORMAT = 'yyyy-MM-dd ';

export function formatToDateTime(date: Date | number, formatStr = DATE_TIME_FORMAT): string {
  return format(date, formatStr);
}

export function formatToDate(date: Date | number, formatStr = DATE_FORMAT): string {
  return format(date, formatStr);
}
