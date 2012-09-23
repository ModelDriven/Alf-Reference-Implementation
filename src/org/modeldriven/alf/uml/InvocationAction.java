package org.modeldriven.alf.uml;

import java.util.List;

public interface InvocationAction extends Action {
	public List<InputPin> getArgument();

	public void addArgument(InputPin argument);
}
