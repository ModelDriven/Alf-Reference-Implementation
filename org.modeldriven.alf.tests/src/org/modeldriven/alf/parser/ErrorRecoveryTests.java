/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.modeldriven.alf.parser.Helper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.modeldriven.alf.syntax.common.SourceProblem;
import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.ExpressionPlaceholder;
import org.modeldriven.alf.syntax.expressions.LiteralExpression;
import org.modeldriven.alf.syntax.expressions.NameExpression;
import org.modeldriven.alf.syntax.expressions.NaturalLiteralExpression;
import org.modeldriven.alf.syntax.expressions.PositionalTuple;
import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;
import org.modeldriven.alf.syntax.statements.AcceptBlock;
import org.modeldriven.alf.syntax.statements.AcceptStatement;
import org.modeldriven.alf.syntax.statements.EmptyStatement;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.ClassifierDefinition;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.syntax.units.UnknownClassifierDefinition;

public class ErrorRecoveryTests {
    
    @Test
    void badToken() {
        String model = "package\n\"";
        Parser parser = newParser(model);
        UnitDefinition unit = parseUnit(parser);
        assertNotNull(unit);
        SourceProblem problem = require(1, parser.getProblems()).get(0);
        assertTrue(problem instanceof LexicalProblem, () -> problem.getClass().getSimpleName());
        assertEquals(2, problem.getBeginLine());
    }
    
    @Test
    void badTokenAfterParsingProblem() {
        String model =
                  "package SomePackage {\n" +
                  "public activity Good1() {\n" +
                  "  WriteLine(foo);\n" +
                  "}\n" +
                  "public activity Bad()\n" +
                  "public activity Good2() {\n" +
                  "  WriteLine(foo);\n" +     
                  "}\n" +
                  "public activity Good2() {\n" +
                  "  WriteLine(foo);\n" +     
                  "}\n" +
                  "// Invalid chars (guillemets) to cause lexical problem \n" +
                  "\u00AB \u00BB\n" +
                  "// end of invalid chars\n" + 
                  "}";

        Parser parser = newParser(model);
        UnitDefinition unitDefinition = parseUnit(parser);
        Collection<SourceProblem> problems = require(2, parser.getProblems());
        ParsingProblem parsingProblem = search(problems, p -> p instanceof ParsingProblem);
        assertEquals(5, parsingProblem.getBeginLine());
        LexicalProblem lexicalProblem = search(parser.getProblems(), p -> p instanceof LexicalProblem);
        assertEquals(13, lexicalProblem.getBeginLine());
        assertNotNull(unitDefinition);
    }
	
    @Test
    void badPackageMember() {
        String model = //
                "package SomePackage {\n" + //
                "  public activity Good1() {\n" + //
                "    WriteLine(foo);\n" + //
                "  }\n" + //
                "  public foo();\n" + //
                "  public activity Good2() {\n" + //
                "    WriteLine(foo);\n" + //
                "  }\n" + //
                "}";

        Parser parser = newParser(model);
        
        UnitDefinition unitDefinition = parseUnit(parser);
        List<ParsingProblem> problems = requireAtLeast(1, parser.getProblems());
        PackageDefinition asPackage = require(unitDefinition.getDefinition());
        List<ClassifierDefinition> activities = assertAndMap(2, asPackage.getOwnedMember());
        assertEquals("Good1", activities.get(0).getName());
        assertTrue(activities.get(1) instanceof UnknownClassifierDefinition);
        assertEquals("Good2", activities.get(2).getName());
        assertEquals(5, problems.get(0).getBeginLine(), () -> problems.get(0).toString());
        assertEquals(1, problems.size(), () -> problems.toString());

    }
    
	@Test
	void badActivity() {
		String model =
		  "package SomePackage {\n" + //
            "public activity Bad1()\n" + //
			"public activity Good1() {\n" + //
			"  WriteLine(foo);\n" + //
			"}\n" + //
			"public activity Bad2()\n" + //
			"public activity Good2() {\n" + //
			"  WriteLine(foo);\n" + //
			"}\n" + //
			"public activity Bad3()\n" + //			
		  "}";

		Parser parser = newParser(model);
		
		UnitDefinition unitDefinition = parseUnit(parser);
		List<SourceProblem> problems = new ArrayList<>(parser.getProblems());
		assertProblems(problems.size() >= 3, problems);
		assertEquals(2, problems.get(0).getBeginLine());
		assertEquals(6, problems.get(1).getBeginLine());
		assertEquals(10, problems.get(2).getBeginLine());
		
		assertNotNull(unitDefinition);
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		List<ClassifierDefinition> activities = assertAndMap(2, asPackage.getOwnedMember());
		assertEquals("Good1", activities.get(1).getName());
		assertEquals("Good2", activities.get(3).getName());
		Arrays.asList(0, 2, 4).forEach(i -> assertTrue(activities.get(i) instanceof UnknownClassifierDefinition));
	}

