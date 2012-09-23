package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.ActivityNode;
import org.modeldriven.uml.fuml.Type;

public class ObjectNode extends ActivityNode implements
		org.modeldriven.alf.uml.ObjectNode {

	public ObjectNode(
			fUML.Syntax.Activities.IntermediateActivities.ObjectNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.ObjectNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.ObjectNode) this.base;
	}

	public org.modeldriven.alf.uml.Type getType() {
		return new Type(this.getBase().typedElement.type);
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(((Type) type).getBase());
	}

}
