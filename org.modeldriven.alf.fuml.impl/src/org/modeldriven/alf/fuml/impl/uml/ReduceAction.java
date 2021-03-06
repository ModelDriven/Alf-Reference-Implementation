/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class ReduceAction extends Action implements
		org.modeldriven.alf.uml.ReduceAction {
	public ReduceAction() {
		this(new fUML.Syntax.Actions.CompleteActions.ReduceAction());
	}

	public ReduceAction(fUML.Syntax.Actions.CompleteActions.ReduceAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.ReduceAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.ReduceAction) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getReducer() {
		return (Behavior)wrap(this.getBase().reducer);
	}

	public void setReducer(org.modeldriven.alf.uml.Behavior reducer) {
		this.getBase().setReducer(reducer==null? null: ((Behavior) reducer).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (OutputPin)wrap(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(result==null? null: ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getCollection() {
		return (InputPin)wrap(this.getBase().collection);
	}

	public void setCollection(org.modeldriven.alf.uml.InputPin collection) {
		this.getBase().setCollection(collection==null? null: ((InputPin) collection).getBase());
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered;
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

}
