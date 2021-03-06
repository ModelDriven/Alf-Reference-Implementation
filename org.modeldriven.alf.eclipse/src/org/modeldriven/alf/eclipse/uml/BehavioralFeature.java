/*******************************************************************************
 * Copyright 2011, 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class BehavioralFeature extends Feature implements
		org.modeldriven.alf.uml.BehavioralFeature {

	public BehavioralFeature(org.eclipse.uml2.uml.BehavioralFeature base) {
		super(base);
	}

	public org.eclipse.uml2.uml.BehavioralFeature getBase() {
		return (org.eclipse.uml2.uml.BehavioralFeature) this.base;
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (org.eclipse.uml2.uml.Parameter element : this.getBase()
				.getOwnedParameters()) {
			list.add((org.modeldriven.alf.uml.Parameter) wrap(element));
		}
		return list;
	}

	public void addOwnedParameter(
			org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase().getOwnedParameters().add(
				ownedParameter == null ? null : ((Parameter) ownedParameter)
						.getBase());
	}

	public boolean getIsAbstract() {
		return this.getBase().isAbstract();
	}

	public void setIsAbstract(boolean isAbstract) {
		this.getBase().setIsAbstract(isAbstract);
	}

	public List<org.modeldriven.alf.uml.Behavior> getMethod() {
		List<org.modeldriven.alf.uml.Behavior> list = new ArrayList<org.modeldriven.alf.uml.Behavior>();
		for (org.eclipse.uml2.uml.Behavior element : this.getBase()
				.getMethods()) {
			list.add((org.modeldriven.alf.uml.Behavior) wrap(element));
		}
		return list;
	}

	public void addMethod(org.modeldriven.alf.uml.Behavior method) {
		this.getBase().getMethods().add(
				method == null ? null : ((Behavior) method).getBase());
	}

	public String getConcurrency() {
		return this.getBase().getConcurrency().toString();
	}

	public void setConcurrency(String concurrency) {
		this.getBase().setConcurrency(
				org.eclipse.uml2.uml.CallConcurrencyKind.get(concurrency));
	}

}
