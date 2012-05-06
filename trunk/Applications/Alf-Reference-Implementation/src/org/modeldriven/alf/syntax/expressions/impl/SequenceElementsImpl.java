
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Map;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A specification of the elements of a sequence.
 **/

public abstract class SequenceElementsImpl extends SyntaxElementImpl {

	private Integer upper = null; // DERIVED
	private Integer lower = null; // DERIVED

	public SequenceElementsImpl(SequenceElements self) {
		super(self);
	}

	@Override
	public SequenceElements getSelf() {
		return (SequenceElements) this.self;
	}

	public Integer getUpper() {
		if (this.upper == null) {
			this.setUpper(this.deriveUpper());
		}
		return this.upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getLower() {
		if (this.lower == null) {
			this.setLower(this.deriveLower());
		}
		return this.lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	protected abstract Integer deriveUpper();

	protected abstract Integer deriveLower();
	
	/*
	 * Helper methods
	 */

    public abstract Map<String, AssignedSource> getAssignmentAfterMap(
            Map<String, AssignedSource> assignmentBefore);

    public abstract void setCurrentScope(NamespaceDefinition currentScope);
    
    public boolean isEmpty() {
        return false;
    }
    
    // This is overridden by SequenceExpressionListImpl. It has no effect for
    // a sequence range.
    public void setCollectionTypeName(QualifiedName typeName) {        
    }

    public abstract boolean checkElements(SequenceConstructionExpression owner);

} // SequenceElementsImpl
