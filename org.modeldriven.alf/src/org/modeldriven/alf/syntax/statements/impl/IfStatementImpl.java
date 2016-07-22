/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A conditional statement that executes (at most) one of a set of clauses based
 * on boolean conditions.
 **/

public class IfStatementImpl extends StatementImpl {

	private List<ConcurrentClauses> nonFinalClauses = new ArrayList<ConcurrentClauses>();
	private Block finalClause = null;
	private Boolean isAssured = null; // DERIVED
	private Boolean isDeterminate = null; // DERIVED
	
	public IfStatementImpl(IfStatement self) {
		super(self);
	}

	public IfStatement getSelf() {
		return (IfStatement) this.self;
	}

	public List<ConcurrentClauses> getNonFinalClauses() {
		return this.nonFinalClauses;
	}

	public void setNonFinalClauses(List<ConcurrentClauses> nonFinalClauses) {
		this.nonFinalClauses = nonFinalClauses;
        for (ConcurrentClauses clauses: nonFinalClauses) {
            clauses.getImpl().setEnclosingStatement(this.getSelf());
        }
	}

	public void addNonFinalClauses(ConcurrentClauses nonFinalClauses) {
		this.nonFinalClauses.add(nonFinalClauses);
        nonFinalClauses.getImpl().setEnclosingStatement(this.getSelf());
	}

	public Block getFinalClause() {
		return this.finalClause;
	}

	public void setFinalClause(Block finalClause) {
		this.finalClause = finalClause;
        if (finalClause != null) {
            finalClause.getImpl().setEnclosingStatement(this.getSelf());
        }
	}

	public Boolean getIsAssured() {
		if (this.isAssured == null) {
			this.setIsAssured(this.deriveIsAssured());
		}
		return this.isAssured;
	}

	public void setIsAssured(Boolean isAssured) {
		this.isAssured = isAssured;
	}

	public Boolean getIsDeterminate() {
		if (this.isDeterminate == null) {
			this.setIsDeterminate(this.deriveIsDeterminate());
		}
		return this.isDeterminate;
	}

	public void setIsDeterminate(Boolean isDetermined) {
		this.isDeterminate = isDetermined;
	}
	
	/**
     * An if statement is assured if it has an @assured annotation.
     **/
	protected Boolean deriveIsAssured() {
		return this.hasAnnotation("assured");
	}

    /**
     * An if statement is determinate if it has an @determinate annotation.
     **/
	protected Boolean deriveIsDeterminate() {
		return this.hasAnnotation("determinate");
	}
	
    /**
     * The assignments before each non-final clause of an if statement are the
     * same as the assignments before the if statement, adjusted for known nulls
     * and non-nulls due to the failure of the conditions in all previous sets
     * of concurrent clauses. If the statement has a final clause, then the
     * assignments before that clause are also the same as the assignments
     * before the if statement, adjusted for the failure of the conditions of
     * all previous clauses.
     *
     * Any name that is unassigned before an if statement and is assigned in one
     * or more clauses of the if statement, has, after the if statement, a type
     * that is is the effective common ancestor of the types of the name in each
     * clause in which it is defined. For a name that has an assigned source
     * after any clause of an if statement that is different than before that
     * clause, then the assigned source after the if statement is the if
     * statement, with a multiplicity lower bound that is the minimum of the
     * lower bound for the name in each clause and a multiplicity upper bound
     * that is the maximum for the name in each clause (where the name is
     * considered to have multiplicity [0..0] for clauses in which it is not
     * defined and unchanged multiplicity for an implicit "else" clause).
     * Otherwise, the assigned source of a name after the if statement is the
     * same as before the if statement.
     **/
    @Override
    protected Map<String, AssignedSource> deriveAssignmentAfter() {
        IfStatement self = this.getSelf();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Collection<Block> blocks = new ArrayList<Block>();
        for (ConcurrentClauses clauses: self.getNonFinalClauses()) {
            clauses.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsBefore = clauses.getImpl().updateAssignmentsBefore();
            blocks.addAll(clauses.getImpl().getBlocks());
        }
        Block finalClause = self.getFinalClause();
        if (finalClause == null) {
            // NOTE: This adds an empty block for an implicit "else", so that the
            // final multiplicities for names assigned in the other clauses are
            // set correctly on merging.
            finalClause = new Block();
            finalClause.setStatement(new ArrayList<Statement>());
        }
        finalClause.getImpl().setAssignmentBefore(assignmentsBefore);
        blocks.add(finalClause);
        Map<String, AssignedSource> assignmentsAfter = 
            new HashMap<String, AssignedSource>(super.deriveAssignmentAfter());
        assignmentsAfter.putAll(this.mergeAssignments(blocks, assignmentsBefore));
        return assignmentsAfter;
    }
    
