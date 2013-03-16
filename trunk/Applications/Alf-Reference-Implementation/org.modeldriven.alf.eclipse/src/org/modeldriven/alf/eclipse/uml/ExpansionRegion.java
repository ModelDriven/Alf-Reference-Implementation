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

public class ExpansionRegion extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ExpansionRegion {
	public ExpansionRegion() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createExpansionRegion());
	}

	public ExpansionRegion(org.eclipse.uml2.uml.ExpansionRegion base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ExpansionRegion getBase() {
		return (org.eclipse.uml2.uml.ExpansionRegion) this.base;
	}

	public String getMode() {
		return this.getBase().getMode().toString();
	}

	public void setMode(String mode) {
		this.getBase().setMode(org.eclipse.uml2.uml.ExpansionKind.get(mode));
	}

	public List<org.modeldriven.alf.uml.ExpansionNode> getOutputElement() {
		List<org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList<org.modeldriven.alf.uml.ExpansionNode>();
		for (org.eclipse.uml2.uml.ExpansionNode element : this.getBase()
				.getOutputElements()) {
			list.add((org.modeldriven.alf.uml.ExpansionNode) wrap(element));
		}
		return list;
	}

	public void addOutputElement(
			org.modeldriven.alf.uml.ExpansionNode outputElement) {
		this.getBase().getOutputElements().add(
				outputElement == null ? null : ((ExpansionNode) outputElement)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.ExpansionNode> getInputElement() {
		List<org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList<org.modeldriven.alf.uml.ExpansionNode>();
		for (org.eclipse.uml2.uml.ExpansionNode element : this.getBase()
				.getInputElements()) {
			list.add((org.modeldriven.alf.uml.ExpansionNode) wrap(element));
		}
		return list;
	}

	public void addInputElement(
			org.modeldriven.alf.uml.ExpansionNode inputElement) {
		this.getBase().getInputElements().add(
				inputElement == null ? null : ((ExpansionNode) inputElement)
						.getBase());
	}

}
