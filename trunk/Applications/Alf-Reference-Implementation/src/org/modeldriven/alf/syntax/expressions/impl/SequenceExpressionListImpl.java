
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

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
    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (Expression element: this.getSelf().getElement()) {
            element.getImpl().setCurrentScope(currentScope);
        }
    }

} // SequenceExpressionListImpl
