
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

public class OperationDefinition extends BehaviorDefinition {

	private QualifiedNameList redefinition = null;
	private boolean isAbstract = false;

	public void setRedefinition(QualifiedNameList redefinition) {
		this.redefinition = redefinition;
	} // setRedefinition

	public QualifiedNameList getRedefinition() {
		return this.redefinition;
	} // getRedefinition

	public void setIsAbstract() {
		this.isAbstract = true;
	} // setIsAbstract

	public boolean isAbstract() {
		return this.isAbstract;
	} // isAbstract

	public String toString() {
		return super.toString() + " isAbstract:" + this.isAbstract();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (this.getRedefinition() != null) {
			this.getRedefinition().printChild(prefix);
		}

		if (this.getBody() != null) {
			this.getBody().printChild(prefix);
		}
	} // print

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		if (!(other instanceof ReceptionDefinition
				|| other instanceof SignalReceptionDefinition || other instanceof OperationDefinition)
				|| super.isDistinguishableFrom(other, namespace)) {
			return true;
		} else {
			Member member;
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
				ArrayList<Member> otherMembers = otherDefinition.getMembers();

				// Must be formal parameters
				ArrayList<Member> parameters = this.getMembers();

				if (parameters.size() != otherMembers.size()) {
					return true;
				} else {
					for (int i = 0; i < parameters.size(); i++) {
						if (((TypedElementDefinition) parameters.get(i))
								.getType() != ((TypedElementDefinition) otherMembers
								.get(i)).getType()) {
							return true;
						}
					}
					return false;
				}
			}
		}
	} // isDistinguishableFrom

} // OperationDefinition
