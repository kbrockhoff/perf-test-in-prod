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
package org.codekaizen.demos.ptip.aggregatesvc.repositories;

import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class LatinAtomFeedGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(LatinAtomFeedGeneratorTest.class);

    @Test
    void shouldGenerateFeedForAnyId() throws FeedException, InterruptedException {
        LatinAtomFeedGenerator generator = new LatinAtomFeedGenerator();
        generator.setBaseUrl("http://localhost:8082/feeds");
        Feed result = generator.getFeed("test");
        WireFeedOutput formatter = new WireFeedOutput();
        logger.info("{}", formatter.outputString(result));
        assertEquals("Lorem Ipsum", result.getTitle());
        assertEquals(16, result.getEntries().size());
    }

}