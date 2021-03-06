/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
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

    private Map<String, AssignedSource> assignmentBefore = null; // DERIVED

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

    public void addExternalReferences(Collection<ExternalElementReference> references) {
        ExtentOrExpression self = this.getSelf();
        self._addExternalReferences(references);
        if (self.getNonNameExpression() == null) {
            SyntaxElement.addExternalReferencesFor(references, self.getExpression());
        }
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

    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
        
        // NOTE: The proper derivation of the expression depends on
        // assignmentBefore being set.
        if (this.expression != null) {
            this.setExpression(this.deriveExpression());
        }
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
        if (expression != null) {
            expression.getImpl().setAssignmentBefore(this.assignmentBefore);
        } else if (name != null) {
	        expression = new NameExpression(self);
	        // NOTE: assignmentBefore must be set before getAssignment is called.
            expression.getImpl().setAssignmentBefore(this.assignmentBefore);
	        ((NameExpression)expression).setName(name);
	        if (((NameExpression)expression).getAssignment() == null
	                && name.getImpl().getClassReferent() != null) {
	            expression = new ClassExtentExpression(self);
	            expression.getImpl().setAssignmentBefore(this.assignmentBefore);
	            ((ClassExtentExpression)expression).setClassName(name);
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
