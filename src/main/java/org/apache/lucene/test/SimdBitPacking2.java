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

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.Vector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorShuffle;
import jdk.incubator.vector.VectorSpecies;

/**
 * Supports packing and unpacking integers arrays of 128 elements, using 128
 * bit vectors with 4 int lanes.
 * Based on https://github.com/lemire/simdcomp/blob/master/src/simdbitpacking.c
 *
 * So far only:
 * a) 1 - 5 bits per element value are supported. 6 - 32 just need to be implemented.
 * b) The code is a direct port of the C variant. Likely there are better shapes of code, less
 *    unrolling, etc, that might optimize better on the JVM. Doing so should be driven by
 *    benchmarks. For now, a direct port seems like a good start.
 */
public class SimdBitPacking2 {
  private static final VectorSpecies<Integer> SPECIES_128 = IntVector.SPECIES_128;
  private static final VectorSpecies<Byte> BYTE_SPECIES_128 = ByteVector.SPECIES_128;

  // SIMD_fastpackwithoutmask2_32
  static void SIMD_fastPack2(int[] input, int[] output) {
    int inOff = 0;
    int outOff = 0;
    IntVector outVec;
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);

    outVec = inVec;
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 2).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 4).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 6).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 8).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 10).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 12).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 14).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 16).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 18).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 20).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 22).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 24).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 26).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 28).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 30).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec;
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 2).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 4).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 6).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 8).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 10).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 12).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 14).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 16).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 18).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 20).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 22).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 24).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 26).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 28).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 30).or(outVec);
    outVec.intoArray(output, outOff);
  }

  // __SIMD_fastunpack2_32
  static void SIMD_fastUnpackAndPrefixDecode2(int base, int[] input, int[] output) {
    IntVector outVec;
    final int mask = (1 << 2) - 1;

    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);

    outVec = inVec.and(mask);
    outVec = outVec.add(outVec.unslice(1));
    outVec = outVec.add(outVec.unslice(2));
    outVec = outVec.add(IntVector.broadcast(SPECIES_128, base));
    outVec.intoArray(output, 0);

    int i = 4;
    int shift = 2;
    for (; i < 64; i += 4, shift += 2) {
      outVec = inVec.lanewise(VectorOperators.LSHR, shift).and(mask);
      outVec = outVec.add(outVec.unslice(1));
      outVec = outVec.add(outVec.unslice(2));
      outVec = outVec.add(IntVector.broadcast(SPECIES_128, output[i - 1]));
      outVec.intoArray(output, i);
    }

    inVec = IntVector.fromArray(SPECIES_128, input, 4);

    shift = 0;
    for (; i < 128; i += 4, shift += 2) {
      outVec = inVec.lanewise(VectorOperators.LSHR, shift).and(mask);
      outVec = outVec.add(outVec.unslice(1));
      outVec = outVec.add(outVec.unslice(2));
      outVec = outVec.add(IntVector.broadcast(SPECIES_128, output[i - 1]));
      outVec.intoArray(output, i);
    }
  }

  static void SIMD_fastUnpack2(int[] input, int[] output) {
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);
    IntVector outVec;
    int inOff = 0;
    int outOff = 0;
    final int mask = (1 << 2) - 1;

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff);

    outVec = inVec.lanewise(VectorOperators.LSHR, 2).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 6).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 10).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 14).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 18).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 22).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 26).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 30).and(mask);
    outVec.intoArray(output, outOff+=4);

    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 2).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 6).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 10).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 14).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 18).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 22).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 26).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 30).and(mask);
    outVec.intoArray(output, outOff+=4);
  }

//  static void SIMD_fastUnpack2(byte[] input, int[] output) {
//    byte[] byteOut = new byte[128];
//    ByteVector inVec = ByteVector.fromArray(BYTE_SPECIES_128, input, 0);
//    ByteVector outVec;
//    int outOff = 0;
//    final byte mask = (1 << 2) - 1;
//
//    outVec = inVec.and(mask);
//    outVec.intoArray(byteOut, outOff);
//
//    outVec = inVec.lanewise(VectorOperators.LSHR, 2).and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    outVec = inVec.lanewise(VectorOperators.LSHR, 6).and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    inVec = ByteVector.fromArray(BYTE_SPECIES_128, input, 16);
//
//    outVec = inVec.and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    outVec = inVec.lanewise(VectorOperators.LSHR, 2).and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    outVec = inVec.lanewise(VectorOperators.LSHR, 6).and(mask);
//    outVec.intoArray(byteOut, outOff+=16);
//
//    for (int i = 0; i < 128; i++) {
//      output[i] = byteOut[i];
//    }
//  }
}