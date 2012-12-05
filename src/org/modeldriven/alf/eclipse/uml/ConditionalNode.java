package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ConditionalNode extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ConditionalNode {
	public ConditionalNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createConditionalNode());
	}

	public ConditionalNode(org.eclipse.uml2.uml.ConditionalNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ConditionalNode getBase() {
		return (org.eclipse.uml2.uml.ConditionalNode) this.base;
	}

	public boolean getIsDeterminate() {
		return this.getBase().getIsDeterminate();
	}

	public void setIsDeterminate(boolean isDeterminate) {
		this.getBase().setIsDeterminate(isDeterminate);
	}

	public boolean getIsAssured() {
		return this.getBase().getIsAssured();
	}

	public void setIsAssured(boolean isAssured) {
		this.getBase().setIsAssured(isAssured);
	}

	public List<org.modeldriven.alf.uml.Clause> getClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (org.eclipse.uml2.uml.Clause element : this.getBase().getClauses()) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addClause(org.modeldriven.alf.uml.Clause clause) {
		this.getBase().getClauses().add(
				clause == null ? null : ((Clause) clause).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getResults()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().getResults().add(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
