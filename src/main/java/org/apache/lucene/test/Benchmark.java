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


  private static final long MASK = 0x0FL;
  private static final long MASK84 = 0X0F0F0F0F0F0F0F0FL;

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ModifiedForUtil() throws IOException {
    int outUpto = 0;
    for (int j = 0; j < 8; j++) {
      long l = longs[j];
      scratchLongsUnpacked[outUpto] = (l >>> 60) & MASK;
      scratchLongsUnpacked[outUpto + 1] = (l >>> 52) & MASK;
      scratchLongsUnpacked[outUpto + 2] = (l >>> 44) & MASK;
      scratchLongsUnpacked[outUpto + 3] = (l >>> 36) & MASK;
      scratchLongsUnpacked[outUpto + 4] = (l >>> 28) & MASK;
      scratchLongsUnpacked[outUpto + 5] = (l >>> 20) & MASK;
      scratchLongsUnpacked[outUpto + 6] = (l >>> 12) & MASK;
      scratchLongsUnpacked[outUpto + 7] = (l >>> 4) & MASK;
      outUpto += 8;
    }

    for (int j = 0; j < 8; j++) {
      long l = longs[j];
      scratchLongsUnpacked[outUpto] = (l >>> 56) & MASK;
      scratchLongsUnpacked[outUpto + 1] = (l >>> 48) & MASK;
      scratchLongsUnpacked[outUpto + 2] = (l >>> 40) & MASK;
      scratchLongsUnpacked[outUpto + 3] = (l >>> 32) & MASK;
      scratchLongsUnpacked[outUpto + 4] = (l >>> 24) & MASK;
      scratchLongsUnpacked[outUpto + 5] = (l >>> 16) & MASK;
      scratchLongsUnpacked[outUpto + 6] = (l >>> 8) & MASK;
      scratchLongsUnpacked[outUpto + 7] = l & MASK;
      outUpto += 8;
    }

    return scratchLongsUnpacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ModifiedForUtil2() throws IOException {
    int outUpto = 0;
    for (int shift = 60; shift >= 0; shift -= 4) {
      for (int i = 0; i < 8; i++) {
        scratchLongsUnpacked[outUpto + i] = (longsPacked[i] >>> shift) & MASK;
      }
      outUpto += 8;
    }

    return scratchLongsUnpacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ModifiedForUtil3() throws IOException {
    for (int i = 0; i < 8; ++i) {
      long l = longsPacked[i];
      scratchLongsUnpacked[i] = (l >>> 60) & MASK;
      scratchLongsUnpacked[i + 8] = (l >>> 56) & MASK;
      scratchLongsUnpacked[i + 16] = (l >>> 52) & MASK;
      scratchLongsUnpacked[i + 24] = (l >>> 48) & MASK;
      scratchLongsUnpacked[i + 32] = (l >>> 44) & MASK;
      scratchLongsUnpacked[i + 40] = (l >>> 40) & MASK;
      scratchLongsUnpacked[i + 48] = (l >>> 36) & MASK;
      scratchLongsUnpacked[i + 56] = (l >>> 32) & MASK;
      scratchLongsUnpacked[i + 64] = (l >>> 28) & MASK;
      scratchLongsUnpacked[i + 72] = (l >>> 24) & MASK;
      scratchLongsUnpacked[i + 80] = (l >>> 20) & MASK;
      scratchLongsUnpacked[i + 88] = (l >>> 16) & MASK;
      scratchLongsUnpacked[i + 96] = (l >>> 12) & MASK;
      scratchLongsUnpacked[i + 104] = (l >>> 8) & MASK;
      scratchLongsUnpacked[i + 112] = (l >>> 4) & MASK;
      scratchLongsUnpacked[i + 120] = l & MASK;
    }

    return scratchLongsUnpacked;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ModifiedForUtil4() throws IOException {
    for (int i = 0; i < 8; ++i) {
      long l = longsPacked[i];
      scratchLongsUnpacked[i] = (l >>> 60) & MASK;
      scratchLongsUnpacked[i + 8] = (l >>> 56) & MASK;
      scratchLongsUnpacked[i + 16] = (l >>> 52) & MASK;
      scratchLongsUnpacked[i + 24] = (l >>> 48) & MASK;
      scratchLongsUnpacked[i + 32] = (l >>> 44) & MASK;
      scratchLongsUnpacked[i + 40] = (l >>> 40) & MASK;
      scratchLongsUnpacked[i + 48] = (l >>> 36) & MASK;
      scratchLongsUnpacked[i + 56] = (l >>> 32) & MASK;
      scratchLongsUnpacked[i + 64] = (l >>> 28) & MASK;
      scratchLongsUnpacked[i + 72] = (l >>> 24) & MASK;
      scratchLongsUnpacked[i + 80] = (l >>> 20) & MASK;
      scratchLongsUnpacked[i + 88] = (l >>> 16) & MASK;
      scratchLongsUnpacked[i + 96] = (l >>> 12) & MASK;
      scratchLongsUnpacked[i + 104] = (l >>> 8) & MASK;
      scratchLongsUnpacked[i + 112] = (l >>> 4) & MASK;
      scratchLongsUnpacked[i + 120] = l & MASK;
    }

    return scratchLongsUnpacked;
  }

  private final long[] tmp = new long[16];

  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ForUtilReplica() throws IOException {
    for (int i = 0; i < 8; ++i) {
      tmp[i] = (longsPacked[i] >>> 4) & MASK84;
    }
    for (int i = 0; i < 8; ++i) {
      tmp[8 + i] = longsPacked[i] & MASK84;
    }
    for (int i = 0; i < 16; ++i) {
      long l = tmp[i];
      scratchLongsUnpacked[i] = (l >>> 56) & 0xFFL;
      scratchLongsUnpacked[16 + i] = (l >>> 48) & 0xFFL;
      scratchLongsUnpacked[32 + i] = (l >>> 40) & 0xFFL;
      scratchLongsUnpacked[48 + i] = (l >>> 32) & 0xFFL;
      scratchLongsUnpacked[64 + i] = (l >>> 24) & 0xFFL;
      scratchLongsUnpacked[80 + i] = (l >>> 16) & 0xFFL;
      scratchLongsUnpacked[96 + i] = (l >>> 8) & 0xFFL;
      scratchLongsUnpacked[112 + i] = l & 0xFFL;
    }

//    int base = 0;
//    for (int shift = 56; shift >= 0; shift -= 8) {
//      for (int i = 0; i < 16; ++i) {
//        scratchLongsUnpacked[base + i] = (tmp[i] >>> shift) & 0xFFL;
//      }
//      base += 16;
//    }

//    shiftLongs(longsPacked, 8, longs, 0, 4, MASK84);
//    shiftLongs(longsPacked, 8, longs, 8, 0, MASK84);
//    expand8(longs);

    return scratchLongsUnpacked;
  }

  private static void shiftLongs(long[] a, int count, long[] b, int bi, int shift, long mask) {
    for (int i = 0; i < count; ++i) {
      b[bi + i] = (a[i] >>> shift) & mask;
    }
  }

  private static void expand8(long[] arr) {
    for (int i = 0; i < 16; ++i) {
      long l = arr[i];
      arr[i] = (l >>> 56) & 0xFFL;
      arr[16 + i] = (l >>> 48) & 0xFFL;
      arr[32 + i] = (l >>> 40) & 0xFFL;
      arr[48 + i] = (l >>> 32) & 0xFFL;
      arr[64 + i] = (l >>> 24) & 0xFFL;
      arr[80 + i] = (l >>> 16) & 0xFFL;
      arr[96 + i] = (l >>> 8) & 0xFFL;
      arr[112 + i] = l & 0xFFL;
    }
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode4ForUtil() throws IOException {
    forUtil.encode(longs, 4, scratchLongsPacked);
    return scratchLongsPacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public long[] decode4ForUtil() throws IOException {
    forUtil.decode(4, longsPacked, scratchLongsUnpacked);
    return scratchLongsUnpacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode4Simd() throws IOException {
    SimdBitPacking.simdPack(ints, scratchIntsPacked, 4);
    return scratchIntsPacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] decode4Simd() throws IOException {
    SimdBitPacking.simdUnpack(intsPacked, scratchIntsUnpacked, 4);
    return scratchIntsUnpacked;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode4SimdFlex() {
    SimdBitPacking.SIMD_fastPack4_Flex(ints, scratchIntsPacked);
    return scratchIntsPacked;
  }


//  @org.openjdk.jmh.annotations.Benchmark
  public int[] decode4SimdFlex() {
    SimdBitPacking.SIMD_fastUnpack4_Flex(intsPackedFlex, scratchIntsUnpacked);
    return scratchIntsUnpacked;
  }
}
