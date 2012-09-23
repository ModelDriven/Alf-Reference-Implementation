package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.uml.Behavior;

public interface ReduceAction extends Action {
	public Behavior getReducer();

	public void setReducer(Behavior reducer);

	public OutputPin getResult();

	public void setResult(OutputPin result);

	public InputPin getCollection();

	public void setCollection(InputPin collection);

	public boolean getIsOrdered();

	public void setIsOrdered(boolean isOrdered);
}
