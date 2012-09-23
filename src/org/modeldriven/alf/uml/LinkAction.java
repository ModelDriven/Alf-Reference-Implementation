package org.modeldriven.alf.uml;

import java.util.List;

public interface LinkAction extends Action {
	public List<? extends LinkEndData> getEndData();

	public void addEndData(LinkEndData endData);

	public List<InputPin> getInputValue();

	public void addInputValue(InputPin inputValue);
}
