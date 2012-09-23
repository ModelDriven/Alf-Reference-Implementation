package org.modeldriven.alf.uml;


public interface ReadLinkAction extends LinkAction {
	public OutputPin getResult();

	public void setResult(OutputPin result);
}
