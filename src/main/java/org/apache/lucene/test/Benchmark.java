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

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 3)
@Fork(value = 1, jvmArgsPrepend = {"--add-modules=jdk.incubator.vector"})
public class Benchmark {

  private final int[] ints = new int[128];
  private final int[] intsPacked = new int[16];
  private final int[] intsPackedFlex = new int[16];
  private final long[] longs = new long[128];
  private final long[] longsPacked = new long[8];

  private final int[] scratchIntsPacked = new int[16];
  private final int[] scratchIntsUnpacked = new int[128];
  private final long[] scratchLongsPacked = new long[8];
  private final long[] scratchLongsUnpacked = new long[128];

  final ForUtil forUtil = new ForUtil();

  @Setup(Level.Trial)
  public void init() throws IOException {
    for (int i = 0; i < 128; i++) {
      ints[i] = ThreadLocalRandom.current().nextInt();
    }
    for (int i = 0; i < 128; i++) {
      longs[i] = ThreadLocalRandom.current().nextLong();
    }
    SimdBitPacking.simdPack(ints, intsPacked, 4);
    SimdBitPacking.SIMD_fastPack4_Flex(ints, intsPackedFlex);
    forUtil.encode(longs, 4, longsPacked);
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode4ForUtil() throws IOException {
    forUtil.encode(longs, 4, scratchLongsPacked);
    return scratchLongsPacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ForUtil() throws IOException {
    forUtil.decode(4, longs, scratchLongsUnpacked);
    return scratchLongsUnpacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode4Simd() throws IOException {
    SimdBitPacking.simdPack(ints, scratchIntsPacked, 4);
    return scratchIntsPacked;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] decode4Simd() throws IOException {
    SimdBitPacking.simdUnpack(intsPacked, scratchIntsUnpacked, 4);
    return scratchIntsUnpacked;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode4SimdFlex() {
    SimdBitPacking.SIMD_fastPack4_Flex(ints, scratchIntsPacked);
    return scratchIntsPacked;
  }


  @org.openjdk.jmh.annotations.Benchmark
  public int[] decode4SimdFlex() {
    SimdBitPacking.SIMD_fastUnpack4_Flex(intsPackedFlex, scratchIntsUnpacked);
    return scratchIntsUnpacked;
  }
}
