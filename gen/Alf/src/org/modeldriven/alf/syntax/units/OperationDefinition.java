
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinition extends NamespaceDefinition {

	private QualifiedNameList redefinition = null;
	private Boolean isAbstract = false;
	private Block body = null;
	private ArrayList<ElementReference> redefinedOperations = null; // DERIVED
	private Boolean isConstructor = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED

	public OperationDefinition() {
		this.impl = new OperationDefinitionImpl(this);
	}

	public OperationDefinitionImpl getImpl() {
		return (OperationDefinitionImpl) this.impl;
	}

	public QualifiedNameList getRedefinition() {
		return this.redefinition;
	}

	public void setRedefinition(QualifiedNameList redefinition) {
		this.redefinition = redefinition;
	}

	public Boolean getIsAbstract() {
		return this.isAbstract;
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public ArrayList<ElementReference> getRedefinedOperations() {
		if (this.redefinedOperations == null) {
			this.redefinedOperations = this.getImpl()
					.deriveRedefinedOperations();
		}
		return this.redefinedOperations;
	}

	public Boolean getIsConstructor() {
		if (this.isConstructor == null) {
			this.isConstructor = this.getImpl().deriveIsConstructor();
		}
		return this.isConstructor;
	}

	public Boolean getIsDestructor() {
		if (this.isDestructor == null) {
			this.isDestructor = this.getImpl().deriveIsDestructor();
		}
		return this.isDestructor;
	}

	/**
	 * The namespace for an operation definition must be a class definition. If
	 * the operation definition is abstract, then the class definition must be
	 * abstract.
	 **/
	public boolean operationDefinitionNamespace() {
		return this.getImpl().operationDefinitionNamespace();
	}

	/**
	 * If an operation definition has a redefinition list, its redefined
	 * operations are the referent operations of the names in the redefinition
	 * list for the operation definition. Otherwise, the redefined operations
	 * are any operations that would otherwise be indistinguishable from the
	 * operation being defined in this operation definition.
	 **/
	public boolean operationDefinitionRedefinedOperationsDerivation() {
		return this.getImpl()
				.operationDefinitionRedefinedOperationsDerivation();
	}

	/**
	 * Each name in the redefinition list of an operation definition must have a
	 * signal referent that is an operation. This operation must be a
	 * non-private operation that is a member of a specialization referent of
	 * the class definition of the operation definition.
	 **/
	public boolean operationDefinitionRedefinition() {
		return this.getImpl().operationDefinitionRedefinition();
	}

	/**
	 * The redefined operations of an operation definition must have formal
	 * parameters that match each of the formal parameters of this operation
	 * definition, in order. Two formal parameters match if they have the same
	 * direction, name, multiplicity bounds, ordering, uniqueness and type
	 * reference.
	 **/
	public boolean operationDefinitionRedefinedOperations() {
		return this.getImpl().operationDefinitionRedefinedOperations();
	}

	/**
	 * An operation definition is a feature.
	 **/
	public boolean operationDefinitionIsFeatureDerivation() {
		return this.getImpl().operationDefinitionIsFeatureDerivation();
	}

	/**
	 * An operation definition is a constructor if it has a @Create annotation.
	 **/
	public boolean operationDefinitionIsConstructorDefinition() {
		return this.getImpl().operationDefinitionIsConstructorDefinition();
	}

	/**
	 * An operation definition is a destructor if it has a @Destroy annotation.
	 **/
	public boolean operationDefinitionIsDestructorDefinition() {
		return this.getImpl().operationDefinitionIsDestructorDefinition();
	}

	/**
	 * An operation definition cannot be both a constructor and a destructor.
	 **/
	public boolean operationDefinitionConstructorDestructor() {
		return this.getImpl().operationDefinitionConstructorDestructor();
	}

	/**
	 * If an operation definition is a constructor, any redefined operation for
	 * it must also be a constructor. The body of a constructor may contain an
	 * alternative constructor invocation for another constructor in the same
	 * class or super constructor invocations for constructors in immediate
	 * superclasses.
	 **/
	public boolean operationDefinitionConstructor() {
		return this.getImpl().operationDefinitionConstructor();
	}

	/**
	 * If an operation definition is a destructor, any redefined operation for
	 * it must also be a destructor.
	 **/
	public boolean operationDefinitionDestructor() {
		return this.getImpl().operationDefinitionDestructor();
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Operation.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * The namespace definition associated with the given unit definition must
	 * be an activity definition with no template parameters. In addition, the
	 * subunit definition must have formal parameters that match each of the
	 * formal parameters of the stub definition, in order. Two formal parameters
	 * match if they have the same direction, name, multiplicity bounds,
	 * ordering, uniqueness and type reference.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * Return true if the given member is either an OperationDefinition or an
	 * imported member whose referent is an OperationDefinition or an Operation,
	 * and the formal parameters of this operation definition match, in order,
	 * the parameters of the other operation definition or operation. In this
	 * context, matching means the same name and type (per UML Superstructure,
	 * Subclause 7.3.5).
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAbstract:");
		s.append(this.getIsAbstract());
		Boolean isConstructor = this.getIsConstructor();
		if (isConstructor != null) {
			s.append(" /isConstructor:");
			s.append(isConstructor);
		}
		Boolean isDestructor = this.getIsDestructor();
		if (isDestructor != null) {
			s.append(" /isDestructor:");
			s.append(isDestructor);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedNameList redefinition = this.getRedefinition();
		if (redefinition != null) {
			redefinition.print(prefix + " ");
		}
		Block body = this.getBody();
		if (body != null) {
			body.print(prefix + " ");
		}
		ArrayList<ElementReference> redefinedOperations = this
				.getRedefinedOperations();
		if (redefinedOperations != null) {
			for (ElementReference item : this.getRedefinedOperations()) {
				System.out.println(prefix + " /" + item);
			}
		}
	}
} // OperationDefinition
