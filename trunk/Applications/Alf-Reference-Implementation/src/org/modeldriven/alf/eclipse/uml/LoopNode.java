package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LoopNode extends StructuredActivityNode implements
		org.modeldriven.alf.uml.LoopNode {
	public LoopNode() {
		this(UMLFactory.eINSTANCE.createLoopNode());
	}

	public LoopNode(
			fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LoopNode getBase() {
		return (org.eclipse.uml2.uml.LoopNode) this.base;
	}

	public boolean getIsTestedFirst() {
		return this.getBase().getIsTestedFirst();
	}

	public void setIsTestedFirst(boolean isTestedFirst) {
		this.getBase().setIsTestedFirst(isTestedFirst);
	}

	public org.modeldriven.alf.uml.OutputPin getDecider() {
		return new OutputPin(this.getBase().getDecider());
	}

	public void setDecider(org.modeldriven.alf.uml.OutputPin decider) {
		this.getBase().setDecider(
				decider == null ? null : ((OutputPin) decider).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getTest() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getTest()) {
			list.add(new ExecutableNode(element));
		}
		return list;
	}

	public void addTest(org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().addTest(
				test == null ? null : ((ExecutableNode) test).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getBodyOutput() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getBodyOutput()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addBodyOutput(org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().addBodyOutput(
				bodyOutput == null ? null : ((OutputPin) bodyOutput).getBase());
	}

	public List<org.modeldriven.alf.uml.InputPin> getLoopVariableInput() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getLoopVariableInput()) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addLoopVariableInput(
			org.modeldriven.alf.uml.InputPin loopVariableInput) {
		this.getBase().addLoopVariableInput(
				loopVariableInput == null ? null
						: ((InputPin) loopVariableInput).getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getBodyPart() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getBodyPart()) {
			list.add(new ExecutableNode(element));
		}
		return list;
	}

	public void addBodyPart(org.modeldriven.alf.uml.ExecutableNode bodyPart) {
		this.getBase()
				.addBodyPart(
						bodyPart == null ? null : ((ExecutableNode) bodyPart)
								.getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getResult()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().addResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public List<org.modeldriven.alf.uml.OutputPin> getLoopVariable() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getLoopVariable()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addLoopVariable(org.modeldriven.alf.uml.OutputPin loopVariable) {
		this.getBase().addLoopVariable(
				loopVariable == null ? null : ((OutputPin) loopVariable)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.ExecutableNode> getSetupPart() {
		List<org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList<org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode element : this.getBase()
				.getSetupPart()) {
			list.add(new ExecutableNode(element));
		}
		return list;
	}

	public void addSetupPart(org.modeldriven.alf.uml.ExecutableNode setupPart) {
		this.getBase().addSetupPart(
				setupPart == null ? null : ((ExecutableNode) setupPart)
						.getBase());
	}

}
