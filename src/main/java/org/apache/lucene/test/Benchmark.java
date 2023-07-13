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
import org.openjdk.jmh.infra.Blackhole;

import java.nio.ByteBuffer;
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
  private int[] packedInts = new int[4];
  private byte[] packedBytes = new byte[16];

  private int[] packOutInts = new int[4];
  private byte[] packedOutBytes = new byte[16];
  private int[] unpackOutInts = new int[64];
  private byte[] unpackOutBytes = new byte[64 * 4];

  private ByteBuffer buff = ByteBuffer.wrap(unpackOutBytes);

  private Blackhole bh = new Blackhole("Today's password is swordfish. I understand instantiating Blackholes directly is dangerous.");

  @Setup(Level.Trial)
  public void init() {
    ints = new int[128];
    for (int i = 0; i < 128; i++) {
      ints[i] = ThreadLocalRandom.current().nextInt(4);
    }
    Impl.packBasic(ints, packedInts);
    Impl.packBasicToBytes(ints, packedBytes);
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] packBasic() throws Exception {
    Impl.packBasic(ints, packOutInts);
    return packOutInts;
  }

//  @org.openjdk.jmh.annotations.Benchmark
  public int[] unpackBasic() throws Exception {
    Impl.unpackBasic(packedInts, unpackOutInts);
    return packOutInts;
  }

  @org.openjdk.jmh.annotations.Benchmark
  public void unpackSimd() throws Exception {
    Impl.unpackSimd(packedInts, unpackOutInts);
//    for (int i = 0; i < 64; i++) {
//      bh.consume(unpackOutInts[i]);
//    }
  }

  @org.openjdk.jmh.annotations.Benchmark
  public void unpackSimdByte() throws Exception {
    Impl.unpackSimd2(packedBytes, unpackOutBytes);
//    buff.rewind();
//    for (int i = 0; i < 64; i++) {
//      bh.consume(buff.getInt());
//    }
  }
}
