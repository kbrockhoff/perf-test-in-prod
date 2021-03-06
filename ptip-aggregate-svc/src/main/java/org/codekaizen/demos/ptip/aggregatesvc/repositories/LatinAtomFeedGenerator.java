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

import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.synd.SyndPerson;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.apache.commons.math3.distribution.ParetoDistribution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Implementation of AtomFeedRepository which generates a fake feed filled with Lorem Ipsum.
 *
 * @author kbrockhoff
 */
@Component
public class LatinAtomFeedGenerator implements AtomFeedRepository {

    private static final String DATE_FMT = "yyyyMMddHHmmss";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FMT);
    private final Lorem lorem = LoremIpsum.getInstance();
    private final ParetoDistribution paretoDistribution = new ParetoDistribution(20.0, 2.5);
    @Value("${codekaizen.feeds.baseurl}")
    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Feed getFeed(String feedId) {
        simulateRemoteCalls();
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        feed.setTitle("Lorem Ipsum");
        feed.setId("https://codekaizen.org/feeds/" + feedId);

        Content subtitle = new Content();
        subtitle.setType("text/plain");
        subtitle.setValue("Scrambled, nonsensical Latin derived from Cicero's 1st-century BC text De Finibus Bonorum et Malorum.");
        feed.setSubtitle(subtitle);

        Instant currentTs = Instant.now();
        feed.setUpdated(Date.from(currentTs));

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            entries.add(generateEntry(feedId, currentTs.minusSeconds((long) i * 124L)));
        }
        feed.setEntries(entries);
        return feed;
    }

    private void simulateRemoteCalls() {
        long delay = 75L + (long) paretoDistribution.sample();
        spin(delay);
    }

    void spin(long milliseconds) {
        long sleepTime = milliseconds * 1000000L; // convert to nanoseconds
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {
            System.getProperty("codekaizen.user");
        }
    }

    private Entry generateEntry(String feedId, Instant when) {
        String id = formatter.format(LocalDateTime.ofInstant(when, ZoneId.systemDefault()));
        Entry entry = new Entry();

        Link link = new Link();
        link.setHref(baseUrl + "/" + feedId + "/" + id);
        entry.setAlternateLinks(Collections.singletonList(link));
        SyndPerson author = new Person();
        author.setName("Anonymous");
        entry.setAuthors(Collections.singletonList(author));
        Date postDate = Date.from(when);
        entry.setCreated(postDate);
        entry.setPublished(postDate);
        entry.setUpdated(postDate);
        entry.setId("https://codekaizen.org/feeds/" + feedId + "/" + id);
        entry.setTitle(lorem.getTitle(4, 8));

        Category category = new Category();
        category.setTerm("Testing");
        entry.setCategories(Collections.singletonList(category));

        Content summary = new Content();
        summary.setType("text/plain");
        summary.setValue(lorem.getParagraphs(1, 1));
        entry.setSummary(summary);
        return entry;
    }

}
