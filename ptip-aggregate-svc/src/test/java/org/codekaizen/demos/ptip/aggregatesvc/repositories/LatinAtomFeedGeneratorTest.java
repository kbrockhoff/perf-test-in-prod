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
package org.codekaizen.demos.ptip.aggregatesvc.repositories;

import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LatinAtomFeedGenerator.
 *
 * @author kbrockhoff
 */
class LatinAtomFeedGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(LatinAtomFeedGeneratorTest.class);

    @Test
    void shouldGenerateFeedForAnyId() throws FeedException {
        LatinAtomFeedGenerator generator = new LatinAtomFeedGenerator();
        generator.setBaseUrl("http://localhost:8082/feeds");
        Feed result = generator.getFeed("test");
        WireFeedOutput formatter = new WireFeedOutput();
        logger.info("{}", formatter.outputString(result));
        assertEquals("Lorem Ipsum", result.getTitle());
        assertEquals(16, result.getEntries().size());
    }

    @Test
    void shouldSpinThePrescribedAmountOfTime() {
        LatinAtomFeedGenerator generator = new LatinAtomFeedGenerator();
        generator.setBaseUrl("http://localhost:8082/feeds");
        long specifiedNs = 120L * 1000000L;
        long startTs = System.nanoTime();
        generator.spin(specifiedNs / 1000000L);
        long elapsed = System.nanoTime() - startTs;
        logger.info("elpased time: {} ns", elapsed);
        assertTrue(elapsed > specifiedNs - 5000000L && elapsed < specifiedNs + 5000000L);
    }

    @Test
    void shouldCorrectlyUseParetoDistribution() {
        ParetoDistribution paretoDistribution = new ParetoDistribution(20, 2.5);
        logger.info("min {}", paretoDistribution.getSupportLowerBound());
        logger.info("max {}", paretoDistribution.getSupportUpperBound());
        logger.info("mean {}", paretoDistribution.getNumericalMean());
        for (int i = 0; i < 16; i++) {
            long sample = (long) paretoDistribution.sample();
            logger.info("sample {}", sample);
            assertTrue(sample >= 20L);
        }
    }

}