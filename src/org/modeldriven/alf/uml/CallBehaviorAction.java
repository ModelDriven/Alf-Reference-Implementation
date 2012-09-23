package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.CallAction;
import org.modeldriven.uml.Behavior;

public interface CallBehaviorAction extends CallAction {
	public Behavior getBehavior();

	public void setBehavior(Behavior behavior);
}
