/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;

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
