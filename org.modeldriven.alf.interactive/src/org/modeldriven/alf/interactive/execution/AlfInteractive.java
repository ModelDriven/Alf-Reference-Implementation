/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.interactive.execution;

import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.modeldriven.alf.fuml.impl.execution.Executor;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.units.NamespaceDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.ParserImpl;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.NameExpression;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.LocalNameDeclarationStatement;
import org.modeldriven.alf.syntax.statements.ReturnStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.Element;
import fUML.Semantics.Classes.Kernel.Reference;
import fUML.Semantics.Classes.Kernel.Value;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;

public class AlfInteractive extends org.modeldriven.alf.fuml.impl.execution.Alf {
	
	protected int counter = 0;
	protected boolean isRun = false;
	protected LocalNameDeclarationStatement variableDeclaration = null;
	protected ValueList result = null;
	
	protected Map<String, FormalParameter> variableMap = new HashMap<>();
	protected Map<String, ValueList> valueMap = new HashMap<>();
	
	public AlfInteractive() {
        this.loadResources();
        this.eval(";");
        this.counter++;
	}
	
	public AlfInteractive(String libraryDirectory, String modelDirectory) {
		this.setLibraryDirectory(libraryDirectory);
		this.setModelDirectory(modelDirectory);
        this.loadResources();
        this.eval(";");
        this.counter++;
	}
	
	public boolean isRun() {
		return this.isRun;
	}
	
	public void setIsRun(boolean isRun) {
		this.isRun = isRun;
	}
	
	public ValueList getResult() {
		return this.result;
	}
	
	public void defineVariable(String name, ElementReference type, int lower, int upper) {
		FormalParameter parameter = new FormalParameter();
		parameter.setName(name);
		parameter.setDirection("inout");
		parameter.setType(type);
		parameter.setLower(lower);
		parameter.setUpper(upper);
		
		this.variableMap.put(name, parameter);
	}
	
	public FormalParameter getVariable(String name) {
		return this.variableMap.get(name);
	}
	
	public Collection<FormalParameter> getAllVariables() {
		return this.variableMap.values();
	}
	
	public ValueList getValues(String variable) {
		return this.valueMap.get(variable);
	}
	
	public void putValues(String variable, ValueList values) {
		this.valueMap.put(variable, values);
	}
	
	protected UnitDefinition makeUnit(Statement statement) {
		
		Block body = new Block();
		body.addStatement(statement);
		
		// If a new variable is being defined using a local name declaration statement, return the values
		// computed for that statement, so the initial values of the variable can be set.
		if (statement instanceof LocalNameDeclarationStatement) {
			LocalNameDeclarationStatement declaration = (LocalNameDeclarationStatement)statement;
			String name = declaration.getName();
			if (this.getVariable(name) == null) {
				this.variableDeclaration = declaration;
				
				NameExpression expression = new NameExpression();
				expression.setName(new QualifiedName().getImpl().addName(name));
				
				ReturnStatement returnStatement = new ReturnStatement();
				returnStatement.setExpression(expression);
				
				body.addStatement(returnStatement);
			}
		}
		
		FormalParameter result = new FormalParameter();
		result.setName("result");
		result.setDirection("return");
		result.setLower(0);
		result.setUpper(-1);
		result.setType(ElementReferenceImpl.any);
		
		ActivityDefinition activity = new ActivityDefinition();
		activity.getImpl().setExactName("_" + this.counter);
		activity.setBody(body);

		for (FormalParameter parameter: this.getAllVariables()) {
			activity.addOwnedMember(parameter);
			parameter.setNamespace(activity);
		}

		activity.addOwnedMember(result);
		result.setNamespace(activity);
		
		UnitDefinition unit = new UnitDefinition();
		unit.setDefinition(activity);
		activity.setUnit(unit);
		
		return unit;
	}
	
	protected UnitDefinition makeUnit(Expression expression) {
		ReturnStatement statement = new ReturnStatement();
		statement.setExpression(expression);
		
		return this.makeUnit(statement);
	}
	
	protected static List<Member> filterMembers(NamespaceDefinition namespace, boolean isMapped) {
		return namespace.getOwnedMember().stream().
				filter(member->(member.getImpl().getMapping() != null) == isMapped).
				collect(Collectors.toList());
	}
	
	protected static List<Member> getMappedMembers(NamespaceDefinition namespace) {
		return filterMembers(namespace, true);
	}
	
	protected static List<Member> getUnmappedMembers(NamespaceDefinition namespace) {
		return filterMembers(namespace, false);
	}
	
	@Override
	public Collection<ConstraintViolation> check(UnitDefinition unit) {
		if (unit == null) {
			return null;
		} else {
			if (this.counter == 0) {
				return super.check(unit);
			} else {
				NamespaceDefinition modelScope = RootNamespace.getModelScope(unit);
				modelScope.deriveAll();
				Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
				for (Member member: getUnmappedMembers(modelScope)) {
					violations.addAll(member.checkConstraints());
				}
				if (!violations.isEmpty()) {
					this.printConstraintViolations(violations);
				}
				return violations;
			}
		}
	}
	
	@Override
	public UnitDefinition process(UnitDefinition unit) {
		if (unit != null) {
			unit.getImpl().addImplicitImports();
			if (this.counter == 0) {
				unit = super.process(unit);
			} else {
				Collection<ConstraintViolation> violations = this.check(unit);
				if (violations.isEmpty()) {
					NamespaceDefinition modelScope = RootNamespace.getModelScope(unit);
					for (Member member: getUnmappedMembers(modelScope)) {
						if (member instanceof NamespaceDefinition) {
							NamespaceDefinitionMapping mapping = 
									(NamespaceDefinitionMapping)this.map((NamespaceDefinition)member);
							if (mapping == null) {
								return null;
							} else {
								try {
									mapping.mapBody();
								} catch (MappingError e) {
					                this.println("Mapping failed.");
					                this.println(e.getMapping().toString());                  
					                this.println(" error: " + e.getMessage());
					                return null;
								}
							}
							
						}
					}
					return this.execute(unit);
				}
			}
		}
		return null;
	}

