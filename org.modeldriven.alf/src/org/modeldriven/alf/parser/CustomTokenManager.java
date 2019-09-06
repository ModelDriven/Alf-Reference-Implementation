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
            try {
                input_stream.readChar();
            } catch (IOException e1) {
                //
            }
            jjmatchedKind = EOL;
            Token placeholder = jjFillToken();
            return placeholder;
        }
    }

}
