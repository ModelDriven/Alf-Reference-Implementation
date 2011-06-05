
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinition extends NamespaceDefinition {

	public OperationDefinition() {
		this.impl = new OperationDefinitionImpl(this);
	}

	public OperationDefinitionImpl getImpl() {
		return (OperationDefinitionImpl) this.impl;
	}

	public QualifiedNameList getRedefinition() {
		return this.getImpl().getRedefinition();
	}

	public void setRedefinition(QualifiedNameList redefinition) {
		this.getImpl().setRedefinition(redefinition);
	}

	public Boolean getIsAbstract() {
		return this.getImpl().getIsAbstract();
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.getImpl().setIsAbstract(isAbstract);
	}

	public Block getBody() {
		return this.getImpl().getBody();
	}

	public void setBody(Block body) {
		this.getImpl().setBody(body);
	}

	public Collection<ElementReference> getRedefinedOperations() {
		return this.getImpl().getRedefinedOperations();
	}

	public void setRedefinedOperations(
			Collection<ElementReference> redefinedOperations) {
		this.getImpl().setRedefinedOperations(redefinedOperations);
	}

	public void addRedefinedOperations(ElementReference redefinedOperations) {
		this.getImpl().addRedefinedOperations(redefinedOperations);
	}

	public Boolean getIsConstructor() {
		return this.getImpl().getIsConstructor();
	}

	public void setIsConstructor(Boolean isConstructor) {
		this.getImpl().setIsConstructor(isConstructor);
	}

	public Boolean getIsDestructor() {
		return this.getImpl().getIsDestructor();
	}

	public void setIsDestructor(Boolean isDestructor) {
		this.getImpl().setIsDestructor(isDestructor);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.operationDefinitionNamespace()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionNamespace", this));
		}
		if (!this.operationDefinitionRedefinedOperationsDerivation()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionRedefinedOperationsDerivation", this));
		}
		if (!this.operationDefinitionRedefinition()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionRedefinition", this));
		}
		if (!this.operationDefinitionRedefinedOperations()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionRedefinedOperations", this));
		}
		if (!this.operationDefinitionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionIsFeatureDerivation", this));
		}
		if (!this.operationDefinitionIsConstructorDefinition()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionIsConstructorDefinition", this));
		}
		if (!this.operationDefinitionIsDestructorDefinition()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionIsDestructorDefinition", this));
		}
		if (!this.operationDefinitionConstructorDestructor()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionConstructorDestructor", this));
		}
		if (!this.operationDefinitionConstructor()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionConstructor", this));
		}
		if (!this.operationDefinitionDestructor()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionDestructor", this));
		}
		QualifiedNameList redefinition = this.getRedefinition();
		if (redefinition != null) {
			redefinition.checkConstraints(violations);
		}
		Block body = this.getBody();
		if (body != null) {
			body.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
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

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedNameList redefinition = this.getRedefinition();
		if (redefinition != null) {
			System.out.println(prefix + " redefinition:");
			redefinition.print(prefix + "  ");
		}
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ");
		}
		Collection<ElementReference> redefinedOperations = this
				.getRedefinedOperations();
		if (redefinedOperations != null) {
			if (redefinedOperations.size() > 0) {
				System.out.println(prefix + " /redefinedOperations:");
			}
			for (ElementReference _redefinedOperations : redefinedOperations) {
				System.out.println(prefix + "  " + _redefinedOperations);
			}
		}
	}
} // OperationDefinition
