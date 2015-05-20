/**
 * Copyright (c) 2012-2014, jcabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.s3.mock;

import com.jcabi.s3.Bucket;
import com.jcabi.s3.Ocket;
import com.jcabi.s3.Region;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Test case for {@link MkBucket}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.8.1
 */
public final class MkBucketTest {

    /**
     * Temp directory.
     * @checkstyle VisibilityModifierCheck (5 lines)
     */
    @Rule
    public final transient TemporaryFolder temp = new TemporaryFolder();

    /**
     * MkBucket can list ockets.
     * @throws Exception If fails
     */
    @Test
    @SuppressWarnings("unchecked")
    public void listsOckets() throws Exception {
        final Region region = new MkRegion(this.temp.newFolder());
        final Bucket bucket = region.bucket("test");
        new Ocket.Text(bucket.ocket("a/first.txt")).write("");
        new Ocket.Text(bucket.ocket("a/b/hello.txt")).write("");
        new Ocket.Text(bucket.ocket("a/b/f/2.txt")).write("");
        new Ocket.Text(bucket.ocket("a/b/c/d/3.txt")).write("");
        MatcherAssert.assertThat(
            new Bucket.Prefixed(bucket, "a/b/").list(""),
            Matchers.allOf(
                // @checkstyle MagicNumberCheck (1 line)
                Matchers.<String>iterableWithSize(7),
                Matchers.hasItem("/"),
                Matchers.hasItem("hello.txt"),
                Matchers.hasItem("f"),
                Matchers.hasItem("f/2.txt"),
                Matchers.hasItem("c"),
                Matchers.hasItem("c/d"),
                Matchers.hasItem("c/d/3.txt")
            )
        );
    }

}
