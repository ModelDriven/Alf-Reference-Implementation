/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;


public class StartObjectBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.StartObjectBehaviorAction {
	public StartObjectBehaviorAction() {
		this(
				new fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction());
	}

	public StartObjectBehaviorAction(
			fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.StartObjectBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return (InputPin)this.wrap(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(object==null? null: ((InputPin) object).getBase());
	}

}
