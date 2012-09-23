package org.modeldriven.alf.uml;


public interface ReadSelfAction extends Action {
	public OutputPin getResult();

	public void setResult(OutputPin result);
}
