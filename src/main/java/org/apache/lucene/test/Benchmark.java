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

  private int[] encodeIn = new int[128];
  private int[] encodeOut1 = new int[8];
  private byte[] encodeOut2 = new byte[32];
  private int[] decodeIn1 = new int[8];
  private byte[] decodeIn2 = new byte[32];
  private int[] decodeOut = new int[128];
  private int base;

  private int[] ints;
  private int[] intsOutput = new int[32];
  private long[] longs;

  final ForUtil forUtil = new ForUtil();

  @Setup(Level.Trial)
  public void init() {
    ints = new int[128];
    for (int i = 0; i < 128; i++) {
      ints[i] = ThreadLocalRandom.current().nextInt(100);
    }
    longs = new long[128];
    for (int i = 0; i < 128; i++) {
      longs[i] = ThreadLocalRandom.current().nextLong();
    }
    encodeIn = ints;
    SimdBitPacking.simdPack(encodeIn, decodeIn1, 2);
//    SimdBitPacking2.simdPack(encodeIn, decodeIn2, 2);
    base = ThreadLocalRandom.current().nextInt(1000);
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] decodePrefixSum2() throws IOException {
    SimdBitPacking2.SIMD_fastUnpackAndPrefixDecode2(base, encodeIn, encodeOut1);
    return encodeOut1;
  }

//  @org.openjdk.jmh.annotations.Benchmark
//  public long[] encode2ForUtil() throws IOException {
//    forUtil.encode(longs, 2, longs);
//    return longs;
//  }

//  @org.openjdk.jmh.annotations.Benchmark
//  public int[] encode2SimdPack() throws IOException {
//    SimdBitPacking.simdPack(encodeIn, encodeOut1, 2);
//    return encodeOut1;
//  }

//  @org.openjdk.jmh.annotations.Benchmark
//  public byte[] encode2SimdPack2() throws IOException {
//    SimdBitPacking2.simdPack(encodeIn, encodeOut2, 2);
//    return encodeOut2;
//  }

//  @org.openjdk.jmh.annotations.Benchmark
//  public int[] decode2SimdPack() throws IOException {
//    SimdBitPacking.simdUnpack(decodeIn1, decodeOut, 2);
//    return decodeOut;
//  }

//  @org.openjdk.jmh.annotations.Benchmark
//  public int[] decode2SimdPack2() throws IOException {
////    SimdBitPacking2.simdUnpack(decodeIn2, decodeOut, 2);
//    return decodeOut;
//  }
}
