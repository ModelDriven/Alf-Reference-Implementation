
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;
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
	
	private InstanceCreationExpression instanceCreationExpression = null;
	
	public SequenceConstructionExpressionImpl(
			SequenceConstructionExpression self) {
		super(self);
	}

	@Override
	public SequenceConstructionExpression getSelf() {
		return (SequenceConstructionExpression) this.self;
	}
	
	@Override
	public void deriveAll() {
	    super.deriveAll();
	    InstanceCreationExpression instanceCreationExpression = 
	        this.getInstanceCreationExpression();
	    if (instanceCreationExpression != null) {
	        instanceCreationExpression.deriveAll();
	    }
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
     * If the type name of a sequence construction expression is not empty, then
     * it must resolve to a non-template classifier. If the expression does not
     * have multiplicity, then the type name must not be empty and the
     * classifier to which it resolves must be the instantiation of a collection
     * class.
     **/
	public boolean sequenceConstructionExpressionType() {
	    SequenceConstructionExpression self = this.getSelf();
	    ElementReference type = self.getType();
		return self.getHasMultiplicity()? 
		            self.getTypeName() == null || type != null:
		            type != null && type.getImpl().isCollectionClass();
	}
	
    /**
     * If the elements of a sequence construction expression are given by a
     * sequence range, then the expression must have type Integer. If the
     * elements are given by a sequence element list, and the sequence
     * construction expression has a non-empty type, then each expression in the
     * list must have a type that either conforms to the type of the sequence
     * construction expression or is convertible to it by bit string conversion.
     **/
    public boolean sequenceConstructionExpressionElementType() {
        SequenceConstructionExpression self = this.getSelf();
        SequenceElements elements = self.getElements();
        return elements == null || elements.getImpl().checkElements(self);
    }

    /**
     * If the elements of a sequence construction expression are given by a
     * sequence expression list, then the assignments before the first
     * expression in the list are the same as the assignments before the
     * sequence construction expression, and the assignments before each
     * subsequent expression are the assignments after the previous expression.
     * If the elements are given by a sequence range, the assignments before
     * both expressions in the range are the same as the assignments before the
     * sequence construction expression.
     **/
    public boolean sequenceConstructionExpressionAssignmentsBefore() {
        // NOTE: This is handled in updateAssignmentsMap.
        return true;
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
	
	/**
	 * For a sequence construction expression without multiplicity, return the
	 * equivalent collection class instance creation expression.
	 */
	public InstanceCreationExpression getInstanceCreationExpression() {
	    if (this.instanceCreationExpression == null && 
	            !this.getSelf().getHasMultiplicity()) {
	        this.instanceCreationExpression = 
	            this.deriveInstanceCreationExpression();
	    }
	    return this.instanceCreationExpression;
	}
	
	protected InstanceCreationExpression deriveInstanceCreationExpression() {
	    SequenceConstructionExpression self = this.getSelf();
	    SequenceElements elements = self.getElements();
	    SequenceConstructionExpression argument = 
	        new SequenceConstructionExpression(elements);
	    argument.setElements(elements);
	    argument.setHasMultiplicity(true);

	    ElementReference type = self.getType();
	    QualifiedName typeName = self.getTypeName();
	    if (type != null) {
	        argument.setType(type.getImpl().getCollectionArgument());
	        if (typeName == null) {
	            typeName = type.getImpl().getQualifiedName();
	            // NOTE: Root scope is used here because the typeName
	            // generated above will be fully qualified.
	            typeName.getImpl().setCurrentScope(RootNamespace.getRootScope());
	        }
	    }

	    PositionalTuple tuple = new PositionalTuple(argument);
	    tuple.addExpression(argument);

	    InstanceCreationExpression instanceCreation = 
	        new InstanceCreationExpression(self);
	    instanceCreation.setConstructor(typeName);
	    instanceCreation.setTuple(tuple);
	    tuple.setInvocation(instanceCreation);

	    return instanceCreation;
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
	    QualifiedName typeName = self.getTypeName();
	    SequenceElements elements = self.getElements();
	    if (typeName != null) {
	        typeName.getImpl().setCurrentScope(currentScope);
	    }
	    if (elements != null) {
	        elements.getImpl().setCurrentScope(currentScope);
	    }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SequenceConstructionExpression) {
            SequenceConstructionExpression self = this.getSelf();
            SequenceConstructionExpression baseExpression = 
                (SequenceConstructionExpression)base;
            SequenceElements elements = baseExpression.getElements();
            QualifiedName typeName = baseExpression.getTypeName();
            if (elements != null) {
                self.setElements((SequenceElements)elements.getImpl().
                        bind(templateParameters, templateArguments));
            }
            self.setHasMultiplicity(baseExpression.getHasMultiplicity());
            if (typeName != null) {
                self.setTypeName(typeName.getImpl().
                        updateForBinding(templateParameters, templateArguments));
            }
        }
    }

} // SequenceConstructionExpressionImpl
