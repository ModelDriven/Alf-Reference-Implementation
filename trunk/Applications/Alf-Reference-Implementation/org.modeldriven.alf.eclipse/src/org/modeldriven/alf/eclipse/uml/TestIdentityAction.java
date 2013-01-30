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

public class TestIdentityAction extends Action implements
		org.modeldriven.alf.uml.TestIdentityAction {
	public TestIdentityAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createTestIdentityAction());
	}

	public TestIdentityAction(org.eclipse.uml2.uml.TestIdentityAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.TestIdentityAction getBase() {
		return (org.eclipse.uml2.uml.TestIdentityAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getSecond() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getSecond());
	}

	public void setSecond(org.modeldriven.alf.uml.InputPin second) {
		this.getBase().setSecond(
				second == null ? null : ((InputPin) second).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getFirst() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getFirst());
	}

	public void setFirst(org.modeldriven.alf.uml.InputPin first) {
		this.getBase().setFirst(
				first == null ? null : ((InputPin) first).getBase());
	}

}
