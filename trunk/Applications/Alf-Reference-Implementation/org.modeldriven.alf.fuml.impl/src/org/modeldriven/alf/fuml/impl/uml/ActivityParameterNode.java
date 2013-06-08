/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class ActivityParameterNode extends ObjectNode implements
		org.modeldriven.alf.uml.ActivityParameterNode {
	public ActivityParameterNode() {
		this(
				new fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode());
	}

	public ActivityParameterNode(
			fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode) this.base;
	}

	public org.modeldriven.alf.uml.Parameter getParameter() {
		return (Parameter)wrap(this.getBase().parameter);
	}

	public void setParameter(org.modeldriven.alf.uml.Parameter parameter) {
		this.getBase().setParameter(parameter==null? null: ((Parameter) parameter).getBase());
	}

}
