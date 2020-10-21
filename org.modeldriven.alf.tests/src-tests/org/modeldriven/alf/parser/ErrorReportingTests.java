/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import static org.junit.jupiter.api.Assertions.*;

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