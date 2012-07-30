
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A statement that executes a block.
 **/

public class BlockStatementImpl extends StatementImpl {

    private Block block = null;
    private Boolean isParallel = null; // DERIVED

	public BlockStatementImpl(BlockStatement self) {
		super(self);
	}

	@Override
	public BlockStatement getSelf() {
		return (BlockStatement) this.self;
	}

    public Block getBlock() {
        return this.block;
    }

    /**
     * The enclosing statement for all the statements in the block of a block
     * statement is the block statement.
     **/
    public void setBlock(Block block) {
        this.block = block;
        if (block != null) {
            block.getImpl().setEnclosingStatement(this.getSelf());
        }
    }

    public Boolean getIsParallel() {
        if (this.isParallel == null) {
            this.setIsParallel(this.deriveIsParallel());
        }
        return this.isParallel;
    }

    public void setIsParallel(Boolean isParallel) {
        this.isParallel = isParallel;
    }
    
    /**
     * A block statement is parallel if it has a @parallel annotation.
     **/
    protected Boolean deriveIsParallel() {
        return this.hasAnnotation("parallel");
    }
    
    /**
     * The assignments before the block of a block statement are the same as the
     * assignments before the block statement.
     *
     * The assignments after a block statement are the same as the assignments
     * after the block of the block statement.
     **/
    @Override
    protected Map<String, AssignedSource> deriveAssignmentAfter() {
        BlockStatement self = this.getSelf();
        Block block = self.getBlock();
        if (block == null) {
            return null;
        } else {
            BlockImpl blockImpl = block.getImpl();
            blockImpl.setAssignmentBefore(this.getAssignmentBeforeMap());
            return blockImpl.getAssignmentAfterMap();
        }
    }

    /*
     * Derivations
     */
    
    public boolean blockStatementIsParallelDerivation() {
        this.getSelf().getIsParallel();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * In a parallel block statement, any name assigned in one statement of the
	 * block may not be further assigned in any subsequent statement in the same
	 * block.
	 **/
	public boolean blockStatementParallelAssignments() {
	    BlockStatement self = this.getSelf();
	    Block block = self.getBlock();
	    if (self.getIsParallel() && block != null) {
	        self.getAssignmentAfter(); // Make sure assignments are derived for the block.
	        Set<AssignedSource> previousAssignments = new HashSet<AssignedSource>();
	        for (Statement statement: block.getStatement()) {
	            for (AssignedSource assignment: statement.getImpl().getNewAssignments()) {
	                if (assignment.getImpl().isAssignedIn(previousAssignments)) {
	                    return false;
	                }
	                previousAssignments.add(assignment);
	            }
	        }
	    }
		return true;
	}

	/**
	 * The assignments before the block of a block statement are the same as the
	 * assignments before the block statement.
	 **/
	public boolean blockStatementAssignmentsBefore() {
        // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The assignments after a block statement are the same as the assignments
	 * after the block of the block statement.
	 **/
	public boolean blockStatementAssignmentsAfter() {
        // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The enclosing statement for all the statements in the block of a block
	 * statement is the block statement.
	 **/
	public boolean blockStatementEnclosedStatements() {
	    //Note: This is handled by setEnclosingStatement.
		return true;
	}

	/**
	 * In addition to an @isolated annotation, a block statement may have a @parallel
	 * annotation. It may not have any arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return annotation != null && 
		    (super.annotationAllowed(annotation) || 
		            annotation.getIdentifier().equals("parallel"));
	} // annotationAllowed

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        Block block = this.getSelf().getBlock();
        if (block != null) {
            block.getImpl().setCurrentScope(currentScope);
        }
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof BlockStatement) {
            Block block = ((BlockStatement)base).getBlock();
            if (block != null) {
                this.getSelf().setBlock((Block)block.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // BlockStatementImpl
