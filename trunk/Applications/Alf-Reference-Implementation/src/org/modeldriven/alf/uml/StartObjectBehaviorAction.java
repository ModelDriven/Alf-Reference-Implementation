package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.CallAction;
import org.modeldriven.alf.uml.InputPin;

public interface StartObjectBehaviorAction extends CallAction {
	public InputPin getObject();

	public void setObject(InputPin object);
}
