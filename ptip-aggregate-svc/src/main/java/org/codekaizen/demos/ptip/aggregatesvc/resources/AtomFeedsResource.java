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

import com.rometools.rome.feed.atom.Feed;
import io.micrometer.core.annotation.Timed;
import org.codekaizen.demos.ptip.aggregatesvc.repositories.AtomFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Atom feed with artifical delay request handler.
 *
 * @author kbrockhoff
 */
@RestController
public class AtomFeedsResource {

    private AtomFeedRepository atomFeedRepository;

    @GetMapping(path = "/feeds/{feedId}")
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.98, 0.99, 0.999}, histogram = true)
    public Feed getAtomFeed(@PathVariable String feedId) {
        return atomFeedRepository.getFeed(feedId.toLowerCase());
    }

    @Autowired
    public void setAtomFeedRepository(AtomFeedRepository atomFeedRepository) {
        this.atomFeedRepository = atomFeedRepository;
    }

}
