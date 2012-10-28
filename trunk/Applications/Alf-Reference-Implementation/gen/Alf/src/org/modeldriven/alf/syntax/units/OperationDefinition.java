
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinition extends NamespaceDefinition {

	public OperationDefinition() {
		this.impl = new OperationDefinitionImpl(this);
	}

	public OperationDefinition(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public OperationDefinition(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public Collection<ElementReference> getRedefinedOperation() {
		return this.getImpl().getRedefinedOperation();
	}

	public void setRedefinedOperation(
			Collection<ElementReference> redefinedOperation) {
		this.getImpl().setRedefinedOperation(redefinedOperation);
	}

	public void addRedefinedOperation(ElementReference redefinedOperation) {
		this.getImpl().addRedefinedOperation(redefinedOperation);
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
	 * The namespace for an operation definition must be a class definition.
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
	public boolean operationDefinitionRedefinedOperationDerivation() {
		return this.getImpl().operationDefinitionRedefinedOperationDerivation();
	}

	/**
	 * Each name in the redefinition list of an operation definition must have a
	 * single referent that is an operation. This operation must be a
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
	public boolean operationDefinitionIsConstructorDerivation() {
		return this.getImpl().operationDefinitionIsConstructorDerivation();
	}

	/**
	 * An operation definition is a destructor if it has a @Destroy annotation.
	 **/
	public boolean operationDefinitionIsDestructorDerivation() {
		return this.getImpl().operationDefinitionIsDestructorDerivation();
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
	 * ordering, uniqueness and type reference If this operation definition is a
	 * constructor, then it is considered to have an implicit return parameter,
	 * following any other formal parameters, with the same type as the class of
	 * the operation definition and a multiplicity of 1..1.
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
	 * Subclause 7.3.5). A constructor operation without an explicit return
	 * parameter is considered to implicitly have a return parameter, following
	 * any other formal parameters, of the same type as the owner of the
	 * constructor operation.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public void _deriveAll() {
		this.getRedefinedOperation();
		this.getIsConstructor();
		this.getIsDestructor();
		super._deriveAll();
		QualifiedNameList redefinition = this.getRedefinition();
		if (redefinition != null) {
			redefinition.deriveAll();
		}
		Block body = this.getBody();
		if (body != null) {
			body.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.operationDefinitionNamespace()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionNamespace", this));
		}
		if (!this.operationDefinitionRedefinedOperationDerivation()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionRedefinedOperationDerivation", this));
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
		if (!this.operationDefinitionIsConstructorDerivation()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionIsConstructorDerivation", this));
		}
		if (!this.operationDefinitionIsDestructorDerivation()) {
			violations.add(new ConstraintViolation(
					"operationDefinitionIsDestructorDerivation", this));
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

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" isAbstract:");
		s.append(this.getIsAbstract());
		if (includeDerived) {
			s.append(" /isConstructor:");
			s.append(this.getIsConstructor());
		}
		if (includeDerived) {
			s.append(" /isDestructor:");
			s.append(this.getIsDestructor());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedNameList redefinition = this.getRedefinition();
		if (redefinition != null) {
			System.out.println(prefix + " redefinition:");
			redefinition.print(prefix + "  ", includeDerived);
		}
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Collection<ElementReference> redefinedOperation = this
					.getRedefinedOperation();
			if (redefinedOperation != null && redefinedOperation.size() > 0) {
				System.out.println(prefix + " /redefinedOperation:");
				for (Object _object : redefinedOperation.toArray()) {
					ElementReference _redefinedOperation = (ElementReference) _object;
					System.out.println(prefix + "  "
							+ _redefinedOperation.toString(includeDerived));
				}
			}
		}
	}
} // OperationDefinition
