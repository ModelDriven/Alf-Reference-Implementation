
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Map;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression used to construct a sequence of values.
 **/

public class SequenceConstructionExpressionImpl extends ExpressionImpl {

	private SequenceElements elements = null;
	private Boolean hasMultiplicity = false;
	private QualifiedName typeName = null;
	
	// If this expression does not have an explicit type name, but is contained
	// in the element list of a sequence construction expression with a
	// collection type, then this is the collection type name for that outer
	// sequence construction expression.
	private QualifiedName collectionTypeName = null;

	public SequenceConstructionExpressionImpl(
			SequenceConstructionExpression self) {
		super(self);
	}

	@Override
	public SequenceConstructionExpression getSelf() {
		return (SequenceConstructionExpression) this.self;
	}

	public SequenceElements getElements() {
		return this.elements;
	}

	public void setElements(SequenceElements elements) {
		this.elements = elements;
	}

	public Boolean getHasMultiplicity() {
		return this.hasMultiplicity;
	}

	public void setHasMultiplicity(Boolean hasMultiplicity) {
		this.hasMultiplicity = hasMultiplicity;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
		
		// This propagates the type name of this expression down as a collection
		// type to be potentially used in determining the type of contained
		// sequence construction expressions without explicit type names.
		// Note: This requires that the elements be set BEFORE the type name.
        SequenceConstructionExpression self = this.getSelf();
        if (!self.getHasMultiplicity() && typeName != null) {
            self.getElements().getImpl().setCollectionTypeName(typeName);
        }
	}

	public QualifiedName getCollectionTypeName() {
	    return this.collectionTypeName;
	}
	
	public void setCollectionTypeName(QualifiedName collectionTypeName) {
        this.collectionTypeName = collectionTypeName;
    }

	/**
	 * The type of a sequence construction expression is the named type.
	 **/
	@Override
	protected ElementReference deriveType() {
	    QualifiedName typeName = this.getSelf().getTypeName();
	    QualifiedName collectionTypeName = this.getCollectionTypeName();
	    if (typeName != null) {
	        return typeName.getImpl().getNonTemplateClassifierReferent();
	    } else if (collectionTypeName != null) {
	        ElementReference collectionType = 
	            collectionTypeName.getImpl().getNonTemplateClassifierReferent();
	        return collectionType == null? null: collectionType.getImpl().getCollectionArgument();
	    } else {
	        return null;
	    }
	}
	
	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity upper bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity upper bound is one.
	 **/
	@Override
	protected Integer deriveUpper() {
	    SequenceConstructionExpression self = this.getSelf();
	    SequenceElements elements = self.getElements();
	    return self.getHasMultiplicity()? 
	                elements == null? 0: elements.getUpper():
	                1;
	}
	
	/**
	 * If a sequence construction expression has multiplicity, then its
	 * multiplicity lower bound is given by its elements, if this is not empty,
	 * and zero otherwise. If a sequence construction expression does not have
	 * multiplicity, then its multiplicity lower bound is one.
	 **/
    @Override
    protected Integer deriveLower() {
        SequenceConstructionExpression self = this.getSelf();
        SequenceElements elements = self.getElements();
        return self.getHasMultiplicity()? 
                    elements == null? 0: elements.getLower():
                    1;
    }
	
	/*
	 * Derivations
	 */
	
	public boolean sequenceConstructionExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean sequenceConstructionExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean sequenceConstructionExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The type name of a sequence construction expression must resolve to a
	 * non-template classifier. If the expression does not have multiplicity,
	 * then this classifier must be the instantiation of a collection class.
	 **/
	public boolean sequenceConstructionExpressionType() {
	    SequenceConstructionExpression self = this.getSelf();
	    ElementReference type = self.getType();
	    SequenceElements elements = self.getElements();
	    // Note: The checking of the sequence elements should really be done
	    // in separate constraints on the SequenceElements subclasses.
		return (elements == null || elements.getImpl().checkElements(self)) &&
		        self.getHasMultiplicity()? 
		            self.getTypeName() == null || type != null:
		            type != null && type.getImpl().isCollectionClass();
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
	    SequenceConstructionExpression self = this.getSelf();
	    SequenceElements elements = self.getElements();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    if (elements != null) {
	        assignments = elements.getImpl().getAssignmentAfterMap(assignments);
	    }
	    return assignments;
	}
	
	public static SequenceConstructionExpression makeNull() {
	    SequenceConstructionExpression nullExpression = 
	        new SequenceConstructionExpression();
	    nullExpression.setHasMultiplicity(true);
	    return nullExpression;
	}
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    SequenceConstructionExpression self = this.getSelf();
	    QualifiedName typeName =self.getTypeName();
	    SequenceElements elements = self.getElements();
	    if (typeName != null) {
	        typeName.getImpl().setCurrentScope(currentScope);
	    }
	    if (elements != null) {
	        elements.getImpl().setCurrentScope(currentScope);
	    }
	}

} // SequenceConstructionExpressionImpl
