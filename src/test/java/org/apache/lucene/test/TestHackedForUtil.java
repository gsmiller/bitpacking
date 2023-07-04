/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.LongStream;

import static org.junit.Assert.assertArrayEquals;

// Just a sanity that the copied ForUtil works as expected before we benchmark it.
public class TestHackedForUtil {

    final Random random = new Random();

    final ForUtil forUtil = new ForUtil();

    @Test
    public void encodeDecode1() throws Exception {
        final int bitsPerValue = 1;
        long[] packed = new long[bitsPerValue * 2];
        for (int i = 0; i < 100; i++) {
            long[] input = LongStream.range(0, 128).map(x -> random.nextLong(1 << bitsPerValue)).toArray();
            long[] copy = Arrays.copyOf(input, input.length);
            forUtil.encode(input, bitsPerValue, packed);
            long[] unpacked = new long[128];
            forUtil.decode(bitsPerValue, packed, unpacked);
            assertArrayEquals(copy, unpacked);
        }
    }

    @Test
    public void encodeDecode2() throws Exception {
        final int bitsPerValue = 2;
        long[] packed = new long[bitsPerValue * 2];
        for (int i = 0; i < 100; i++) {
            long[] input = LongStream.range(0, 128).map(x -> random.nextLong(1 << bitsPerValue)).toArray();
            long[] copy = Arrays.copyOf(input, input.length);
            forUtil.encode(input, bitsPerValue, packed);
            long[] unpacked = new long[128];
            forUtil.decode(bitsPerValue, packed, unpacked);
            assertArrayEquals(copy, unpacked);
        }
    }

    @Test
    public void encodeDecode3() throws Exception {
        final int bitsPerValue = 3;
        long[] packed = new long[bitsPerValue * 2];
        for (int i = 0; i < 100; i++) {
            long[] input = LongStream.range(0, 128).map(x -> random.nextLong(1 << bitsPerValue)).toArray();
            long[] copy = Arrays.copyOf(input, input.length);
            forUtil.encode(input, bitsPerValue, packed);
            long[] unpacked = new long[128];
            forUtil.decode(bitsPerValue, packed, unpacked);
            assertArrayEquals(copy, unpacked);
        }
    }

    @Test
    public void encodeDecode4() throws Exception {
        final int bitsPerValue = 4;
        long[] packed = new long[bitsPerValue * 2];
        for (int i = 0; i < 100; i++) {
            long[] input = LongStream.range(0, 128).map(x -> random.nextLong(1 << bitsPerValue)).toArray();
            long[] copy = Arrays.copyOf(input, input.length);
            forUtil.encode(input, bitsPerValue, packed);
            long[] unpacked = new long[128];
            forUtil.decode(bitsPerValue, packed, unpacked);
            assertArrayEquals(copy, unpacked);
        }
    }

    @Test
    public void encodeDecode5() throws Exception {
        final int bitsPerValue = 5;
        long[] packed = new long[bitsPerValue * 2];
        for (int i = 0; i < 100; i++) {
            long[] input = LongStream.range(0, 128).map(x -> random.nextLong(1 << bitsPerValue)).toArray();
            long[] copy = Arrays.copyOf(input, input.length);
            forUtil.encode(input, bitsPerValue, packed);
            long[] unpacked = new long[128];
            forUtil.decode(bitsPerValue, packed, unpacked);
            assertArrayEquals(copy, unpacked);
        }
    }
}