   @Test
    void badActivityBody() {
        String model =
          "package SomePackage {\n" + //
            "public activity Bad1() {\n" + //
            "-\n" + //
            "}\n" + //
            "public activity Good1() {\n" + //
            "  WriteLine(foo);\n" + //
            "}\n" + //
            "public activity Bad2() {\n" + //
            "-\n" + //
            "}\n" + //
            "public activity Good2() {\n" + //
            "  WriteLine(foo);\n" + //
            "}\n" + //
            "public activity Bad3() {\n" + //
            "-\n" + //
            "}\n" + //
          "}";

        Parser parser = newParser(model);
        
        UnitDefinition unitDefinition = parseUnit(parser);
        List<SourceProblem> problems = new ArrayList<>(parser.getProblems());
        assertProblems(problems.size() >= 3, problems);
        assertEquals(4, problems.get(0).getBeginLine());
        assertEquals(10, problems.get(1).getBeginLine());
        assertEquals(16, problems.get(2).getBeginLine());
        
        assertNotNull(unitDefinition);
        PackageDefinition asPackage = require(unitDefinition.getDefinition());
        List<ActivityDefinition> activities = assertAndMap(5, asPackage.getOwnedMember());
        assertEquals("Good1", activities.get(1).getName());
        assertEquals("Good2", activities.get(3).getName());
        Arrays.asList(0, 2, 4).forEach(i -> {
            @SuppressWarnings("unused")
			EmptyStatement empty = single(activities.get(i).getBody().getStatement());
        });
    }
   
   @Test
   void badActivityUnit_additionalInput() {
       String model =
           "activity Good() { }\n" +
           "x";
       Parser parser = newParser(model);
       UnitDefinition unitDefinition = parseUnit(parser);
       
       ActivityDefinition asActivity = (ActivityDefinition) unitDefinition.getDefinition();
       ParsingProblem problem = single(parser.getProblems());
       assertEquals(2, problem.getBeginLine());

       assertEquals("Good", asActivity.getName());
   }
   
   @Test
   void badPackageUnit_additionalInput() {
       String model =
           "package Good {\n" +
           "}\n" +
           "x";
       Parser parser = newParser(model);
       UnitDefinition unitDefinition = parseUnit(parser);
       
       PackageDefinition asPackage = (PackageDefinition) unitDefinition.getDefinition();
       ParsingProblem problem = single(parser.getProblems());
       assertEquals(3, problem.getBeginLine());

       assertEquals("Good", asPackage.getName());
   }
   
   @Test
   void badUnit_notAUnit() {
       String model =
           "x";
       Parser parser = newParser(model);
       UnitDefinition unitDefinition = parseUnit(parser);
       
       ParsingProblem problem = single(parser.getProblems());
       assertEquals(1, problem.getBeginLine());
       
       assertNull(unitDefinition.getDefinition());
   }

   void badUnit_empty() {
       String model =
           "";
       Parser parser = newParser(model);
       UnitDefinition unitDefinition = parseUnit(parser);
       
       assertNotNull(unitDefinition);
       
       ParsingProblem problem = single(parser.getProblems());
       assertEquals(0, problem.getBeginLine());
   }
   
   void badExpression_empty() {
       String model =
           "";
       Parser parser = newParser(model);
       Expression expression = parseExpression(parser);
       assertNotNull(expression);
       
       ParsingProblem problem = single(parser.getProblems());
       assertEquals(0, problem.getBeginLine());
   }
   
   void badExpression_additionalInput() {
       String model =
           "\nx\ny";
       Parser parser = newParser(model);
       Expression expression = parseExpression(parser);
       NameExpression name = assertIsA(expression, NameExpression.class);
       assertEquals("x", name.getName().getUnqualifiedName().getName());
       
       ParsingProblem problem = single(parser.getProblems());
       assertEquals(2, problem.getBeginLine());
   }
   
   
   

