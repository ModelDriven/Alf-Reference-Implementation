package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;

public interface InvocationAction extends Action {
	public List<InputPin> getArgument();

	public void addArgument(InputPin argument);
}
