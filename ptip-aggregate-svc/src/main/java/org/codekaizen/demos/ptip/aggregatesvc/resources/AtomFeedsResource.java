/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Kevin Brockhoff
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.codekaizen.demos.ptip.aggregatesvc.resources;

import com.rometools.rome.feed.atom.Feed;
import io.micrometer.core.annotation.Timed;
import org.codekaizen.demos.ptip.aggregatesvc.repositories.AtomFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AtomFeedsResource {

    @Autowired
    private AtomFeedRepository atomFeedRepository;

    @GetMapping(path = "/feeds/{feedId}")
    @Timed(percentiles = {0.5, 0.75, 0.95, 0.98, 0.99, 0.999}, histogram = true)
    public Feed getAtomFeed(@PathVariable String feedId) {
        return atomFeedRepository.getFeed(feedId.toLowerCase());
    }

}
