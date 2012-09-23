package org.modeldriven.alf.uml;

import java.util.List;

public interface CallAction extends InvocationAction {
	public boolean getIsSynchronous();

	public void setIsSynchronous(boolean isSynchronous);

	public List<OutputPin> getResult();

	public void addResult(OutputPin result);
}
