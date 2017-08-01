/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinitionImpl extends NamespaceDefinitionImpl {

	private QualifiedNameList redefinition = null;
	private Boolean isAbstract = false;
	private Block body = null;
	private Collection<ElementReference> redefinedOperation = null; // DERIVED
	private Boolean isConstructor = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED
	private Block effectiveBody = null; // DERIVED

	public OperationDefinitionImpl(OperationDefinition self) {
		super(self);
	}

	public OperationDefinition getSelf() {
		return (OperationDefinition) this.self;
	}

    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        SyntaxElement.addExternalReferences(references, this.getSelf().getRedefinedOperation());
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
        if (body != null) {
            body.getImpl().setCurrentScope(this.getSelf());
        }
	}

	public Collection<ElementReference> getRedefinedOperation() {
		if (this.redefinedOperation == null) {
			this.setRedefinedOperation(this.deriveRedefinedOperation());
		}
		return this.redefinedOperation;
	}

	public void setRedefinedOperation(
			Collection<ElementReference> redefinedOperation) {
		this.redefinedOperation = redefinedOperation;
	}

	public void addRedefinedOperation(ElementReference redefinedOperation) {
		this.redefinedOperation.add(redefinedOperation);
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

	@Override
    public Block getEffectiveBody() {
        if (this.effectiveBody == null) {
            this.setEffectiveBody(this.deriveEffectiveBody());
        }
        return this.effectiveBody;
    }
    
    public void setEffectiveBody(Block effectiveBody) {
        this.effectiveBody = effectiveBody;
    }
    
    /**
     * If an operation definition has a redefinition list, its redefined
     * operations are the referent operations of the names in the redefinition
     * list for the operation definition. Otherwise, the redefined operations
     * are any operations that would otherwise be indistinguishable from the
     * operation being defined in this operation definition.
     **/
	protected Collection<ElementReference> deriveRedefinedOperation() {
	    OperationDefinition self = this.getSelf();
	    ArrayList<ElementReference> redefinedOperations = new ArrayList<ElementReference>();
	    QualifiedNameList redefinitions = self.getRedefinition();
	    if (redefinitions != null) {
    	    for (QualifiedName redefinitionName: redefinitions.getName()) {
    	        redefinitionName.getImpl().setCurrentScope(this.getOuterScope());
    	        ElementReference referent = redefinitionName.getImpl().getOperationReferent();
    	        if (referent != null) {
    	            redefinedOperations.add(referent);
    	        }
    	    }
	    }
		return redefinedOperations;
	}

    /**
     * An operation definition is a constructor if it has a @Create annotation.
     **/
	protected Boolean deriveIsConstructor() {
		return this.isStereotyped(RootNamespace.getRootScope().getCreateStereotype());
	}

    /**
     * An operation definition is a destructor if it has a @Destroy annotation.
     **/
	protected Boolean deriveIsDestructor() {
        return this.isStereotyped(RootNamespace.getRootScope().getDestroyStereotype());
	}
	
    /**
     * An operation definition is a feature.
     **/
	@Override
	protected Boolean deriveIsFeature() {
	    return true;
	}
	
    /**
     * If an operation definition is a stub, then its effective body is the body
     * of the corresponding subunit. Otherwise, the operation body is the same
     * as the body of the operation definition.
     */
    public Block deriveEffectiveBody() {
        OperationDefinition self = this.getSelf();
        UnitDefinition subunit = self.getSubunit();
        if (subunit == null) {
            return self.getBody();
        } else {
            NamespaceDefinition definition = subunit.getDefinition();
            return definition instanceof ActivityDefinition?
                        ((ActivityDefinition)definition).getBody():
                        null;
        }
    }
        
	/*
	 * Derivations
	 */

    public boolean operationDefinitionRedefinedOperationDerivation() {
        this.getSelf().getRedefinedOperation();
        return true;
    }

    public boolean operationDefinitionIsFeatureDerivation() {
        this.getSelf().getIsFeature();
        return true;
    }

    public boolean operationDefinitionIsConstructorDerivation() {
        this.getSelf().getIsConstructor();
        return true;
    }

    public boolean operationDefinitionIsDestructorDerivation() {
        this.getSelf().getIsDestructor();
        return true;
    }
    
    public boolean operationDefinitionEffectiveBodyDerivation() {
        this.getSelf().getEffectiveBody();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * The namespace for an operation definition must be a class definition.
	 **/
	public boolean operationDefinitionNamespace() {
	    ElementReference namespace = this.getNamespaceReference();
		return namespace != null && namespace.getImpl().isClass();
	}

	/**
	 * Each name in the redefinition list of an operation definition must have a
	 * referent that is an operation. This operation must be a
	 * non-private operation that is a member of a specialization referent of
	 * the class definition of the operation definition.
	 **/
	public boolean operationDefinitionRedefinition() {
	    QualifiedNameList redefinitionList = this.getSelf().getRedefinition();
	    if (redefinitionList != null) {
    	    for (QualifiedName redefinitionName: redefinitionList.getName()) {
    	        redefinitionName.getImpl().setCurrentScope(this.getOuterScope());
    	        if (redefinitionName.getImpl().getOperationReferent() == null) {
    	            return false;
    	        }
    	    }
	    }
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
	    for (ElementReference operation: this.getSelf().getRedefinedOperation()) {
	        if (!this.equateParameters(operation)) {
	            return false;
	        }
	    }
		return true;
	}

	/**
	 * An operation definition cannot be both a constructor and a destructor.
	 **/
	public boolean operationDefinitionConstructorDestructor() {
	    OperationDefinition self = this.getSelf();
		return !(self.getIsConstructor() && self.getIsDestructor());
	}

	/**
	 * If an operation definition is a constructor, any redefined operation for
	 * it must also be a constructor. The body of a constructor may contain an
	 * alternative constructor invocation for another constructor in the same
	 * class or super constructor invocations for constructors in immediate
	 * superclasses.
	 **/
	public boolean operationDefinitionConstructor() {
	    // NOTE: The constraints on alternative and super constructors
	    // are handled by the constraints on feature invocation expressions
	    // and super invocation expressions related to constructor invocation.
	    OperationDefinition self = this.getSelf();
	    if (self.getIsConstructor()) {
    		for (ElementReference redefinedOperation: self.getRedefinedOperation()) {
    		    if (!redefinedOperation.getImpl().isConstructor()) {
    		        return false;
    		    }
    		}
	    }
		return true;
	}

	/**
	 * If an operation definition is a destructor, any redefined operation for
	 * it must also be a destructor.
	 **/
	public boolean operationDefinitionDestructor() {
        OperationDefinition self = this.getSelf();
        if (self.getIsDestructor()) {
            for (ElementReference redefinedOperation: self.getRedefinedOperation()) {
                if (!redefinedOperation.getImpl().isDestructor()) {
                    return false;
                }
            }
        }
        return true;
	}
	
    /**
     * The assignments before the effective body of an operation definition
     * include an assignment for each "in" or "inout" formal parameter of the
     * operation definition, with the formal parameter as the assigned source.
     */
    public boolean operationDefinitionEffectiveBodyAssignmentsBefore() {
        // This handled by BlockImpl::deriveAssignmentsAfter.
        return true;
    }
    
    /**
     * If an operation definition that is not a constructor or destructor has a
     * return parameter with a multiplicity lower bound greater than 0, then the
     * effective body of the operation definition must have a return value.
     */
    public boolean operationDefinitionReturn() {
        OperationDefinition self = this.getSelf();
        ElementReference returnParameter = this.getReturnParameter();
        Block body = self.getEffectiveBody();
        return self.getIsConstructor() || self.getIsDestructor() ||
               returnParameter == null || 
               returnParameter.getImpl().getLower() == 0 ||
               body == null || body.hasReturnValue();
    }

    /**
     * If an operation definition is abstract, then its body must be empty.
     */
    public boolean operationDefinitionAbstractOperation() {
        OperationDefinition self = this.getSelf();
        return !self.getIsAbstract() || self.getBody() == null;
    }

	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Operation.
	 **/
    @Override
    public Class<?> getUMLMetaclass() {
        return org.modeldriven.alf.uml.Operation.class;
    }

	/**
	 * The namespace definition associated with the given unit definition must
	 * be an activity definition with no template parameters. In addition, the
	 * subunit definition must have formal parameters that match each of the
	 * formal parameters of the stub definition, in order. Two formal parameters
	 * match if they have the same direction, name, multiplicity bounds,
	 * ordering, uniqueness and type reference.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
        NamespaceDefinition definition = unit.getDefinition();
        return definition instanceof ActivityDefinition && 
            !((ActivityDefinition)definition).getImpl().isTemplate() &&
            FormalParameterImpl.equal(this.getParameters(), 
                    ((ActivityDefinition)definition).getImpl().getParameters());
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
	    ElementReference operation = member.getImpl().getReferent();
	    return operation.getImpl().isOperation() && this.matchParameters(operation);
	}
	
	public ElementReference getType() {
	    ElementReference returnParameter = this.getReturnParameter();
	    return returnParameter == null? null: returnParameter.getImpl().getType();
	}

    public int getLower() {
        ElementReference returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getImpl().getLower();
    }
    
    public int getUpper() {
        ElementReference returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getImpl().getUpper();
    }
    
    /**
     * For a constructor, return an implicit return parameter if none is given
     * explicitly.
     */
    public ElementReference getReturnParameter() {
        ElementReference returnParameter = super.getReturnParameter();
        if (returnParameter == null && this.getSelf().getIsConstructor()) {
            FormalParameter parameter = new FormalParameter();
            parameter.setType(this.getNamespaceReference());
            parameter.setDirection("return");
            returnParameter = parameter.getImpl().getReferent();
        }
        return returnParameter;
    }

    private boolean equateParameters(ElementReference operation) {
        // NOTE: Return parameters are handled separately, so they are not
        // presumed to be at the end.
        ElementReference myReturnParameter = this.getReturnParameter();
        ElementReference otherReturnParameter = operation.getImpl().getReturnParameter();
        return operation != null &&
                    FormalParameterImpl.equal(
                        removeReturnParameter(this.getParameters()), 
                        removeReturnParameter(operation.getImpl().getParameters())) &&
                        
                        // Presume that return parameters conform for two
                        // constructor operations.
                        (this.getSelf().getIsConstructor() && 
                                operation.getImpl().isConstructor() ||
                                
                         myReturnParameter != null && 
                             FormalParameterImpl.equal(myReturnParameter, otherReturnParameter) ||
                         myReturnParameter == null && otherReturnParameter == null);
    }
    
    public static List<ElementReference> removeReturnParameter(List<ElementReference> parameters) {
        List<ElementReference> list = new ArrayList<ElementReference>();
        for (ElementReference parameter: parameters) {
            if (!"return".equals(parameter.getImpl().getDirection())) {
                list.add(parameter);
            }
        }
        return list;
    }
    
    private boolean matchParameters(ElementReference operation) {
        return operation != null && 
                    FormalParameterImpl.match(
                        this.getParameters(), 
                        operation.getImpl().getParameters());
    }
    
    /**
     * For an OperationDefinition, do not return the subunit, if it has one, as
     * the referent, because this will be an ActivityDefinition, not an
     * OperationDefinition.
     */
    @Override
    public ElementReference getReferent() {
        InternalElementReference referent = new InternalElementReference();
        referent.setElement(this.getSelf());
        return referent;
    }

    public boolean hasAlternativeConstructorCall() {
        Block body = this.getEffectiveBody();
        if (body == null) {
            return false;
        } else {
            List<Statement> statements = body.getStatement();
            if (statements.size() == 0) {
                return false;
            } else {
                Statement statement = statements.get(0);
                if (!(statement instanceof ExpressionStatement)) {
                    return false;
                } else {
                    Expression expression = 
                            ((ExpressionStatement)statement).getExpression();
                    return expression instanceof FeatureInvocationExpression &&
                            ((FeatureInvocationExpression)expression).
                                getBoundReferent().getImpl().isConstructor();
                }
            }
        }
    }
    
    public Block getSuperInvocationSegment() {
        Block body = this.getEffectiveBody();
        if (body == null) {
            return null;
        } else {
            Block superInvocationSegment = new Block();
            for (Statement statement: body.getStatement()) {
                if (statement.getImpl().isSuperConstructorInvocation()) {
                    superInvocationSegment.addStatement(statement);
                } else {
                    break;
                }
            }            
            return superInvocationSegment;
        }
    }
    
    public Block getBodySegment() {
        Block body = this.getEffectiveBody();
        if (body == null) {
            return null;
        } else {
            Block bodySegment = new Block();
            List<Statement> statements = body.getStatement();
            int i;
            for (i = 0; i < statements.size(); i++) {
                if (!statements.get(i).getImpl().isSuperConstructorInvocation()) {
                    break;
                }
            }
            for (; i < statements.size(); i++) {
                bodySegment.addStatement(statements.get(i));
            }            
            return bodySegment;
        }
    }
    
    @Override
    public ElementReference getContext() {
        return this.getNamespaceReference();
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof OperationDefinition) {
            OperationDefinition self = this.getSelf();
            OperationDefinition baseOperation = (OperationDefinition)base;
            QualifiedNameList baseRedefinitions = baseOperation.getRedefinition();
            Block body = baseOperation.getImpl().getEffectiveBody();
            self.setIsAbstract(baseOperation.getIsAbstract());
            if (baseRedefinitions != null) {
                QualifiedNameList redefinitions = new QualifiedNameList();
                
                for (QualifiedName redefinition: baseRedefinitions.getName()) {
                    redefinitions.addName(redefinition.getImpl().
                            updateBindings(templateParameters, templateArguments));
                }
                self.setRedefinition(redefinitions);
            }
            if (body != null) {
                self.setBody((Block)body.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

}
