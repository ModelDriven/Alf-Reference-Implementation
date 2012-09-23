package org.modeldriven.alf.uml;

import java.util.List;

public interface Action extends ExecutableNode {
	public List<OutputPin> getOutput();

	public Classifier getContext();

	public List<InputPin> getInput();

	public boolean getIsLocallyReentrant();

	public void setIsLocallyReentrant(boolean isLocallyReentrant);
}
