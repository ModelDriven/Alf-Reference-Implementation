
/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.expressions.impl.SequenceExpressionListImpl;

/**
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionList extends SequenceElements {

	public SequenceExpressionList() {
		this.impl = new SequenceExpressionListImpl(this);
	}

	public SequenceExpressionList(Parser parser) {
		this();
		this.init(parser);
	}

	public SequenceExpressionList(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public SequenceExpressionListImpl getImpl() {
		return (SequenceExpressionListImpl) this.impl;
	}

	public List<Expression> getElement() {
		return this.getImpl().getElement();
	}

	public void setElement(List<Expression> element) {
		this.getImpl().setElement(element);
	}

	public void addElement(Expression element) {
		this.getImpl().addElement(element);
	}

	/**
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list.
	 **/
	public boolean sequenceExpressionListLowerDerivation() {
		return this.getImpl().sequenceExpressionListLowerDerivation();
	}

	/**
	 * The multiplicity upper bound of the elements of a sequence expression
	 * list is given by the sum of the upper bounds of each of the expressions
	 * in the list. If any of the expressions in the list have an unbounded
	 * upper bound, then the sequence expression list also has an unbounded
	 * upper bound.
	 **/
	public boolean sequenceExpressionListUpperDerivation() {
		return this.getImpl().sequenceExpressionListUpperDerivation();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getElement());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<Expression> element = this.getElement();
		if (element != null) {
			for (Object _element : element.toArray()) {
				((Expression) _element).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceExpressionListLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceExpressionListLowerDerivation", this));
		}
		if (!this.sequenceExpressionListUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceExpressionListUpperDerivation", this));
		}
		Collection<Expression> element = this.getElement();
		if (element != null) {
			for (Object _element : element.toArray()) {
				((Expression) _element).checkConstraints(violations);
			}
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		List<Expression> element = this.getElement();
		if (element != null && element.size() > 0) {
			System.out.println(prefix + " element:");
			for (Object _object : element.toArray()) {
				Expression _element = (Expression) _object;
				if (_element != null) {
					_element.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // SequenceExpressionList
