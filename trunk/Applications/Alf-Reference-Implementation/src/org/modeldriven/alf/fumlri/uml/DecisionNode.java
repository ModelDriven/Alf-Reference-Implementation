/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fumlri.uml;


public class DecisionNode extends ControlNode implements
		org.modeldriven.alf.uml.DecisionNode {
	public DecisionNode() {
		this(new fUML.Syntax.Activities.IntermediateActivities.DecisionNode());
	}

	public DecisionNode(
			fUML.Syntax.Activities.IntermediateActivities.DecisionNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.IntermediateActivities.DecisionNode getBase() {
		return (fUML.Syntax.Activities.IntermediateActivities.DecisionNode) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getDecisionInput() {
		return (Behavior)this.wrap(this.getBase().decisionInput);
	}

	public void setDecisionInput(org.modeldriven.alf.uml.Behavior decisionInput) {
		this.getBase().setDecisionInput(decisionInput==null? null: ((Behavior) decisionInput).getBase());
	}

	public org.modeldriven.alf.uml.ObjectFlow getDecisionInputFlow() {
		return (ObjectFlow)this.wrap(this.getBase().decisionInputFlow);
	}

	public void setDecisionInputFlow(
			org.modeldriven.alf.uml.ObjectFlow decisionInputFlow) {
		this.getBase().setDecisionInputFlow(
				((ObjectFlow) decisionInputFlow).getBase());
	}

}
