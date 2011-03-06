
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class ExpressionImpl extends SyntaxElementImpl {

    private Collection<AssignedSource> assignmentBefore = null; // DERIVED
    private Collection<AssignedSource> assignmentAfter = null; // DERIVED
    private Integer upper = null; // DERIVED
    private Integer lower = null; // DERIVED
    private ElementReference type = null; // DERIVED

	public ExpressionImpl(Expression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.Expression getSelf() {
		return (Expression) this.self;
	}

    public Collection<AssignedSource> getAssignmentBefore() {
        if (this.assignmentBefore == null) {
            this.setAssignmentBefore(this.deriveAssignmentBefore());
        }
        return this.assignmentBefore;
    }

    public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
    }

    public void addAssignmentBefore(AssignedSource assignmentBefore) {
        this.assignmentBefore.add(assignmentBefore);
    }

    public Collection<AssignedSource> getAssignmentAfter() {
        if (this.assignmentAfter == null) {
            this.setAssignmentAfter(this.deriveAssignmentAfter());
        }
        return this.assignmentAfter;
    }

    public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
        this.assignmentAfter = assignmentAfter;
    }

    public void addAssignmentAfter(AssignedSource assignmentAfter) {
        this.assignmentAfter.add(assignmentAfter);
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

    public ElementReference getType() {
        if (this.type == null) {
            this.setType(this.deriveType());
        }
        return this.type;
    }

    public void setType(ElementReference type) {
        this.type = type;
    }

	public Collection<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	public Collection<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	public Integer deriveUpper() {
		return null; // STUB
	}

	public Integer deriveLower() {
		return null; // STUB
	}

	public ElementReference deriveType() {
		return null; // STUB
	}

	/**
	 * The assignments after an expression are given by the result of the
	 * updateAssignments helper operation.
	 **/
	public boolean expressionAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

	/**
	 * No name may be assigned more than once before or after an expression.
	 **/
	public boolean expressionUniqueAssignments() {
		return true;
	}

	/**
	 * Returns the assignments from before this expression updated for any
	 * assignments made in the expression. By default, this is the same set as
	 * the assignments before the expression. This operation is redefined only
	 * in subclasses of Expression for kinds of expressions that make
	 * assignments.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

    public SyntaxElement resolve(String name) {
        Collection<AssignedSource> assignments = this.getSelf().getAssignmentBefore();
        for (AssignedSource assignment: assignments) {
            if (assignment.getName().equals(name)) {
                return assignment.getSource();
            }
        }
        return null;
    }

    public void setCurrentScope(NamespaceDefinition outerScope) {
        // TODO Should be abstract        
    }

} // ExpressionImpl
