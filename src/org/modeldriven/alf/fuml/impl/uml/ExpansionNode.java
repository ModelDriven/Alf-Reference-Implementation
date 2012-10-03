/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;


public class ExpansionNode extends ObjectNode implements
		org.modeldriven.alf.uml.ExpansionNode {
	public ExpansionNode() {
		this(
				new fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode());
	}

	public ExpansionNode(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode getBase() {
		return (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode) this.base;
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsOutput() {
		return (ExpansionRegion)this.wrap(this.getBase().regionAsOutput);
	}

	public void setRegionAsOutput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsOutput) {
	    if (regionAsOutput != null) {
	        regionAsOutput.addOutputElement(this);
	    }
	}

	public org.modeldriven.alf.uml.ExpansionRegion getRegionAsInput() {
		return (ExpansionRegion)this.wrap(this.getBase().regionAsInput);
	}

	public void setRegionAsInput(
			org.modeldriven.alf.uml.ExpansionRegion regionAsInput) {
	    if (regionAsInput != null) {
	        regionAsInput.addInputElement(this);
	    }
	}

}
