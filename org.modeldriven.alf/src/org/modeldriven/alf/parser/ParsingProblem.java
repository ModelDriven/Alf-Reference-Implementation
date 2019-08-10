package org.modeldriven.alf.parser;

import org.modeldriven.alf.syntax.common.SourceProblem;

public class ParsingProblem extends SourceProblem{
    public static final String PARSING_ERROR = "parsingError";
    public ParsingProblem(String problemName, ParsedElement violatingElement) {
        super(problemName, violatingElement);
    }
    public ParsingProblem(ParsedElement violatingElement) {
        this(PARSING_ERROR, violatingElement);
    }
}
