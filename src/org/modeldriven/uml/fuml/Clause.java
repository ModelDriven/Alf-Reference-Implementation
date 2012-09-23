package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Clause;
import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.ExecutableNode;
import org.modeldriven.uml.fuml.OutputPin;

public class Clause extends Element implements org.modeldriven.alf.uml.Clause {
	public Clause() {
		this(new fUML.Syntax.Activities.CompleteStructuredActivities.Clause());
	}

	public Clause(
			fUML.Syntax.Activities.CompleteStructuredActivities.Clause base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.Clause getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.Clause) this.base;
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getTest() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().test) {
			list.add(new ExecutableNode(element));
		}
		return list;
	}

	public void addTest(org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().addTest(((ExecutableNode) test).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getBody() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().body) {
			list.add(new ExecutableNode(element));
		}
		return list;
	}

	public void addBody(org.modeldriven.alf.uml.ExecutableNode body) {
		this.getBase().addBody(((ExecutableNode) body).getBase());
	}

	public List<org.modeldriven.alf.uml.Clause> getPredecessorClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.Clause element : this
				.getBase().predecessorClause) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addPredecessorClause(
			org.modeldriven.alf.uml.Clause predecessorClause) {
		this.getBase().addPredecessorClause(
				((Clause) predecessorClause).getBase());
	}

	public List<org.modeldriven.alf.uml.Clause> getSuccessorClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.Clause element : this
				.getBase().successorClause) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addSuccessorClause(org.modeldriven.alf.uml.Clause successorClause) {
		successorClause.addPredecessorClause(this);
	}

	public org.modeldriven.alf.uml.OutputPin getDecider() {
		return new OutputPin(this.getBase().decider);
	}

	public void setDecider(org.modeldriven.alf.uml.OutputPin decider) {
		this.getBase().setDecider(((OutputPin) decider).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getBodyOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().bodyOutput) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addBodyOutput(org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().addBodyOutput(((OutputPin) bodyOutput).getBase());
	}

}
