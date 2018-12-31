/*******************************************************************************
 * Copyright 2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.interactive.units;

import org.modeldriven.alf.interactive.execution.AlfWorkspace;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;

public class ActivityDefinitionWrapper extends ActivityDefinition {
	
	public ActivityDefinitionWrapper(String name, Block body) {
		super();
		
		this.getImpl().setExactName(name);
		
		FormalParameter result = new FormalParameter();
		result.setName("");
		result.setDirection("return");
		result.setLower(0);
		result.setUpper(-1);
		result.setType(ElementReferenceImpl.any);
		
		// Note: Parameters must be added before the body, in order for the initial assigned sources
		// to be set correctly.
		for (FormalParameter parameter: AlfWorkspace.INSTANCE.getAllVariables()) {
			this.addOwnedMember(parameter);
			parameter.setNamespace(this);
		}
		this.addOwnedMember(result);
		result.setNamespace(this);
		
		this.setBody(body);
	}

}
