/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class StartClassifierBehaviorAction extends Action implements
		org.modeldriven.alf.uml.StartClassifierBehaviorAction {
	public StartClassifierBehaviorAction() {
		this(
				new fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction());
	}

	public StartClassifierBehaviorAction(
			fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.StartClassifierBehaviorAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return (InputPin)this.wrap(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(object==null? null: ((InputPin) object).getBase());
	}

}
