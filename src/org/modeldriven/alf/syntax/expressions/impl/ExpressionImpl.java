
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class ExpressionImpl extends AssignableElementImpl {

    private Map<String, AssignedSource> assignmentBefore = null; // DERIVED
    private Map<String, AssignedSource> assignmentAfter = null; // DERIVED
    private Integer upper = null; // DERIVED
    private Integer lower = null; // DERIVED
    private ElementReference type = null; // DERIVED

	public ExpressionImpl(Expression self) {
		super(self);
	}

	@Override
	public Expression getSelf() {
		return (Expression) this.self;
	}
	
	@Override
	public String toString(boolean includesDerived) {
	    Expression self = this.getSelf();
	    return super.toString(includesDerived) + " type:(" + self.getType() + ")";
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
        return this.getAssignmentBeforeMap().get(name);
    }

    public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
        if (this.assignmentBefore == null) {
            this.assignmentBefore = new HashMap<String, AssignedSource>();
        } else {
            this.assignmentBefore.clear();
        }
        for (AssignedSource assignment: assignmentBefore) {
            this.addAssignmentBefore(assignment);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
    }

    public void addAssignmentBefore(AssignedSource assignmentBefore) {
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
        return this.getAssignmentAfterMap().get(name);
    }

    public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
        if (this.assignmentBefore == null) {
            this.assignmentBefore = new HashMap<String, AssignedSource>();
        } else {
            this.assignmentBefore.clear();
        }
        for (AssignedSource assignment: assignmentAfter) {
            this.addAssignmentAfter(assignment);
        }
    }

    public void setAssignmentAfter(Map<String, AssignedSource> assignmentAfter) {
        this.assignmentAfter = assignmentAfter;
    }

    public void addAssignmentAfter(AssignedSource assignmentAfter) {
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

    /**
     * The assignments before are usually set externally.
     */
    protected Map<String, AssignedSource> deriveAssignmentBefore() {
		return new HashMap<String, AssignedSource>();
	}

    /**
     * The assignments after an expression are given by the result of the
     * updateAssignments helper operation.
     **/
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
		return this.updateAssignmentMap();
	}

	protected abstract Integer deriveUpper();

	protected abstract Integer deriveLower();

	protected abstract ElementReference deriveType();

	/*
	 * Derivations
	 */
	
	public boolean expressionAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * No name may be assigned more than once before or after an expression.
	 **/
	public boolean expressionUniqueAssignments() {
	    // Note: This is enforced by the use of Map data structures.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns the assignments from before this expression updated for any
	 * assignments made in the expression. By default, this is the same set as
	 * the assignments before the expression. This operation is redefined only
	 * in subclasses of Expression for kinds of expressions that make
	 * assignments.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.updateAssignmentMap().values();
	} // updateAssignments
	
	protected Map<String, AssignedSource> updateAssignmentMap() {
	    return this.getAssignmentBeforeMap();
	}
	
	/**
	 * Get the syntax element assigned to the given name before this expression.
	 */
    public SyntaxElement resolve(String name) {
        AssignedSource assignment = this.getAssignmentBefore(name);
        return assignment == null? null: assignment.getSource();
    }

    /**
     * Set the current scope as required for this expression. By default, the
     * current scope is ignored. Subclasses should override this to propagate
     * the current scope to subexpressions or qualified names as appropriate.
     */
    public void setCurrentScope(NamespaceDefinition currentScope) {
    }
    
    /**
     * Called when this expression is the first argument in an invocation of the
     * CollectionFunctions::add library behavior (which is important for local
     * names identified in a @parallel annotation on a for statement). By
     * default this does nothing. (It is overridden by NameExpression.)
     */
    public void setIsAddTarget() {
    }

    /**
     * Get the assigned sources for new assignments made within this expression.
     */
    public Collection<AssignedSource> getNewAssignments() {
        return AssignedSourceImpl.selectNewAssignments(
                this.getAssignmentBeforeMap(), 
                this.getSelf().getAssignmentAfter());
    }

} // ExpressionImpl
