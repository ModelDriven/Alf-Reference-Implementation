/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;

import org.modeldriven.alf.syntax.common.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.expressions.impl.ExtentOrExpressionImpl;

/**
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpression extends ParsedElement {

	protected ExtentOrExpressionImpl impl;

	public ExtentOrExpression() {
		this.impl = new ExtentOrExpressionImpl(this);
	}

	public ExtentOrExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ExtentOrExpression(ParsedElement element) {
		this();
        this.init(element);
	}

	public ExtentOrExpressionImpl getImpl() {
		return (ExtentOrExpressionImpl) this.impl;
	}

	public QualifiedName getName() {
		return this.getImpl().getName();
	}

	public void setName(QualifiedName name) {
		this.getImpl().setName(name);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Expression getNonNameExpression() {
		return this.getImpl().getNonNameExpression();
	}

	public void setNonNameExpression(Expression nonNameExpression) {
		this.getImpl().setNonNameExpression(nonNameExpression);
	}

	/**
	 * The effective expression for the target is the parsed primary expression,
	 * if the target is not a qualified name, a name expression, if the target
	 * is a qualified name other than a class name, or a class extent
	 * expression, if the target is the qualified name of a class.
	 **/
	public boolean extentOrExpressionExpressionDerivation() {
		return this.getImpl().extentOrExpressionExpressionDerivation();
	}
	
    public Collection<ExternalElementReference> getExternalReferences() {
        Collection<ExternalElementReference> references = new ArrayList<ExternalElementReference>();
        this.addExternalReferences(references);
        return references;
    }

    public void addExternalReferences(Collection<ExternalElementReference> references) {
        this.getImpl().addExternalReferences(references);
    }
    
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        SyntaxElement.addExternalReferencesFor(references, this.getName());
        SyntaxElement.addExternalReferencesFor(references, this.getNonNameExpression());
    }
    
    public static void addExternalReferencesFor(
            Collection<ExternalElementReference> references, 
            ExtentOrExpression primary) {
        if (primary != null) {
            primary.addExternalReferences(references);
        }
    }
    
	public void deriveAll() {
		this.getImpl().deriveAll();
	}

	public void _deriveAll() {
		this.getExpression();
		QualifiedName name = this.getName();
		if (name != null) {
			name.deriveAll();
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
		Expression nonNameExpression = this.getNonNameExpression();
		if (nonNameExpression != null) {
			nonNameExpression.deriveAll();
		}
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		if (!this.extentOrExpressionExpressionDerivation()) {
			violations.add(new ConstraintViolation(
					"extentOrExpressionExpressionDerivation", this));
		}
		QualifiedName name = this.getName();
		if (name != null) {
			name.checkConstraints(violations);
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
		Expression nonNameExpression = this.getNonNameExpression();
		if (nonNameExpression != null) {
			nonNameExpression.checkConstraints(violations);
		}
	}

	public String getId() {
		return Integer.toHexString(this.hashCode());
	}

	@Override
    public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.getId() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		System.out.println(prefix + "[" + this.getId() + "]"
				+ this._toString(includeDerived));
		QualifiedName name = this.getName();
		if (name != null) {
			System.out.println(prefix + " name:");
			name.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Expression expression = this.getExpression();
			if (expression != null) {
				System.out.println(prefix + " /expression:");
				expression.print(prefix + "  ", includeDerived);
			}
		}
		Expression nonNameExpression = this.getNonNameExpression();
		if (nonNameExpression != null) {
			System.out.println(prefix + " nonNameExpression:");
			nonNameExpression.print(prefix + "  ", includeDerived);
		}
	}
} // ExtentOrExpression
