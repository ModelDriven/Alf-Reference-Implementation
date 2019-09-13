/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import static org.junit.Assert.assertEquals;
import static org.modeldriven.alf.parser.TokenSequence.tokens;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TokenSequenceTests {

    /** Simple token sequence wrapping a list of integers. */
    class SimpleTokenSequence implements TokenSequence {
        private List<Integer> tokens;
        private int eof;
        private int currentIndex = 0;

        public SimpleTokenSequence(List<Integer> tokens, int eof) {
            this.tokens = tokens;
            this.eof = eof;
        }

        private Integer safeGet(int index) {
            return tokens.size() > index ? tokens.get(index) : eof;
        }

        @Override
        public int skip() {
            return safeGet(currentIndex++);
        }

        @Override
        public int peek(int index) {
            return safeGet(currentIndex + index);
        }

    }

    @Test
    public void sanity() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 4), 0);
        assertEquals(1, source.peek(0));
        assertEquals(2, source.peek(1));
        assertEquals(0, source.peek(4));
        assertEquals(1, source.peek(0));
        assertEquals(2, source.peek(1));
        assertEquals(1, source.skip());
        
        assertEquals(2, source.peek(0));
        assertEquals(3, source.peek(1));
        assertEquals(2, source.skip());
        
        assertEquals(3, source.peek(0));
        assertEquals(4, source.peek(1));
        assertEquals(3, source.skip());
        
        assertEquals(4, source.peek(0));
        assertEquals(0, source.peek(1));
        assertEquals(4, source.skip());
        
        assertEquals(0, source.peek(0));
        assertEquals(0, source.peek(1));
        assertEquals(0, source.skip());
    }

    @Test
    public void skipTo() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 4), 0);
        source.skipToOrPast(false, tokens(3), tokens(4));
        assertEquals(3, source.peek(1));
    }

    @Test
    public void skipPast() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 4), 0);
        source.skipToOrPast(false, tokens(4), tokens(2));
        assertEquals(3, source.peek(1));
    }

    @Test
    public void skipPastIgnoringCurrent() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 2, 5), 0);
        source.skip(1);
        assertEquals(2, source.peek(0));
        assertEquals(3, source.peek(1));
        source.skipToOrPast(true, tokens(4), tokens(2));
        assertEquals(5, source.peek(1));
    }

    @Test
    public void skipPastSameAsCurrent() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 2, 5), 0);
        source.skip(1);
        assertEquals(2, source.peek(0));
        assertEquals(3, source.peek(1));
        source.skipToOrPast(false, tokens(4), tokens(2));
        assertEquals(5, source.peek(1));
    }

    @Test
    public void skipToUnknown() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 4), 0);
        source.skipToOrPast(false, tokens(5), tokens());
        assertEquals(0, source.peek(1));
    }

    @Test
    public void skipPastUnknown() {
        TokenSequence source = new SimpleTokenSequence(Arrays.asList(1, 2, 3, 4), 0);
        source.skipToOrPast(false, tokens(), tokens(0));
        assertEquals(0, source.peek(0));
    }

}
