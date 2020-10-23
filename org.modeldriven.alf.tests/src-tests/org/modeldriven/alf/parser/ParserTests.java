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
import static org.modeldriven.alf.parser.Helper.assertIsA;
import static org.modeldriven.alf.parser.Helper.ensureNoProblems;
import static org.modeldriven.alf.parser.Helper.newParser;
import static org.modeldriven.alf.parser.Helper.parseExpression;
import static org.modeldriven.alf.parser.Helper.parseStatementSequence;
import static org.modeldriven.alf.parser.Helper.parseUnit;
import static org.modeldriven.alf.parser.Helper.safeGet;
import static org.modeldriven.alf.parser.Helper.single;

import org.junit.jupiter.api.Test;
import org.modeldriven.alf.syntax.expressions.ArithmeticExpression;
import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.LiteralExpression;
import org.modeldriven.alf.syntax.expressions.NameExpression;
import org.modeldriven.alf.syntax.expressions.PositionalTuple;
import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ParserTests {
    /**
     * A simple happy path test to exercise the parsing of a simple packaged activity.
     */
    @Test
    void packagedActivity() {
        String model =
            "package SimplePackage {\n" +
            "public activity Hello() {\n" +
            "  WriteLine(\"Hello World!\");\n" +
            "}\n" + 
            "}";

        Parser parser = newParser(model);
        UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
        
        assertTrue(parser.getProblems().isEmpty());

        PackageDefinition asPackage = safeGet(unitDefinition, u -> u.getDefinition());

        ActivityDefinition activity = single(asPackage.getMember());
        checkSimpleActivity(activity);
    }
    
    /**
     * A simple happy path test to exercise the parsing of a simple standalone activity.
     */
    @Test
    void standaloneActivity() {
        String model =
            "activity Hello() {\n" +
            "  WriteLine(\"Hello World!\");\n" +
            "}"; 

        Parser parser = newParser(model);
        UnitDefinition unitDefinition = parseUnit(parser, true);
        
        ensureNoProblems(parser.getProblems());

        ActivityDefinition activity = safeGet(unitDefinition, u -> u.getDefinition());
        checkSimpleActivity(activity);
    }
    
    /**
     * A simple happy path test to exercise the parsing of a simple expression.
     */
    @Test
    void standaloneExpression() {
        String model =
            "x + 3"; 

        Parser parser = newParser(model);
        Expression expression = parseExpression(parser, true);
        
        ensureNoProblems(parser.getProblems());

        ArithmeticExpression arithmetic = assertIsA(expression, ArithmeticExpression.class);
        assertIsA(arithmetic.getOperand1(), NameExpression.class);
        assertIsA(arithmetic.getOperand2(), LiteralExpression.class);
        assertEquals("+", arithmetic.getOperator());
    }
    
    /**
     * A simple happy path test to exercise the parsing of a simple sequence of statements.
     */
    @Test
    void standaloneBlock() {
        String model =
            "x = 3;\n" + //
            "WriteLine(\"Hello World!\" + x);"; 

        Parser parser = newParser(model);
        Block block = parseStatementSequence(parser, true);
        
        ensureNoProblems(parser.getProblems());
        assertEquals(2, block.getStatement().size());
        assertIsA(block.getStatement().get(0), ExpressionStatement.class);
        assertIsA(block.getStatement().get(1), ExpressionStatement.class);

    }

    private void checkSimpleActivity(ActivityDefinition activity) {
        assertEquals("Hello", activity.getName());

        BehaviorInvocationExpression behaviorInvocation = single(activity.getBody().getStatement(),
                (ExpressionStatement s) -> s.getExpression());
        assertEquals("WriteLine", behaviorInvocation.getTarget().getPathName());

        String argument = single(((PositionalTuple) behaviorInvocation.getTuple()).getExpression(),
                (StringLiteralExpression exp) -> exp.getImage());
        assertEquals("\"Hello World!\"", argument);
    }

}
