package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LinkAction extends Action implements
		org.modeldriven.alf.uml.LinkAction {

	public LinkAction(fUML.Syntax.Actions.IntermediateActions.LinkAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LinkAction getBase() {
		return (org.eclipse.uml2.uml.LinkAction) this.base;
	}

	public List<org.modeldriven.alf.uml.LinkEndData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndData>();
		for (org.eclipse.uml2.uml.LinkEndData element : this.getBase()
				.getEndData()) {
			list.add(new LinkEndData(element));
		}
		return list;
	}

	public void addEndData(org.modeldriven.alf.uml.LinkEndData endData) {
		this.getBase().addEndData(
				endData == null ? null : ((LinkEndData) endData).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getInputValue() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getInputValue()) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addInputValue(org.modeldriven.alf.uml.InputPin inputValue) {
		this.getBase().addInputValue(
				inputValue == null ? null : ((InputPin) inputValue).getBase());
	}

}
