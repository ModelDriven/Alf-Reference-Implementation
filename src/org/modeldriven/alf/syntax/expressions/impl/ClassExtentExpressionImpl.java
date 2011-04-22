
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

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

} // ClassExtentExpressionImpl
