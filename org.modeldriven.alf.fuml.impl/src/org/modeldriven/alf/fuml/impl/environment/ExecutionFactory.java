/*******************************************************************************
 * Copyright 2013 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.environment;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public class ExecutionFactory extends org.modeldriven.fuml.environment.ExecutionFactory {

	public OpaqueBehaviorExecution instantiateOpaqueBehaviorExecution(
			OpaqueBehavior behavior) {
		for (String language: behavior.language) {
			if (language.equals("Alf")) {
				OpaqueBehaviorExecution execution = new AlfOpaqueBehaviorExecution();
				execution.types.add(behavior);
				return execution;
			}
		}
		return super.instantiateOpaqueBehaviorExecution(behavior);
	}
	
}
