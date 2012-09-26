/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;

import java.util.ArrayList;
import java.util.List;

public class AcceptEventAction extends Action implements
		org.modeldriven.alf.uml.AcceptEventAction {
	public AcceptEventAction() {
		this(new fUML.Syntax.Actions.CompleteActions.AcceptEventAction());
	}

	public AcceptEventAction(
			fUML.Syntax.Actions.CompleteActions.AcceptEventAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.CompleteActions.AcceptEventAction getBase() {
		return (fUML.Syntax.Actions.CompleteActions.AcceptEventAction) this.base;
	}

	public boolean getIsUnmarshall() {
		return this.getBase().isUnmarshall;
	}

	public void setIsUnmarshall(boolean isUnmarshall) {
		this.getBase().setIsUnmarshall(isUnmarshall);
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

	public List<org.modeldriven.alf.uml.Trigger> getTrigger() {
		List<org.modeldriven.alf.uml.Trigger> list = new ArrayList<org.modeldriven.alf.uml.Trigger>();
		for (fUML.Syntax.CommonBehaviors.Communications.Trigger element : this
				.getBase().trigger) {
			list.add(new Trigger(element));
		}
		return list;
	}

	public void addTrigger(org.modeldriven.alf.uml.Trigger trigger) {
		this.getBase().addTrigger(trigger==null? null: ((Trigger) trigger).getBase());
	}

}
