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

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 3)
@Measurement(iterations = 5, time = 3)
@Fork(value = 1, jvmArgsPrepend = {"--add-modules=jdk.incubator.vector"})
public class Benchmark {

  private int[] ints = new int[64];
  private int[] packed = new int[4];

  private int[] packOut = new int[4];
  private int[] unpackOut = new int[64];

  @Setup(Level.Trial)
  public void init() {
    ints = new int[128];
    for (int i = 0; i < 128; i++) {
      ints[i] = ThreadLocalRandom.current().nextInt(4);
    }
    Impl.packBasic(ints, packed);
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] packBasic() throws Exception {
    Impl.packBasic(ints, packOut);
    return packOut;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] unpackBasic() throws Exception {
    Impl.unpackBasic(packed, unpackOut);
    return packOut;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public int[] unpackSimd() throws Exception {
    Impl.unpackSimd(packed, unpackOut);
    return packOut;
  }
}
