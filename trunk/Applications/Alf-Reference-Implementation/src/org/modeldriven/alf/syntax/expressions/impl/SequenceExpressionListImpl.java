
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionListImpl extends SequenceElementsImpl {

	private Collection<Expression> element = new ArrayList<Expression>();
	
	public SequenceExpressionListImpl(SequenceExpressionList self) {
		super(self);
	}

	public SequenceExpressionList getSelf() {
		return (SequenceExpressionList) this.self;
	}

	public Collection<Expression> getElement() {
		return this.element;
	}

	public void setElement(Collection<Expression> element) {
		this.element = element;
	}

	public void addElement(Expression element) {
		this.element.add(element);
	}

	/**
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list.
	 **/
	@Override
	protected Integer deriveLower() {
	    int lower = 0;
	    for (Expression element: this.getSelf().getElement()) {
	        lower += element.getLower();
	    }
	    return lower;
	}
	
	/**
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list. If any of the expressions in the list have an unbounded
	 * upper bound, then the sequence expression list also has an unbounded
	 * upper bound.
	 **/
    @Override
    protected Integer deriveUpper() {
        int upper = 0;
        for (Expression element: this.getSelf().getElement()) {
            int elementUpper = element.getUpper();
            if (elementUpper == -1) {
                return -1;
            }
            upper += elementUpper;
        }
        return upper;
    }
	
	/*
	 * Derivations
	 */
	
	public boolean sequenceExpressionListLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean sequenceExpressionListUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Helper Methods
	 */

    @Override
    public Map<String, AssignedSource> getAssignmentAfterMap(
            Map<String, AssignedSource> assignments) {
        for (Expression element: this.getSelf().getElement()) {
            element.getImpl().setAssignmentBefore(assignments);
            assignments = element.getImpl().getAssignmentAfterMap();
        }
        return assignments;
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (Expression element: this.getSelf().getElement()) {
            element.getImpl().setCurrentScope(currentScope);
        }
    }
    
    @Override
    public boolean isEmpty() {
        return element.isEmpty();
    }

    /**
     * Each expression in the list must have a multiplicity upper bound of no 
     * more than 1. The the type of each expression in the list must conform to 
     * the given type of the owning sequence construction expression (with 
     * allowance for bit string conversion), if that expression has a 
     * multiplicity indicator, or to the collection argument type of the given 
     * collection class, otherwise.
     */
    @Override
    public boolean checkElements(SequenceConstructionExpression owner) {
        ElementReference type = owner.getType();
        if (!owner.getHasMultiplicity() && type != null) {
            type = type.getImpl().getCollectionArgument();
        }
        for (Expression element: this.getSelf().getElement()) {
            ElementReference elementType = element.getType();
            if (element.getUpper() > 1 || elementType == null && type != null ||
                    elementType != null && 
                    !elementType.getImpl().conformsTo(type) &&
                    !(elementType.getImpl().isInteger() && 
                            type.getImpl().isBitString())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Set the collection type of all list elements that are sequence
     * construction expressions without explicit type names.
     */
    @Override
    public void setCollectionTypeName(QualifiedName typeName) {
        for (Expression element: this.getSelf().getElement()) {
            if (element instanceof SequenceConstructionExpression) {
                SequenceConstructionExpression expression = 
                    (SequenceConstructionExpression)element;
                if (expression.getTypeName() == null) {
                    expression.getImpl().setCollectionTypeName(typeName);
                }
            }
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SequenceExpressionList) {
            SequenceExpressionList self = this.getSelf();
            for (Expression element: 
                ((SequenceExpressionList)base).getElement()) {
                self.addElement((Expression)element.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // SequenceExpressionListImpl
