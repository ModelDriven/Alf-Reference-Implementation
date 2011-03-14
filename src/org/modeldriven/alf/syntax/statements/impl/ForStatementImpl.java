
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
 * A looping statement that gives successive values to one or more loop
 * variables on each iteration.
 **/

public class ForStatementImpl extends StatementImpl {

	private Block body = null;
	private List<LoopVariableDefinition> variableDefinition = new ArrayList<LoopVariableDefinition>();
	private Boolean isParallel = null; // DERIVED

	public ForStatementImpl(ForStatement self) {
		super(self);
	}

	@Override
	public ForStatement getSelf() {
		return (ForStatement) this.self;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public List<LoopVariableDefinition> getVariableDefinition() {
		return this.variableDefinition;
	}

	public void setVariableDefinition(
			List<LoopVariableDefinition> variableDefinition) {
		this.variableDefinition = variableDefinition;
        if (variableDefinition.size() > 0) {
            variableDefinition.get(0).setIsFirst(true);
        }
	}

	public void addVariableDefinition(LoopVariableDefinition variableDefinition) {
		this.variableDefinition.add(variableDefinition);
		if (this.variableDefinition.size() == 1) {
		    variableDefinition.setIsFirst(true);
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
     * A for statement is parallel if it has a @parallel annotation.
     **/
	protected Boolean deriveIsParallel() {
		return this.hasAnnotation("parallel");
	}
	
    /**
     * The assignments before a loop variable definition in a for statement are
     * the same as before the for statement. The assignments before the body of
     * the statement include all the assignments before the statement plus any
     * new assignments from the loop variable definitions, except that, if the
     * statement is parallel, the assigned sources of any names given in @parallel
     * annotations are changed to be the for statement itself.
     *
     * The loop variables are unassigned after a for statement. Other than the
     * loop variables, if the assigned source for a name after the body of a for
     * statement is the same as after the for variable definitions, then the
     * assigned source for the name after the for statement is the same as after
     * the for variable definitions. If a name is unassigned after the for
     * variable definitions, then it is unassigned after the for statement (even
     * if it is assigned in the body of the for statement). If, after the loop
     * variable definitions, a name has an assigned source, and it has a
     * different assigned source after the body of the for statement, then the
     * assigned source after the for statement is the for statement itself.
     *
     * If, after the loop variable definitions of a parallel for statement, a
     * name has an assigned source, then it must have the same assigned source
     * after the block of the for statement. Other than for names defined in the @parallel
     * annotation of the for statement, the assigned source for such names is
     * the same after the for statement as before it. Any names defined in the @parallel
     * annotation have the for statement itself as their assigned source after
     * the for statement. Other than names given in the @parallel annotation, if
     * a name is unassigned after the loop variable definitions, then it is
     * considered unassigned after the for statement, even if it is assigned in
     * the block of the for statement.
     **/
	@Override
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
	    ForStatement self = this.getSelf();
	    Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    List<LoopVariableDefinition> variableDefinitions = self.getVariableDefinition();
	    Block body = self.getBody();
	    for (LoopVariableDefinition variableDefinition: variableDefinitions) {
	        variableDefinition.getImpl().setAssignmentBefore(assignmentsBefore);
	        assignmentsBefore = variableDefinition.getImpl().getAssignmentAfterMap();
	    }
	    Collection<String> parallelNames = this.getParallelNames();
	    if (!parallelNames.isEmpty()) {
	        assignmentsBefore = new HashMap<String, AssignedSource>(assignmentsBefore);
    	    for (String name: parallelNames) {
    	        AssignedSource assignment = assignmentsBefore.get(name);
    	        if (assignment != null) {
    	            assignment = AssignedSourceImpl.makeAssignment(assignment);
    	            assignment.setSource(self);
    	            assignmentsBefore.put(name, assignment);
    	        }
    	    }
	    }
	    Map<String, AssignedSource> assignmentsAfter = 
	        new HashMap<String, AssignedSource>(assignmentsBefore);
	    if (body != null) {
	        body.getImpl().setAssignmentBefore(assignmentsBefore);
	        for (AssignedSource newAssignment: body.getImpl().getNewAssignments()) {
	            String name = newAssignment.getName();
	            if (assignmentsBefore.containsKey(name)) {
	                AssignedSource assignment = AssignedSourceImpl.makeAssignment(newAssignment);
	                assignment.setSource(self);
	                assignmentsAfter.put(name, newAssignment);
	            }
	        }
	    }
	    for (LoopVariableDefinition variableDefinition: variableDefinitions) {
	        assignmentsAfter.remove(variableDefinition.getVariable());
	    }
	    return assignmentsAfter;
	}
	
    /**
     * The enclosing statement for all statements in the body of a for statement
     * are the for statement.
     **/
	public void setEnclosingStatement(Statement enclosingStatement) {
	    super.setEnclosingStatement(enclosingStatement);
	    ForStatement self = this.getSelf();
	    Block body = self.getBody();
	    if (body != null) {
	        body.getImpl().setEnclosingStatement(self);
	    }
	}
	
	/*
	 * Derivations
	 */

    public boolean forStatementIsParallelDerivation() {
        this.getSelf().getIsParallel();
        return true;
    }

	/**
	 * The assignments before a loop variable definition in a for statement are
	 * the same as before the for statement. The assignments before the body of
	 * the statement include all the assignments before the statement plus any
	 * new assignments from the loop variable definitions, except that, if the
	 * statement is parallel, the assigned sources of any names given in @parallel
	 * annotations are changed to be the for statement itself.
	 **/
	public boolean forStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The loop variables are unassigned after a for statement. Other than the
	 * loop variables, if the assigned source for a name after the body of a for
	 * statement is the same as after the for variable definitions, then the
	 * assigned source for the name after the for statement is the same as after
	 * the for variable definitions. If a name is unassigned after the for
	 * variable definitions, then it is unassigned after the for statement (even
	 * if it is assigned in the body of the for statement). If, after the loop
	 * variable definitions, a name has an assigned source, and it has a
	 * different assigned source after the body of the for statement, then the
	 * assigned source after the for statement is the for statement itself.
	 **/
	public boolean forStatementAssignmentsAfter() {
	    // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
	 * A @parallel annotation of a for statement may include a list of names.
	 * Each such name must be already assigned after the loop variable
	 * definitions of the for statement, with a multiplicity of [0..*]. These
	 * names may only be used within the body of the for statement as the first
	 * argument to for the CollectionFunctions::add behavior.
	 **/
	public boolean forStatementParallelAnnotationNames() {
	    Collection<String> parallelNames = this.getParallelNames();
	    if (!parallelNames.isEmpty()) {
	        this.getAssignmentAfterMap(); // Force computation of assignments.
	        Map<String, AssignedSource> assignments = 
	            this.getAssignmentAfterVariables();
	        for (String name: parallelNames) {
	            AssignedSource assignment = assignments.get(name);
	            if (assignment == null || 
	                    assignment.getLower() != 0 || 
	                    assignment.getUpper() != -1) {
	                return false;
	            }
	        }
	    }    
		return true;
	}
	
	/**
	 * If, after the loop variable definitions of a parallel for statement, a
	 * name has an assigned source, then it must have the same assigned source
	 * after the block of the for statement. Other than for names defined in the @parallel
	 * annotation of the for statement, the assigned source for such names is
	 * the same after the for statement as before it. Any names defined in the @parallel
	 * annotation have the for statement itself as their assigned source after
	 * the for statement. Other than names given in the @parallel annotation, if
	 * a name is unassigned after the loop variable definitions, then it is
	 * considered unassigned after the for statement, even if it is assigned in
	 * the block of the for statement.
	 **/
	public boolean forStatementParallelAssignmentsAfter() {
	    ForStatement self = this.getSelf();
	    if (self.getIsParallel()) {
	        Map<String, AssignedSource> assignmentsAfter = this.getAssignmentAfterMap();
    	    Map<String, AssignedSource> assignmentsAfterVariables = this.getAssignmentAfterVariables();
            Block body = self.getBody();
            if (body != null) {
                for (AssignedSource newAssignment: body.getImpl().getNewAssignments()) {
                    if (assignmentsAfterVariables.containsKey(newAssignment.getName())) {
                        return false;
                    }
                }
            }
            if (assignmentsAfter.size() != assignmentsAfterVariables.size()) {
                return false;
            }
            Collection<String> parallelNames = this.getParallelNames();
            for (AssignedSource assignment: assignmentsAfter.values()) {
                String name = assignment.getName();
                AssignedSource oldAssignment = assignmentsAfterVariables.get(name);
                if (oldAssignment == null || assignment.getSource() != 
                    (parallelNames.contains(name)? self: oldAssignment.getSource())) {
                    return false;
                }
                    
            }
	    }    
		return true;
	}

	/**
	 * The isFirst attribute of the first loop variable definition for a for
	 * statement is true while the isFirst attribute is false for any other
	 * definitions.
	 **/
	public boolean forStatementVariableDefinitions() {
	    // Note: This is handled by setVariableDefinition and addVariableDefinition.
		return true;
	}

	/**
	 * The assigned sources for loop variables after the body of a for statement
	 * must be the for statement (the same as before the body).
	 **/
	public boolean forStatementLoopVariables() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The enclosing statement for all statements in the body of a for statement
	 * are the for statement.
	 **/
	public boolean forStatementEnclosedStatements() {
	    // Note: This is handled by overriding setEnclosingStatement.
		return true;
	}

    /*
     * Helper Methods
     */

	/**
	 * In addition to an @isolated annotation, a for statement may have a @parallel
	 * annotation.
	 **/
	@Override
	public Boolean annotationAllowed(Annotation annotation) {
		return super.annotationAllowed(annotation) || 
		                annotation.getIdentifier().equals("parallel");
	} // annotationAllowed

    @Override
    protected Statement getLoopStatement() {
        return this.getSelf();
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        ForStatement self = this.getSelf();
        for (LoopVariableDefinition variableDefinition: self.getVariableDefinition()) {
            variableDefinition.getImpl().setCurrentScope(currentScope);
        }
        Block body = self.getBody();
        if (body != null) {
            body.getImpl().setCurrentScope(currentScope);
        }
    }
    
    private Annotation getParallelAnnotation() {
        ForStatement self = this.getSelf();
        if (self.getIsParallel()) {
            for (Annotation annotation: self.getAnnotation()) {
                if (annotation.getIdentifier().equals("parallel")) {
                    return annotation;
                }
            }
        }
        return null;
    }
    
    private Collection<String> getParallelNames() {
        Annotation annotation = this.getParallelAnnotation();
        return annotation == null? new ArrayList<String>():
            annotation.getArgument();
    }
    
    // Note: Requires that assignments have already been computed.
    private Map<String, AssignedSource> getAssignmentAfterVariables() {
        ForStatement self = this.getSelf();
        List<LoopVariableDefinition> variableDefinitions = 
            self.getVariableDefinition();
        if (variableDefinitions.isEmpty()) {
            return new HashMap<String, AssignedSource>();
        } else {
            return variableDefinitions.get(variableDefinitions.size()-1).
                        getImpl().getAssignmentAfterMap();
        }
    }

} // ForStatementImpl
