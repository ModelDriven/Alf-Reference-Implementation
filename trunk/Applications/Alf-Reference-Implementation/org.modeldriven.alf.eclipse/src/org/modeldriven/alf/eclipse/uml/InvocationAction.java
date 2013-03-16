/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InvocationAction extends Action implements
		org.modeldriven.alf.uml.InvocationAction {

	public InvocationAction(org.eclipse.uml2.uml.InvocationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InvocationAction getBase() {
		return (org.eclipse.uml2.uml.InvocationAction) this.base;
	}

	public List<org.modeldriven.alf.uml.InputPin> getArgument() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getArguments()) {
			list.add((org.modeldriven.alf.uml.InputPin) wrap(element));
		}
		return list;
	}

	public void addArgument(org.modeldriven.alf.uml.InputPin argument) {
		this.getBase().getArguments().add(
				argument == null ? null : ((InputPin) argument).getBase());
	}

}
