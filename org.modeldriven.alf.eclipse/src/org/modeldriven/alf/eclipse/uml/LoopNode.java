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

public class LoopNode extends StructuredActivityNode implements
		org.modeldriven.alf.uml.LoopNode {
	public LoopNode() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLoopNode());
	}

	public LoopNode(org.eclipse.uml2.uml.LoopNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LoopNode getBase() {
		return (org.eclipse.uml2.uml.LoopNode) this.base;
	}

	public boolean getIsTestedFirst() {
		return this.getBase().isTestedFirst();
	}

	public void setIsTestedFirst(boolean isTestedFirst) {
		this.getBase().setIsTestedFirst(isTestedFirst);
	}

	public org.modeldriven.alf.uml.OutputPin getDecider() {
		return (org.modeldriven.alf.uml.OutputPin) wrap(this.getBase()
				.getDecider());
	}

	public void setDecider(org.modeldriven.alf.uml.OutputPin decider) {
		this.getBase().setDecider(
				decider == null ? null : ((OutputPin) decider).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getTest() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getTests()) {
			list.add((org.modeldriven.alf.uml.ExecutableNode) wrap(element));
		}
		return list;
	}

	public void addTest(org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().getTests().add(
				test == null ? null : ((ExecutableNode) test).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getBodyOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getBodyOutputs()) {
			list.add((org.modeldriven.alf.uml.OutputPin) wrap(element));
		}
		return list;
	}

	public void addBodyOutput(org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().getBodyOutputs().add(
				bodyOutput == null ? null : ((OutputPin) bodyOutput).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getLoopVariableInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getLoopVariableInputs()) {
			list.add((org.modeldriven.alf.uml.InputPin) wrap(element));
		}
		return list;
	}

	public void addLoopVariableInput(
			org.modeldriven.alf.uml.InputPin loopVariableInput) {
		this.getBase().getLoopVariableInputs().add(
				loopVariableInput == null ? null
						: ((InputPin) loopVariableInput).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getBodyPart() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getBodyParts()) {
			list.add((org.modeldriven.alf.uml.ExecutableNode) wrap(element));
		}
		return list;
	}

	public void addBodyPart(org.modeldriven.alf.uml.ExecutableNode bodyPart) {
		this.getBase().getBodyParts()
				.add(
						bodyPart == null ? null : ((ExecutableNode) bodyPart)
								.getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		List<org.eclipse.uml2.uml.OutputPin> loopVariables = this.getBase().getLoopVariables();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getResults()) {
		    // NOTE: This test corrects for the loop variables that are stored
		    // as structured node outputs.
		    if (!loopVariables.contains(element)) {
		        list.add((org.modeldriven.alf.uml.OutputPin) wrap(element));
		    }
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().getResults().add(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getLoopVariable() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getLoopVariables()) {
			list.add((org.modeldriven.alf.uml.OutputPin) wrap(element));
		}
		return list;
	}

	public void addLoopVariable(org.modeldriven.alf.uml.OutputPin loopVariable) {
	    org.eclipse.uml2.uml.OutputPin element = ((OutputPin) loopVariable).getBase();
		this.getBase().getLoopVariables().add(element);
		
		// NOTE: Since the UML 2.4.1 metamodel does not define any owner for
		// loop variables, they are stored here in the structured node outputs
		// for the loop node. However, this also results in them being added to
		// the results for the loop node, since LoopNode::result redefines
		// StructuredNodeOutput.
		this.getBase().getStructuredNodeOutputs().add(element);
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getSetupPart() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getSetupParts()) {
			list.add((org.modeldriven.alf.uml.ExecutableNode) wrap(element));
		}
		return list;
	}

	public void addSetupPart(org.modeldriven.alf.uml.ExecutableNode setupPart) {
		this.getBase().getSetupParts().add(
				setupPart == null ? null : ((ExecutableNode) setupPart)
						.getBase());
	}

}
