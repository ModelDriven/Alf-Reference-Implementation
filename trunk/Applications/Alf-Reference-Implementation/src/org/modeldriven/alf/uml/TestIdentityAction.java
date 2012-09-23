package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;

public interface TestIdentityAction extends Action {
	public InputPin getSecond();

	public void setSecond(InputPin second);

	public OutputPin getResult();

	public void setResult(OutputPin result);

	public InputPin getFirst();

	public void setFirst(InputPin first);
}
