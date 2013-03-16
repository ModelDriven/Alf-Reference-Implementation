/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class ActivityParameterNode extends ObjectNode implements
		org.modeldriven.alf.uml.ActivityParameterNode {
	public ActivityParameterNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createActivityParameterNode());
	}

	public ActivityParameterNode(org.eclipse.uml2.uml.ActivityParameterNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ActivityParameterNode getBase() {
		return (org.eclipse.uml2.uml.ActivityParameterNode) this.base;
	}

	public org.modeldriven.alf.uml.Parameter getParameter() {
		return (org.modeldriven.alf.uml.Parameter) wrap(this.getBase()
				.getParameter());
	}

	public void setParameter(org.modeldriven.alf.uml.Parameter parameter) {
		this.getBase().setParameter(
				parameter == null ? null : ((Parameter) parameter).getBase());
	}

}
