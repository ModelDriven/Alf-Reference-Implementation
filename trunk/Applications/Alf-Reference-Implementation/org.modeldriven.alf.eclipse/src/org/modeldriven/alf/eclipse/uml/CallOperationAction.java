/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CallOperationAction extends CallAction implements
		org.modeldriven.alf.uml.CallOperationAction {
	public CallOperationAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createCallOperationAction());
	}

	public CallOperationAction(org.eclipse.uml2.uml.CallOperationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CallOperationAction getBase() {
		return (org.eclipse.uml2.uml.CallOperationAction) this.base;
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return (org.modeldriven.alf.uml.Operation) wrap(this.getBase()
				.getOperation());
	}

	public void setOperation(org.modeldriven.alf.uml.Operation operation) {
		this.getBase().setOperation(
				operation == null ? null : ((Operation) operation).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getTarget());
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(
				target == null ? null : ((InputPin) target).getBase());
	}

}
