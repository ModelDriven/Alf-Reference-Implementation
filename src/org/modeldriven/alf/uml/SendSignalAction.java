package org.modeldriven.alf.uml;


public interface SendSignalAction extends InvocationAction {
	public InputPin getTarget();

	public void setTarget(InputPin target);

	public Signal getSignal();

	public void setSignal(Signal signal);
}
