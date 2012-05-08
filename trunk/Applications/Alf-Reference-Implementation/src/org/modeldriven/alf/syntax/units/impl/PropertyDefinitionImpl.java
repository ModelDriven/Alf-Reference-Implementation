
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinitionImpl extends TypedElementDefinitionImpl {

	private Boolean isComposite = false;
	private Expression initializer = null;
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED

	public PropertyDefinitionImpl(PropertyDefinition self) {
		super(self);
	}

	public PropertyDefinition getSelf() {
		return (PropertyDefinition) this.self;
	}

	public Boolean getIsComposite() {
		return this.isComposite;
	}

	public void setIsComposite(Boolean isComposite) {
		this.isComposite = isComposite;
	}

	public Expression getInitializer() {
		return this.initializer;
	}

	public void setInitializer(Expression initializer) {
		this.initializer = initializer;
		
        // Note: The following accounts for short form instance and sequence 
        // initialization expressions. It requires that the type name and
		// multiplicity be set before the initializer.
		PropertyDefinition self = this.getSelf();
		if (this.initializer instanceof InstanceCreationExpression) {
		    InstanceCreationExpression expression =
		        (InstanceCreationExpression)this.initializer;
		    if (expression.getConstructor() == null) {
		        expression.setConstructor(self.getTypeName());
		    }
		} else if (this.initializer instanceof SequenceConstructionExpression) {
		    SequenceConstructionExpression expression =
		        (SequenceConstructionExpression)this.initializer;
		    if (expression.getTypeName() == null) {
		        expression.setTypeName(self.getTypeName());
		        int upperBound = self.getUpper();
	            expression.setHasMultiplicity(upperBound == -1 || upperBound > 1);
		    }
		}
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.setIsCollectionConversion(this.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}
	
	// Note: This presumes that the initializer for a property is set before
	// the property is added to its classifier (namespace).	
	@Override
	public void setNamespace(NamespaceDefinition namespace) {
	    super.setNamespace(namespace);
	    Expression initializer = this.getSelf().getInitializer();
	    if (initializer != null) {
	        initializer.getImpl().setCurrentScope(namespace);
	    }
	}

    /**
     * A property definition requires collection conversion if its initializer
     * has a collection class as its type and the property definition does not.
     **/
	protected Boolean deriveIsCollectionConversion() {
	    PropertyDefinition self = this.getSelf();
	    Expression initializer = self.getInitializer();
	    if (initializer == null) {
	        return false;
	    } else {
	        ElementReference initializerType = initializer.getType();
	        ElementReference selfType = self.getType();
	        return initializerType != null && selfType != null &&
	                initializerType.getImpl().isCollectionClass() &&
	                !self.getType().getImpl().isCollectionClass();
	    }
	}

    /**
     * A property definition requires BitString conversion if its type is
     * BitString and the type of its initializer is Integer or a collection
     * class whose argument type is Integer.
     **/
	protected Boolean deriveIsBitStringConversion() {
        PropertyDefinition self = this.getSelf();
        Expression initializer = self.getInitializer();
        if (initializer == null) {
            return false;
        } else {
            ElementReference initializerType = self.getInitializer().getType();
            ElementReference selfType = self.getType();
            return initializerType != null && selfType != null &&
                    self.getType().getImpl().isBitString() &&
                    (initializerType.getImpl().isInteger() ||
                    initializerType.getImpl().isIntegerCollection());
        }
	}
	
    /**
     * A property definition is a feature.
     **/
	@Override
	protected Boolean deriveIsFeature() {
	    return true;
	}
	
	/*
	 * Derivations
	 */

	public boolean propertyDefinitionIsCollectionConversionDerivation() {
		this.getSelf().getIsCollectionConversion();
		return true;
	}

    public boolean propertyDefinitionIsBitStringConversion() {
        this.getSelf().getIsBitStringConversion();
        return true;
    }

    public boolean propertyDefinitionIsFeatureDerivation() {
        this.getSelf().getIsFeature();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * If a property definition has an initializer, then the initializer
	 * expression must be assignable to the property definition.
	 **/
	public boolean propertyDefinitionInitializer() {
	    PropertyDefinition self = this.getSelf();
	    Expression initializer = self.getInitializer();
	    if (initializer == null) {
	        return true;
	    } else {
	        initializer.getImpl().setCurrentScope(this.getOuterScope());
    		return new AssignableTypedElementImpl(this).isAssignableFrom(initializer);
	    }
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Property.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO Allow stereotypes consistent with properties.
		return false;
	} // annotationAllowed

	/**
	 * Return true if the given member is either a PropertyDefinition or an
	 * imported member whose referent is a PropertyDefinition or a Property.
	 **/
	public Boolean isSameKindAs(Member member) {
		return member.getImpl().getReferent().getImpl().isProperty();
	} // isSameKindAs

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof PropertyDefinition) {
            PropertyDefinition self = this.getSelf();
            PropertyDefinition baseDefinition = (PropertyDefinition)base;
            Expression initializer = baseDefinition.getInitializer();
            self.setIsComposite(baseDefinition.getIsComposite());
            if (initializer != null) {
                self.setInitializer((Expression)initializer.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // PropertyDefinitionImpl
