package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ExecutableNode;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.StructuredActivityNode;

public interface LoopNode extends StructuredActivityNode {
	public boolean getIsTestedFirst();

	public void setIsTestedFirst(boolean isTestedFirst);

	public OutputPin getDecider();

	public void setDecider(OutputPin decider);

	public List<ExecutableNode> getTest();

	public void addTest(ExecutableNode test);

	public List<OutputPin> getBodyOutput();

	public void addBodyOutput(OutputPin bodyOutput);

	public List<InputPin> getLoopVariableInput();

	public void addLoopVariableInput(InputPin loopVariableInput);

	public List<ExecutableNode> getBodyPart();

	public void addBodyPart(ExecutableNode bodyPart);

	public List<OutputPin> getResult();

	public void addResult(OutputPin result);

	public List<OutputPin> getLoopVariable();

	public void addLoopVariable(OutputPin loopVariable);

	public List<ExecutableNode> getSetupPart();
}
