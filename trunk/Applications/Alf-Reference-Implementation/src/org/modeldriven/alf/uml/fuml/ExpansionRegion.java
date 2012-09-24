/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml.fuml;

import java.util.ArrayList;
import java.util.List;

public class ExpansionRegion extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ExpansionRegion {
	public ExpansionRegion() {
		this(
				new fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion());
	}

	public ExpansionRegion(
			fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion base) {
		super(base);
	}

	public fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion getBase() {
		return (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion) this.base;
	}

	public String getMode() {
        String mode = this.getBase().mode.toString();
        return mode == null? null: mode;
	}

	public void setMode(String mode) {
		this.getBase().setMode(mode == null? null:
				fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind
						.valueOf(mode));
	}

	public List<org.modeldriven.alf.uml.ExpansionNode> getOutputElement() {
		List<org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList<org.modeldriven.alf.uml.ExpansionNode>();
		for (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode element : this
				.getBase().outputElement) {
			list.add(new ExpansionNode(element));
		}
		return list;
	}

	public void addOutputElement(org.modeldriven.alf.uml.ExpansionNode outputElement) {
		this.getBase().addOutputElement(
				((ExpansionNode) outputElement).getBase());
	}

	public List<org.modeldriven.alf.uml.ExpansionNode> getInputElement() {
		List<org.modeldriven.alf.uml.ExpansionNode> list = new ArrayList<org.modeldriven.alf.uml.ExpansionNode>();
		for (fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode element : this
				.getBase().inputElement) {
			list.add(new ExpansionNode(element));
		}
		return list;
	}

	public void addInputElement(org.modeldriven.alf.uml.ExpansionNode inputElement) {
		this.getBase()
				.addInputElement(inputElement==null? null: ((ExpansionNode) inputElement).getBase());
	}

}
