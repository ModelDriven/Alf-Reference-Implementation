
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.AssignableTypedElementImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpressionImpl extends ExpressionImpl {

	private Boolean isBehavior = null; // DERIVED
	private Boolean isAssociationEnd = null; // DERIVED
	private FeatureReference feature = null; // DERIVED
	private Tuple tuple = null;
	private Boolean isOperation = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED
	private Boolean isImplicit = null; // DERIVED
	private ElementReference referent = null; // DERIVED
	private List<ElementReference> parameter = null; // DERIVED
	private Boolean isSignal = null; // DERIVED

	public InvocationExpressionImpl(InvocationExpression self) {
		super(self);
	}

	@Override
	public InvocationExpression getSelf() {
		return (InvocationExpression) this.self;
	}

	public Boolean getIsBehavior() {
		if (this.isBehavior == null) {
			this.setIsBehavior(this.deriveIsBehavior());
		}
		return this.isBehavior;
	}

	public void setIsBehavior(Boolean isBehavior) {
		this.isBehavior = isBehavior;
	}

	public Boolean getIsAssociationEnd() {
		if (this.isAssociationEnd == null) {
			this.setIsAssociationEnd(this.deriveIsAssociationEnd());
		}
		return this.isAssociationEnd;
	}

	public void setIsAssociationEnd(Boolean isAssociationEnd) {
		this.isAssociationEnd = isAssociationEnd;
	}

	public FeatureReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(FeatureReference feature) {
		this.feature = feature;
	}

	public Tuple getTuple() {
		return this.tuple;
	}

	public void setTuple(Tuple tuple) {
		this.tuple = tuple;
	}

	public Boolean getIsOperation() {
		if (this.isOperation == null) {
			this.setIsOperation(this.deriveIsOperation());
		}
		return this.isOperation;
	}

	public void setIsOperation(Boolean isOperation) {
		this.isOperation = isOperation;
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

	public Boolean getIsImplicit() {
		if (this.isImplicit == null) {
			this.setIsImplicit(this.deriveIsImplicit());
		}
		return this.isImplicit;
	}

	public void setIsImplicit(Boolean isImplicit) {
		this.isImplicit = isImplicit;
	}

	public ElementReference getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	public List<ElementReference> getParameter() {
		if (this.parameter == null) {
			this.setParameter(this.deriveParameter());
		}
		return this.parameter;
	}

	public void setParameter(List<ElementReference> parameter) {
		this.parameter = parameter;
	}

	public void addParameter(ElementReference parameter) {
		this.parameter.add(parameter);
	}

	public Boolean getIsSignal() {
		if (this.isSignal == null) {
			this.setIsSignal(this.deriveIsSignal());
		}
		return this.isSignal;
	}

	public void setIsSignal(Boolean isSignal) {
		this.isSignal = isSignal;
	}

    /**
     * An invocation expression is a behavior invocation if its referent is a
     * behavior.
     **/
	protected Boolean deriveIsBehavior() {
	    InvocationExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
		return referent != null && referent.getImpl().isBehavior();
	}

    /**
     * An invocation expression is an association end read if its referent is an
     * association end.
     **/
	protected Boolean deriveIsAssociationEnd() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isAssociationEnd();
	}

	/**
	 * This property is set for a feature invocation expression or for an 
	 * expression initially parsed as a behavior invocation expression that
     * disambiguates to a feature invocation expression.
	 */
	protected abstract FeatureReference deriveFeature();

    /**
     * An invocation expression is an operation call if its referent is an
     * operation.
     **/
	protected Boolean deriveIsOperation() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isOperation();
	}

    /**
     * An invocation expression is a destructor call either implicitly or if it
     * is an explicit operation call to a destructor operation.
     **/
	protected Boolean deriveIsDestructor() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return self.getIsImplicit() || 
                    referent != null && referent.getImpl().isDestructor();
	}

    /**
     * An invocation expression is an implicit object destruction if it has a
     * feature with the name "destroy" and no explicit referents.
     **/
	protected Boolean deriveIsImplicit() {
        InvocationExpression self = this.getSelf();
        FeatureReference feature = self.getFeature();
        NameBinding nameBinding = feature == null? null: feature.getNameBinding();
        ElementReference referent = self.getReferent();
        return nameBinding != null && referent == null &&
                    nameBinding.getName().equals("destroy") && 
                    nameBinding.getBinding() == null;
	}

	/**
	 * The behavior, operation or signal being invoked. The derivation of this 
	 * property is specific to each kind of invocation expression.
	 */
	protected abstract ElementReference deriveReferent();

    /**
     * The parameters of an invocation expression are given by the result of the
     * parameterElements helper operation.
     **/
	protected List<ElementReference> deriveParameter() {
		return this.parameterElementList();
	}

    /**
     * An invocation expression is a signal send if its referent is a signal.
     **/
	protected Boolean deriveIsSignal() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isSignal();
	}
	
    /**
     * The type of an invocation expression is determined by the return
     * parameter (if any) of the referent.
     **/
	@Override
	protected ElementReference deriveType() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
	    return referent == null? null: referent.getImpl().getType();
	}
	
    /**
     * The multiplicity upper bound of an invocation expression is determined by
     * the return parameter (if any) of the referent.
     **/
    @Override
	protected Integer deriveUpper() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent == null? 0: referent.getImpl().getUpper();
	}
	
    /**
     * The multiplicity lower bound of an invocation expression is determined by
     * the return parameter (if any) of the referent.
     **/
    @Override
	protected Integer deriveLower() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent == null? 0: referent.getImpl().getLower();
	}
    
    /*
     * Derivations
     */

	public boolean invocationExpressionIsBehaviorDerivation() {
		this.getSelf().getIsBehavior();
		return true;
	}

	public boolean invocationExpressionIsAssociationEndDerivation() {
		this.getSelf().getIsAssociationEnd();
		return true;
	}

	public boolean invocationExpressionIsOperationDerivation() {
		this.getSelf().getIsOperation();
		return true;
	}

	public boolean invocationExpressionIsDestructorDerivation() {
		this.getSelf().getIsDestructor();
		return true;
	}

	public boolean invocationExpressionIsImplicitDerivation() {
		this.getSelf().getIsImplicit();
		return true;
	}

	public boolean invocationExpressionIsSignalDerivation() {
		this.getSelf().getIsSignal();
		return true;
	}

	public boolean invocationExpressionParameterDerivation() {
		this.getSelf().getParameter();
		return true;
	}

	public boolean invocationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean invocationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean invocationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments before the target expression of the feature reference of
	 * an invocation expression (if any) are the same as the assignments before
	 * the invocation expression.
	 **/
	public boolean invocationExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns references to the elements that act as the parameters of the
	 * referent. For a behavior or operation, these are the owned parameters, in
	 * order. Otherwise (by default), they are actually any properties of the
	 * referent (e.g., signal attributes), which are treated as if they were in
	 * parameters. (This is defined as a helper operation, so that it can be
	 * overridden by subclasses of InvocationExpression, if necessary.)
	 **/
	// Note: The result of this operation should be a List.
	public Collection<ElementReference> parameterElements() {
		return this.parameterElementList();
	} // parameterElements

    public List<ElementReference> parameterElementList() {
        List<ElementReference> parameters = new ArrayList<ElementReference>();
        for (FormalParameter parameter: this.parameters()) {
            parameters.add(parameter.getImpl().getReferent());
        }
        return parameters;
    }
    
    public List<FormalParameter> parameters() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        List<FormalParameter> parameters = new ArrayList<FormalParameter>();
        if (self.getIsBehavior() || self.getIsOperation()) {
            parameters = referent.getImpl().getParameters();
        } else if (self.getIsAssociationEnd()) {
            ElementReference association = referent.getImpl().getAssociation();
            String referentName = referent.getImpl().getName();
            for (ElementReference property: association.getImpl().getAssociationEnds()) {
                if (!property.getImpl().getName().equals(referentName)) {
                    parameters.add(parameterFromProperty(property));
                }
            }
        } else if (referent != null) {
             for (ElementReference property: referent.getImpl().getAttributes()) {
                parameters.add(parameterFromProperty(property));
            }
        }
        return parameters;
    }
    
    protected static FormalParameter parameterFromProperty(ElementReference property) {
        ElementReferenceImpl propertyImpl = property.getImpl();
        FormalParameter parameter = new FormalParameter();
        parameter.setName(propertyImpl.getName());
        parameter.setType(propertyImpl.getType());
        parameter.setLower(propertyImpl.getLower());
        parameter.setUpper(propertyImpl.getUpper());
        parameter.setDirection("in");
        return parameter;
    }

	/**
	 * The assignments after an invocation expression are the same as those
	 * after the tuple of the expression.
	 **/
	public Map<String, AssignedSource> updateAssignmentMap() {
	    InvocationExpression self = this.getSelf();
	    FeatureReference feature = self.getFeature();
	    Tuple tuple = self.getTuple();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    if (feature != null) {
	        Expression expression = feature.getExpression();
	        if (expression != null) {
	            expression.getImpl().setAssignmentBefore(assignments);
	            assignments = expression.getImpl().getAssignmentAfterMap();
	        }
	    }
	    if (tuple != null) {
	        assignments = tuple.getImpl().getAssignmentsAfterMap();
	    }
	    return assignments;
	} // updateAssignments
	
	protected boolean parameterIsAssignableFrom(NamedExpression input) {
	    FormalParameter namedParameter = this.parameterNamed(input.getName());
        if (namedParameter == null) {
	        return false;
	    } else {
	        String direction = namedParameter.getDirection();
	        return (direction.equals("in") || direction.equals("inout")) &&
	                    namedParameter.getImpl().isAssignableFrom(input.getExpression());
	    }
	}

    protected boolean parameterIsAssignableTo(NamedExpression output) {
        FormalParameter namedParameter = this.parameterNamed(output.getName());
        if (namedParameter == null) {
            return false;
        } else {
            String direction = namedParameter.getDirection();
            return (direction.equals("out") || direction.equals("inout")) &&
                        output instanceof OutputNamedExpression &&
                        ((OutputNamedExpression)output).getLeftHandSide().getImpl().
                            isAssignableFrom(new AssignableTypedElementImpl(namedParameter.getImpl()));
        }
    }
    
    public FormalParameter parameterNamed(String name) {
        for (FormalParameter parameter: this.parameters()) {
            if (parameter.getName().equals(name)) {
                return parameter;
            }
        }
        return null;
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        Tuple tuple = this.getSelf().getTuple();
        if (tuple != null) {
            tuple.getImpl().setCurrentScope(currentScope);
        }
    }

} // InvocationExpressionImpl
