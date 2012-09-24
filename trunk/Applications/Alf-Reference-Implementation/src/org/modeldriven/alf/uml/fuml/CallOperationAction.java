/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class CallOperationAction extends CallAction implements
		org.modeldriven.alf.uml.CallOperationAction {
	public CallOperationAction() {
		this(new fUML.Syntax.Actions.BasicActions.CallOperationAction());
	}

	public CallOperationAction(
			fUML.Syntax.Actions.BasicActions.CallOperationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.CallOperationAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.CallOperationAction) this.base;
	}

	public org.modeldriven.alf.uml.Operation getOperation() {
		return (Operation)this.wrap(this.getBase().operation);
	}

	public void setOperation(org.modeldriven.alf.uml.Operation operation) {
		this.getBase().setOperation(operation==null? null: ((Operation) operation).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return (InputPin)this.wrap(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(target==null? null: ((InputPin) target).getBase());
	}

}
