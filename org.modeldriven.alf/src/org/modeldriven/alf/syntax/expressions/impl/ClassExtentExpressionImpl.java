
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

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

/**
 * An expression used to obtain the objects in the extent of a class.
 **/

public class ClassExtentExpressionImpl extends ExpressionImpl {

	private QualifiedName className = null;

	public ClassExtentExpressionImpl(ClassExtentExpression self) {
		super(self);
	}

	@Override
	public ClassExtentExpression getSelf() {
		return (ClassExtentExpression) this.self;
	}

	public QualifiedName getClassName() {
		return this.className;
	}

	public void setClassName(QualifiedName className) {
		this.className = className;
	}

    /**
     * The type of a class extent expression is the given class.
     **/
	@Override
	protected ElementReference deriveType() {
	    QualifiedName className = this.getSelf().getClassName();
	    return className == null? null: className.getImpl().getClassReferent();
	}
	
	/**
	 * The multiplicity upper bound of a class expression is *.
	 **/
	@Override
	protected Integer deriveUpper() {
	    return -1;
	}
	
	/**
	 * The multiplicity lower bound of a class extent expression is 0.
	 **/
	@Override
	protected Integer deriveLower() {
	    return 0;
	}
	
	/*
	 * Derivations
	 */
	
	public boolean classExtentExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean classExtentExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean classExtentExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The given type name must resolve to a non-template class.
	 **/
	public boolean classExtentExpressionExtentType() {
	    ElementReference type = this.getSelf().getType();
		return type != null && !type.getImpl().isTemplate();
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    QualifiedName className = this.getSelf().getClassName();
	    if (className != null) {
	        className.getImpl().setCurrentScope(currentScope);
	    }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ClassExtentExpression) {
            QualifiedName className = 
                ((ClassExtentExpression)base).getClassName();
            this.getSelf().setClassName(className.getImpl().
                    updateForBinding(templateParameters, templateArguments));
        }
    }
    
} // ClassExtentExpressionImpl
