
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

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
	private Boolean isDetermined = null; // DERIVED

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
     * An if statement is assured if it has an @assured annotation.
     **/
	protected Boolean deriveIsAssured() {
		return this.hasAnnotation("assured");
	}

    /**
     * An if statement is determined if it has an @determined annotation.
     **/
	protected Boolean deriveIsDetermined() {
		return this.hasAnnotation("determined");
	}
	
    /**
     * The assignments before all the non-final clauses of an if statement are
     * the same as the assignments before the if statement. If the statement has
     * a final clause, then the assignments before that clause are also the same
     * as the assignments before the if statement.
     *
     * If an if statement does not have a final else clause, then any name that
     * is unassigned before the if statement is unassigned after the if
     * statement. If an if statement does have a final else clause, then names
     * assigned in any clause of the if statement have a type after the if 
     * statement that is the effective common ancestor of the types of the name 
     * in each clause with a multiplicity lower bound that is the minimum of the 
     * lower bound for the name in each clause and a multiplicity upper bound 
     * that is the maximum for the name in each clause. For a name that has an 
     * assigned source after any clause of an if statement that is different 
     * than before that clause, then the assigned source after the if statement 
     * is the if statement. Otherwise, the assigned source of a name after the 
     * if statement is the same as before the if statement.
     **/
    @Override
    protected Map<String, AssignedSource> deriveAssignmentAfter() {
        IfStatement self = this.getSelf();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Collection<Block> blocks = new ArrayList<Block>();
        for (ConcurrentClauses clauses: self.getNonFinalClauses()) {
            clauses.getImpl().setAssignmentBefore(assignmentsBefore);
            blocks.addAll(clauses.getImpl().getBlocks());
        }
        Block finalClause = self.getFinalClause();
        if (finalClause != null) {
            finalClause.getImpl().setAssignmentBefore(assignmentsBefore);
            blocks.add(finalClause);
        }
        Map<String, AssignedSource> assignmentsAfter = 
            new HashMap<String, AssignedSource>(super.deriveAssignmentAfter());
        assignmentsAfter.putAll(this.mergeAssignments(blocks));
        if (finalClause == null) {
            for (Object name: assignmentsAfter.keySet().toArray()) {
                if (!assignmentsBefore.containsKey(name)) {
                    assignmentsAfter.remove(name);
                }
            }
        }
        return assignmentsAfter;
    }
    
	/*
	 * Derivations
	 */

    public boolean ifStatementIsAssuredDerivation() {
        this.getSelf().getIsAssured();
        return true;
    }

    public boolean ifStatementIsDeterminedDerivation() {
        this.getSelf().getIsDetermined();
        return true;
    }
    
    /*
     * Constraints
     */

	/**
	 * The assignments before all the non-final clauses of an if statement are
	 * the same as the assignments before the if statement. If the statement has
	 * a final clause, then the assignments before that clause are also the same
	 * as the assignments before the if statement.
	 **/
	public boolean ifStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentsAfter.
		return true;
	}

	/**
	 * If an if statement does not have a final else clause, then any name that
	 * is unassigned before the if statement is unassigned after the if
	 * statement. If an if statement does have a final else clause, then any
	 * name that is unassigned before the if statement and is assigned after any
	 * one clause of the if statement must also be assigned after every other
	 * clause. The type of such names after the if statement is the effective
	 * common ancestor of the types of the name in each clause with a
	 * multiplicity lower bound that is the minimum of the lower bound for the
	 * name in each clause and a multiplicity upper bound that is the maximum
	 * for the name in each clause. For a name that has an assigned source after
	 * any clause of an if statement that is different than before that clause,
	 * then the assigned source after the if statement is the if statement.
	 * Otherwise, the assigned source of a name after the if statement is the
	 * same as before the if statement.
	 **/
	public boolean ifStatementAssignmentsAfter() {
	    // Note: This is partly handled by overriding deriveAssignmentAfter.
	    IfStatement self = this.getSelf();
	    Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    Map<String, AssignedSource> assignmentsAfter = this.getAssignmentAfterMap();
	    if (self.getFinalClause() == null) {
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
	                        definitionCount.put(name, count+1);
	                    }
	                }
	            }
	            int n = blocks.size();
	            for (String name: definitionCount.keySet()) {
	                if (!assignmentsBefore.containsKey(name) && 
	                        definitionCount.get(name) != n) {
	                    return false;
	                }
	            }
	        }
	    }
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
	 * and @determined annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
	    String identifier = annotation.getIdentifier();
		return super.annotationAllowed(annotation) ||
		            (identifier.equals("assured") || 
		                    identifier.equals("determined")) &&
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
	
	private Collection<Block> getAllBlocks() {
	    IfStatement self = this.getSelf();
        Collection<Block> blocks = new ArrayList<Block>();
        for (ConcurrentClauses clauses: self.getNonFinalClauses()) {
            blocks.addAll(clauses.getImpl().getBlocks());
        }
        Block finalClause = self.getFinalClause();
        if (finalClause != null) {
            blocks.add(finalClause);
        }
	    return blocks;
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
