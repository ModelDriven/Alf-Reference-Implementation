/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public abstract class InvocationAction extends Action implements
		org.modeldriven.alf.uml.InvocationAction {

	public InvocationAction(
			fUML.Syntax.Actions.BasicActions.InvocationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.InvocationAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.InvocationAction) this.base;
	}

	public List<org.modeldriven.alf.uml.InputPin> getArgument() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().argument) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addArgument(org.modeldriven.alf.uml.InputPin argument) {
		this.getBase().addArgument(((InputPin) argument).getBase());
	}

}
