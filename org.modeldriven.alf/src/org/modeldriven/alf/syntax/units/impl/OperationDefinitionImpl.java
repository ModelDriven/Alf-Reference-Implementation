
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
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
		return this.hasAnnotation("Create");
	}

    /**
     * An operation definition is a destructor if it has a @Destroy annotation.
     **/
	protected Boolean deriveIsDestructor() {
        return this.hasAnnotation("Destroy");
	}
	
    /**
     * An operation definition is a feature.
     **/
	@Override
	protected Boolean deriveIsFeature() {
	    return true;
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
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Operation.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow stereotypes consistent with operations.
		return annotation.getStereotypeName().getImpl().equals("Create") || 
		       annotation.getStereotypeName().getImpl().equals("Destroy");
	} // annotationAllowed

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
            FormalParameterImpl.equals(this.getFormalParameters(), 
                    ((ActivityDefinition)definition).getImpl().getFormalParameters());
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
	    ElementReference operation = member.getImpl().getReferent();
	    return operation.getImpl().isOperation() && this.matchParameters(operation);
	} // isSameKindAs
	
	public ElementReference getType() {
	    FormalParameter returnParameter = this.getReturnParameter();
	    return returnParameter == null? null: returnParameter.getType();
	}

    public int getLower() {
        FormalParameter returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getLower();
    }
    
    public int getUpper() {
        FormalParameter returnParameter = this.getReturnParameter();
        return returnParameter == null? 0: returnParameter.getUpper();
    }
    
    /*
    @Override
    public List<FormalParameter> getFormalParameters() {
        List<FormalParameter> parameters = super.getFormalParameters();
        if (this.getSelf().getIsConstructor() && !this.hasReturnParameter()) {
            FormalParameter returnParameter = new FormalParameter();
            returnParameter.setType(this.getNamespaceReference());
            returnParameter.setDirection("return");
            parameters.add(returnParameter);
        }
        return parameters;
    }
    
    private boolean hasReturnParameter() {
        List<FormalParameter> parameters = super.getFormalParameters();        
        for (FormalParameter parameter: parameters) {
            if (parameter.getDirection().equals("return")) {
                return true;
            }
        }
        return false;
    }
    */
    
    /**
     * For a constructor, return an implicit return parameter if none is given
     * explicitly.
     */
    public FormalParameter getReturnParameter() {
        FormalParameter returnParameter = super.getReturnParameter();
        if (returnParameter == null && this.getSelf().getIsConstructor()) {
            returnParameter = new FormalParameter();
            returnParameter.setType(this.getNamespaceReference());
            returnParameter.setDirection("return");
        }
        return returnParameter;
    }

    private boolean equateParameters(ElementReference operation) {
        return operation != null &&
                    FormalParameterImpl.equals(
                        this.getFormalParameters(), 
                        operation.getImpl().getParameters());
    }
    
    private boolean matchParameters(ElementReference operation) {
        return operation != null && 
                    FormalParameterImpl.match(
                        this.getFormalParameters(), 
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
                                getReferent().getImpl().isConstructor();
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
    
    public Block getBodySegement() {
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
    
    public Block getEffectiveBody() {
        return this.getEffectiveBody(this.getSelf().getSubunit());
    }
    
    private Block getEffectiveBody(UnitDefinition subunit) {
        OperationDefinition self = this.getSelf();
        if (subunit == null) {
            return self.getBody();
        } else {
            NamespaceDefinition definition = subunit.getDefinition();
            return definition instanceof ActivityDefinition?
                        ((ActivityDefinition)definition).getBody():
                        null;
        }
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

} // OperationDefinitionImpl
