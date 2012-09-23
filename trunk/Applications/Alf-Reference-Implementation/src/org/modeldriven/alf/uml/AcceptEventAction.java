package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.Trigger;

public interface AcceptEventAction extends Action {
	public boolean getIsUnmarshall();

	public void setIsUnmarshall(boolean isUnmarshall);

	public List<OutputPin> getResult();

	public void addResult(OutputPin result);

	public List<Trigger> getTrigger();

	public void addTrigger(Trigger trigger);
}
