package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.LinkEndData;

public interface LinkAction extends Action {
	public List<? extends LinkEndData> getEndData();

	public void addEndData(LinkEndData endData);

	public List<InputPin> getInputValue();

	public void addInputValue(InputPin inputValue);
}
