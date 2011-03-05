
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {
    
    private NamespaceDefinition currentScope = null;

	public AcceptStatementImpl(AcceptStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.AcceptStatement getSelf() {
		return (AcceptStatement) this.self;
	}
	
	/**
	 * The current scope for an accept statement should be the containing
	 * behavior. It is implicitly set by setCurrentScope().
	 */
	public ElementReference deriveBehavior() {
		return null;
	}

    /**
     * An accept statement is simple if it has exactly one accept block and that
     * accept block does not have a block.
     **/
	public Boolean deriveIsSimple() {
		List<AcceptBlock> acceptBlocks = this.getSelf().getAcceptBlock();
		return acceptBlocks.size() == 1 && acceptBlocks.get(0).getBlock() == null;
	}
	
    /**
     * A local name specified in the accept block of a simple accept statement
     * has the accept statement as its assigned source after the accept
     * statement. The type of the local name is the effective common ancestor of
     * the specified signals, if one exists, and it is untyped otherwise.
     *
     * For a compound accept statement, a local name defined in an accept block
     * is considered unassigned after the accept statement. If a name is 
     * assigned in any block of an accept statement, then the assigned source of
     * the name after the accept statement is the accept statement itself.
     **/
	@Override
    @SuppressWarnings("unchecked")
	public ArrayList<AssignedSource> deriveAssignmentAfter() {
        AcceptStatement self = this.getSelf();
        ArrayList<AssignedSource> assignmentsBefore = self.getAssignmentBefore();
        ArrayList<AssignedSource> assignmentsAfter = 
            (ArrayList<AssignedSource>)assignmentsBefore.clone();
/*        if (self.getIsSimple()) {
            AcceptBlock block = self.getAcceptBlock().get(0);
            String name = block.getName();
            if (name != null) {
                AssignedSource assignment = new AssignedSource();  
                assignment.setName(name);
                assignment.setSource(self);
                assignment.setType(ClassifierDefinition.commonAncestor(block.getSignal()));
                AssignedSourceImpl.addAssignment(assignment, assignmentsAfter);
            }
        } else {
            this.setAssignmentsBeforeBlocks();
            ArrayList<AssignedSource> newAssignments = new ArrayList<AssignedSource>();
            HashMap<String, ArrayList<ElementReference>> typeMap = new HashMap<String, ArrayList<ElementReference>>();
            for (AcceptBlock acceptBlock: self.getAcceptBlock()) {
                Block block = acceptBlock.getBlock();
                if (block != null) {
                    for (AssignedSource assignment: block.getImpl().getNewAssignments()) {
                        AssignedSource newAssignment = assignment.getImpl().copy();
                        newAssignment.setSource(self);
                        AssignedSourceImpl.addAssignment(newAssignment, newAssignments);
                        typeMap.get(assignment.getName()).add(assignment.getType());
                    }
                }
                for (AssignedSource newAssignment: newAssignments) {
                    if (!assignmentsBefore.contains(newAssignment)) {
                        newAssignment.setType(
                            ClassifierDefinition.commonAncestor(
                                    typeMap.get(newAssignment.getName())));
                    }
                }
            }
        }
*/
        return assignmentsAfter;
	}

    /*
	 * Derivations
	 */
	
    public boolean acceptStatementIsSimpleDerivation() {
        this.getSelf().getIsSimple();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * An accept statement can only be used within the definition of an active
	 * behavior or the classifier behavior of an active class.
	 **/
	public boolean acceptStatementContext() {
	    ElementReference behavior = this.getSelf().getBehavior();
		return behavior != null && behavior.getImpl().isActiveBehavior();
	}

	/**
	 * The containing behavior of an accept statement must have receptions for
	 * all signals from all accept blocks of the accept statement. No signal may
	 * be referenced in more than one accept block of an accept statement.
	 **/
	public boolean acceptStatementSignals() {
	    AcceptStatement self = this.getSelf();
	    ElementReference behavior = self.getBehavior();	    
	    if (behavior == null) {
	        return false;
	    } else {
	        behavior = behavior.getImpl().getActiveClass();
    	    Collection<ElementReference> signals = new ArrayList<ElementReference>();
    	    for (AcceptBlock block: self.getAcceptBlock()) {
    	        Collection<ElementReference> blockSignals = block.getSignal();
    	        for (ElementReference signal: blockSignals) {
        	        if (!behavior.getImpl().hasReceptionFor(signal) ||
        	                signals.contains(signal)) {
        	            return false;
        	        }
    	        }
    	        signals.addAll(blockSignals);
     	    }
    	    return true;
	    }
	}

    /**
	 * Any name defined in an accept block of an accept statement must be
	 * unassigned before the accept statement.
	 **/
	public boolean acceptStatementNames() {
	    AcceptStatement self = this.getSelf();
	    ArrayList<AssignedSource> assignmentsBefore = self.getAssignmentBefore();
	    for (AcceptBlock block: self.getAcceptBlock()) {
	        String name = block.getName();
	        if (name != null && 
	                AssignedSourceImpl.getAssignment(name, assignmentsBefore) 
	                    != null) {
	            return false;
	        }
	    }
		return true;
	}

	/**
	 * A local name specified in the accept block of a simple accept statement
	 * has the accept statement as its assigned source after the accept
	 * statement. The type of the local name is the effective common ancestor of
	 * the specified signals, if one exists, and it is untyped otherwise.
	 **/
    public boolean acceptStatementSimpleAcceptLocalName() {
        // Note: This is handled by deriveAssignmentsAfter.
	    return true;
	}

	/**
	 * For a compound accept statement, a local name defined in an accept block
	 * has the accept block as its assigned source before the block associated
	 * with the accept block. The type of the local name is the effective common
	 * ancestor of the specified signals for that accept clause, if one exists,
	 * and it is untyped otherwise. However, the local name is considered
	 * unassigned after the accept statement.
	 **/
    public boolean acceptStatementCompoundAcceptLocalName() {
        // Note: This is handled by deriveAssignmentsAfter.
		return true;
	}

	/**
	 * The assignments before any block of an accept statement are the
	 * assignments before the accept statement.
	 **/
	public boolean acceptStatementAssignmentsBefore() {
        // Note: This is handled by deriveAssignmentsAfter.
		return true;
	}

	/**
	 * If a name is assigned in any block of an accept statement, then the
	 * assigned source of the name after the accept statement is the accept
	 * statement itself.
	 **/
	public boolean acceptStatementAssignmentsAfter() {
        // Note: This is handled by deriveAssignmentsAfter.
		return true;
	}

	/**
	 * If a name is unassigned before an accept statement and assigned in any
	 * block of an accept statement, then it must be assigned in every block.
	 **/
	public boolean acceptStatementNewAssignments() {
	    AcceptStatement self = this.getSelf();
	    ArrayList<AcceptBlock> acceptBlocks = self.getAcceptBlock();
	    if (acceptBlocks.size() > 1) {
    	    ArrayList<AssignedSource> assignmentsBefore = self.getAssignmentBefore();
    	    self.getAssignmentAfter();
    	    HashMap<String, AssignedSource> assignments = new HashMap<String, AssignedSource>();
    	    Block block = acceptBlocks.get(0).getBlock();
    	    if (block != null) {
    	        
    	    }
	    }
		return true;
	}

	/**
	 * The enclosing statement for all statements in the blocks of all accept
	 * blocks of an accept statement is the accept statement.
	 **/
	public boolean acceptStatementEnclosedStatements() {
/*	    AcceptStatement self = this.getSelf();
	    for (AcceptBlock acceptBlock: self.getAcceptBlock()) {
	        Block block = acceptBlock.getBlock();
	        if (block != null) {
	            block.getImpl().setEnclosingStatement(self);
	        }
	    }*/
		return true;
	}
	
	/*
	 * Helper Methods
	 */

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (AcceptBlock acceptBlock: this.getSelf().getAcceptBlock()) {
            acceptBlock.getImpl().setCurrentScope(currentScope);
        }
        if (currentScope != null) {
            this.getSelf().setBehavior(currentScope.getImpl().getReferent());
        }
    }

    /**
    * For a compound accept statement, a local name defined in an accept block
    * has the accept block as its assigned source before the block associated
    * with the accept block. The type of the local name is the effective common
    * ancestor of the specified signals for that accept clause, if one exists,
    * and it is untyped otherwise.
    *
    * Otherwise, the assignments before any block of an accept statement are the
    * assignments before the accept statement.
    *
    **/
    @SuppressWarnings("unchecked")
    private void setAssignmentsBeforeBlocks() {
/*        AcceptStatement self = this.getSelf();
        ArrayList<AssignedSource> assignmentsBefore = self.getAssignmentBefore();
        for (AcceptBlock acceptBlock: self.getAcceptBlock()) {
            Block block = acceptBlock.getBlock();
            if (block != null) {
                ArrayList<AssignedSource> assignments = 
                    (ArrayList<AssignedSource>)assignmentsBefore.clone();
                String name = acceptBlock.getName();
                if (name != null) {
                    AssignedSource assignment = new AssignedSource();  
                    assignment.setName(name);
                    assignment.setSource(self);
                    assignment.setType(ClassifierDefinition.commonAncestor(acceptBlock.getSignal()));
                    AssignedSourceImpl.addAssignment(assignment, assignments);
                }
                block.setAssignmentBefore(assignments);
            }
        }*/
    }

} // AcceptStatementImpl
