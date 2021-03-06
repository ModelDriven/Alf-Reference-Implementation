/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.statements.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinition extends NamespaceDefinition {

	public OperationDefinition() {
		this.impl = new OperationDefinitionImpl(this);
	}

	public OperationDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public OperationDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

    public Block getEffectiveBody() {
        return this.getImpl().getEffectiveBody();
    }
    
    public void setEffectiveBody(Block effectiveBody) {
        this.getImpl().setEffectiveBody(effectiveBody);
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
     * The assignments before the effective body of an operation definition
     * include an assignment for each "in" or "inout" formal parameter of the
     * operation definition, with the formal parameter as the assigned source.
     */
    public boolean operationDefinitionEffectiveBodyAssignmentsBefore() {
        return this.getImpl().operationDefinitionEffectiveBodyAssignmentsBefore();
    }

    /**
     * If an operation definition that is not a constructor or destructor has a
     * return parameter with a multiplicity lower bound greater than 0, then the
     * effective body of the operation definition must have a return value.
     */
    public boolean operationDefinitionReturn() {
        return this.getImpl().operationDefinitionReturn();
    }
    
   /**
     * If an operation definition is a stub, then its effective body is the body
     * of the corresponding subunit. Otherwise, the effective body is the same
     * as the body of the operation definition.
     */
    public boolean operationDefinitionEffectiveBodyDerivation() {
        return this.getImpl().operationDefinitionEffectiveBodyDerivation();
    }
    
    /**
     * If an operation definition is abstract, then its body must be empty.
     */
    public boolean operationDefinitionAbstractOperation() {
        return this.getImpl().operationDefinitionAbstractOperation();
    }

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Operation.
	 **/
	@Override
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
	@Override
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
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getRedefinition());
        addExternalReferencesFor(references, this.getBody());
    }

	@Override
    public void _deriveAll() {
		this.getRedefinedOperation();
		this.getIsConstructor();
		this.getIsDestructor();
		this.getEffectiveBody();
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

	@Override
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
        if (!this.operationDefinitionEffectiveBodyAssignmentsBefore()) {
            violations.add(new ConstraintViolation(
                    "operationDefinitionEffectiveBodyAssignmentsBefore", this));
        }
        if (!this.operationDefinitionReturn()) {
            violations.add(new ConstraintViolation(
                    "operationDefinitionReturn", this));
        }
        if (!this.operationDefinitionEffectiveBodyDerivation()) {
            violations.add(new ConstraintViolation(
                    "operationDefinitionEffectiveBodyDerivation", this));
        }
        if (!this.operationDefinitionAbstractOperation()) {
            violations.add(new ConstraintViolation(
                    "operationDefinitionAbstractOperation", this));
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

	@Override
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

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
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
