
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
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
                    (initializerType.getImpl().isCollectionClass() ||
                    initializerType.getImpl().isIntegerCollection()) &&
                    self.getType().getImpl().isBitString();
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
	    initializer.getImpl().setCurrentScope(this.getOuterScope());
		return new AssignableTypedElementImpl(this).isAssignableFrom(initializer);
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

} // PropertyDefinitionImpl
