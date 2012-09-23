package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.LinkEndData;

public class LinkEndCreationData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndCreationData {
	public LinkEndCreationData() {
		this(new fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData());
	}

	public LinkEndCreationData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll;
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public org.modeldriven.alf.uml.InputPin getInsertAt() {
		return new InputPin(this.getBase().insertAt);
	}

	public void setInsertAt(org.modeldriven.alf.uml.InputPin insertAt) {
		this.getBase().setInsertAt(((InputPin) insertAt).getBase());
	}

}