	@Test
	void badStatement() {
		String model =
			"activity Bad() {\n" +
	        "  bad1(exp()-;\n" +
			"  WriteLine(foo);\n" +		
			"  bad2(exp()-;\n" +
			"  ReadLine(bar);\n" +
			"  bad3(exp()-;\n" +			
			"}";
		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parseUnit(parser);
		
        ActivityDefinition asActivity = (ActivityDefinition) unitDefinition.getDefinition();
		List<SourceProblem> problems = new ArrayList<>(parser.getProblems());
		assertProblems(problems.size() == 3, problems);
        assertEquals(2, problems.get(0).getBeginLine());
        assertEquals(4, problems.get(1).getBeginLine());
        assertEquals(6, problems.get(2).getBeginLine());
        problems.forEach(p -> assertTrue(p instanceof ParsingProblem));

		List<Statement> regularStatements = filter(asActivity.getBody().getStatement(), it -> !EmptyStatement.class.isInstance(it));
		List<BehaviorInvocationExpression> expressions = assertAndMap(2, regularStatements, (ExpressionStatement s) -> s.getExpression());
		
		List<String> targets = map(expressions, e -> e.getTarget().getPathName());
		assertEquals(Arrays.asList("WriteLine","ReadLine"), targets);
		
		List<String> arguments = map(expressions,
				e -> single(((PositionalTuple) e.getTuple()).getExpression(),
				(NameExpression n) -> n.getName().getPathName()));
		
		assertEquals("foo", arguments.get(0));
		assertEquals("bar", arguments.get(1));
	}
	
    @Test
    void badStatement2() {
        String model = //
            "activity Bad() {\n" + //
            "  bad1(exp)-;\n" + //
            "  WriteLine(foo);\n" + //
            "  bad2(exp)-;\n" + //
            "  ReadLine(bar);\n" + //
            "  bad3(exp)-;\n" + //
            "}";
        Parser parser = newParser(model);
        UnitDefinition unitDefinition = parseUnit(parser);

        ActivityDefinition asActivity = (ActivityDefinition) unitDefinition.getDefinition();
        List<SourceProblem> problems = new ArrayList<>(parser.getProblems());
        assertProblems(problems.size() == 3, problems);
        assertEquals(2, problems.get(0).getBeginLine());
        assertEquals(4, problems.get(1).getBeginLine());
        assertEquals(6, problems.get(2).getBeginLine());
        problems.forEach(p -> assertTrue(p instanceof ParsingProblem));

        List<Statement> regularStatements = filter(asActivity.getBody().getStatement(),
                it -> !EmptyStatement.class.isInstance(it));
        List<BehaviorInvocationExpression> expressions = assertAndMap(2, regularStatements,
                (ExpressionStatement s) -> s.getExpression());

        List<String> targets = map(expressions, e -> e.getTarget().getPathName());
        assertEquals(Arrays.asList("WriteLine", "ReadLine"), targets);

        List<String> arguments = map(expressions, e -> single(((PositionalTuple) e.getTuple()).getExpression(),
                (NameExpression n) -> n.getName().getPathName()));

        assertEquals("foo", arguments.get(0));
        assertEquals("bar", arguments.get(1));
    }

    @Test
    void badStatement3() {
        String model = "activity FailureTest() {\n" + //
                "       x =;\n" + //
                "       y();\n" + //
                "}";

        Parser parser = newParser(model);

        UnitDefinition unitDefinition = parseUnit(parser);
        List<SourceProblem> problems = new ArrayList<>(parser.getProblems());
        assertProblems(problems.size() > 0, problems);
        assertEquals(2, problems.get(0).getBeginLine());
        assertProblems(problems.stream().allMatch(problem -> problem.getEndLine() == 2), problems);

        assertNotNull(unitDefinition);
        ActivityDefinition asActivity = require(unitDefinition.getDefinition());
        List<Statement> statements = require(2, asActivity.getBody().getStatement());
        ExpressionStatement goodStatement = getAt(1, statements, 1);
        BehaviorInvocationExpression invocation = (BehaviorInvocationExpression) goodStatement.getExpression();
        assertEquals("y", invocation.getTarget().getPathName());
    }
    
    @Test
    void badStatement4() {
        String model = "activity ParsingError1() {\n" + //
                "       x->selec y (true);\n" + //
                "}";

        Parser parser = newParser(model);

        UnitDefinition unitDefinition = parseUnit(parser);
        List<SourceProblem> problems = new ArrayList<>(parser.getProblems());
        assertProblems(problems.size() > 0, problems);
        assertEquals(2, problems.get(0).getBeginLine());
        assertProblems(problems.stream().allMatch(problem -> problem.getEndLine() == 2), problems);

        assertNotNull(unitDefinition);
        ActivityDefinition asActivity = require(unitDefinition.getDefinition());
        @SuppressWarnings("unused")
		Statement badStatement = single(asActivity.getBody().getStatement());
        
    }    
    
