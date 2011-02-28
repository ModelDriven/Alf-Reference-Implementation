
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
import org.modeldriven.alf.syntax.units.*;

/**
 * The common properties of the definitions of typed elements.
 **/

public abstract class TypedElementDefinitionImpl extends MemberImpl {

	public TypedElementDefinitionImpl(TypedElementDefinition self) {
		super(self);
	}

	@Override
	public TypedElementDefinition getSelf() {
		return (TypedElementDefinition) this.self;
	}

    /**
     * The type of a typed element definition is the single classifier referent
     * of the type name.
     **/
	public ElementReference deriveType() {
	    QualifiedName typeName = this.getSelf().getTypeName();
	    if (typeName == null) {
	        return null;
	    } else {
	        typeName.getImpl().setCurrentScope(this.getOuterScope());
	        return typeName.getImpl().getClassifierReferent();
	    }
	}

    /**
     * If the lower bound string image of a typed element definition is not
     * empty, then the integer lower bound is the integer value of the lower
     * bound string. Otherwise the lower bound is equal to the upper bound,
     * unless the upper bound is unbounded, in which case the lower bound is 0.
     **/
	public Integer deriveLower() {
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
	public Integer deriveUpper() {
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
		return this.getSelf().getType() != null;
	}

} // TypedElementDefinitionImpl
