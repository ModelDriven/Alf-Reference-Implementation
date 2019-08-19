package org.modeldriven.alf.parser;

import static org.junit.Assert.*;
import static org.modeldriven.alf.parser.Helper.*;

import org.junit.jupiter.api.Test;
import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.PositionalTuple;
import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;
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
            "};"; 

        Parser parser = newParser(model);
        UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
        
        assertTrue(parser.getProblems().isEmpty());

        ActivityDefinition activity = safeGet(unitDefinition, u -> u.getDefinition());
        checkSimpleActivity(activity);
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
