
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A grouped sequence of statements.
 **/

public class BlockImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

    private List<Statement> statement = new ArrayList<Statement>();
    private Map<String, AssignedSource> assignmentAfter = null; // DERIVED
    private Map<String, AssignedSource> assignmentBefore = null; // DERIVED

	public BlockImpl(Block self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.Block getSelf() {
		return (Block) this.self;
	}

    public List<Statement> getStatement() {
        return this.statement;
    }

    public void setStatement(List<Statement> statement) {
        this.statement = statement;
    }

    public void addStatement(Statement statement) {
        this.statement.add(statement);
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

    /**
     * If a block is not empty, then the assignments after the block are the
     * same as the assignments after the last statement of the block. Otherwise
     * they are the same as the assignments before the block.
     **/
    protected Map<String, AssignedSource> deriveAssignmentAfter() {
        Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
        for (Statement statement: this.getSelf().getStatement()) {
            statement.getImpl().setAssignmentBefore(assignments);
            assignments = statement.getImpl().getAssignmentAfterMap();
        }
        return assignments;
    }

    // The assignments before are set externally.
    protected Map<String, AssignedSource> deriveAssignmentBefore() {
        return new HashMap<String, AssignedSource>();
    }
    
    /*
     * Derivations
     */

	public boolean blockAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}
	
	/*
	 * Constraints
	 */
	
	/**
	 * The assignments before each statement in a block other than the first are
	 * the same as the assignments after the previous statement.
	 **/
	public boolean blockAssignmentsBeforeStatements() {
	    // Note: This is handled by deriveAssignementAfter.
		return true;
	}

	// TODO: Remove this from the Abstract Syntax model.
	public boolean blockAssignmentsBefore() {
		return true;
	}

	/*
	 * Helper Methods
	 */

    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (Statement statement: this.getSelf().getStatement()) {
            statement.getImpl().setCurrentScope(currentScope);
        }        
    }
    
    public void setEnclosingStatement(Statement enclosingStatement) {
        for (Statement statement: this.getSelf().getStatement()) {
            statement.setEnclosingStatement(enclosingStatement);
        }
    }

    /**
     * Get the assigned sources for assignments made within this expression.
     */
    public Collection<AssignedSource> getNewAssignments() {
        return AssignedSourceImpl.selectNewAssignments(
                this.getAssignmentBeforeMap(), 
                this.getSelf().getAssignmentAfter());
    }
    
} // BlockImpl
