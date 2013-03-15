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

public class AcceptEventAction extends Action implements
		org.modeldriven.alf.uml.AcceptEventAction {
	public AcceptEventAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createAcceptEventAction());
	}

	public AcceptEventAction(org.eclipse.uml2.uml.AcceptEventAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.AcceptEventAction getBase() {
		return (org.eclipse.uml2.uml.AcceptEventAction) this.base;
	}

	public boolean getIsUnmarshall() {
		return this.getBase().isUnmarshall();
	}

	public void setIsUnmarshall(boolean isUnmarshall) {
		this.getBase().setIsUnmarshall(isUnmarshall);
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getResults()) {
			list.add((org.modeldriven.alf.uml.OutputPin) wrap(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().getResults().add(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public List<org.modeldriven.alf.uml.Trigger> getTrigger() {
		List<org.modeldriven.alf.uml.Trigger> list = new ArrayList<org.modeldriven.alf.uml.Trigger>();
		for (org.eclipse.uml2.uml.Trigger element : this.getBase()
				.getTriggers()) {
			list.add((org.modeldriven.alf.uml.Trigger) wrap(element));
		}
		return list;
	}

	public void addTrigger(org.modeldriven.alf.uml.Trigger trigger) {
		this.getBase().getTriggers().add(
				trigger == null ? null : ((Trigger) trigger).getBase());
	}

}
