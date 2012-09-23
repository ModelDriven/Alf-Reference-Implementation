package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.BehavioralFeature;
import org.modeldriven.alf.uml.Signal;

public interface Reception extends BehavioralFeature {
	public Signal getSignal();

	public void setSignal(Signal signal);
}
