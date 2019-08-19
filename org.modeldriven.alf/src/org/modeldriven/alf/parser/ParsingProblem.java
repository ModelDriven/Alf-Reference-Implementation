package org.modeldriven.alf.parser;

import org.modeldriven.alf.syntax.common.SourceProblem;

public class ParsingProblem extends SourceProblem{
    public static final String PARSING_ERROR = "parsingError";
    public ParsingProblem(String originalMessage, ParsedElement violatingElement) {
        super(PARSING_ERROR, originalMessage, violatingElement);
    }
}
