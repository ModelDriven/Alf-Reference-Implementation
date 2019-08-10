package org.modeldriven.alf.parser;

import static org.junit.Assert.*;
import static org.modeldriven.alf.parser.Helper.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
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
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ErrorRecoveryTests {
	
	@Test
	void badActivity() {
		String model =
		  "package SomePackage {\n" +
			"public activity Good1() {\n" +
			"  WriteLine(foo);\n" +
			"}\n" +
			"public activity Bad()\n" +
			"public activity Good2() {\n" +
			"  WriteLine(foo);\n" +		
			"}\n" +
		  "}";

		Parser parser = newParser(model);
		
		UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
		ParsingProblem problem = single(parser.getProblems());
		assertEquals(5, problem.getBeginLine());
		
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		List<ActivityDefinition> activities = assertAndMap(2, asPackage.getOwnedMember());
		assertEquals("Good1", activities.get(0).getName());
		assertEquals("Good2", activities.get(1).getName());
	}
	
	@Test
	void badStatement() {
		String model =
			"activity Bad() {\n" +
			"  WriteLine(foo);\n" +		
			"  -;\n" +
			"  ReadLine(bar);\n" +					
			"}";
		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
		
        ActivityDefinition asActivity = (ActivityDefinition) unitDefinition.getDefinition();
		ParsingProblem problem = single(parser.getProblems());
		assertEquals(3, problem.getBeginLine());
		
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
		UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
		
		ParsingProblem problem = single(parser.getProblems());
		assertEquals(4, problem.getBeginLine());
		
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		ActivityDefinition activity = single(asPackage.getOwnedMember());
		
		List<BehaviorInvocationExpression> expressions = assertAndMap(3, activity.getBody().getStatement(), (ExpressionStatement s) -> s.getExpression());
		
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
					"public activity Simple() {\n" +
					"  accept (s : Signal1) {\n" + 
					"      Activity1();\n" + 
					"    } or accept (s : ) {\n" + 
					"      Activity2();\n" + 
					"    } or accept (s : Signal3) {\n" + 
					"      Activity3();\n" + 
					"    }\n" + 
					"  }\n" + 
					"}\n" +
				  "}";
		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
		
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
	void badAccept_badClause() {
		String model =
				  "package SomePackage {\n" +
					"public activity Simple() {\n" +
					"  accept (s : Signal1) {\n" + 
					"      Activity1();\n" + 
					"    } or accept (s : Signal2) {\n" + 
					"      -\n" + 
					"    } or accept (s : Signal3) {\n" + 
					"      Activity3();\n" + 
					"    }\n" + 
					"  }\n" + 
					"}\n" +
				  "}";
		Parser parser = newParser(model);
		UnitDefinition unitDefinition = parser.parseUnitDefinition(false);
		
		search(parser.getProblems(), it -> it.getBeginLine() == 6);
		
		PackageDefinition asPackage = require(unitDefinition.getDefinition());
		ActivityDefinition activity = single(asPackage.getOwnedMember());
		List<AcceptBlock> accept = single(activity.getBody().getStatement(), (AcceptStatement s) -> assertAndMap(3, s.getAcceptBlock()));
		
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
