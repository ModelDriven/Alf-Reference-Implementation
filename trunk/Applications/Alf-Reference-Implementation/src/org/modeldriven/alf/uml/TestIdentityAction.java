package org.modeldriven.alf.uml;


public interface TestIdentityAction extends Action {
	public InputPin getSecond();

	public void setSecond(InputPin second);

	public OutputPin getResult();

	public void setResult(OutputPin result);

	public InputPin getFirst();

	public void setFirst(InputPin first);
}
