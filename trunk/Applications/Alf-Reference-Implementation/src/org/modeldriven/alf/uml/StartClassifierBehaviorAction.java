package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.InputPin;

public interface StartClassifierBehaviorAction extends Action {
	public InputPin getObject();

	public void setObject(InputPin object);
}
