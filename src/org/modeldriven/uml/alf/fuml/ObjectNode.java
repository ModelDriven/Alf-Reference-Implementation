package org.modeldriven.uml.alf.fuml;


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
