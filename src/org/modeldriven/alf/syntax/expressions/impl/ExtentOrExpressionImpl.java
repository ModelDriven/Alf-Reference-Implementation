
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * The target of a sequence operation, reduction or expansion expression, which
 * may be either a primary expression or a class name denoting the class extent.
 **/

public class ExtentOrExpressionImpl {

	private QualifiedName name = null;
	private Expression expression = null; // DERIVED
	private Expression nonNameExpression = null;

	protected ExtentOrExpression self;

	public ExtentOrExpressionImpl(ExtentOrExpression self) {
		this.self = self;
	}

	public ExtentOrExpression getSelf() {
		return (ExtentOrExpression) this.self;
	}
	
	@Override
	public String toString() {
	    return this.toString(false);
	}
	
	public String toString(boolean includeDerived) {
	    return this.getSelf()._toString(includeDerived);
	}

    public void deriveAll() {
        this.getSelf()._deriveAll();
    }

	public QualifiedName getName() {
		return this.name;
	}

	public void setName(QualifiedName name) {
		this.name = name;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.setExpression(this.deriveExpression());
		}
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getNonNameExpression() {
		return this.nonNameExpression;
	}

	public void setNonNameExpression(Expression nonNameExpression) {
		this.nonNameExpression = nonNameExpression;
	}

	/**
	 * The effective expression for the target is the parsed primary expression,
	 * if the target is not a qualified name, a name expression, if the target
	 * is a qualified name other than a class name, or a class extent
	 * expression, if the target is the qualified name of a class.
	 **/
	protected Expression deriveExpression() {
	    ExtentOrExpression self = this.getSelf();
	    QualifiedName name = self.getName();
	    Expression expression = self.getNonNameExpression();
	    if (expression == null && name != null) {
	        if (name.getImpl().getClassReferent() != null) {
	            expression = new ClassExtentExpression(self);
	            ((ClassExtentExpression)expression).setClassName(name);
	        } else {
	            expression = new NameExpression(self);
	            ((NameExpression)expression).setName(name);
	        }
	    }
		return expression;
	}
	
	/*
	 * Derivations
	 */

	public boolean extentOrExpressionExpressionDerivation() {
		this.getSelf().getExpression();
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	public void setCurrentScope(NamespaceDefinition currentScope) {
        ExtentOrExpression self = this.getSelf();
        QualifiedName name = self.getName();
        Expression nonNameExpression = self.getNonNameExpression();
        if (name != null) {
            name.getImpl().setCurrentScope(currentScope);
        }
        if (nonNameExpression != null) {
            nonNameExpression.getImpl().setCurrentScope(currentScope);
        }
	}
	
	public void setContainingExpression(Expression expression) {
        ExtentOrExpression self = this.getSelf();
        QualifiedName name = self.getName();
        if (name != null) {
            name.getImpl().setContainingExpression(expression);
        }
	}

    public ExtentOrExpression bind(
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        ExtentOrExpression self = this.getSelf();
        QualifiedName name = self.getName();
        Expression nonNameExpression = self.getNonNameExpression();
        ExtentOrExpression boundElement = new ExtentOrExpression();
        if (name != null) {
            boundElement.setName(name.getImpl().
                    updateForBinding(templateParameters, templateArguments));
        }
        if (nonNameExpression != null) {
            boundElement.setNonNameExpression
                ((Expression)nonNameExpression.getImpl().
                        bind(templateParameters, templateArguments));
        }
        return boundElement;
    }
    
} // ExtentOrExpressionImpl
