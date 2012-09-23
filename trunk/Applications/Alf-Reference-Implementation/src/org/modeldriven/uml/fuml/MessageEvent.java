package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Event;

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
