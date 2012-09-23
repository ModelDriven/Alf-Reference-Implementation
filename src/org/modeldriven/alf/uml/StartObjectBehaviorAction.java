package org.modeldriven.alf.uml;


public interface StartObjectBehaviorAction extends CallAction {
	public InputPin getObject();

	public void setObject(InputPin object);
}
