
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinition extends NamespaceDefinition {

	private QualifiedNameList redefinition = null;
	private boolean isAbstract = false;
	private Block body = null;
	private ArrayList<ElementReference> redefinedOperations = new ArrayList<ElementReference>(); // DERIVED
	private boolean isConstructor = false; // DERIVED
	private boolean isDestructor = false; // DERIVED

	public QualifiedNameList getRedefinition() {
		return this.redefinition;
	}

	public void setRedefinition(QualifiedNameList redefinition) {
		this.redefinition = redefinition;
	}

	public boolean getIsAbstract() {
		return this.isAbstract;
	}

	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public ArrayList<ElementReference> getRedefinedOperations() {
		return this.redefinedOperations;
	}

	public void setRedefinedOperations(
			ArrayList<ElementReference> redefinedOperations) {
		this.redefinedOperations = redefinedOperations;
	}

	public void addRedefinedOperations(ElementReference redefinedOperations) {
		this.redefinedOperations.add(redefinedOperations);
	}

	public boolean getIsConstructor() {
		return this.isConstructor;
	}

	public void setIsConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	public boolean getIsDestructor() {
		return this.isDestructor;
	}

	public void setIsDestructor(boolean isDestructor) {
		this.isDestructor = isDestructor;
	}

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * Returns true if the annotation is for a stereotype that has a
		 * metaclass consistent with Operation.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * The namespace definition associated with the given unit definition
		 * must be an activity definition with no template parameters. In
		 * addition, the subunit definition must have formal parameters that
		 * match each of the formal parameters of the stub definition, in order.
		 * Two formal parameters match if they have the same direction, name,
		 * multiplicity bounds, ordering, uniqueness and type reference.
		 */
		return false; // STUB
	} // matchForStub

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either an OperationDefinition or
		 * an imported member whose referent is an OperationDefinition or an
		 * Operation, and the formal parameters of this operation definition
		 * match, in order, the parameters of the other operation definition or
		 * operation. In this context, matching means the same name and type
		 * (per UML Superstructure, Subclause 7.3.5).
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAbstract:");
		s.append(this.isAbstract);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.redefinition != null) {
			this.redefinition.print(prefix + " ");
		}
		if (this.body != null) {
			this.body.print(prefix + " ");
		}
	}
} // OperationDefinition
