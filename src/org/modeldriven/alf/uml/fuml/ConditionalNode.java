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

public class ConditionalNode extends StructuredActivityNode implements
		org.modeldriven.alf.uml.ConditionalNode {
	public ConditionalNode() {
		this(
				new fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode());
	}

	public ConditionalNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode) this.base;
	}

	public boolean getIsDeterminate() {
		return this.getBase().isDeterminate;
	}

	public void setIsDeterminate(boolean isDeterminate) {
		this.getBase().setIsDeterminate(isDeterminate);
	}

	public boolean getIsAssured() {
		return this.getBase().isAssured;
	}

	public void setIsAssured(boolean isAssured) {
		this.getBase().setIsAssured(isAssured);
	}

	public List<org.modeldriven.alf.uml.Clause> getClause() {
		List<org.modeldriven.alf.uml.Clause> list = new ArrayList<org.modeldriven.alf.uml.Clause>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.Clause element : this
				.getBase().clause) {
			list.add(new Clause(element));
		}
		return list;
	}

	public void addClause(org.modeldriven.alf.uml.Clause clause) {
		this.getBase().addClause(clause==null? null: ((Clause) clause).getBase());
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
