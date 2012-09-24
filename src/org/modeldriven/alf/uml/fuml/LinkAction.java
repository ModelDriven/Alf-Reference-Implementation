/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;

import java.util.ArrayList;
import java.util.List;

public abstract class LinkAction extends Action implements
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
