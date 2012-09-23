package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.LinkEndData;

public class LinkEndDestructionData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndDestructionData {
	public LinkEndDestructionData() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData());
	}

	public LinkEndDestructionData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData) this.base;
	}

	public boolean getIsDestroyDuplicates() {
		return this.getBase().isDestroyDuplicates;
	}

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates) {
		this.getBase().setIsDestroyDuplicates(isDestroyDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getDestroyAt() {
		return new InputPin(this.getBase().destroyAt);
	}

	public void setDestroyAt(org.modeldriven.alf.uml.InputPin destroyAt) {
		this.getBase().setDestroyAt(((InputPin) destroyAt).getBase());
	}

}
