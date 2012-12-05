package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LinkEndDestructionData extends LinkEndData implements
		org.modeldriven.alf.uml.LinkEndDestructionData {
	public LinkEndDestructionData() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createLinkEndDestructionData());
	}

	public LinkEndDestructionData(
			org.eclipse.uml2.uml.LinkEndDestructionData base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LinkEndDestructionData getBase() {
		return (org.eclipse.uml2.uml.LinkEndDestructionData) this.base;
	}

	public boolean getIsDestroyDuplicates() {
		return this.getBase().isDestroyDuplicates();
	}

	public void setIsDestroyDuplicates(boolean isDestroyDuplicates) {
		this.getBase().setIsDestroyDuplicates(isDestroyDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getDestroyAt() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getDestroyAt());
	}

	public void setDestroyAt(org.modeldriven.alf.uml.InputPin destroyAt) {
		this.getBase().setDestroyAt(
				destroyAt == null ? null : ((InputPin) destroyAt).getBase());
	}

}
