package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Clause;
import org.modeldriven.uml.fuml.OutputPin;
import org.modeldriven.uml.fuml.StructuredActivityNode;

public class ConditionalNode extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ConditionalNode {
	public ConditionalNode() {
		this(
				new fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode());
	}

	public ConditionalNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode) this.base;
	}

	public boolean getIsDeterminate() {
		return this.getBase().isDeterminate;
	}

	public void setIsDeterminate(boolean isDeterminate) {
		this.getBase().setIsDeterminate(isDeterminate);
	}

	public boolean getIsAssured() {
		return this.getBase().isAssured;
	}

	public void setIsAssured(boolean isAssured) {
		this.getBase().setIsAssured(isAssured);
	}

	public List<org.modeldriven.alf.uml.Clause> getClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.Clause element : this
				.getBase().clause) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addClause(org.modeldriven.alf.uml.Clause clause) {
		this.getBase().addClause(((Clause) clause).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().result) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().addResult(((OutputPin) result).getBase());
	}

}
