package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ExpansionNode;
import org.modeldriven.uml.fuml.StructuredActivityNode;

public class ExpansionRegion extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ExpansionRegion {
	public ExpansionRegion() {
		this(
				new fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion());
	}

	public ExpansionRegion(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion base) {
		super(base);
	}

	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion getBase() {
		return (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion) this.base;
	}

	public String getMode() {
		return this.getBase().mode.toString();
	}

	public void setMode(String mode) {
		this.getBase().setMode(
				fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind
						.valueOf(mode));
	}

	public List<org.modeldriven.alf.uml.ExpansionNode> getOutputElement() {
		List<org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList<org.modeldriven.alf.uml.ExpansionNode>();
		for (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode element : this
				.getBase().outputElement) {
			list.add(new ExpansionNode(element));
		}
		return list;
	}

	public void addOutputElement(org.modeldriven.alf.uml.ExpansionNode outputElement) {
		this.getBase().addOutputElement(
				((ExpansionNode) outputElement).getBase());
	}

	public List<org.modeldriven.alf.uml.ExpansionNode> getInputElement() {
		List<org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList<org.modeldriven.alf.uml.ExpansionNode>();
		for (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode element : this
				.getBase().inputElement) {
			list.add(new ExpansionNode(element));
		}
		return list;
	}

	public void addInputElement(org.modeldriven.alf.uml.ExpansionNode inputElement) {
		this.getBase()
				.addInputElement(((ExpansionNode) inputElement).getBase());
	}

}
