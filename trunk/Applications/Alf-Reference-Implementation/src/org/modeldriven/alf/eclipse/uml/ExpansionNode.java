package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ExpansionNode extends ObjectNode implements
		org.modeldriven.alf.uml.ExpansionNode {
	public ExpansionNode() {
		this(UMLFactory.eINSTANCE.createExpansionNode());
	}

	public ExpansionNode(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ExpansionNode getBase() {
		return (org.eclipse.uml2.uml.ExpansionNode) this.base;
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsOutput() {
		return new ExpansionRegion(this.getBase().getRegionAsOutput());
	}

	public void setRegionAsOutput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsOutput) {
		this.getBase().setRegionAsOutput(
				regionAsOutput == null ? null
						: ((ExpansionRegion) regionAsOutput).getBase());
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsInput() {
		return new ExpansionRegion(this.getBase().getRegionAsInput());
	}

	public void setRegionAsInput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsInput) {
		this.getBase().setRegionAsInput(
				regionAsInput == null ? null
						: ((ExpansionRegion) regionAsInput).getBase());
	}

}
