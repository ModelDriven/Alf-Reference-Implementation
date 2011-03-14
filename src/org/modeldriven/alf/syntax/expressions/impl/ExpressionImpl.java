
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
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.TypedElementDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class ExpressionImpl extends SyntaxElementImpl {

    private Map<String, AssignedSource> assignmentBefore = null; // DERIVED
    private Map<String, AssignedSource> assignmentAfter = null; // DERIVED
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
        return this.getAssignmentBeforeMap().values();
    }
    
    public Map<String, AssignedSource> getAssignmentBeforeMap() {
        if (this.assignmentBefore == null) {
            this.setAssignmentBefore(this.deriveAssignmentBefore());
        }
        return this.assignmentBefore;
    }
    
    public AssignedSource getAssignmentBefore(String name) {
        this.getAssignmentBefore();
        return this.assignmentBefore.get(name);
    }

    public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
        this.setAssignmentBefore(new HashMap<String, AssignedSource>());
        for (AssignedSource assignment: assignmentBefore) {
            this.addAssignmentBefore(assignment);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
    }

    public void addAssignmentBefore(AssignedSource assignmentBefore) {
        this.getAssignmentBefore();
        this.assignmentBefore.put(assignmentBefore.getName(), assignmentBefore);
    }

    public Collection<AssignedSource> getAssignmentAfter() {
        return this.getAssignmentAfterMap().values();
    }

    public Map<String, AssignedSource> getAssignmentAfterMap() {
        if (this.assignmentAfter == null) {
            this.setAssignmentAfter(this.deriveAssignmentAfter());
        }
        return this.assignmentAfter;
    }

    public AssignedSource getAssignmentAfter(String name) {
        this.getAssignmentAfter();
        return this.assignmentAfter.get(name);
    }

    public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
        this.assignmentAfter.clear();
        for (AssignedSource assignment: assignmentAfter) {
            this.addAssignmentBefore(assignment);
        }
    }

    public void setAssignmentAfter(Map<String, AssignedSource> assignmentAfter) {
        this.assignmentAfter = assignmentAfter;
    }

    public void addAssignmentAfter(AssignedSource assignmentAfter) {
        this.getAssignmentAfter();
        this.assignmentAfter.put(assignmentAfter.getName(), assignmentAfter);
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

    protected Map<String, AssignedSource> deriveAssignmentBefore() {
		return new HashMap<String, AssignedSource>(); // STUB
	}

    /**
     * By default, the assignments after are the same as the assignments before.
     */
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
		return this.assignmentBefore;
	}

	protected Integer deriveUpper() {
		return null; // STUB
	}

	protected Integer deriveLower() {
		return null; // STUB
	}

	protected ElementReference deriveType() {
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
        AssignedSource assignment = this.getAssignmentBefore(name);
        return assignment == null? null: assignment.getSource();
    }

    public void setCurrentScope(NamespaceDefinition outerScope) {
        // TODO Should be abstract        
    }

    /**
     * Get the assigned sources for assignments made within this expression.
     */
    public Collection<AssignedSource> getNewAssignments() {
        return AssignedSourceImpl.selectNewAssignments(
                this.getAssignmentBeforeMap(), 
                this.getSelf().getAssignmentAfter());
    }

} // ExpressionImpl
