
/*******************************************************************************
 * Copyright 2011, 2016, 2020 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
	    ElementReference behavior = this.getEffectiveBehavior();
	    return behavior != null && behavior.getImpl().isActiveBehavior();
	}

	/**
	 * No signal may be referenced in more than one accept block of an accept statement.
	 **/
	// NOTE: Checking that there are receptions for the signals has been moved to
	// the separate acceptStatementReceptions check.
	public boolean acceptStatementSignals() {
	    ElementReference behavior = this.getEffectiveBehavior();
	    behavior = behavior == null? null: behavior.getImpl().getContext();
	    if (behavior == null) {
	        return false;
	    } else {
    	    Collection<ElementReference> signals = new ArrayList<ElementReference>();
    	    for (AcceptBlock block: this.getSelf().getAcceptBlock()) {
    	        Collection<ElementReference> blockSignals = block.getSignal();
    	        for (ElementReference signal: blockSignals) {
        	        if (signal.getImpl().isContainedIn(signals)) {
        	            return false;
        	        }
    	        }
    	        signals.addAll(blockSignals);
     	    }
    	    return true;
	    }
	}

	/**
	 * The containing behavior of an accept statement must have receptions for
	 * all signals from all accept blocks of the accept statement.
	 **/
	// NOTE: acceptStatementSignals has been separated from acceptStatementReceptions
	// so that a tool can allow accept statements without requiring receptions by
	// ignoring violations of this constraint.
	public boolean acceptStatementReceptions() {
	    ElementReference behavior = this.getEffectiveBehavior();
	    behavior = behavior == null? null: behavior.getImpl().getContext();
	    if (behavior == null) {
	        return false;
	    } else {
    	    for (AcceptBlock block: this.getSelf().getAcceptBlock()) {
    	        for (ElementReference signal: block.getSignal()) {
        	        if (!behavior.getImpl().hasReceptionFor(signal)) {
        	            return false;
        	        }
    	        }
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
     * Any name that is unassigned before an accept statement and is assigned in
     * one or more blocks of the accept statement, has, after the accept
     * statement, a type that is is the effective common ancestor of the types
     * of the name in each block in which it is defined, with a multiplicity
     * lower bound that is the minimum of the lower bound for the name in each
     * block (where it is considered to have multiplicity lower bound of zero
     * for blocks in which it is not defined), and a multiplicity upper bound
     * that is the maximum for the name in each block in which it is defined.
     **/
	public boolean acceptStatementNewAssignments() {
        // Note: This is handled by deriveAssignmentsAfter.
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

    /**
     * An accept statement has a return value if all of its accept clauses
     * have return values.
     */
    @Override
    public Boolean hasReturnValue() {
        for (AcceptBlock acceptBlock: this.getSelf().getAcceptBlock()) {
            Block block = acceptBlock.getBlock();
            if (block != null && !block.hasReturnValue()) {
                return false;
            }
        }
        return true;
    }

	/**
	 * If the behavior for this accept statement is a subunit, then return the
	 * corresponding stub. Note that, if the original behavior is an Alf
	 * activity definition, the "stub" may be an external operation or activity.
	 */
	public ElementReference getEffectiveBehavior() {
	    ElementReference behavior = this.getBehavior();
        ElementReference stub = behavior == null? null:
                behavior.getImpl().asNamespace().getImpl().getStubReference();
        return stub == null? behavior: stub;
	}
	
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
            block.addAssignmentBefore(AssignedSourceImpl.makeAssignment(
                    name, acceptBlock,
                    ClassifierDefinitionImpl.commonAncestor(acceptBlock.getSignal()),
                    1, 1));
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof AcceptStatement) {
            AcceptStatement self = this.getSelf();
            for (AcceptBlock acceptBlock: ((AcceptStatement)base).getAcceptBlock()) {
                self.addAcceptBlock((AcceptBlock)acceptBlock.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // AcceptStatementImpl
