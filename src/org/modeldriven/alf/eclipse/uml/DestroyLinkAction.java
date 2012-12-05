package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class DestroyLinkAction extends WriteLinkAction implements
		org.modeldriven.alf.uml.DestroyLinkAction {
	public DestroyLinkAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createDestroyLinkAction());
	}

	public DestroyLinkAction(org.eclipse.uml2.uml.DestroyLinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.DestroyLinkAction getBase() {
		return (org.eclipse.uml2.uml.DestroyLinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndDestructionData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndDestructionData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndDestructionData>();
		for (org.eclipse.uml2.uml.LinkEndDestructionData element : this
				.getBase().getEndDatas()) {
			list
					.add((org.modeldriven.alf.uml.LinkEndDestructionData) wrap(element));
		}
		return list;
	}

	public void addEndData(
			org.modeldriven.alf.uml.LinkEndDestructionData endData) {
		this.getBase().getEndDatas().add(
				endData == null ? null : ((LinkEndDestructionData) endData)
						.getBase());
	}

}
