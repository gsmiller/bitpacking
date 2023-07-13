package org.apache.lucene.test;

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;

import java.nio.ByteBuffer;

public class Impl {

//    private static final int MASK = (3 << 24) | (3 << 16) | (3 << 8) | 3;
    private static final int MASK = 3;
    private static final byte BYTE_MASK = 3;

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

    public static void packBasicToBytes(int[] in, byte[] out) {
        assert out.length == 16;
        int upto = 0;
        for (int shift = 0; shift < 8; shift += 2) {
            for (int i = 0; i < 16; i++) {
                out[i] = (byte) ((out[i] | (in[upto] << shift)) & 0xFF);
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
    private static final VectorSpecies<Byte> BYTE_SPECIES_128 = ByteVector.SPECIES_128;
    private static final byte[] scratchBytes = new byte[64 * 4];
    private static final int[] scatterMap = new int[16];
    static {
        int upto = 3;
        for (int i = 0; i < 16; i++) {
            scatterMap[i] = upto;
            upto += 4;
        }
    }
    private static final int[] packScatterMap = new int[]{0, 4, 8, 16};

    public static void packSimd(int[] in, byte[] out) {
        assert in.length == 64;
        assert out.length == 16;
        IntVector inVec;
        IntVector outVec;
        int upto = 0;
        for (int i = 0; i < 64; ) {
            inVec = IntVector.fromArray(SPECIES_128, in, i);
            outVec = inVec;
            i += 4;
            for (int shift = 2; shift < 8; shift += 2) {
                inVec = IntVector.fromArray(SPECIES_128, in, i);
                outVec = outVec.or(inVec.lanewise(VectorOperators.LSHL, shift));
                i += 4;
            }
            outVec.reinterpretAsBytes().intoArray(out, upto, packScatterMap, 0);
            upto += 4;
        }
    }

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

    public static void unpackSimd2(byte[] in, byte[] out) {
        assert out.length == 64 * 4; // 64 ints
        ByteVector inVec = ByteVector.fromArray(BYTE_SPECIES_128, in, 0);
        ByteVector outVec;
        int upto = 0;
        for (int shift = 0; shift < 8; shift += 2) {
            outVec = inVec.lanewise(VectorOperators.LSHR, shift).and(BYTE_MASK);
            outVec.intoArray(out, upto, scatterMap, 0);
            upto += 64;
        }
    }

}
