/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class CallAction extends InvocationAction implements
		org.modeldriven.alf.uml.CallAction {

	public CallAction(fUML.Syntax.Actions.BasicActions.CallAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.CallAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.CallAction) this.base;
	}

	public boolean getIsSynchronous() {
		return this.getBase().isSynchronous;
	}

	public void setIsSynchronous(boolean isSynchronous) {
		this.getBase().isSynchronous = isSynchronous;
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().result) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().addResult(result==null? null: ((OutputPin) result).getBase());
	}

}
