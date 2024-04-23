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
package org.codekaizen.demos.ptip.aggregatesvc;

import org.codekaizen.demos.ptip.aggregatesvc.repositories.AtomFeedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for PTiPAggregationServiceApplication.
 *
 * @author kbrockhoff
 */
@SpringBootTest
class PTiPAggregationServiceApplicationTests {

	@Autowired
	private AtomFeedRepository atomFeedRepository;

	@Test
	void contextLoads() {
		assertNotNull(atomFeedRepository);
	}

}
