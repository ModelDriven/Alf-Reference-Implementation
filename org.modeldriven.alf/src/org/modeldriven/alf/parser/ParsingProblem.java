package org.modeldriven.alf.parser;

import org.modeldriven.alf.syntax.common.SourceProblem;

public class ParsingProblem extends SourceProblem {
    public ParsingProblem(String problemKey, ParsedElement violatingElement) {
        super(problemKey, violatingElement);
    }
}
