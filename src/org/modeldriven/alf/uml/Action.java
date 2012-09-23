package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ExecutableNode;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.uml.Classifier;

public interface Action extends ExecutableNode {
	public List<OutputPin> getOutput();

	public Classifier getContext();

	public List<InputPin> getInput();

	public boolean getIsLocallyReentrant();

	public void setIsLocallyReentrant(boolean isLocallyReentrant);
}
