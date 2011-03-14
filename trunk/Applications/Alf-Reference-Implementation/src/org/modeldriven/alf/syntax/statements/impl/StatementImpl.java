
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A model of an Alf statement.
 **/

public abstract class StatementImpl extends
		org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl {

	private Collection<Annotation> annotation = new ArrayList<Annotation>();
	private Map<String, AssignedSource> assignmentBefore = null; // DERIVED
	private Map<String, AssignedSource> assignmentAfter = null; // DERIVED
	private Statement enclosingStatement = null; // DERIVED
	private Boolean isIsolated = null; // DERIVED

	public StatementImpl(Statement self) {
		super(self);
	}

	public Statement getSelf() {
		return (Statement) this.self;
	}

	public Collection<Annotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(Collection<Annotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(Annotation annotation) {
		this.annotation.add(annotation);
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

	public Statement getEnclosingStatement() {
		if (this.enclosingStatement == null) {
			this.setEnclosingStatement(this.deriveEnclosingStatement());
		}
		return this.enclosingStatement;
	}

	public void setEnclosingStatement(Statement enclosingStatement) {
		this.enclosingStatement = enclosingStatement;
	}

	public Boolean getIsIsolated() {
		if (this.isIsolated == null) {
			this.setIsIsolated(this.deriveIsIsolated());
		}
		return this.isIsolated;
	}

	public void setIsIsolated(Boolean isIsolated) {
		this.isIsolated = isIsolated;
	}

    /**
     * The assignments before are usually set externally.
     */
	protected Map<String, AssignedSource> deriveAssignmentBefore() {
		return new HashMap<String, AssignedSource>();
	}

	/**
	 * By default, the assignments after are the same as the assignments before.
	 */
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
		return this.assignmentBefore;
	}

	/**
	 * The enclosing statement is always set externally.
	 */
	protected Statement deriveEnclosingStatement() {
		return null;
	}

	/**
	 * A statement is isolated if it has an @isolated annotation.
	 **/
	protected Boolean deriveIsIsolated() {
		return this.hasAnnotation("isolated");
	}

	/*
	 * Derivations
	 */
	
	public boolean statementIsIsolatedDerivation() {
		this.getSelf().getIsIsolated();
		return true;
	}
	
	/*
	 * Constraints
	 */
	
	/**
	 * All the annotations of a statement must be allowed, as given by the
	 * annotationAllowed operation for the statement.
	 **/
	public boolean statementAnnotationsAllowed() {
	    for (Annotation annotation: this.getSelf().getAnnotation()) {
	        if (!this.annotationAllowed(annotation)) {
	            return false;
	        }
	    }
		return true;
	}

	/**
	 * No name may be assigned more than once before or after a statement.
	 **/
	public boolean statementUniqueAssignments() {
	    Statement self = this.getSelf();
	    return uniqueAssignments(self.getAssignmentBefore()) &&
	                uniqueAssignments(self.getAssignmentAfter());
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * Returns true if the given annotation is allowed for this kind of
	 * statement. By default, only an @isolated annotation is allowed, with no
	 * arguments. This operation is redefined only in subclasses of Statement
	 * for kinds of statements that allow different annotations than this
	 * default.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return annotation.getIdentifier().equals("isolated") && 
		            annotation.getArgument().size() == 0;
	} // annotationAllowed
	
    public boolean hasAnnotation(String name) {
        for (Annotation annotation: this.getSelf().getAnnotation()) {
            if (annotation.getIdentifier().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean uniqueAssignments(Collection<AssignedSource> assignments) {
        return new HashSet<AssignedSource>(assignments).size() == assignments.size();
    }

    public SyntaxElement resolve(String name) {
        AssignedSource assignment = this.getAssignmentBefore(name);
        return assignment == null? null: assignment.getSource();
    }
    
    /**
     * Get the assigned sources for assignments made within this statement.
     */
    public Collection<AssignedSource> getNewAssignments() {
        return AssignedSourceImpl.selectNewAssignments(
                this.getAssignmentBeforeMap(), 
                this.getSelf().getAssignmentAfter());
    }
    
    /**
     * Merge the assignments made in a collection of blocks, such as occur in an
     * accept or if statement.
     */
    protected Map<String, AssignedSource> mergeAssignments(Collection<Block> blocks) {
        Map<String, AssignedSource> mergedAssignments = new HashMap<String, AssignedSource>();
        Map<String, Collection<AssignedSource>> assignmentMap = new HashMap<String, Collection<AssignedSource>>();
        
        // Collect assignments made in each block and group by local name.
        for (Block block: blocks) {
            Map<String, AssignedSource> assignmentsBefore = block.getImpl().getAssignmentBeforeMap();
            for (AssignedSource assignment: block.getAssignmentAfter()) {
                String name = assignment.getName();
                AssignedSource oldAssignment = assignmentsBefore.get(name);
                if (oldAssignment == null || 
                        oldAssignment.getSource() != assignment.getSource()) {
                    Collection<AssignedSource> assignments = assignmentMap.get(name);
                    if (assignments == null) {
                        assignments = new ArrayList<AssignedSource>();
                        assignmentMap.put(name, assignments);
                    }
                    assignments.add(assignment);
                }
            }
        }
        
        // Merge the types and multiplicities of assignments to the same local name.
        for (String name: assignmentMap.keySet()) {
            Collection<AssignedSource> assignments = assignmentMap.get(name);
            int lower = -1;
            int upper = 0;
            Set<ElementReference> types = new HashSet<ElementReference>();
            for (AssignedSource assignment: assignments) {
                lower = lower == -1? assignment.getLower(): 
                    Math.min(lower, assignment.getLower());
                upper = upper == -1? -1: 
                    Math.max(upper, assignment.getUpper());
                types.add(assignment.getType());
            }
            mergedAssignments.put(name, AssignedSourceImpl.makeAssignment(name,
                    this.getSelf(),
                    ClassifierDefinitionImpl.commonAncestor(types),
                    lower, upper));
        }
        
        return mergedAssignments;
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
    }
    
    // This is redefined in loop statements (for, while, do) to return themselves.
    protected Statement getLoopStatement() {
        Statement enclosingStatement = this.getSelf().getEnclosingStatement();
        return enclosingStatement == null? null:
                    enclosingStatement.getImpl().getLoopStatement();
    }

} // StatementImpl
