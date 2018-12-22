/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.interactive.execution;

import java.util.List;
import java.util.stream.Collectors;

import org.modeldriven.alf.interactive.unit.ActivityDefinitionWrapper;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ReturnStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class AlfInteractiveUtil {

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

	public static UnitDefinition makeUnit(String unitName, Statement statement) {
		
		Block body = new Block();
		body.addStatement(statement);
				
		ActivityDefinition activity = 
				new ActivityDefinitionWrapper(unitName, AlfWorkspace.INSTANCE.getAllVariables(), body);

		UnitDefinition unit = new UnitDefinition();
		unit.setDefinition(activity);
		activity.setUnit(unit);
		
		return unit;
	}
	
	public static UnitDefinition makeUnit(String unitName, Expression expression) {
		ReturnStatement statement = new ReturnStatement();
		statement.setExpression(expression);
		
		return makeUnit(unitName, statement);
	}
	
	public static FormalParameter copyFormalParameter(FormalParameter parameter) {
		FormalParameter copy = new FormalParameter();
		copy.getImpl().setExactName(parameter.getName());
		copy.setDirection(parameter.getDirection());
		copy.setType(parameter.getType());
		copy.setLower(parameter.getLower());
		copy.setUpper(parameter.getUpper());
		copy.setIsOrdered(true);
		copy.setIsNonunique(true);
		return copy;	
	}
	
}
