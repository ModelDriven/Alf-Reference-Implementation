
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
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatementImpl extends StatementImpl {
    
    private Collection<AcceptBlock> acceptBlock = new ArrayList<AcceptBlock>();
    private ElementReference behavior = null; // DERIVED
    private Boolean isSimple = null; // DERIVED

    public AcceptStatementImpl(AcceptStatement self) {
        super(self);
    }

    public AcceptStatement getSelf() {
        return (AcceptStatement) this.self;
    }

    public Collection<AcceptBlock> getAcceptBlock() {
        return this.acceptBlock;
    }

    public void setAcceptBlock(Collection<AcceptBlock> acceptBlock) {
        this.acceptBlock = acceptBlock;
        if (acceptBlock != null) {
            for (AcceptBlock anAcceptBlock: acceptBlock) {
                Block block = anAcceptBlock.getBlock();
                if (block != null) {
                    block.getImpl().setEnclosingStatement(this.getSelf());
                }
            }
        }
    }

    /**
     * The enclosing statement for all statements in the blocks of all accept
     * blocks of an accept statement is the accept statement.
     **/
    public void addAcceptBlock(AcceptBlock acceptBlock) {
        this.acceptBlock.add(acceptBlock);
        Block block = acceptBlock == null? null: acceptBlock.getBlock();
        if (block != null) {
            block.getImpl().setEnclosingStatement(this.getSelf());
        }
    }

    public ElementReference getBehavior() {
        if (this.behavior == null) {
            this.setBehavior(this.deriveBehavior());
        }
        return this.behavior;
    }

    public void setBehavior(ElementReference behavior) {
        this.behavior = behavior;
    }

    public Boolean getIsSimple() {
        if (this.isSimple == null) {
            this.setIsSimple(this.deriveIsSimple());
        }
        return this.isSimple;
    }

    public void setIsSimple(Boolean isSimple) {
        this.isSimple = isSimple;
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
		Collection<AcceptBlock> acceptBlocks = this.getSelf().getAcceptBlock();
		return acceptBlocks.size() == 1 && 
		        ((AcceptBlock)acceptBlocks.toArray()[0]).getBlock() == null;
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
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
        AcceptStatement self = this.getSelf();
        Map<String, AssignedSource> assignmentsAfter = 
            new HashMap<String, AssignedSource>(super.deriveAssignmentAfter());
        if (self.getIsSimple()) {
            AcceptBlock acceptBlock = (AcceptBlock)self.getAcceptBlock().toArray()[0];
            String name = acceptBlock.getName();
            if (name == null) {
                assignmentsAfter = super.deriveAssignmentAfter();
            } else {
                 assignmentsAfter.put(name, 
                    AssignedSourceImpl.makeAssignment(name, self, 
                            ClassifierDefinitionImpl.commonAncestor(acceptBlock.getSignal()), 
                            1, 1));
            }
        } else {
            Collection<Block> blocks = new ArrayList<Block>();
            Collection<AcceptBlock> acceptBlocks = self.getAcceptBlock();
            for (AcceptBlock acceptBlock: acceptBlocks) {
                Block block = acceptBlock.getBlock();
                if (block != null) {
                    this.setAssignmentBeforeBlock(acceptBlock);
                    blocks.add(block);
                }
            }
            assignmentsAfter.putAll(this.mergeAssignments(blocks));
            for (AcceptBlock acceptBlock: acceptBlocks) {
                String name = acceptBlock.getName();
                if (name != null) {
                    assignmentsAfter.remove(name);
                }
            }
        }

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
        	                signal.getImpl().isContainedIn(signals)) {
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
	    for (AcceptBlock block: self.getAcceptBlock()) {
	        String name = block.getName();
	        if (name != null && this.getAssignmentBefore(name) != null) {
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
	    Collection<AcceptBlock> acceptBlocks = self.getAcceptBlock();
	    if (acceptBlocks.size() > 1) {
	        this.deriveAssignmentAfter(); // Force computing of assignments.
	        Map<String, Integer> definitionCount = new HashMap<String, Integer>();
	        for (AcceptBlock acceptBlock: acceptBlocks) {
	            Block block = acceptBlock.getBlock();
	            if (block != null) {
	                for (AssignedSource assignment: block.getImpl().getNewAssignments()) {
	                    String name = assignment.getName();
	                    Integer count = definitionCount.get(name);
	                    if (count == null) {
	                        definitionCount.put(name, 1);
	                    } else {
	                        definitionCount.put(name, count++);
	                    }
	                }
	            }
	        }
	        int n = acceptBlocks.size();
	        for (int count: definitionCount.values()) {
	            if (count != n) {
	                return false;
	            }
	        }
	    }
		return true;
	}

	/**
	 * The enclosing statement for all statements in the blocks of all accept
	 * blocks of an accept statement is the accept statement.
	 **/
	public boolean acceptStatementEnclosedStatements() {
	    // Note: This is handled by setEnclosingStatement.
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
    private void setAssignmentBeforeBlock(AcceptBlock acceptBlock) {
        AcceptStatement self = this.getSelf();
        Block block = acceptBlock.getBlock();
        block.setAssignmentBefore(self.getAssignmentBefore());
        String name = acceptBlock.getName();
        if (name != null) {
            block.addAssignmentBefore(AssignedSourceImpl.makeAssignment(name, self,
                    ClassifierDefinitionImpl.commonAncestor(acceptBlock.getSignal()),
                    1, 1));
        }
    }

} // AcceptStatementImpl
