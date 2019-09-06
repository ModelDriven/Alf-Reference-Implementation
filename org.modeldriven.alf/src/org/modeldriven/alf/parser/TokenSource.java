package org.modeldriven.alf.parser;

import java.util.BitSet;


/** 
 * A protocol for navigating token sequences. 
 * 
 * For a sequence 1, 2, 3, 4:
 * 
 * assert peek(0) == 1
 * assert skip() == 1
 * assert peek(0) == 2
 * assert peek(1) == 3
 * assert skip() == 2
 * assert peek(0) == 3
 * assert peek(100) == 0
 * 
 */
public interface TokenSource {
    int skip();

    /** Skips multiple times. */
    default int skip(int count) {
        int result = -1;
        for (int i = 0; i < count; i++) {
            result = skip();
        }
        return result;
    }
    
    default int eof() {
        return 0;
    }

    /**
     * Returns the next n-th element.
     *  
     * @param offset if 0, returns the current element 
     * @return 
     */
    int peek(int offset);

    /**
     * @see #tokens(BitSet, int...)
     */
    public static BitSet tokens(int... tokenIds) {
        return tokens(new BitSet(), tokenIds);
    }

    /**
     * Helper method to build up a set of token ids.
     * 
     * @param existing
     *            an existing set of token ids
     * @param newTokenIds
     *            token ids to add
     * @return the provided (and modified) set of token ids
     */
    public static BitSet tokens(BitSet existing, int... newTokenIds) {
        for (int i = 0; i < newTokenIds.length; i++) {
            existing.set(newTokenIds[i]);
        }
        return existing;
    }
    
    public default boolean skipToOrPast(BitSet skipTo, BitSet skipPast) {
        return skipToOrPast(false, skipTo, skipPast);
    }
    
    /***
     * Skips tokens until a target token is found.
     * 
     * @param skipCurrent whether the current token should be skipped first
     * @param skipTo tokens to skip up to
     * @param skipPast tokens to skip after
     * @return whether one of the target tokens was actually seen (otherwise we reached EOF)
     */
    public default boolean skipToOrPast(boolean skipCurrent, BitSet skipTo, BitSet skipPast) {
        BitSet stop = new BitSet();
        // stop at EOF as well
        int eof = eof();
        stop.set(eof);
        stop.or(skipTo);
        stop.or(skipPast);
        int tokenId;
        if (skipCurrent) {
            skip();
        }
        while (!stop.get(tokenId = peek(1))) {
            skip();
        }
        if (skipPast.get(tokenId)) {
            skip();
        }
        return peek(0) != eof;
    }

}
