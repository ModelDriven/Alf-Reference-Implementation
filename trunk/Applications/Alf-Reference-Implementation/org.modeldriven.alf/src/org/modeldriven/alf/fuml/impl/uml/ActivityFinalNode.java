/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class ActivityFinalNode extends FinalNode implements
		org.modeldriven.alf.uml.ActivityFinalNode {
	public ActivityFinalNode() {
		this(
				new fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode());
	}

	public ActivityFinalNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityFinalNode) this.base;
	}

}
