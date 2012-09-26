/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public abstract class FinalNode extends ControlNode implements
		org.modeldriven.alf.uml.FinalNode {

	public FinalNode(
			fUML.Syntax.Activities.IntermediateActivities.FinalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.FinalNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.FinalNode) this.base;
	}

}
