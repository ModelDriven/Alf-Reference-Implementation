package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ExpansionRegion;
import org.modeldriven.uml.fuml.ObjectNode;

public class ExpansionNode extends ObjectNode implements
		org.modeldriven.alf.uml.ExpansionNode {
	public ExpansionNode() {
		this(
				new fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode());
	}

	public ExpansionNode(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode getBase() {
		return (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode) this.base;
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsOutput() {
		return new ExpansionRegion(this.getBase().regionAsOutput);
	}

	public void setRegionAsOutput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsOutput) {
		regionAsOutput.addOutputElement(this);
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsInput() {
		return new ExpansionRegion(this.getBase().regionAsInput);
	}

	public void setRegionAsInput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsInput) {
		regionAsInput.addInputElement(this);
	}

}
