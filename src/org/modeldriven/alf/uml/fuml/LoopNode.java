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

public class LoopNode extends StructuredActivityNode implements
		org.modeldriven.alf.uml.LoopNode {
	public LoopNode() {
		this(new fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode());
	}

	public LoopNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode base) {
		super(base);
	}

	public fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode getBase() {
		return (fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode) this.base;
	}

	public boolean getIsTestedFirst() {
		return this.getBase().isTestedFirst;
	}

	public void setIsTestedFirst(boolean isTestedFirst) {
		this.getBase().setIsTestedFirst(isTestedFirst);
	}

	public org.modeldriven.alf.uml.OutputPin getDecider() {
		return new OutputPin(this.getBase().decider);
	}

	public void setDecider(org.modeldriven.alf.uml.OutputPin decider) {
		this.getBase().setDecider(((OutputPin) decider).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getTest() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().test) {
			list.add((ExecutableNode)this.wrap(element));
		}
		return list;
	}

	public void addTest(org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().addTest(((ExecutableNode) test).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getBodyOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().bodyOutput) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addBodyOutput(org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().addBodyOutput(((OutputPin) bodyOutput).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getLoopVariableInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().loopVariableInput) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addLoopVariableInput(
			org.modeldriven.alf.uml.InputPin loopVariableInput) {
		this.getBase().addLoopVariableInput(
				((InputPin) loopVariableInput).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getBodyPart() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().bodyPart) {
			list.add((ExecutableNode)this.wrap(element));
		}
		return list;
	}

	public void addBodyPart(org.modeldriven.alf.uml.ExecutableNode bodyPart) {
		this.getBase().addBodyPart(((ExecutableNode) bodyPart).getBase());
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
		this.getBase().addResult(((OutputPin) result).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getLoopVariable() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().loopVariable) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addLoopVariable(org.modeldriven.alf.uml.OutputPin loopVariable) {
		this.getBase().addLoopVariable(((OutputPin) loopVariable).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getSetupPart() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode element : this
				.getBase().setupPart) {
			list.add((ExecutableNode)this.wrap(element));
		}
		return list;
	}
}
