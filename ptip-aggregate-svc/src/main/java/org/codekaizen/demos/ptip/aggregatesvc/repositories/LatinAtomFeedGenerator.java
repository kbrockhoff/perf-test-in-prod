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

import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.synd.SyndPerson;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
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
import java.util.Random;

@Component
public class LatinAtomFeedGenerator implements AtomFeedRepository {

    private static final String DATE_FMT = "yyyyMMddHHmmss";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FMT);
    private final Lorem lorem = LoremIpsum.getInstance();
    private final Random random = new Random();
    @Value("${codekaizen.feeds.baseurl}")
    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Feed getFeed(String feedId) throws InterruptedException {
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

    private void simulateRemoteCalls() throws InterruptedException {
        long delay = 100L + (long) (50.0 * random.nextGaussian());
        Thread.sleep(delay);
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