	public UnitDefinition process(UnitDefinition unit, boolean isRun) {
		this.setIsRun(isRun);
		unit = this.process(unit);
		this.setIsRun(false);
		return unit;
	}
	
	protected ValueList execute(Behavior behavior) {
		
		// Set input parameter values to the current variable values.
		ParameterValueList input = new ParameterValueList();
		for (org.modeldriven.alf.uml.Parameter parameter: behavior.getOwnedParameter()) {
			if ("inout".equals(parameter.getDirection()) || "in".equals(parameter.getDirection())) {
				ParameterValue parameterValue = new ParameterValue();
				parameterValue.parameter = ((org.modeldriven.alf.fuml.impl.uml.Parameter)parameter).getBase();
				parameterValue.values = this.getValues(parameter.getName());
				input.add(parameterValue);
			}
		}
		
		// Execute the behavior.
		ParameterValueList output = ((Executor)this.getLocus().getExecutor()).getBase().execute(
				((org.modeldriven.alf.fuml.impl.uml.Behavior)behavior).getBase(), null,
                input);
		
		// Update the variable values from the outputs for the corresponding parameters.
		for (ParameterValue parameterValue: output) {
			Parameter parameter = parameterValue.parameter;
			if (ParameterDirectionKind.inout.equals(parameter.direction) || 
					ParameterDirectionKind.out.equals(parameter.direction)) {
				this.putValues(parameter.name, parameterValue.values);
			}
		}
		
		// Determine the result values.
		if (output.isEmpty()) {
			return null;
		} else {
			ParameterValue result = output.get(output.size() - 1);
			if (this.variableDeclaration != null) {
				// If a new variable is being defined, set its initial values.
				String name = this.variableDeclaration.getName();
				AssignedSource assignment = this.variableDeclaration.getImpl().getAssignmentAfter(name);
				this.defineVariable(name, assignment.getType(), assignment.getLower(), assignment.getUpper());
				this.putValues(name, result.values);
			}
			return ParameterDirectionKind.return_.equals(result.parameter.direction)? result.values: null;
		}
	}
	
	@Override
	public UnitDefinition execute(UnitDefinition unit) {
		this.result = null;
		if (unit != null && this.isRun()) {
			NamespaceDefinition definition = unit.getDefinition();
			Mapping elementMapping = definition.getImpl().getMapping();
			if (elementMapping == null) {
				this.println(definition.getName() + " is unmapped.");
				return null;
			} else {
				Element element = ((FumlMapping)elementMapping).getElement();
				if (element instanceof Behavior) {
					this.result = this.execute((Behavior)element);
				} else {
					this.println(definition.getName() + " is not a behavior.");
				}
			}
		}
		return unit;
	}
	
	protected void reset() {
		ModelNamespace modelScope = this.rootScopeImpl.getModelNamespace();
		modelScope.setOwnedMember(getMappedMembers(modelScope));
	}
	
	protected Parser createParser(String input) {
		Parser parser = new ParserImpl(new StringReader(input));
		parser.setFileName(this.counter + "");
		return parser;
	}
	
	public ValueList eval(String input) {
		this.result = null;
		this.variableDeclaration = null;
		Parser parser = this.createParser(input);
		try {
			try {
				this.process(this.makeUnit(parser.ExpressionEOF()), true);
			} catch (ParseException e) {
				parser = this.createParser(input);
				try {
					this.process(this.makeUnit(parser.StatementEOF()), true);
				} catch (ParseException e1) {
					parser = this.createParser(input);
					try {
						this.process(parser.UnitDefinitionEOF(), false);
					} catch (ParseException e2) {
						throw e1.getBeginColumn() > e2.getBeginColumn()? e1: e2;
					}
				}
			}
		} catch (ParseException | TokenMgrError e) {
			System.out.println(e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		reset();
		return this.result;
	}
	
	public void printResult() {
		ValueList result = this.getResult();
		if (result != null) {
			if (result.isEmpty()) {
				System.out.println("null");
			} else {
				for (Value value: result) {
					System.out.print(value);
					if (value instanceof Reference) {
						System.out.println();
					} else {
						System.out.print(" ");
					}
				}
				System.out.println();
			}
		}
		System.out.println();
	}
	
	public void run(String input) {
		if (input != null && !input.isEmpty()) {
			this.eval(input);
			this.printResult();
			this.counter++;
		}
	}
	
	public void run() {
        try (Scanner in = new Scanner(System.in)) {
	        do {
	        	System.out.print(this.counter + "> ");
	        	String input = in.nextLine().trim();
	        	if ("@exit".equals(input)) {
	        		break;
	        	} else {
	        		run(input);
	        	}
	        } while(true);
        }
    }
	
	@Override
	public void run(String[] args) {
		this.setLibraryDirectory(args[0]);
		this.setModelDirectory(args[1]);
		this.run();
	}
		 
   public static void main(String[] args) {
        if (args.length < 2) {
        	System.out.println("Usage: alfi library-directory model-directory");
        	return;
        }
        System.out.println("Alf Reference Implementation v" + ALF_VERSION);
        System.out.println("Initializing...");
        new AlfInteractive(args[0], args[1]).run();
    }
}
