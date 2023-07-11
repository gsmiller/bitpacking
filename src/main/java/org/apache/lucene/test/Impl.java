package org.apache.lucene.test;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

public class Impl {

//    private static final int MASK = (3 << 24) | (3 << 16) | (3 << 8) | 3;
    private static final int MASK = 3;

    public static void packBasic(int[] in, int[] out) {
        assert out.length == 4;
        int upto = 0;
        for (int shift = 0; shift < 32; shift += 2) {
            for (int i = 0; i < 4; i++) {
                out[i] = out[i] | (in[upto] << shift);
                upto++;
            }
        }
    }

    public static void unpackBasic(int[] in, int[] out) {
        assert out.length == 64;
        int upto = 0;
        for (int shift = 0; shift < 32; shift += 2) {
            shiftAndMask(in, out, upto, shift);
            upto += 4;
//            for (int i = 0; i < 4; i++) {
//                out[upto] = (in[i] >>> shift) & MASK;
//                upto++;
//            }
        }
    }

    private static void shiftAndMask(int[] in, int[] out, int off, int shift) {
        out[off] = (in[0] >>> shift) & MASK;
        out[off + 1] = (in[1] >>> shift) & MASK;
        out[off + 2] = (in[2] >>> shift) & MASK;
        out[off + 3] = (in[3] >>> shift) & MASK;
    }

    private static final VectorSpecies<Integer> SPECIES_128 = IntVector.SPECIES_128;

    public static void unpackSimd(int[] in, int[] out) {
        assert out.length == 64;
        IntVector inVec = IntVector.fromArray(SPECIES_128, in, 0);

        IntVector outVec;
        int upto = 0;
        for (int shift = 0; shift < 32; shift += 2) {
            outVec = inVec.lanewise(VectorOperators.LSHR, shift).and(MASK);
            outVec.intoArray(out, upto);
            upto += 4;
        }
    }
}
