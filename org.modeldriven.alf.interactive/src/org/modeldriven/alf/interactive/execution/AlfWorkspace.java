/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.interactive.execution;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.fuml.impl.execution.Executor;
import org.modeldriven.alf.syntax.common.AssignedSource;
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
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Behavior;

import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;

public class AlfWorkspace {
	
	private AlfInteractive alf;
	
	private LocalNameDeclarationStatement variableDeclaration = null;
	private Map<String, FormalParameter> variableMap = new HashMap<>();
	private Map<String, ValueList> valueMap = new HashMap<>();
	
	public AlfWorkspace(AlfInteractive alf) {
		this.alf = alf;
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
	
	protected UnitDefinition makeUnit(String unitName, Statement statement) {
		
		Block body = new Block();
		body.addStatement(statement);
		
		this.variableDeclaration = null;
		
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
		activity.getImpl().setExactName(unitName);
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
	
	protected UnitDefinition makeUnit(String unitName, Expression expression) {
		ReturnStatement statement = new ReturnStatement();
		statement.setExpression(expression);
		
		return this.makeUnit(unitName, statement);
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
		ParameterValueList output = ((Executor)this.alf.getLocus().getExecutor()).getBase().execute(
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
	
}