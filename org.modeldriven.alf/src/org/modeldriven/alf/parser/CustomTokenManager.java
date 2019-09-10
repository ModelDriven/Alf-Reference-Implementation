/*******************************************************************************
 * Copyright 2019 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.parser;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * An optional specialization of token manager that reports token manager errors to a consumer, 
 * instead of throwing them.
 */
public class CustomTokenManager extends ParserImplTokenManager {
    private Consumer<TokenMgrError> lexicalProblemReporter;

    public CustomTokenManager(SimpleCharStream stream, Consumer<TokenMgrError> lexicalProblemReporter) {
        super(stream);
        this.lexicalProblemReporter = lexicalProblemReporter;
    }

    @Override
    public Token getNextToken() {
        try {
            return super.getNextToken();
        } catch (TokenMgrError e) {
            lexicalProblemReporter.accept(e);
            // unrecoverable error - consume remaining input 
            // to avoid spurious errors afterwards
            try {
                while (true) input_stream.readChar();
            } catch (IOException e1) {
                // expected: thrown by readChar when hitting EOF
            }
            jjmatchedKind = EOF;
            Token placeholder = jjFillToken();
            return placeholder;
        }
    }

}
