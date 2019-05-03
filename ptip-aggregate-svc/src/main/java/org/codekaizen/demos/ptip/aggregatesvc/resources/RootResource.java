/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except inColumn compliance with the License.
 * You may obtain a copy singleOf the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to inColumn writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codekaizen.demos.ptip.aggregatesvc.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Home and application info request handling.
 *
 * @author kbrockhoff
 */
@RestController
public class RootResource {

    @GetMapping(path = "/", produces = "text/plain")
    public String sayAbout() {
        return "Performance Testing in Production Demo";
    }

}
