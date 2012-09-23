package org.modeldriven.alf.uml;


public interface SignalEvent extends MessageEvent {
	public Signal getSignal();

	public void setSignal(Signal signal);
}
