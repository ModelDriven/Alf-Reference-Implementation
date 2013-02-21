/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

public class DecisionNode extends ControlNode implements
		org.modeldriven.alf.uml.DecisionNode {
	public DecisionNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createDecisionNode());
	}

	public DecisionNode(org.eclipse.uml2.uml.DecisionNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.DecisionNode getBase() {
		return (org.eclipse.uml2.uml.DecisionNode) this.base;
	}

	public org.modeldriven.alf.uml.Behavior getDecisionInput() {
		return (org.modeldriven.alf.uml.Behavior) wrap(this.getBase()
				.getDecisionInput());
	}

	public void setDecisionInput(org.modeldriven.alf.uml.Behavior decisionInput) {
		this.getBase().setDecisionInput(
				decisionInput == null ? null : ((Behavior) decisionInput)
						.getBase());
	}

	public org.modeldriven.alf.uml.ObjectFlow getDecisionInputFlow() {
		return (org.modeldriven.alf.uml.ObjectFlow) wrap(this.getBase()
				.getDecisionInputFlow());
	}

	public void setDecisionInputFlow(
			org.modeldriven.alf.uml.ObjectFlow decisionInputFlow) {
		this.getBase().setDecisionInputFlow(
				decisionInputFlow == null ? null
						: ((ObjectFlow) decisionInputFlow).getBase());
	}

}
