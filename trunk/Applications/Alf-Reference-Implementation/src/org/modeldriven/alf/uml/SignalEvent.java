package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.MessageEvent;
import org.modeldriven.uml.Signal;

public interface SignalEvent extends MessageEvent {
	public Signal getSignal();

	public void setSignal(Signal signal);
}
