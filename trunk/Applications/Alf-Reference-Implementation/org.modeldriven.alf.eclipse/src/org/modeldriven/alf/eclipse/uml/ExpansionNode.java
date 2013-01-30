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

public class ExpansionNode extends ObjectNode implements
		org.modeldriven.alf.uml.ExpansionNode {
	public ExpansionNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createExpansionNode());
	}

	public ExpansionNode(org.eclipse.uml2.uml.ExpansionNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ExpansionNode getBase() {
		return (org.eclipse.uml2.uml.ExpansionNode) this.base;
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsOutput() {
		return (org.modeldriven.alf.uml.ExpansionRegion) wrap(this.getBase()
				.getRegionAsOutput());
	}

	public void setRegionAsOutput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsOutput) {
		this.getBase().setRegionAsOutput(
				regionAsOutput == null ? null
						: ((ExpansionRegion) regionAsOutput).getBase());
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsInput() {
		return (org.modeldriven.alf.uml.ExpansionRegion) wrap(this.getBase()
				.getRegionAsInput());
	}

	public void setRegionAsInput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsInput) {
		this.getBase().setRegionAsInput(
				regionAsInput == null ? null
						: ((ExpansionRegion) regionAsInput).getBase());
	}

}
