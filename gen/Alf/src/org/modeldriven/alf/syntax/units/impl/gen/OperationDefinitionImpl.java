
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl.gen;

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

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.NamespaceDefinitionImpl {

	private QualifiedNameList redefinition = null;
	private Boolean isAbstract = false;
	private Block body = null;
	private Collection<ElementReference> redefinedOperations = null; // DERIVED
	private Boolean isConstructor = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED

	public OperationDefinitionImpl(OperationDefinition self) {
		super(self);
	}

	public OperationDefinition getSelf() {
		return (OperationDefinition) this.self;
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

	public Collection<ElementReference> getRedefinedOperations() {
		if (this.redefinedOperations == null) {
			this.setRedefinedOperations(this.deriveRedefinedOperations());
		}
		return this.redefinedOperations;
	}

	public void setRedefinedOperations(
			Collection<ElementReference> redefinedOperations) {
		this.redefinedOperations = redefinedOperations;
	}

	public void addRedefinedOperations(ElementReference redefinedOperations) {
		this.redefinedOperations.add(redefinedOperations);
	}

	public Boolean getIsConstructor() {
		if (this.isConstructor == null) {
			this.setIsConstructor(this.deriveIsConstructor());
		}
		return this.isConstructor;
	}

	public void setIsConstructor(Boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	public Boolean getIsDestructor() {
		if (this.isDestructor == null) {
			this.setIsDestructor(this.deriveIsDestructor());
		}
		return this.isDestructor;
	}

	public void setIsDestructor(Boolean isDestructor) {
		this.isDestructor = isDestructor;
	}

	protected Collection<ElementReference> deriveRedefinedOperations() {
		return null; // STUB
	}

	protected Boolean deriveIsConstructor() {
		return null; // STUB
	}

	protected Boolean deriveIsDestructor() {
		return null; // STUB
	}

	/**
	 * The namespace for an operation definition must be a class definition. If
	 * the operation definition is abstract, then the class definition must be
	 * abstract.
	 **/
	public boolean operationDefinitionNamespace() {
		return true;
	}

	/**
	 * If an operation definition has a redefinition list, its redefined
	 * operations are the referent operations of the names in the redefinition
	 * list for the operation definition. Otherwise, the redefined operations
	 * are any operations that would otherwise be indistinguishable from the
	 * operation being defined in this operation definition.
	 **/
	public boolean operationDefinitionRedefinedOperationsDerivation() {
		this.getSelf().getRedefinedOperations();
		return true;
	}

	/**
	 * Each name in the redefinition list of an operation definition must have a
	 * signal referent that is an operation. This operation must be a
	 * non-private operation that is a member of a specialization referent of
	 * the class definition of the operation definition.
	 **/
	public boolean operationDefinitionRedefinition() {
		return true;
	}

	/**
	 * The redefined operations of an operation definition must have formal
	 * parameters that match each of the formal parameters of this operation
	 * definition, in order. Two formal parameters match if they have the same
	 * direction, name, multiplicity bounds, ordering, uniqueness and type
	 * reference.
	 **/
	public boolean operationDefinitionRedefinedOperations() {
		return true;
	}

	/**
	 * An operation definition is a feature.
	 **/
	public boolean operationDefinitionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	/**
	 * An operation definition is a constructor if it has a @Create annotation.
	 **/
	public boolean operationDefinitionIsConstructorDefinition() {
		return true;
	}

	/**
	 * An operation definition is a destructor if it has a @Destroy annotation.
	 **/
	public boolean operationDefinitionIsDestructorDefinition() {
		return true;
	}

	/**
	 * An operation definition cannot be both a constructor and a destructor.
	 **/
	public boolean operationDefinitionConstructorDestructor() {
		return true;
	}

	/**
	 * If an operation definition is a constructor, any redefined operation for
	 * it must also be a constructor. The body of a constructor may contain an
	 * alternative constructor invocation for another constructor in the same
	 * class or super constructor invocations for constructors in immediate
	 * superclasses.
	 **/
	public boolean operationDefinitionConstructor() {
		return true;
	}

	/**
	 * If an operation definition is a destructor, any redefined operation for
	 * it must also be a destructor.
	 **/
	public boolean operationDefinitionDestructor() {
		return true;
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Operation.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * The namespace definition associated with the given unit definition must
	 * be an activity definition with no template parameters. In addition, the
	 * subunit definition must have formal parameters that match each of the
	 * formal parameters of the stub definition, in order. Two formal parameters
	 * match if they have the same direction, name, multiplicity bounds,
	 * ordering, uniqueness and type reference.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

	/**
	 * Return true if the given member is either an OperationDefinition or an
	 * imported member whose referent is an OperationDefinition or an Operation,
	 * and the formal parameters of this operation definition match, in order,
	 * the parameters of the other operation definition or operation. In this
	 * context, matching means the same name and type (per UML Superstructure,
	 * Subclause 7.3.5).
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // OperationDefinitionImpl
