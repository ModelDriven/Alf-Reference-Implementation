/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.interactive.unit;

import java.util.Collection;
import java.util.stream.Collectors;

import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.interactive.execution.AlfWorkspace;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.uml.Classifier;

public class ActivityDefinitionWrapperMapping extends ActivityDefinitionMapping {
	
	@Override
	public void mapTo(Classifier classifier) throws MappingError {
		ActivityDefinition activityDefinition = this.getActivityDefinition();
		Collection<AssignedSource> newAssignments = 
				activityDefinition.getEffectiveBody().getImpl().getNewAssignments();
		Collection<String> parameterNames = activityDefinition.getImpl().getParameters().stream().
				map(parameter->parameter.getImpl().getName()).collect(Collectors.toSet());
		
		for (AssignedSource assignment: newAssignments) {
			String name = assignment.getName();

			if (!parameterNames.contains(name)) {
				FormalParameter parameter = AlfWorkspace.INSTANCE.defineVariable(
						assignment.getName(), assignment.getType(), assignment.getLower(), assignment.getUpper());
				
				parameter.setDirection("out");
				parameter.setNamespace(activityDefinition);
				
				activityDefinition.addOwnedMember(parameter);
				activityDefinition.addMember(parameter);
			}
		}
		
		super.mapTo(classifier);
	}

}
