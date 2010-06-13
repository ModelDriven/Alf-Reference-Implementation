
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class ReceptionDefinition extends Member {

	private QualifiedName signal = null;

	public ReceptionDefinition(QualifiedName signal) {
		this.signal = signal;

		ArrayList<String> names = signal.getNames();
		this.setName(names.get(names.size() - 1));
	} // ReceptionDefinition

	public QualifiedName getSignal() {
		return this.signal;
	} // getSignal

	public void print(String prefix) {
		super.print(prefix);
		this.getSignal().printChild(prefix);
	} // print

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		if (!(other instanceof ReceptionDefinition
				|| other instanceof SignalReceptionDefinition || other instanceof OperationDefinition)
				|| super.isDistinguishableFrom(other, namespace)) {
			return true;
		} else {
			Member member = this.resolveSignal();

			if (member.isError()) {
				return true;
			} else {
				SignalDefinition signalDefinition = (SignalDefinition) member;

				// Must be property definitions
				ArrayList<Member> signalAttributes = signalDefinition
						.getMembers();

				if (other instanceof ReceptionDefinition) {
					member = ((ReceptionDefinition) other).resolveSignal();
				} else {
					member = other;
				}

				if (member.isError()) {
					return true;
				} else {

					// Signal definition or operation definition
					NamespaceDefinition otherDefinition = (NamespaceDefinition) member;

					// Must be typed element definitions
					ArrayList<Member> otherMembers = otherDefinition
							.getMembers();

					if (signalAttributes.size() != otherMembers.size()) {
						return true;
					} else {
						for (int i = 0; i < signalAttributes.size(); i++) {
							if (((TypedElementDefinition) signalAttributes
									.get(i)).getType() != ((TypedElementDefinition) otherMembers
									.get(i)).getType()) {
								return true;
							}
						}
						return false;
					}
				}
			}
		}

	} // isDistinguishableFrom

	public Member resolveSignal() {
		QualifiedName signal = this.getSignal();
		ArrayList<Member> members = signal.resolve(this.getNamespace());

		if (members.size() == 1 && members.get(0).isError()) {
			return new ErrorMember(this, (ErrorMember) members.get(0));
		} else {
			for (Object member : members.toArray()) {
				if (!(member instanceof SignalDefinition)) {
					members.remove(member);
				}
			}

			if (members.size() == 1) {
				return (SignalDefinition) (members.get(0));
			} else if (members.size() == 0) {
				return new ErrorMember(this, "Cannot resolve signal: " + signal);
			} else {
				return new ErrorMember(this, "Ambiguous signal reference: "
						+ signal);
			}
		}
	} // resolveSignal

} // ReceptionDefinition
