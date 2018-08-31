/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import java.util.Collection;
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
		return this.isConstructorless(null);
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
     * constructor. Further, if the constructor name of an instance creation
     * expression is empty, then the referent must be determined from the
     * context of use of the expression.
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
	            ElementReference operationReferent = null;
    	        if (classReferent != null) {
                    String name = constructor.getUnqualifiedName().getName();
                    constructor = constructor.getImpl().copy().addName(name);
    	        }
	            Collection<ElementReference> constructorReferents = 
	                    new ArrayList<ElementReference>();
	            for (ElementReference referent: constructor.getReferent()) {
	                if (referent.getImpl().isConstructor()) {
	                    constructorReferents.add(referent);
	                }
	            }
                operationReferent = this.resolveOverloading(constructorReferents);
     	        
                if (operationReferent == null || 
                        !operationReferent.getImpl().isConstructor()) {
                    return classReferent;
                } else {
                    classReferent = operationReferent.getImpl().getNamespace();
                    if (classReferent.getImpl().isAbstractClassifier()) {
                        
                        // Check for an "Impl" package.
                        ElementReference template = classReferent.getImpl().getTemplate();
                        while (template != null) {
                            classReferent = template;
                            template = classReferent.getImpl().getTemplate();
                        }
                        ElementReference namespaceReferent = 
                                classReferent.getImpl().getNamespace();
                        if (namespaceReferent != null) {
                            NamespaceDefinition namespaceDefinition = 
                                    namespaceReferent.getImpl().asNamespace();
                            QualifiedName namespaceName = namespaceDefinition.getImpl().
                                    getQualifiedName();
                            QualifiedName implPackageName = 
                                    namespaceName.getImpl().copy().addName("Impl");
                            implPackageName.getImpl().setIsVisibleOnly(false);
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
                                            addName(namespaceDefinition.getName());
                                    implConstructor.getImpl().
                                        setCurrentScope(namespaceDefinition);

                                } else {                           
                                    // Note: Constructing a qualified name with 
                                    // "Impl" inserted and resolving it makes sure
                                    // that all template bindings are handled 
                                    // correctly.
                                    implConstructor = 
                                            qualification.getImpl().copy().getQualification();
                                    if (implConstructor == null) {
                                        implConstructor = new QualifiedName();
                                        implConstructor.getImpl().
                                            setCurrentScope(namespaceDefinition);
                                    }
                                    implConstructor.getImpl().addName("Impl").
                                        addNameBinding(qualification.getUnqualifiedName());
                                }

                                implConstructor.addNameBinding(unqualifiedName);
                                implConstructor.getImpl().setIsVisibleOnly(false);
                                ElementReference implOperationReferent = 
                                        implConstructor.getImpl().getOperationReferent();

                                if (implOperationReferent != null && 
                                        implOperationReferent.getImpl().isConstructor()) {
                                    operationReferent = implOperationReferent;
                                }                            
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
	 * type of an instance creation expression is given by the referent itself. 
     * Otherwise, for a constructor operation, return the owning class of the 
     * constructor (not the return type of the constructor). (This allows for 
     * the use of external constructors that may not actually return the 
     * constructed object, as they should according to UML rules.)
	 */
	@Override
	protected ElementReference deriveType() {
	    ElementReference referent = this.getSelf().getReferent();
	    if (referent != null && referent.getImpl().isClassifier()) {
	        return referent;
	    } else {
	        // For a constructor operation, return the owning class of the
	        // constructor, rather than the return type of the constructor.
	        // This allows for the use of (external) constructors that may
	        // not actually return the constructed object (as they should
	        // according to UML rules).
	        return referent == null? null: referent.getImpl().getNamespace();
	    }
	}
	
    /**
     * The lower bound of an instance creation expression should always be 1. 
     */
    @Override
    protected Integer deriveLower() {
        return 1;
    }
    
    /**
     * The upper bound of an instance creation expression should always be 1. 
     */
    @Override
    protected Integer deriveUpper() {
        return 1;
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
		return this.getSelf().getReferent() == null || !self.getIsConstructorless() ||
		            tuple != null && tuple.getImpl().isEmpty() &&
		            this.referentHasNoConstructors();
	}

	/**
	 * If an instance creation expression is a data value creation (not an
	 * object creation), then the tuple argument expressions are matched with
	 * the attributes of the named type.
	 **/
	public boolean instanceCreationExpressionDataTypeCompatibility() {
        InstanceCreationExpression self = this.getSelf();
        return self.getIsObjectCreation() || this.isCompatibleWith(null);
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
    public List<ElementReference> parametersFor(ElementReference referent) {
        return this.isConstructorless(referent)? 
                new ArrayList<ElementReference>():
                super.parametersFor(referent);
    }
    
    protected boolean isConstructorless(ElementReference referent) {
        if (referent == null) {
            referent = this.getSelf().getReferent();
        }
        return referent != null && referent.getImpl().isClass();
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
