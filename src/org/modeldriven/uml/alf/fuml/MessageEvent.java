package org.modeldriven.uml.alf.fuml;


public class MessageEvent extends Event implements
		org.modeldriven.alf.uml.MessageEvent {

	public MessageEvent(
			fUML.Syntax.CommonBehaviors.Communications.MessageEvent base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.MessageEvent getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.MessageEvent) this.base;
	}

}
