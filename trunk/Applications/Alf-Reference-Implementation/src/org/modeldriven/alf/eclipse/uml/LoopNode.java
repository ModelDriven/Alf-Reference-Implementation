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

	public List< org.modeldriven.alf.uml.ExecutableNode> getTest
() {
		List< org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList< org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode
 element: this.getBase().getTest
s()) {
			list.add( new ExecutableNode(element)
);
		}
		return list;
	}

	public void addTest
( org.modeldriven.alf.uml.ExecutableNode test) {
		this.getBase().getTest
s.add( test == null? null: ((ExecutableNode)test).getBase()
);
	}

	public List< org.modeldriven.alf.uml.OutputPin> getBodyOutput
() {
		List< org.modeldriven.alf.uml.OutputPin> list = new ArrayList< org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin
 element: this.getBase().getBodyOutput
s()) {
			list.add( new OutputPin(element)
);
		}
		return list;
	}

	public void addBodyOutput
( org.modeldriven.alf.uml.OutputPin bodyOutput) {
		this.getBase().getBodyOutput
s.add( bodyOutput == null? null: ((OutputPin)bodyOutput).getBase()
);
	}

	public List< org.modeldriven.alf.uml.InputPin> getLoopVariableInput
() {
		List< org.modeldriven.alf.uml.InputPin> list = new ArrayList< org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin
 element: this.getBase().getLoopVariableInput
s()) {
			list.add( new InputPin(element)
);
		}
		return list;
	}

	public void addLoopVariableInput
( org.modeldriven.alf.uml.InputPin loopVariableInput) {
		this.getBase().getLoopVariableInput
s.add( loopVariableInput == null? null: ((InputPin)loopVariableInput).getBase()
);
	}

	public List< org.modeldriven.alf.uml.ExecutableNode> getBodyPart
() {
		List< org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList< org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode
 element: this.getBase().getBodyPart
s()) {
			list.add( new ExecutableNode(element)
);
		}
		return list;
	}

	public void addBodyPart
( org.modeldriven.alf.uml.ExecutableNode bodyPart) {
		this.getBase().getBodyPart
s.add( bodyPart == null? null: ((ExecutableNode)bodyPart).getBase()
);
	}

	public List< org.modeldriven.alf.uml.OutputPin> getResult
() {
		List< org.modeldriven.alf.uml.OutputPin> list = new ArrayList< org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin
 element: this.getBase().getResult
s()) {
			list.add( new OutputPin(element)
);
		}
		return list;
	}

	public void addResult
( org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().getResult
s.add( result == null? null: ((OutputPin)result).getBase()
);
	}

	public List< org.modeldriven.alf.uml.OutputPin> getLoopVariable
() {
		List< org.modeldriven.alf.uml.OutputPin> list = new ArrayList< org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin
 element: this.getBase().getLoopVariable
s()) {
			list.add( new OutputPin(element)
);
		}
		return list;
	}

	public void addLoopVariable
( org.modeldriven.alf.uml.OutputPin loopVariable) {
		this.getBase().getLoopVariable
s.add( loopVariable == null? null: ((OutputPin)loopVariable).getBase()
);
	}

	public List< org.modeldriven.alf.uml.ExecutableNode> getSetupPart
() {
		List< org.modeldriven.alf.uml.ExecutableNode> list = new ArrayList< org.modeldriven.alf.uml.ExecutableNode>();
		for (org.eclipse.uml2.uml.ExecutableNode
 element: this.getBase().getSetupPart
s()) {
			list.add( new ExecutableNode(element)
);
		}
		return list;
	}

	public void addSetupPart
( org.modeldriven.alf.uml.ExecutableNode setupPart) {
		this.getBase().getSetupPart
s.add( setupPart == null? null: ((ExecutableNode)setupPart).getBase()
);
	}

}
