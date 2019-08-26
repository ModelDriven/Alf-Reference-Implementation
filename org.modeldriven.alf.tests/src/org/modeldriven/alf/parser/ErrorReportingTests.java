package org.modeldriven.alf.parser;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;

public class ErrorReportingTests {
    @Test
    public void format() {
        ParsedElement element = new StringLiteralExpression();
        element.setParserInfo("foo.alf", 1, 2, 1, 10);
        SourceProblem problem = new ParsingProblem("unknown.key", element);
        assertEquals("[1:2] unknown.key", problem.getErrorMessage());
    }
}
