
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
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
	private Boolean isDetermined = null; // DERIVED

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

	public Boolean getIsDetermined() {
		if (this.isDetermined == null) {
			this.setIsDetermined(this.deriveIsDetermined());
		}
		return this.isDetermined;
	}

	public void setIsDetermined(Boolean isDetermined) {
		this.isDetermined = isDetermined;
	}

    /**
     * An switch statement is assured if it has an @assured annotation.
     **/
	protected Boolean deriveIsAssured() {
		return this.hasAnnotation("assured");
	}

    /**
     * An switch statement is determined if it has an @determined annotation.
     **/
	protected Boolean deriveIsDetermined() {
		return this.hasAnnotation("determined");
	}
	
    /**
     * The assignments before all clauses of a switch statement are the same as
     * the assignments before the switch statement. If a name has an assigned 
     * source after any clause of a switch statement that is different than 
     * before that clause (including newly defined names), the assigned source 
     * after the switch statement is the switch statement. 
     * 
     * If a switch statement does not have a final default clause, then any name
     * that is unassigned before the switch statement is unassigned after the
     * switch statement. If a switch statement does have a final default clause,
     * then any name assigned in any clause of the switch statement has a type 
     * that is the effective common ancestor of the types of the name in each 
     * clause and a multiplicity lower bound that is the minimum of the lower 
     * bound for the name in each clause and a multiplicity upper bound that is 
     * the maximum for the name in each clause. Otherwise, the assigned source 
     * of a name after the switch statement is the same as before the switch 
     * statement.
     **/
	@Override
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
        SwitchStatement self = this.getSelf();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Expression expression = self.getExpression();
        if (expression != null) {
            expression.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsBefore = expression.getImpl().getAssignmentAfterMap();
        }
        Collection<Block> blocks = new ArrayList<Block>();
        for (SwitchClause clause: self.getNonDefaultClause()) {
            clause.getImpl().setAssignmentBefore(assignmentsBefore);
            blocks.add(clause.getImpl().getBlock());
        }
        Block defaultClause = self.getDefaultClause();
        if (defaultClause != null) {
            defaultClause.getImpl().setAssignmentBefore(assignmentsBefore);
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
	
    public boolean switchStatementIsDeterminedDerivation() {
        this.getSelf().getIsDetermined();
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
	 * The assignments before all clauses of a switch statement are the same as
	 * the assignments before the switch statement.
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
	 * statement. Otherwise, the assigned source of a name after the switch
	 * statement is the same as before the switch statement.
	 **/
	public boolean switchStatementAssignmentsAfter() {
        // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
	 * If a switch statement does not have a final default clause, then any name
	 * that is unassigned before the switch statement is unassigned after the
	 * switch statement. If a switch statement does have a final default clause,
	 * then any name that is unassigned before the switch statement and is
	 * assigned after any one clause of the switch statement must also be
	 * assigned after every other clause. The type of such names after the
	 * switch statement is the effective common ancestor of the types of the
	 * name in each clause with a multiplicity lower bound that is the minimum
	 * of the lower bound for the name in each clause and a multiplicity upper
	 * bound that is the maximum for the name in each clause.
	 **/
	public boolean switchStatementAssignments() {
        // Note: This is partly handled by overriding deriveAssignmentAfter.
        SwitchStatement self = this.getSelf();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = this.getAssignmentAfterMap();
        if (self.getDefaultClause() == null) {
            for (String name: assignmentsAfter.keySet()) {
                if (!assignmentsBefore.containsKey(name)) {
                    return false;
                }
            }
        } else {
            Collection<Block> blocks = this.getAllBlocks();
            if (blocks.size() > 1) {
                Map<String, Integer> definitionCount = new HashMap<String, Integer>();
                for (Block block: blocks) {
                    for (AssignedSource assignment: block.getImpl().getNewAssignments()) {
                        String name = assignment.getName();
                        Integer count = definitionCount.get(name);
                        if (count == null) {
                            definitionCount.put(name, 1);
                        } else {
                            definitionCount.put(name, count + 1);
                        }
                    }
                }
                int n = blocks.size();
                for (int count: definitionCount.values()) {
                    if (count != n) {
                        return false;
                    }
                }
            }
        }
        return true;
	}

    /**
	 * The expression of a switch statement must have a multiplicity no greater
	 * than 1.
	 */
	public boolean switchStatementExpression() {
        SwitchStatement self = this.getSelf();
        Expression expression = self.getExpression();
        if (expression == null || expression.getUpper() > 1) {
            return false;
        } else {
            /*
             * The case expressions of a switch statement must all have a
             * multiplicity no greater than 1.
             * (This should really be a constraint on SwitchClause.)
             */
            for (SwitchClause clause: self.getNonDefaultClause()) {
                for (Expression caseExpression: clause.getCase()) {
                    if (caseExpression.getUpper() > 1) {
                        return false;
                    }
                }
            }
            return true;
        }
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

    private Collection<Block> getAllBlocks() {
        SwitchStatement self = this.getSelf();
        Collection<Block> blocks = new ArrayList<Block>();
        for (SwitchClause clause: self.getNonDefaultClause()) {
            blocks.add(clause.getImpl().getBlock());
        }
        Block defaultClause = self.getDefaultClause();
        if (defaultClause != null) {
            blocks.add(defaultClause);
        }
        return blocks;
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