	@Test
	void badArgument() {
		String model =
		  "package SomePackage {\n" +
			"public activity SomeActivity() {\n" +
			"  OtherActivity1(1, 2, \"foo\");\n" +				  
			"  OtherActivity2(1, -, \"val1\");\n" +
			"  OtherActivity3(3, 4, \"bar\");\n" +			
			"}\n" +
		  "}";

		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parseUnit(parser);
		
		ParsingProblem problem = single(parser.getProblems());
		assertEquals(4, problem.getBeginLine());
		
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		ActivityDefinition activity = single(asPackage.getOwnedMember());
		
		List<Statement> statements = require(3, activity.getBody().getStatement());
		List<ExpressionStatement> expressionStatements = cast(statements);
		List<BehaviorInvocationExpression> expressions = map(expressionStatements, s -> s.getExpression());
		
		PositionalTuple partialTuple = require(expressions.get(1).getTuple());
		List<Expression> partialArgs = require(3, partialTuple.getExpression());
		assertIsA(partialArgs.get(0), NaturalLiteralExpression.class);
		// expression placeholders allow following expressions to preserve position
		assertIsA(partialArgs.get(1), ExpressionPlaceholder.class);
		assertIsA(partialArgs.get(2), StringLiteralExpression.class);
		
		IntStream.of(0, 2).forEach(index -> {
			PositionalTuple completeTuple = require(expressions.get(index).getTuple());
			List<Expression> completeArgs = require(3, completeTuple.getExpression());
			completeArgs.forEach(it -> assertIsA(it, LiteralExpression.class));
		});
	}

	@Test
	void badAccept_badSignalName() {
		String model =
				    "package SomePackage {\n" +
					"  public activity Simple() {\n" +
					"    accept (s : Signal1) {\n" + 
					"      Activity1();\n" + 
					"    } or accept (s : ) {\n" + 
					"      Activity2();\n" + 
					"    } or accept (s : Signal3) {\n" + 
					"      Activity3();\n" + 
					"    }\n" + 
					"  }\n" + 
					"}";
		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parseUnit(parser);
		
		search(parser.getProblems(), it -> it.getBeginLine() == 5);
		
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		ActivityDefinition activity = single(asPackage.getOwnedMember());
		List<AcceptBlock> accept = single(activity.getBody().getStatement(), (AcceptStatement s) -> assertAndMap(3, s.getAcceptBlock()));
		
        assertEquals("Signal1", single(accept.get(0).getSignalNames().getName()).getPathName());
        assertEquals("", single(accept.get(1).getSignalNames().getName()).getPathName());
        assertEquals("Signal3", single(accept.get(2).getSignalNames().getName()).getPathName());
        
        accept.forEach(a -> assertEquals(1, a.getBlock().getStatement().size()));
        
        List<BehaviorInvocationExpression> expressions = map(accept, a -> ((ExpressionStatement) single((a.getBlock().getStatement()))).getExpression());

        List<String> targets = map(expressions, e -> e.getTarget().getPathName());
		assertEquals(Arrays.asList("Activity1", "Activity2", "Activity3"), targets);
	}
	
	@Test
	void badAccept_badStatementInClause() {
		String model =
				    "package SomePackage {\n" +
					"  public activity Simple() {\n" +
					"    accept (s : Signal1) {\n" + 
					"      Activity1();\n" + 
					"    } or accept (s : Signal2) {\n" + 
					"      -\n" + 
					"    } or accept (s : Signal3) {\n" + 
					"      Activity3();\n" + 
					"    }\n" + 
					"  }\n" + 
					"}";
		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parseUnit(parser);
		
		search(parser.getProblems(), it -> it.getBeginLine() == 7);
		
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		ActivityDefinition activity = single(asPackage.getOwnedMember());
		List<AcceptBlock> accept = single(activity.getBody().getStatement(), (AcceptStatement s) -> assertAndMap(3, s.getAcceptBlock()));
		
        @SuppressWarnings("unused")
		EmptyStatement statementPlaceholder = single(accept.get(1).getBlock().getStatement());
        
        IntStream.of(0, 2).forEach(i -> {
            AcceptBlock acceptBlock = accept.get(i);
            BehaviorInvocationExpression expression = single(acceptBlock.getBlock().getStatement(), (ExpressionStatement s) -> s.getExpression());
            assertEquals("Activity" + (++i), expression.getTarget().getPathName());
        });
        

        List<String> signalNames = map(accept, a -> single(a.getSignalNames().getName()).getPathName());
        assertEquals(Arrays.asList("Signal1", "Signal2", "Signal3"), signalNames);
	}
}
