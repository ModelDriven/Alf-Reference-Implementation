package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class DestroyLinkAction extends WriteLinkAction implements
		org.modeldriven.alf.uml.DestroyLinkAction {
	public DestroyLinkAction() {
		this(UMLFactory.eINSTANCE.createDestroyLinkAction());
	}

	public DestroyLinkAction(
			fUML.Syntax.Actions.IntermediateActions.DestroyLinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.DestroyLinkAction getBase() {
		return (org.eclipse.uml2.uml.DestroyLinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndDestructionData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndDestructionData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndDestructionData>();
		for (org.eclipse.uml2.uml.LinkEndDestructionData element : this
				.getBase().getEndData()) {
			list.add(new LinkEndDestructionData(element));
		}
		return list;
	}

	public void addEndData(
			org.modeldriven.alf.uml.LinkEndDestructionData endData) {
		this.getBase().addEndData(
				endData == null ? null : ((LinkEndDestructionData) endData)
						.getBase());
	}

}
