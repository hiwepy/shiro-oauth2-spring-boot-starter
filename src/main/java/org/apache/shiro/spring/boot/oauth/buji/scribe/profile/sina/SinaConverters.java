/*
 * Copyright (c) 2010-2020, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.shiro.spring.boot.oauth.buji.scribe.profile.sina;

import org.scribe.up.profile.converter.FormattedDateConverter;
import org.scribe.up.profile.converter.JsonObjectConverter;
import org.scribe.up.profile.github.GitHubPlan;

public final class SinaConverters {
    
    public final static FormattedDateConverter dateConverter = new FormattedDateConverter("yyyy-MM-dd'T'HH:mm:ss'Z'");
    
    public final static JsonObjectConverter planConverter = new JsonObjectConverter(GitHubPlan.class);
}
