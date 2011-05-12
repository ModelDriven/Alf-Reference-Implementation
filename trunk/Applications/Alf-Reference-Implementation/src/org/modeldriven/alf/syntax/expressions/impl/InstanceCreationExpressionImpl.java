
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.List;

/**
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpressionImpl
		extends InvocationExpressionImpl {

	private Boolean isConstructorless = null; // DERIVED
	private Boolean isObjectCreation = null; // DERIVED
	private QualifiedName constructor = null;

	public InstanceCreationExpressionImpl(InstanceCreationExpression self) {
		super(self);
	}

	@Override
	public InstanceCreationExpression getSelf() {
		return (InstanceCreationExpression) this.self;
	}

	public Boolean getIsConstructorless() {
		if (this.isConstructorless == null) {
			this.setIsConstructorless(this.deriveIsConstructorless());
		}
		return this.isConstructorless;
	}

	public void setIsConstructorless(Boolean isConstructorless) {
		this.isConstructorless = isConstructorless;
	}

	public Boolean getIsObjectCreation() {
		if (this.isObjectCreation == null) {
			this.setIsObjectCreation(this.deriveIsObjectCreation());
		}
		return this.isObjectCreation;
	}

	public void setIsObjectCreation(Boolean isObjectCreation) {
		this.isObjectCreation = isObjectCreation;
	}

	public QualifiedName getConstructor() {
		return this.constructor;
	}

	public void setConstructor(QualifiedName constructor) {
		this.constructor = constructor;
	}

	/**
	 * An instance creation expression is constructorless if its referent is a
	 * class.
	 **/
	protected Boolean deriveIsConstructorless() {
	    InstanceCreationExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
		return referent != null && referent.getImpl().isClass();
	}

	/**
	 * An instance creation expression is an object creation if its referent is
	 * not a data type.
	 **/
	protected Boolean deriveIsObjectCreation() {
        InstanceCreationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isDataType();
	}
	
	/**
	 * The referent of an instance creation expression is the constructor
	 * operation, class or data type to which the constructor name resolves.
	 **/
	@Override
	protected ElementReference deriveReferent() {
        InstanceCreationExpression self = this.getSelf();
	    QualifiedName constructor = self.getConstructor();
	    if (constructor == null) {
	        return null;
	    } else {
	        ElementReference classReferent = 
	            constructor.getImpl().getClassReferent();
	        ElementReference dataTypeReferent = 
	            constructor.getImpl().getDataTypeReferent();
	        if (classReferent != null) {
	            if (dataTypeReferent != null) {
	                return null;
	            } else {
	                return classReferent;
	            }
	        } else if (dataTypeReferent != null) {
	            return dataTypeReferent;
	        } else {
                // TODO: Handle overloading resolution.
                ElementReference operationReferent = 
                    constructor.getImpl().getOperationReferent();
                return operationReferent == null || 
                            !operationReferent.getImpl().isConstructor()? null:
                            operationReferent;
	        }
	    }
	}
	
	/**
	 * There is no feature for an instance creation expression.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
	    return null;
	}
	
	/**
	 * If the referent is a classifier, not a constructor operation, then the 
	 * type of an instance creation expression is given by the referent itself,
	 * not a return parameter type. 
	 */
	@Override
	protected ElementReference deriveType() {
	    ElementReference referent = this.getSelf().getReferent();
	    if (referent != null && referent.getImpl().isClassifier()) {
	        return referent;
	    } else {
	        return super.deriveType();
	    }
	}
	
    /**
     * If the referent is a classifier, not a constructor operation, then the 
     * lower bound of an instance creation expression is 1, rather than being
     * given a return parameter lower bound. 
     */
    @Override
    protected Integer deriveLower() {
        ElementReference referent = this.getSelf().getReferent();
        if (referent != null && referent.getImpl().isClassifier()) {
            return 1;
        } else {
            return super.deriveLower();
        }
    }
    
    /**
     * If the referent is a classifier, not a constructor operation, then the 
     * upper bound of an instance creation expression is 1, rather than being
     * given a return parameter lower bound. 
     */
    @Override
    protected Integer deriveUpper() {
        ElementReference referent = this.getSelf().getReferent();
        if (referent != null && referent.getImpl().isClassifier()) {
            return 1;
        } else {
            return super.deriveUpper();
        }
    }
    
	/*
	 * Derivations
	 */

	public boolean instanceCreationExpressionIsObjectCreationDerivation() {
		this.getSelf().getIsObjectCreation();
		return true;
	}

	public boolean instanceCreationExpressionIsConstructorlessDerivation() {
		this.getSelf().getIsConstructorless();
		return true;
	}

	public boolean instanceCreationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean instanceCreationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/*
	 * Constraints
	 */

    /**
     * The constructor name must resolve to a constructor operation (that is
     * compatible with the tuple argument expressions), a class or a data type,
     * but not both a class and a data type.
     **/
	public boolean instanceCreationExpressionConstructor() {
		return this.getSelf().getReferent() != null;
	}
	
	/**
	 * If the expression is constructorless, then its tuple must be empty.
	 **/
	public boolean instanceCreationExpressionTuple() {
	    InstanceCreationExpression self = this.getSelf();
	    Tuple tuple = self.getTuple();
		return !self.getIsConstructorless() || 
		            tuple != null && tuple.getImpl().isEmpty();
	}

	/**
	 * If an instance creation expression is a data value creation (not an
	 * object creation), then the tuple argument expressions are matched with
	 * the attributes of the named type.
	 **/
	public boolean instanceCreationExpressionDataTypeCompatibility() {
	    // TODO: Once overloading resolution is implemented, change this to only
	    // be for data value creation.
        InstanceCreationExpression self = this.getSelf();
        Tuple tuple = self.getTuple();
        if (tuple == null) {
            return false;
        } else {
            for (NamedExpression input: tuple.getInput()) {
                if (!this.parameterIsAssignableFrom(input)) {
                    return false;
                }
            }
            for (NamedExpression output: tuple.getOutput()) {
                if (!this.parameterIsAssignableTo(output)) {
                    return false;
                }
            }
            return true;
        }
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns the parameters of a constructor operation or the attributes of a
	 * data type, or an empty set for a constructorless instance creation.
	 **/
	@Override
	public List<ElementReference> parameterElementList() {
        if (this.getSelf().getIsConstructorless()) {
            return new ArrayList<ElementReference>();
        } else {
            return super.parameterElementList();
        }
	}
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    InstanceCreationExpression self = this.getSelf();
	    QualifiedName constructor = self.getConstructor();
	    if (constructor != null) {
	        constructor.getImpl().setCurrentScope(currentScope);
	    }
	}

} // InstanceCreationExpressionImpl
