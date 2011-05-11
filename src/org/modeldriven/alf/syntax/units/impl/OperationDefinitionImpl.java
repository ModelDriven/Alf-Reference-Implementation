
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.omg.uml.Operation;

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
        if (body != null) {
            body.getImpl().setCurrentScope(this.getSelf());
        }
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

    /**
     * If an operation definition has a redefinition list, its redefined
     * operations are the referent operations of the names in the redefinition
     * list for the operation definition. Otherwise, the redefined operations
     * are any operations that would otherwise be indistinguishable from the
     * operation being defined in this operation definition.
     **/
	protected Collection<ElementReference> deriveRedefinedOperations() {
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

    public boolean operationDefinitionRedefinedOperationsDerivation() {
        this.getSelf().getRedefinedOperations();
        return true;
    }

    public boolean operationDefinitionIsFeatureDerivation() {
        this.getSelf().getIsFeature();
        return true;
    }

    public boolean operationDefinitionIsConstructorDefinition() {
        this.getSelf().getIsConstructor();
        return true;
    }

    public boolean operationDefinitionIsDestructorDefinition() {
        this.getSelf().getIsDestructor();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * The namespace for an operation definition must be a class definition. If
	 * the operation definition is abstract, then the class definition must be
	 * abstract.
	 **/
	public boolean operationDefinitionNamespace() {
	    ElementReference namespace = this.getNamespaceReference();
		return namespace != null && namespace.getImpl().isClass() && 
		        (!this.getSelf().getIsAbstract() || namespace.getImpl().isAbstractClassifier());
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
	    for (ElementReference operation: this.getSelf().getRedefinedOperations()) {
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
	    // TODO: Allow alternative constructor invocations.
	    OperationDefinition self = this.getSelf();
	    if (self.getIsConstructor()) {
    		for (ElementReference redefinedOperation: self.getRedefinedOperations()) {
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
            for (ElementReference redefinedOperation: self.getRedefinedOperations()) {
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
		return false;
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
    
    /**
     * For a constructor, add an implicit return parameter to the list of
     * formal parameters in the operation signature.
     */
    @Override
    public List<FormalParameter> getFormalParameters() {
        List<FormalParameter> parameters = super.getFormalParameters();
        if (this.getSelf().getIsConstructor() && 
                this.getReturnParameter() == null) {
            FormalParameter returnParameter = new FormalParameter();
            returnParameter.setType(this.getNamespaceReference());
            returnParameter.setDirection("return");
            parameters.add(returnParameter);
        }
        return parameters;
    }
    
    public FormalParameter getReturnParameter() {
        Collection<FormalParameter> parameters = this.getFormalParameters();
        for (FormalParameter parameter: parameters) {
            if (parameter.getDirection().equals("return")) {
                return parameter;
            }
        }
        return null;
    }

    private boolean equateParameters(ElementReference operation) {
        OperationDefinition alfOperation = (OperationDefinition)operation.getImpl().getAlf();
        Operation umlOperation = (Operation)operation.getImpl().getUml();
        if (alfOperation != null) {
            return FormalParameterImpl.equals(this.getFormalParameters(), alfOperation.getImpl().getFormalParameters());
        } else if (umlOperation != null) {
            return FormalParameterImpl.equals(this.getFormalParameters(), umlOperation.getOwnedParameter());
        } else {
            return false;
        }
    }
    
    private boolean matchParameters(ElementReference operation) {
        return operation != null && 
                    FormalParameterImpl.match(this.getFormalParameters(), 
                            operation.getImpl().getParameters());
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

} // OperationDefinitionImpl
