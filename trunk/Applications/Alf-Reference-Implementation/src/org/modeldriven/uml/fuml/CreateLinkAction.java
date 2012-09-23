package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LinkEndCreationData;
import org.modeldriven.uml.fuml.WriteLinkAction;

public class CreateLinkAction extends WriteLinkAction implements
		org.modeldriven.alf.uml.CreateLinkAction {
	public CreateLinkAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.CreateLinkAction());
	}

	public CreateLinkAction(
			fUML.Syntax.Actions.IntermediateActions.CreateLinkAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.CreateLinkAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.CreateLinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndCreationData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndCreationData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndCreationData>();
		for (fUML.Syntax.Actions.IntermediateActions.LinkEndCreationData element : this
				.getBase().endData) {
			list.add(new LinkEndCreationData(element));
		}
		return list;
	}

	public void addEndData(org.modeldriven.alf.uml.LinkEndCreationData endData) {
		this.getBase().addEndData(((LinkEndCreationData) endData).getBase());
	}

}
