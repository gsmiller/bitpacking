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

  private int[] ints;
  private int[] intsOutput = new int[32];
  private long[] longs;

  final ForUtil forUtil = new ForUtil();

  @Setup(Level.Trial)
  public void init() {
    ints = new int[128];
    for (int i = 0; i < 128; i++) {
      ints[i] = ThreadLocalRandom.current().nextInt();
    }
    longs = new long[128];
    for (int i = 0; i < 128; i++) {
      longs[i] = ThreadLocalRandom.current().nextLong();
    }
  }

  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode1ForUtil() throws IOException {
     forUtil.encode(longs, 1, longs);
     return longs;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode1SimdPack() throws IOException {
    SimdBitPacking.simdPack(ints, intsOutput, 1);
    return intsOutput;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode2ForUtil() throws IOException {
    forUtil.encode(longs, 2, longs);
    return longs;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode2SimdPack() throws IOException {
    SimdBitPacking.simdPack(ints, intsOutput, 2);
    return intsOutput;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode3ForUtil() throws IOException {
    forUtil.encode(longs, 3, longs);
    return longs;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode3SimdPack() throws IOException {
    SimdBitPacking.simdPack(ints, intsOutput, 3);
    return intsOutput;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode4ForUtil() throws IOException {
    forUtil.encode(longs, 4, longs);
    return longs;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode4SimdPack() throws IOException {
    SimdBitPacking.simdPack(ints, intsOutput, 4);
    return intsOutput;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public long[] encode5ForUtil() throws IOException {
    forUtil.encode(longs, 5, longs);
    return longs;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] encode5SimdPack() throws IOException {
    SimdBitPacking.simdPack(ints, intsOutput, 5);
    return intsOutput;
  }

  // TODO decode/unpack
  // TODO: 6 .. 32

}
