package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.LinkAction;
import org.modeldriven.alf.uml.OutputPin;

public interface ReadLinkAction extends LinkAction {
	public OutputPin getResult();

	public void setResult(OutputPin result);
}
