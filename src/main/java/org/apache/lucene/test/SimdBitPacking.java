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

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
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
public class SimdBitPacking {

  // simdpackwithoutmask(const uint32_t *in, __m128i *out, const uint32_t bit) {
  /* Assumes that integers fit in the prescribed number of bits */
  static void simdPack(int[] input, int[] output, int bit) {
    switch (bit) {
      case 1: SIMD_fastPack1(input, output); return;
      case 2: SIMD_fastPack2(input, output); return;
      case 3: SIMD_fastPack3(input, output); return;
      case 4: SIMD_fastPack4(input, output); return;
      case 5: SIMD_fastPack5(input, output); return;
      // TODO 6 .. 32
      default : throw new UnsupportedOperationException();
    }
  }

  // void simdunpack(const __m128i *in, uint32_t *out, const uint32_t bit) {
  static void simdUnpack(int[] input, int[] output, int bit) {
    switch (bit) {
      case 1: SIMD_fastUnpack1(input, output); return;
      case 2: SIMD_fastUnpack2(input, output); return;
      case 3: SIMD_fastUnpack3(input, output); return;
      case 4: SIMD_fastUnpack4(input, output); return;
      case 5: SIMD_fastUnpack5(input, output); return;
      // TODO 6 .. 32
      default : throw new UnsupportedOperationException();
    }
  }

  private static final VectorSpecies<Integer> SPECIES_128 = IntVector.SPECIES_128;

  // SIMD_fastpackwithoutmask1_32
  static void SIMD_fastPack1(int[] input, int[] output) {
    int inOff = 0;
    IntVector outVec;
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);

