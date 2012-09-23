package org.modeldriven.uml.alf.fuml;


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
		return new Behavior(this.getBase().reducer);
	}

	public void setReducer(org.modeldriven.alf.uml.Behavior reducer) {
		this.getBase().setReducer(((Behavior) reducer).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getCollection() {
		return new InputPin(this.getBase().collection);
	}

	public void setCollection(org.modeldriven.alf.uml.InputPin collection) {
		this.getBase().setCollection(((InputPin) collection).getBase());
	}

	public boolean getIsOrdered() {
		return this.getBase().isOrdered;
	}

	public void setIsOrdered(boolean isOrdered) {
		this.getBase().setIsOrdered(isOrdered);
	}

}
