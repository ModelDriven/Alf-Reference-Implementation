package org.modeldriven.alf.parser;

import org.modeldriven.alf.syntax.common.SourceProblem;

public class LexicalProblem extends SourceProblem {

    public LexicalProblem(String message, ParsedElement violatingElement) {
        super(message, violatingElement);
    }

}
