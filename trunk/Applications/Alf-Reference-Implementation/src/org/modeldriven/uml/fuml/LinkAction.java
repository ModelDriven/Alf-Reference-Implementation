package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.LinkEndData;

public class LinkAction extends Action implements
		org.modeldriven.alf.uml.LinkAction {

	public LinkAction(fUML.Syntax.Actions.IntermediateActions.LinkAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.LinkAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.LinkAction) this.base;
	}

	public List<? extends org.modeldriven.alf.uml.LinkEndData> getEndData() {
		List<org.modeldriven.alf.uml.LinkEndData> list = new ArrayList<org.modeldriven.alf.uml.LinkEndData>();
		for (fUML.Syntax.Actions.IntermediateActions.LinkEndData element : this
				.getBase().endData) {
			list.add(new LinkEndData(element));
		}
		return list;
	}

	public void addEndData(org.modeldriven.alf.uml.LinkEndData endData) {
		this.getBase().addEndData(((LinkEndData) endData).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getInputValue() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().inputValue) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addInputValue(org.modeldriven.alf.uml.InputPin inputValue) {
		this.getBase().addInputValue(((InputPin) inputValue).getBase());
	}

}
