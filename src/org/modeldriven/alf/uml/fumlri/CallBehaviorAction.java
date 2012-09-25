/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fumlri;


public class CallBehaviorAction extends CallAction implements
		org.modeldriven.alf.uml.CallBehaviorAction {
	public CallBehaviorAction() {
		this(new fUML.Syntax.Actions.BasicActions.CallBehaviorAction());
	}

	public CallBehaviorAction(
			fUML.Syntax.Actions.BasicActions.CallBehaviorAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.CallBehaviorAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.CallBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getBehavior() {
		return (Behavior)this.wrap(this.getBase().behavior);
	}

	public void setBehavior(org.modeldriven.alf.uml.Behavior behavior) {
		this.getBase().setBehavior(behavior==null? null: ((Behavior) behavior).getBase());
	}

}