    outVec = inVec;
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 1).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 2).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 3).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 4).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 5).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 6).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 7).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 8).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 9).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 10).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 11).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 12).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 13).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 14).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 15).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 16).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 17).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 18).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 19).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 20).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 21).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 22).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 23).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 24).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 25).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 26).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 27).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 28).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 29).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 30).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 31).or(outVec);
    outVec.intoArray(output, 0);
  }

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

  // SIMD_fastpackwithoutmask3_32
  static void SIMD_fastPack3(int[] input, int[] output) {
    int inOff = 0;
    int outOff = 0;

    IntVector outVec;
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, inOff);

    outVec = inVec;
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 3).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 6).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 9).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 12).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 15).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 18).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 21).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 24).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 27).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 30).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    outVec = inVec.lanewise(VectorOperators.LSHR, 3 - 1);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 1).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 4).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 7).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 10).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 13).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 16).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 19).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 22).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 25).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 28).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 31).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    outVec = inVec.lanewise(VectorOperators.LSHR, 3 - 2);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 2).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 5).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 8).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 11).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 14).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 17).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 20).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 23).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 26).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 29).or(outVec);
    outVec.intoArray(output, outOff);
  }

  // SIMD_fastpackwithoutmask4_32
  static void SIMD_fastPack4(int[] input, int[] output) {
    int inOff = 0;
    int outOff = 0;
    IntVector outVec;
    IntVector inVec;

    for (int i = 0; i < 4; i++) {
      inVec = IntVector.fromArray(SPECIES_128, input, inOff);
      outVec = inVec;

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 4);
      outVec = inVec.lanewise(VectorOperators.LSHL, 4).or(outVec);

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 8);
      outVec = inVec.lanewise(VectorOperators.LSHL, 8).or(outVec);

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 12);
      outVec = inVec.lanewise(VectorOperators.LSHL, 12).or(outVec);

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 16);
      outVec = inVec.lanewise(VectorOperators.LSHL, 16).or(outVec);

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 20);
      outVec = inVec.lanewise(VectorOperators.LSHL, 20).or(outVec);

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 24);
      outVec = inVec.lanewise(VectorOperators.LSHL, 24).or(outVec);

      inVec = IntVector.fromArray(SPECIES_128, input, inOff + 28);
      outVec = inVec.lanewise(VectorOperators.LSHL, 28).or(outVec);

      outVec.intoArray(output, outOff);
      outOff+=4;
      inOff+=32;
    }
  }

  // SIMD_fastpackwithoutmask5_32
  static void SIMD_fastPack5(int[] input, int[] output) {
    int inOff = 0;
    int outOff = 0;

    IntVector outVec;
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, inOff);

    outVec = inVec;
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 5).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 10).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 15).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 20).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 25).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 30).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    outVec = inVec.lanewise(VectorOperators.LSHR, 5 - 3);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 3).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 8).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 13).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 18).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 23).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 28).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    outVec = inVec.lanewise(VectorOperators.LSHR, 5 - 1);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 1).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 6).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 11).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 16).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 21).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 26).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 31).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    outVec = inVec.lanewise(VectorOperators.LSHR, 5 - 4);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 4).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 9).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 14).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 19).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 24).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 29).or(outVec);
    outVec.intoArray(output, outOff);
    outOff+=4;
    outVec = inVec.lanewise(VectorOperators.LSHR, 5 - 2);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 2).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 7).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 12).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 17).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 22).or(outVec);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHL, 27).or(outVec);
    outVec.intoArray(output, outOff);
  }

  // __SIMD_fastunpack1_32
  static void SIMD_fastUnpack1(int[] input, int[] output) {
    IntVector inVec1 = IntVector.fromArray(SPECIES_128, input, 0);
    IntVector inVec2 = inVec1;
    IntVector outVec1, outVec2, outVec3, outVec4;
    final int mask = 1;
    int shift = 0;

    for (int i = 0; i < 8; i++) {
      outVec1 = inVec1.lanewise(VectorOperators.LSHR, shift++).and(mask);
      outVec2 = inVec2.lanewise(VectorOperators.LSHR, shift++).and(mask);
      outVec3 = inVec1.lanewise(VectorOperators.LSHR, shift++).and(mask);
      outVec4 = inVec2.lanewise(VectorOperators.LSHR, shift++).and(mask);
      outVec1.intoArray(output, i * 16 + 0);
      outVec2.intoArray(output, i * 16 + 4);
      outVec3.intoArray(output, i * 16 + 8);
      outVec4.intoArray(output, i * 16 + 12);
    }
  }

  // __SIMD_fastunpack2_32
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

  // __SIMD_fastunpack3_32
  static void SIMD_fastUnpack3(int[] input, int[] output) {
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);
    IntVector outVec;
    int inOff = 0;
    int outOff = 0;
    final int mask = (1 << 3) - 1;

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff);

    outVec = inVec.lanewise(VectorOperators.LSHR, 3).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 6).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 9).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 15).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 18).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 21).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 27).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 30);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);
    outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, 3 - 1).and(mask));
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 1).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 7).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 10).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 13).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 19).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 22).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 25).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 31);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);
    outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, 3 - 2).and(mask));
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 2).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 5).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 11).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 14).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 17).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 23).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 26).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 29).and(mask);
    outVec.intoArray(output, outOff+=4);
  }

  // __SIMD_fastunpack4_32
  static void SIMD_fastUnpack4(int[] input, int[] output) {
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);
    IntVector outVec;
    int inOff = 0;
    int outOff = 0;
    final int mask = (1 << 4) - 1;

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);

    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);

    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);

    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28).and(mask);
    outVec.intoArray(output, outOff+=4);
  }

  // __SIMD_fastunpack5_32
  static void SIMD_fastUnpack5(int[] input, int[] output) {
    IntVector inVec = IntVector.fromArray(SPECIES_128, input, 0);
    IntVector outVec;
    int inOff = 0;
    int outOff = 0;
    final int mask = (1 << 5) - 1;

    outVec = inVec.and(mask);
    outVec.intoArray(output, outOff);

    outVec = inVec.lanewise(VectorOperators.LSHR, 5).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 10).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 15).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 20).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 25).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 30);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);
    outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, 5 - 3).and(mask));
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 3).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 8).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 13).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 18).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 23).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 28);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);
    outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, 5 - 1).and(mask));
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 1).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 6).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 11).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 16).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 21).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 26).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 31);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);
    outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, 5 - 4).and(mask));
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 4).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 9).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 14).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 19).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 24).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 29).and(mask);
    inVec = IntVector.fromArray(SPECIES_128, input, inOff+=4);
    outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, 5 - 2).and(mask));
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 2).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 7).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 12).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 17).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 22).and(mask);
    outVec.intoArray(output, outOff+=4);

    outVec = inVec.lanewise(VectorOperators.LSHR, 27).and(mask);
    outVec.intoArray(output, outOff+=4);
  }
}