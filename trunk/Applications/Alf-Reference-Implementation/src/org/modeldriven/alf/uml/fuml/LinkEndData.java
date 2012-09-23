package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.Property;

public class LinkEndData extends Element implements
		org.modeldriven.alf.uml.LinkEndData {
	public LinkEndData() {
		this(new fUML.Syntax.Actions.IntermediateActions.LinkEndData());
	}

	public LinkEndData(fUML.Syntax.Actions.IntermediateActions.LinkEndData base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkEndData getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkEndData) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return new InputPin(this.getBase().value);
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.Property getEnd() {
		return new Property(this.getBase().end);
	}

	public void setEnd(org.modeldriven.alf.uml.Property end) {
		this.getBase().setEnd(((Property) end).getBase());
	}

}
