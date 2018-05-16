
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

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.impl.NamedExpressionImpl;

/**
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpression extends SyntaxElement {

	public NamedExpression() {
		this.impl = new NamedExpressionImpl(this);
	}

	public NamedExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public NamedExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public NamedExpressionImpl getImpl() {
		return (NamedExpressionImpl) this.impl;
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Expression getIndex() {
		return this.getImpl().getIndex();
	}

	public void setIndex(Expression index) {
		this.getImpl().setIndex(index);
	}

	public Boolean getIsCollectionConversion() {
		return this.getImpl().getIsCollectionConversion();
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.getImpl().setIsCollectionConversion(isCollectionConversion);
	}

    public Boolean getIsBitStringConversion() {
        return this.getImpl().getIsBitStringConversion();
    }

    public void setIsBitStringConversion(Boolean isBitStringConversion) {
        this.getImpl().setIsBitStringConversion(isBitStringConversion);
    }

    public Boolean getIsRealConversion() {
        return this.getImpl().getIsRealConversion();
    }

    public void setIsRealConversion(Boolean isRealConversion) {
        this.getImpl().setIsRealConversion(isRealConversion);
    }

	/**
	 * Collection conversion is required if the type of the corresponding
	 * parameter is a collection class and the type of the argument expression
	 * is not.
	 **/
	public boolean namedExpressionIsCollectionConversionDerivation() {
		return this.getImpl().namedExpressionIsCollectionConversionDerivation();
	}

    /**
     * Bit string conversion is required if the type of the corresponding
     * parameter is BitString, or a collection class whose sequence type is
     * BitString, and the type of the argument expression is not BitString.
     **/
    public boolean namedExpressionIsBitStringConversionDerivation() {
        return this.getImpl().namedExpressionIsBitStringConversionDerivation();
    }

    /**
     * Real conversion is required if the type of the corresponding parameter is
     * Real, or a collection class whose sequence type is Real, and the type of
     * the argument expression is not Real.
     **/
    public boolean namedExpressionIsRealConversionDerivation() {
        return this.getImpl().namedExpressionIsRealConversionDerivation();
    }

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getExpression());
        addExternalReferencesFor(references, this.getIndex());
    }

	@Override
    public void _deriveAll() {
		this.getIsCollectionConversion();
		this.getIsBitStringConversion();
		this.getIsRealConversion();
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.namedExpressionIsCollectionConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"namedExpressionIsCollectionConversionDerivation", this));
		}
        if (!this.namedExpressionIsBitStringConversionDerivation()) {
            violations.add(new ConstraintViolation(
                    "namedExpressionIsBitStringConversionDerivation", this));
        }
        if (!this.namedExpressionIsRealConversionDerivation()) {
            violations.add(new ConstraintViolation(
                    "namedExpressionIsRealConversionDerivation", this));
        }
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		Expression index = this.getIndex();
		if (index != null) {
			index.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
		if (includeDerived) {
			s.append(" /isCollectionConversion:");
			s.append(this.getIsCollectionConversion());
		}
        if (includeDerived) {
            s.append(" /isBitStringConversion:");
            s.append(this.getIsBitStringConversion());
        }
        if (includeDerived) {
            s.append(" /isRealConversion:");
            s.append(this.getIsRealConversion());
        }
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
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		Expression index = this.getIndex();
		if (index != null) {
			System.out.println(prefix + " index:");
			index.print(prefix + "  ", includeDerived);
		}
	}
} // NamedExpression
