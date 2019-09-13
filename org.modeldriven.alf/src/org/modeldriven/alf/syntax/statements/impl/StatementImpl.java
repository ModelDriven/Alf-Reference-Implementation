/*******************************************************************************
 * Copyright 2011, 2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A model of an Alf statement.
 **/

public abstract class StatementImpl extends DocumentedElementImpl {

	private Collection<Annotation> annotation = new ArrayList<Annotation>();
	private Map<String, AssignedSource> assignmentBefore = null; // DERIVED
	private Map<String, AssignedSource> assignmentAfter = null; // DERIVED
	private Statement enclosingStatement = null; // DERIVED
	private Boolean isIsolated = null; // DERIVED
	private Boolean isIndexFrom0 = null; // DERIVED

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
	    return this.getAssignmentBeforeMap().get(name);
	}

	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
	    if (assignmentBefore == null) {
	        this.assignmentBefore = null;
	    } else {
            if (this.assignmentBefore == null) {
                this.assignmentBefore = new HashMap<String, AssignedSource>();
            } else {
                this.assignmentBefore.clear();
            }
    		for (AssignedSource assignment: assignmentBefore) {
    		    this.addAssignmentBefore(assignment);
    		}
	    }
        
        // Force recomputation of assignments after if assignments before change.
		this.assignmentAfter = null;
	}
	
	public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
	    this.assignmentBefore = assignmentBefore;
        
        // Force recomputation of assignments after if assignments before change.
        this.assignmentAfter = null;
	}

	public void addAssignmentBefore(AssignedSource assignmentBefore) {
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
        return this.getAssignmentAfterMap().get(name);
    }

	public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
	    if (assignmentAfter == null) {
	        this.assignmentAfter = null;
	    } else {
            if (this.assignmentAfter == null) {
                this.assignmentAfter = new HashMap<String, AssignedSource>();
            } else {
                this.assignmentAfter.clear();
            }
            for (AssignedSource assignment: assignmentAfter) {
                this.addAssignmentAfter(assignment);
            }
	    }
	}

    public void setAssignmentAfter(Map<String, AssignedSource> assignmentAfter) {
        this.assignmentAfter = assignmentAfter;
    }

	public void addAssignmentAfter(AssignedSource assignmentAfter) {
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

    public Boolean getIsIndexFrom0() {
        if (this.isIndexFrom0 == null) {
            this.setIsIndexFrom0(this.deriveIsIndexFrom0());
        }
        return this.isIndexFrom0;
    }
    
    public void setIsIndexFrom0(Boolean isIndexFrom0) {
        this.isIndexFrom0 = isIndexFrom0;
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

    /**
     * A statement has indexing from 0 if it has an @indexFrom0 annotation, or
     * it is contained in a statement with indexing from 0 and it does not have
     * an @indexFrom1 annotation applied.
     */
    protected Boolean deriveIsIndexFrom0() {
        if (this.hasAnnotation("indexFrom0")) {
            return true;
        } else if (this.hasAnnotation("indexFrom1")) {
            return false;
        } else {
            Statement enclosingStatement = this.getSelf().getEnclosingStatement();
            return enclosingStatement != null && 
                   enclosingStatement.getIsIndexFrom0();
        }
    }
    
	/*
	 * Derivations
	 */
	
    public boolean statementIsIsolatedDerivation() {
        this.getSelf().getIsIsolated();
        return true;
    }
    
    public boolean statementIsIndexFrom0Derivation() {
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
		return (annotation.getIdentifier().equals("isolated") || 
		        annotation.getIdentifier().equals("indexFrom0") ||
		        annotation.getIdentifier().equals("indexFrom1")) && 
		            annotation.getArgument().size() == 0;
	} // annotationAllowed
	
    /**
     * Returns true if this statement is assured to generate a return value. By
     * default, a statement does not have a return value.
     */
    public Boolean hasReturnValue() {
        return false;
    }

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

    public ElementReference resolve(String name) {
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
    protected Map<String, AssignedSource> mergeAssignments(
            Collection<Block> blocks) {
        Map<String, AssignedSource> mergedAssignments = new HashMap<String, AssignedSource>();
        Set<String> names = new HashSet<String>();
        
        for (Block block: blocks) {
            names.addAll(block.getImpl().getAssignmentAfterMap().keySet());
        }
        
        // Merge the types and multiplicities of assignments to the same local name.
        for (String name: names) {
            int low = -1;
            int high = 0;
            Set<ElementReference> types = new HashSet<ElementReference>();
            for (Block block: blocks) {
                AssignedSource assignment = block.getImpl().getAssignmentAfter(name);
                if (assignment == null) {
                    low = 0;
                } else {
                    int lower = assignment.getLower();
                    int upper = assignment.getUpper();
                    low = low == -1? lower: 
                          lower == -1? low:
                          lower < low? lower:
                          low;
                    high = high == -1 || upper == -1? -1: 
                           upper > high? upper:
                           high;
                    types.add(assignment.getType());
                }
            }
            mergedAssignments.put(name, AssignedSourceImpl.makeAssignment(name,
                    this.getSelf(),
                    ClassifierDefinitionImpl.commonAncestor(types),
                    low, high));
        }
        
        return mergedAssignments;
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
    }
    
    // Note: This is redefined in loop statements (for, while, do) to return 
    // themselves.
    protected Statement getLoopStatement() {
        Statement enclosingStatement = this.getSelf().getEnclosingStatement();
        return enclosingStatement == null? null:
                    enclosingStatement.getImpl().getLoopStatement();
    }
    
    // Note: These are redefined in for statements to record if they contain
    // a return statement.
    protected void setContainsReturn() { 
    }
    protected boolean containsReturn() {
        return false;
    }
    
    // Note: This is redefined to set the enclosing block in the expression of
    // an expression statement.
    public void setEnclosingBlock(Block enclosingBlock) {
    }
    
    // Note: This is redefined to test for a super constructor invocation in an
    // expression statement.
    public boolean isSuperConstructorInvocation() {
        return false;
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof Statement) {
            this.getSelf().setAnnotation(((Statement)base).getAnnotation());
        }
    }
    
} // StatementImpl
