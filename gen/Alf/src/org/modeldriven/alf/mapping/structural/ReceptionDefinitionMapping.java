
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.namespaces.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.Communications.*;

public class ReceptionDefinitionMapping extends MemberMapping {

	private Reception reception = null;

	public void mapTo(Reception reception) {
		super.mapTo(reception);

		ReceptionDefinition definition = this.getReceptionDefinition();
		Member member = definition.resolveSignal();

		if (member.isError()) {
			this.setError(((ErrorMember) member).getError());
		} else {
			SignalDefinitionMapping mapping = (SignalDefinitionMapping) this
					.map(member);
			Signal signal = (Signal) mapping.getClassifier();

			if (mapping.isError()) {
				this.setError(mapping.getError());
			} else {
				reception.setSignal(signal);
			}
		}
	} // mapTo

	public Reception getReception() {
		if (this.reception == null) {
			this.reception = new Reception();
			this.mapTo(this.reception);
		}

		return this.reception;
	} // getReception

	public ReceptionDefinition getReceptionDefinition() {
		return (ReceptionDefinition) this.getSourceNode();
	} // getReceptionDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getReception());
		return elements;
	} // getModelElements

} // ReceptionDefinitionMapping
