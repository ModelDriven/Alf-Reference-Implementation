package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.BehavioralFeature;
import org.modeldriven.uml.Signal;

public interface Reception extends BehavioralFeature {
	public Signal getSignal();

	public void setSignal(Signal signal);
}
