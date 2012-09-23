package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.InvocationAction;
import org.modeldriven.uml.Signal;

public interface SendSignalAction extends InvocationAction {
	public InputPin getTarget();

	public void setTarget(InputPin target);

	public Signal getSignal();

	public void setSignal(Signal signal);
}