	/*
	 * Derivations
	 */

    public boolean ifStatementIsAssuredDerivation() {
        this.getSelf().getIsAssured();
        return true;
    }

    public boolean ifStatementIsDeterminateDerivation() {
        this.getSelf().getIsDeterminate();
        return true;
    }
    
    /*
     * Constraints
     */

    /**
     * The assignments before each non-final clause of an if statement are the
     * same as the assignments before the if statement, adjusted for known nulls
     * and non-nulls due to the failure of the conditions in all previous sets
     * of concurrent clauses. If the statement has a final clause, then the
     * assignments before that clause are also the same as the assignments
     * before the if statement, adjusted for the failure of the conditions of
     * all previous clauses.
     **/
	public boolean ifStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentsAfter.
		return true;
	}

    /**
     * Any name that is unassigned before an if statement and is assigned in one
     * or more clauses of the if statement, has, after the if statement, a type
     * that is is the effective common ancestor of the types of the name in each
     * clause in which it is defined. For a name that has an assigned source
     * after any clause of an if statement that is different than before that
     * clause, then the assigned source after the if statement is the if
     * statement, with a multiplicity lower bound that is the minimum of the
     * lower bound for the name in each clause and a multiplicity upper bound
     * that is the maximum for the name in each clause (where the name is
     * considered to have multiplicity [0..0] for clauses in which it is not
     * defined and unchanged multiplicity for an implicit "else" clause).
     * Otherwise, the assigned source of a name after the if statement is the
     * same as before the if statement.
     **/
	public boolean ifStatementAssignmentsAfter() {
	    // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
	 * The enclosing statement of all the statements in the bodies of all
	 * non-final clauses and in the final clause (if any) of an if statement is
	 * the if statement.
	 **/
	public boolean ifStatementEnclosedStatements() {
	    // Note: This is handled by overriding setEnclosingStatement.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * In addition to an @isolated annotation, an if statement may have @assured
	 * and @determinate annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
	    String identifier = annotation.getIdentifier();
		return super.annotationAllowed(annotation) ||
		            (identifier.equals("assured") || 
		                    identifier.equals("determinate")) &&
		             annotation.getArgument().isEmpty();
	} // annotationAllowed
	
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    IfStatement self = this.getSelf();
	    for (ConcurrentClauses clause: self.getNonFinalClauses()) {
	        clause.getImpl().setCurrentScope(currentScope);
	    }
	    Block finalClause = self.getFinalClause();
	    if (finalClause != null) {
	        finalClause.getImpl().setCurrentScope(currentScope);
	    }
	}
	
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof IfStatement) {
            IfStatement self = this.getSelf();
            IfStatement baseStatement = (IfStatement)base;
            Block finalClause = baseStatement.getFinalClause();
            for (ConcurrentClauses nonFinalClauses:
                baseStatement.getNonFinalClauses()) {
                self.addNonFinalClauses
                    ((ConcurrentClauses)nonFinalClauses.getImpl().
                            bind(templateParameters, templateArguments));
            }
            if (finalClause != null) {
                self.setFinalClause((Block)finalClause.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // IfStatementImpl
