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
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;

public class TestImpl {
    final long seed = new Random().nextLong();
//    final long seed = 3997060971600987478L;

//    @Test
    public void packUnpackBasic() {
        System.out.println("Seed: " + seed);
        Random random = new Random(seed);
        int[] packed = new int[4];
        int[] unpacked = new int[64];
        for (int iter = 0; iter < 100; iter++) {
            int[] input = IntStream.range(0, 64).map(x -> random.nextInt(4)).toArray();
            int[] copy = Arrays.copyOf(input, input.length);
            Arrays.fill(packed, 0);
            Arrays.fill(unpacked, 0);
            Impl.packBasic(input, packed);
            Impl.unpackBasic(packed, unpacked);
            assertArrayEquals(input, unpacked);
            assertArrayEquals(input, copy);
        }
    }

//    @Test
    public void unpackSimd() {
        System.out.println("Seed: " + seed);
        Random random = new Random(seed);
        int[] packed = new int[4];
        int[] unpacked = new int[64];
        for (int iter = 0; iter < 100; iter++) {
            int[] input = IntStream.range(0, 64).map(x -> random.nextInt(4)).toArray();
            int[] copy = Arrays.copyOf(input, input.length);
            Arrays.fill(packed, 0);
            Arrays.fill(unpacked, 0);
            Impl.packBasic(input, packed);
            Impl.unpackSimd(packed, unpacked);
            assertArrayEquals(input, unpacked);
            assertArrayEquals(input, copy);
        }
    }
}
