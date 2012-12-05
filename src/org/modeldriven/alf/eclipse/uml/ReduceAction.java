package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ReduceAction extends Action implements
		org.modeldriven.alf.uml.ReduceAction {
	public ReduceAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createReduceAction());
	}

	public ReduceAction(org.eclipse.uml2.uml.ReduceAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReduceAction getBase() {
		return (org.eclipse.uml2.uml.ReduceAction) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getReducer() {
		return new Behavior(this.getBase().getReducer());
	}

	public void setReducer(org.modeldriven.alf.uml.Behavior reducer) {
		this.getBase().setReducer(
				reducer == null ? null : ((Behavior) reducer).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getCollection() {
		return new InputPin(this.getBase().getCollection());
	}

	public void setCollection(org.modeldriven.alf.uml.InputPin collection) {
		this.getBase().setCollection(
				collection == null ? null : ((InputPin) collection).getBase());
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered();
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

}
