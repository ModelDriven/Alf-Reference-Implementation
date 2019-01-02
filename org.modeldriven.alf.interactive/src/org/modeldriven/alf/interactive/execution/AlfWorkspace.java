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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modeldriven.alf.fuml.execution.Locus;
import org.modeldriven.alf.fuml.impl.execution.Executor;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.ImportReference;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Behavior;

import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;

public class AlfWorkspace {
	
	private final UnitDefinition unit = new UnitDefinition();
	
	private Map<String, FormalParameter> variableMap = new HashMap<>();
	private Map<String, ValueList> valueMap = new HashMap<>();
	
	public static final AlfWorkspace INSTANCE = new AlfWorkspace();
	
	private AlfWorkspace() {
		NamespaceDefinition namespace = new PackageDefinition();
		namespace.getImpl().setExactName("Workspace");
		namespace.setUnit(this.unit);
		this.unit.setDefinition(namespace);
		this.unit.getImpl().addImplicitImports();
		RootNamespace.getModelScope(this.unit);
	}
	
	public UnitDefinition getUnit() {
		return this.unit;
	}
	
	public UnitDefinition addImports(List<ImportReference> imports) {
		for (ImportReference importReference: imports) {
			this.unit.addImport(importReference);
			importReference.setUnit(this.unit);
			importReference.deriveAll();
		}
		this.deriveMembers();		
		return this.getUnit();
	}
	
	public UnitDefinition removeImports(List<ImportReference> imports) {
		this.unit.getImport().removeAll(imports);
		this.deriveMembers();		
		return this.getUnit();
	}
	
	public List<Member> getOwnedMembers() {
		return this.unit.getDefinition().getOwnedMember();
	}
	
	public UnitDefinition addMember(Member member) {
		UnitDefinition unit = this.getUnit();
		
		NamespaceDefinition namespace = unit.getDefinition();
		namespace.addOwnedMember(member);
		namespace.addMember(member);
		member.setNamespace(namespace);
		
		return unit;
	}
	
	public UnitDefinition removeOwnedMember(Member member) {
		UnitDefinition unit = this.getUnit();
		
		NamespaceDefinition namespace = unit.getDefinition();
		namespace.getOwnedMember().remove(member);
		namespace.getImpl().removeMember(member);
		
		return unit;
	}
	
	public Collection<Member> getIndistinguishableFrom(Member newMember) {
		return this.getOwnedMembers().stream().
				filter(member->!newMember.equals(member) && !newMember.isDistinguishableFrom(member)).
				collect(Collectors.toSet());
	}
	
	public void setNames(Collection<Member> members, String name) {
		if (!members.isEmpty()) {
			members.forEach(member->member.getImpl().setExactName(name));
			this.deriveMembers();
		}
	}
	
	public void deriveMembers() {
		NamespaceDefinition namespace = this.getUnit().getDefinition();
		namespace.setMember(null);
		namespace.getMember();
	}
	
	public FormalParameter defineVariable(String name, ElementReference type, int lower, int upper) {
		FormalParameter parameter = new FormalParameter();
		parameter.getImpl().setExactName(name);
		parameter.setDirection("inout");
		parameter.setType(type);
		parameter.setLower(lower);
		parameter.setUpper(upper);
		parameter.setIsOrdered(true);
		parameter.setIsNonunique(true);
		
		this.variableMap.put(name, parameter);
		
		return AlfInteractiveUtil.copyFormalParameter(parameter);
	}
	
	public FormalParameter getVariable(String name) {
		return AlfInteractiveUtil.copyFormalParameter(this.variableMap.get(name));
	}
	
	public Collection<FormalParameter> getAllVariables() {
		return this.variableMap.values().stream().
				map(parameter->AlfInteractiveUtil.copyFormalParameter(parameter)).
				collect(Collectors.toList());
	}
	
	public ValueList getValues(String variable) {
		return this.valueMap.get(variable);
	}
	
	public void putValues(String variable, ValueList values) {
		this.valueMap.put(variable, values);
	}
	
	public ValueList execute(Behavior behavior, Locus locus) {
		
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
		ParameterValueList output = ((Executor)locus.getExecutor()).getBase().execute(
				((org.modeldriven.alf.fuml.impl.uml.Behavior)behavior).getBase(), null,
                input);
		
		// Update the variable values from the outputs for the corresponding parameters, and
		// determine the result value, if any.
		ValueList result = null;
		for (ParameterValue parameterValue: output) {
			Parameter parameter = parameterValue.parameter;
			if (ParameterDirectionKind.inout.equals(parameter.direction) || 
					ParameterDirectionKind.out.equals(parameter.direction)) {
				this.putValues(parameter.name, parameterValue.values);
			} else if (ParameterDirectionKind.return_.equals(parameter.direction)) {
				result = parameterValue.values;
			}
		}
		
		return result;
	}
	
}