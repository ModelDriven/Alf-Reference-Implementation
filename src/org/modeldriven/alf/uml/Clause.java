package org.modeldriven.alf.uml;

import java.util.List;

public interface Clause extends Element {
	public List<ExecutableNode> getTest();

	public void addTest(ExecutableNode test);

	public List<ExecutableNode> getBody();

	public void addBody(ExecutableNode body);

	public List<Clause> getPredecessorClause();

	public void addPredecessorClause(Clause predecessorClause);

	public List<Clause> getSuccessorClause();

	public void addSuccessorClause(Clause successorClause);

	public OutputPin getDecider();

	public void setDecider(OutputPin decider);

	public List<OutputPin> getBodyOutput();

	public void addBodyOutput(OutputPin bodyOutput);
}
