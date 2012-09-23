package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.OutputPin;

public interface ReadSelfAction extends Action {
	public OutputPin getResult();

	public void setResult(OutputPin result);
}
