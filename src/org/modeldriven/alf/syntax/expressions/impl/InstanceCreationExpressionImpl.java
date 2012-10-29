
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
        return referent == null || !referent.getImpl().isDataType();
	}
	
    /**
     * The referent of an instance creation expression is normally the
     * constructor operation, class or data type to which the constructor name
     * resolves. However, if the referent is an operation whose class is
     * abstract or is a class that is itself abstract, and there is an
     * associated Impl class constructor, then the referent is the Impl class
     * constructor.
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
	        if (dataTypeReferent != null) {
	            return classReferent != null? null: dataTypeReferent;
	        } else {
    	        if (classReferent != null) {
    	            String name = constructor.getUnqualifiedName().getName();
    	            constructor = constructor.getImpl().copy().addName(name);
    	        }
                // TODO: Handle overloading resolution.
                ElementReference operationReferent = 
                    constructor.getImpl().getOperationReferent();
                if (operationReferent == null || 
                        !operationReferent.getImpl().isConstructor()) {
                    return classReferent;
                } else {
                    classReferent = operationReferent.getImpl().getNamespace();
                    if (classReferent.getImpl().isAbstractClassifier()) {
                        
                        // Check for an "Impl" package.
                        NamespaceDefinition classDefinition = 
                            classReferent.getImpl().getNamespace().getImpl().
                                asNamespace();
                        QualifiedName className = classDefinition.getImpl().
                            getQualifiedName();
                        QualifiedName implPackageName = 
                            className.getImpl().copy().addName("Impl");
                        ElementReference implPackageReferent = 
                            implPackageName.getImpl().getNamespaceReferent();
                        if (implPackageReferent != null &&
                                implPackageReferent.getImpl().isPackage()) {
                            QualifiedName qualification = 
                                constructor.getQualification();
                            NameBinding unqualifiedName = 
                                constructor.getUnqualifiedName();
                            QualifiedName implConstructor;
                            
                            if (qualification == null) {
                                // Note: If there is no qualification at this
                                // point, then the constructor operation cannot
                                // be for a template binding.
                                implConstructor = new QualifiedName().getImpl().
                                    addName("Impl").getImpl().
                                        addName(classDefinition.getName());
                                implConstructor.getImpl().
                                    setCurrentScope(classDefinition);
                                
                            } else {                           
                                // Note: Constructing a qualified name with 
                                // "Impl" inserted and resolving it makes sure
                                // that all template bindings are handled 
                                // correctly.
                                implConstructor = qualification.getQualification();
                                if (implConstructor == null) {
                                    implConstructor = new QualifiedName();
                                    implConstructor.getImpl().
                                        setCurrentScope(classDefinition);
                                }
                                implConstructor.getImpl().addName("Impl").
                                    addNameBinding(qualification.getUnqualifiedName());
                            }
                            
                            implConstructor.addNameBinding(unqualifiedName);
                            ElementReference implOperationReferent = 
                                implConstructor.getImpl().getOperationReferent();
                            
                            if (implOperationReferent != null) {
                                operationReferent = implOperationReferent;
                            }                            
                        }
                    }
                    
                    return operationReferent;
                }
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
	    ElementReference referent = this.getSelf().getReferent();
	    return referent != null &&
    	          (referent.getImpl().isDataType() ||
    	           referent.getImpl().isClass() ||
    	           referent.getImpl().isOperation());
	}
	
    /**
     * If the expression is constructorless, then its tuple must be empty and
     * the referent class must not have any owned operations that are
     * constructors.
     **/
	public boolean instanceCreationExpressionConstructorlessLegality() {
	    InstanceCreationExpression self = this.getSelf();
	    Tuple tuple = self.getTuple();
		return !self.getIsConstructorless() || 
		            tuple != null && tuple.getImpl().isEmpty() &&
		            this.referentHasNoConstructors();
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
        if (tuple == null || 
                tuple.getImpl().size() > this.parameters().size()) {
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
	
    /**
     * If the referent of an instance creation expression is an operation, then
     * the class of that operation must not be abstract. Otherwise, the referent
     * is a class or data type, which must not be abstract.
     **/
    public boolean instanceCreationExpressionReferent() {
        InstanceCreationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        if (referent != null && referent.getImpl().isOperation()) {
            referent = referent.getImpl().getNamespace();
        }
        return referent == null || !referent.getImpl().isAbstractClassifier();
    }

	/*
	 * Helper Methods
	 */

    /**
     * Returns the parameters of a constructor operation or the attributes of a
     * data type, or an empty set for a constructorless instance creation.
     **/
    @Override
    public List<FormalParameter> parameters() {
        if (this.getSelf().getIsConstructorless()) {
            return new ArrayList<FormalParameter>();
        } else {
            return super.parameters();
        }
    }
    
	private boolean referentHasNoConstructors() {
	    for (ElementReference feature: 
	        this.getSelf().getReferent().getImpl().getOwnedMembers()) {
	        if (feature.getImpl().isConstructor()) {
	            return false;
	        }
	    }
	    return true;
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

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof InstanceCreationExpression) {
            QualifiedName constructor = 
                ((InstanceCreationExpression)base).getConstructor();
            if (constructor != null) {
                this.getSelf().setConstructor(constructor.getImpl().
                        updateForBinding(templateParameters, templateArguments));
            }
        }
    }

} // InstanceCreationExpressionImpl
