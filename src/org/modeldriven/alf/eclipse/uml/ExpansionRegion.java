package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ExpansionRegion extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ExpansionRegion {
	public ExpansionRegion() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createExpansionRegion());
	}

	public ExpansionRegion(org.eclipse.uml2.uml.ExpansionRegion base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ExpansionRegion getBase() {
		return (org.eclipse.uml2.uml.ExpansionRegion) this.base;
	}

	public String getMode() {
		return this.getBase().getMode().toString();
	}

	public void setMode(String mode) {
		this.getBase().setMode(
				fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind
						.valueOf(mode));
	}

	public List< org.modeldriven.alf.uml.ExpansionNode> getOutputElement
() {
		List< org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList< org.modeldriven.alf.uml.ExpansionNode>();
		for (org.eclipse.uml2.uml.ExpansionNode
 element: this.getBase().getOutputElement
s()) {
			list.add( new ExpansionNode(element)
);
		}
		return list;
	}

	public void addOutputElement
( org.modeldriven.alf.uml.ExpansionNode outputElement) {
		this.getBase().getOutputElement
s.add( outputElement == null? null: ((ExpansionNode)outputElement).getBase()
);
	}

	public List< org.modeldriven.alf.uml.ExpansionNode> getInputElement
() {
		List< org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList< org.modeldriven.alf.uml.ExpansionNode>();
		for (org.eclipse.uml2.uml.ExpansionNode
 element: this.getBase().getInputElement
s()) {
			list.add( new ExpansionNode(element)
);
		}
		return list;
	}

	public void addInputElement
( org.modeldriven.alf.uml.ExpansionNode inputElement) {
		this.getBase().getInputElement
s.add( inputElement == null? null: ((ExpansionNode)inputElement).getBase()
);
	}

}
