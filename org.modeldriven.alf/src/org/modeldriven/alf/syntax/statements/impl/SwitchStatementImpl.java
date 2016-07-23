
/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatementImpl extends StatementImpl {

	private Collection<SwitchClause> nonDefaultClause = new ArrayList<SwitchClause>();
	private Expression expression = null;
	private Block defaultClause = null;
	private Boolean isAssured = null; // DERIVED
	private Boolean isDeterminate = null; // DERIVED

	public SwitchStatementImpl(SwitchStatement self) {
		super(self);
	}

	@Override
	public SwitchStatement getSelf() {
		return (SwitchStatement) this.self;
	}

	public Collection<SwitchClause> getNonDefaultClause() {
		return this.nonDefaultClause;
	}

	public void setNonDefaultClause(Collection<SwitchClause> nonDefaultClause) {
		this.nonDefaultClause = nonDefaultClause;
		if (nonDefaultClause != null) {
            for (SwitchClause clause: nonDefaultClause) {
                Block block = clause.getBlock();
                if (block != null) {
                    block.getImpl().setEnclosingStatement(this.getSelf());
                }
            }
		}
	}

	public void addNonDefaultClause(SwitchClause nonDefaultClause) {
		this.nonDefaultClause.add(nonDefaultClause);
		if (nonDefaultClause != null) {
            Block block = nonDefaultClause.getBlock();
            if (block != null) {
                block.getImpl().setEnclosingStatement(this.getSelf());
            }
		}
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Block getDefaultClause() {
		return this.defaultClause;
	}

	public void setDefaultClause(Block defaultClause) {
		this.defaultClause = defaultClause;
        if (defaultClause != null) {
            defaultClause.getImpl().setEnclosingStatement(this.getSelf());
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
     * An switch statement is assured if it has an @assured annotation.
     **/
	protected Boolean deriveIsAssured() {
		return this.hasAnnotation("assured");
	}

    /**
     * An switch statement is determinate if it has an @determined annotation.
     **/
	protected Boolean deriveIsDeterminate() {
		return this.hasAnnotation("determinate");
	}
	
    /**
     * The assignments before all clauses of a switch statement are the same as
     * the assignments before the switch statement.
     * 
     * If a name has an assigned source after any clause of a switch statement
     * that is different than before that clause (including newly defined
     * names), the assigned source after the switch statement is the switch
     * statement, with a multiplicity lower bound that is the minimum of the
     * lower bound for the name in each clause and a multiplicity upper bound
     * that is the maximum for the name in each clause (where the name is
     * considered to have multiplicity [0..0] for clauses in which it is not
     * defined and unchanged multiplicity for an implicit "default" clause).
     * Otherwise, the assigned source of a name after the switch statement is
     * the same as before the switch statement.
     * 
     * Any name that is unassigned before a switch statement and is assigned in
     * one or more clauses of the switch statement, has, after the switch
     * statement, a type that is is the effective common ancestor of the types
     * of the name in each clause in which it is defined.
     **/
	@Override
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
        SwitchStatement self = this.getSelf();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsBeforeClauses = assignmentsBefore;
        Expression expression = self.getExpression();
        if (expression != null) {
            expression.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsBeforeClauses = expression.getImpl().getAssignmentAfterMap();
        }
        Collection<Block> blocks = new ArrayList<Block>();
        for (SwitchClause clause: self.getNonDefaultClause()) {
            clause.getImpl().setAssignmentBefore(assignmentsBeforeClauses);
            blocks.add(clause.getImpl().getBlock());
        }
        Block defaultClause = self.getDefaultClause();
        if (defaultClause != null) {
            defaultClause.getImpl().setAssignmentBefore(assignmentsBeforeClauses);
            blocks.add(defaultClause);
        }
        Map<String, AssignedSource> assignmentsAfter = 
            new HashMap<String, AssignedSource>(super.deriveAssignmentAfter());
        assignmentsAfter.putAll(this.mergeAssignments(blocks));
        return assignmentsAfter;
	}
	
	/*
	 * Derivations
	 */
	
    public boolean switchStatementIsDeterminateDerivation() {
        this.getSelf().getIsDeterminate();
        return true;
    }

    public boolean switchStatementIsAssuredDerivation() {
        this.getSelf().getIsAssured();
        return true;
    }

	/*
	 * Constraints
	 */

	/**
     * The assignments before the case expressions of all clauses of a switch
     * statement are the same as the assignments after the expression of the
     * switch statement.
	 **/
	public boolean switchStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The same local name may not be assigned in more than one case expression
	 * in a switch statement.
	 **/
	public boolean switchStatementCaseAssignments() {
	    SwitchStatement self = this.getSelf();
	    this.getAssignmentAfterMap(); // Force computation of assignments.
	    Collection<AssignedSource> previousAssignments = new HashSet<AssignedSource>();
	    for (SwitchClause clause: self.getNonDefaultClause()) {
	        for (Expression expression: clause.getCase()) {
	            Collection<AssignedSource> newAssignments = 
	                expression.getImpl().getNewAssignments();
	            for (AssignedSource newAssignment: newAssignments) {
	                if (newAssignment.getImpl().isAssignedIn(previousAssignments)) {
	                    return false;
	                }
	                previousAssignments.addAll(newAssignments);
	            }
	        }
	    }
		return true;
	}

    /**
     * If a name has an assigned source after any clause of a switch statement
     * that is different than before that clause (including newly defined
     * names), the assigned source after the switch statement is the switch
     * statement, with a multiplicity lower bound that is the minimum of the
     * lower bound for the name in each clause and a multiplicity upper bound
     * that is the maximum for the name in each clause (where the name is
     * considered to have multiplicity [0..0] for clauses in which it is not
     * defined and unchanged multiplicity for an implicit "default" clause).
     * Otherwise, the assigned source of a name after the switch statement is
     * the same as before the switch statement.
     **/
	public boolean switchStatementAssignmentsAfter() {
        // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
     * Any name that is unassigned before a switch statement and is assigned in
     * one or more clauses of the switch statement, has, after the switch
     * statement, a type that is is the effective common ancestor of the types
     * of the name in each clause in which it is defined.
	 **/
	public boolean switchStatementAssignments() {
        // Note: This is handled by overriding deriveAssignmentAfter.
        return true;
	}

    /**
	 * The expression of a switch statement must have a multiplicity no greater
	 * than 1.
	 */
	public boolean switchStatementExpression() {
        SwitchStatement self = this.getSelf();
        Expression expression = self.getExpression();
        return expression != null && expression.getUpper() <= 1;
	}

	public boolean switchStatementEnclosedStatements() {
	    // Note: This is handled by setEnclosingStatement.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * In addition to an @isolated annotation, a switch statement may have @assured
	 * and @determined annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
	    String identifier = annotation.getIdentifier();
		return super.annotationAllowed(annotation) ||
		            (identifier.equals("assured") || identifier.equals("determined")) &&
		            annotation.getArgument().isEmpty();
	} // annotationAllowed
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
        SwitchStatement self = this.getSelf();
        Expression expression = self.getExpression();
        if (expression != null) {
            expression.getImpl().setCurrentScope(currentScope);
        }
        for (SwitchClause clause: self.getNonDefaultClause()) {
            for (Expression case_: clause.getCase()) {
                case_.getImpl().setCurrentScope(currentScope);
            }
            
            Block block = clause.getBlock();
            if (block != null) {
                block.getImpl().setCurrentScope(currentScope);
            }
        }
        Block defaultClause = self.getDefaultClause();
        if (defaultClause != null) {
            defaultClause.getImpl().setCurrentScope(currentScope);
        }
	    
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SwitchStatement) {
            SwitchStatement self = this.getSelf();
            SwitchStatement baseStatement = (SwitchStatement)base;
            Expression expression = baseStatement.getExpression();
            Block defaultClause = baseStatement.getDefaultClause();
            for (SwitchClause clause: baseStatement.getNonDefaultClause()) {
                self.addNonDefaultClause((SwitchClause)clause.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (expression != null) {
                self.setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (defaultClause != null) {
                self.setDefaultClause((Block)defaultClause.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // SwitchStatementImpl
