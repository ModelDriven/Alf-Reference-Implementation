
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinitionImpl extends MemberImpl {

	private String lowerBound = "";
	private String upperBound = "1";
	private Boolean isOrdered = false;
	private Boolean isNonunique = false;
	private QualifiedName typeName = null;
	private ElementReference type = null; // DERIVED
	private Integer lower = null; // DERIVED
	private Integer upper = null; // DERIVED

	public TypedElementDefinitionImpl(TypedElementDefinition self) {
		super(self);
	}

	@Override
	public TypedElementDefinition getSelf() {
		return (TypedElementDefinition) this.self;
	}

	public String getLowerBound() {
		return this.lowerBound;
	}

	public void setLowerBound(String lowerBound) {
		this.lowerBound = lowerBound;
	}

	public String getUpperBound() {
		return this.upperBound;
	}

	public void setUpperBound(String upperBound) {
		this.upperBound = upperBound;
	}

	public Boolean getIsOrdered() {
		return this.isOrdered;
	}

	public void setIsOrdered(Boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public Boolean getIsNonunique() {
		return this.isNonunique;
	}

	public void setIsNonunique(Boolean isNonunique) {
		this.isNonunique = isNonunique;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public ElementReference getType() {
		if (this.type == null) {
			this.setType(this.deriveType());
		}
		return this.type;
	}

	public void setType(ElementReference type) {
		this.type = type;
	}

	public Integer getLower() {
		if (this.lower == null) {
			this.setLower(this.deriveLower());
		}
		return this.lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getUpper() {
		if (this.upper == null) {
			this.setUpper(this.deriveUpper());
		}
		return this.upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

    /**
     * The type of a typed element definition is the single classifier referent
     * of the type name.
     **/
	protected ElementReference deriveType() {
	    QualifiedName typeName = this.getSelf().getTypeName();
	    if (typeName == null) {
	        return null;
	    } else {
	        typeName.getImpl().setCurrentScope(this.getOuterScope());
	        // Note: getClassifierOnlyReferent is used here to avoid
	        // infinite recursion when doing type resolution during
	        // distinguishibilty checking.
	        return typeName.getImpl().getClassifierOnlyReferent();
	    }
	}

    /**
     * If the lower bound string image of a typed element definition is not
     * empty, then the integer lower bound is the integer value of the lower
     * bound string. Otherwise the lower bound is equal to the upper bound,
     * unless the upper bound is unbounded, in which case the lower bound is 0.
     **/
	protected Integer deriveLower() {
	    TypedElementDefinition self = this.getSelf();
	    String lowerBound = self.getLowerBound();
	    if (lowerBound != null && !lowerBound.equals("")) {
	        return Integer.valueOf(lowerBound);
	    } else {
	        int upper = self.getUpper();
	        return upper > 0? upper: 0;
	    }
	}

	/**
	 * The unlimited natural upper bound value is the unlimited natural value of
	 * the upper bound string (with "*" representing the unbounded value).
	 **/
	protected Integer deriveUpper() {
	    String upperBound = this.getSelf().getUpperBound();
	    if (upperBound == null) {
	        return 0;
	    } else {
	        return upperBound.equals("*")? -1: Integer.valueOf(upperBound);
	    }
	}
	
	/*
	 * Derivations
	 */

    public boolean typedElementDefinitionTypeDerivation() {
        this.getSelf().getType();
        return true;
    }

	public boolean typedElementDefinitionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean typedElementDefinitionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The type name of a typed element definition must have a single classifier
	 * referent. This referent may not be a template.
	 **/
	public boolean typedElementDefinitionTypeName() {
	    TypedElementDefinition self = this.getSelf();
		return self.getTypeName() == null || self.getType() != null;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof TypedElementDefinition) {
            TypedElementDefinition self = this.getSelf();
            TypedElementDefinition baseDefinition = (TypedElementDefinition)base;
            self.setLowerBound(baseDefinition.getLowerBound());
            self.setUpperBound(baseDefinition.getUpperBound());
            self.setIsOrdered(baseDefinition.getIsOrdered());
            self.setIsNonunique(baseDefinition.getIsNonunique());
            QualifiedName typeName = baseDefinition.getTypeName();
            if (typeName != null) {
                typeName.getImpl().setCurrentScope(baseDefinition.getImpl().getOuterScope());
                typeName = typeName.getImpl().
                    updateForBinding(templateParameters, templateArguments);
            }
            self.setTypeName(typeName);
        }
    }
	
} // TypedElementDefinitionImpl
