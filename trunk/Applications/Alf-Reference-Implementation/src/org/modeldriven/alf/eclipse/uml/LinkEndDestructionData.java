package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LinkEndDestructionData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndDestructionData {
	public LinkEndDestructionData() {
		this(UMLFactory.eINSTANCE.createLinkEndDestructionData());
	}

	public LinkEndDestructionData(
			fUML.Syntax.Actions.IntermediateActions.LinkEndDestructionData base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LinkEndDestructionData getBase() {
		return (org.eclipse.uml2.uml.LinkEndDestructionData) this.base;
	}

	public boolean getIsDestroyDuplicates() {
		return this.getBase().getIsDestroyDuplicates();
	}

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates) {
		this.getBase().setIsDestroyDuplicates(isDestroyDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getDestroyAt() {
		return new InputPin(this.getBase().getDestroyAt());
	}

	public void setDestroyAt(org.modeldriven.alf.uml.InputPin destroyAt) {
		this.getBase().setDestroyAt(
				destroyAt == null ? null : ((InputPin) destroyAt).getBase());
	}

}
