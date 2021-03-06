/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.syntax.expressions.impl.ClassExtentExpressionImpl;

/**
 * An expression used to obtain the objects in the extent of a class.
 **/

public class ClassExtentExpression extends Expression {

	public ClassExtentExpression() {
		this.impl = new ClassExtentExpressionImpl(this);
	}

	public ClassExtentExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ClassExtentExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ClassExtentExpressionImpl getImpl() {
		return (ClassExtentExpressionImpl) this.impl;
	}

	public QualifiedName getClassName() {
		return this.getImpl().getClassName();
	}

	public void setClassName(QualifiedName className) {
		this.getImpl().setClassName(className);
	}

	/**
	 * The type of a class extent expression is the given class.
	 **/
	public boolean classExtentExpressionTypeDerivation() {
		return this.getImpl().classExtentExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of a class expression is *.
	 **/
	public boolean classExtentExpressionUpperDerivation() {
		return this.getImpl().classExtentExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a class extent expression is 0.
	 **/
	public boolean classExtentExpressionLowerDerivation() {
		return this.getImpl().classExtentExpressionLowerDerivation();
	}

	/**
	 * The given type name must resolve to a non-template class.
	 **/
	public boolean classExtentExpressionExtentType() {
		return this.getImpl().classExtentExpressionExtentType();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getClassName());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		QualifiedName className = this.getClassName();
		if (className != null) {
			className.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.classExtentExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionTypeDerivation", this));
		}
		if (!this.classExtentExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionUpperDerivation", this));
		}
		if (!this.classExtentExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionLowerDerivation", this));
		}
		if (!this.classExtentExpressionExtentType()) {
			violations.add(new ConstraintViolation(
					"classExtentExpressionExtentType", this));
		}
		QualifiedName className = this.getClassName();
		if (className != null) {
			className.checkConstraints(violations);
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
		QualifiedName className = this.getClassName();
		if (className != null) {
			System.out.println(prefix + " className:");
			className.print(prefix + "  ", includeDerived);
		}
	}
} // ClassExtentExpression
