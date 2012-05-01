
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The left-hand side of an assignment expression.
 *
 * NOTE: The derivations for the derived properties of LeftHandSide are specific
 * to its various subclasses.
 **/

public abstract class LeftHandSideImpl extends AssignableElementImpl {

	private Map<String, AssignedSource> assignmentBefore = null; // DERIVED
	private Map<String, AssignedSource> assignmentAfter = null; // DERIVED
	private Expression index = null;
	
	private Boolean isDataValueUpdate = null;
	private String assignedName = null;
	private NamespaceDefinition currentScope = null;

	public LeftHandSideImpl(LeftHandSide self) {
		super(self);
	}

	@Override
	public LeftHandSide getSelf() {
		return (LeftHandSide) this.self;
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
        if (this.assignmentBefore == null) {
            this.assignmentBefore = new HashMap<String, AssignedSource>();
        } else {
            this.assignmentBefore.clear();
        }
        for (AssignedSource assignment: assignmentBefore) {
            this.addAssignmentBefore(assignment);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
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
        return this.getAssignmentBeforeMap().get(name);
    }

    public void setAssignmentAfter(Collection<AssignedSource> assignmentAfter) {
        if (this.assignmentBefore == null) {
            this.assignmentBefore = new HashMap<String, AssignedSource>();
        } else {
            this.assignmentBefore.clear();
        }
        for (AssignedSource assignment: assignmentAfter) {
            this.addAssignmentAfter(assignment);
        }
    }

    public void setAssignmentAfter(Map<String, AssignedSource> assignmentAfter) {
        this.assignmentAfter = assignmentAfter;
    }

    public void addAssignmentAfter(AssignedSource assignmentAfter) {
        this.assignmentAfter.put(assignmentAfter.getName(), assignmentAfter);
    }

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

    /**
     * The assignments before are usually set externally.
     */
    protected Map<String, AssignedSource> deriveAssignmentBefore() {
        return new HashMap<String, AssignedSource>();
    }

	protected abstract Map<String, AssignedSource> deriveAssignmentAfter();
	
	/*
	 * Constraints
	 */

	/**
	 * If a left-hand side has an index, then the index expression must have a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean leftHandSideIndexExpression() {
	    Expression index = this.getSelf().getIndex();
		return index == null || index.getUpper() <= 1 &&
		    // NOTE: This checks that a feature with an index is ordered, non-unique..
		    // It needs to be added to the spec as a separate constraint.
		    (this.getFeature() == null || 
		            this.getReferent().getImpl().isOrdered() && 
		            !this.getReferent().getImpl().isUnique());
	}
	
	/*
	 * Helper Methods
	 */

    /**
     * The effective expression is the left-hand side treated as a name 
     * expression, property access expression or sequence access expression, 
     * as appropriate for evaluation to obtain the original value of the
     * left-hand side.
     **/
    public Expression getExpression() {
        LeftHandSide self = this.getSelf();
        Expression index = self.getIndex();
        Expression expression = this.getPrimaryExpression();
        expression.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
        
        // NOTE: Assignments before the index expression will have been set
        // previously.
        if (index != null) {
            SequenceAccessExpression sequenceAccess = new SequenceAccessExpression();
            sequenceAccess.setPrimary(expression);
            sequenceAccess.setIndex(index);
            expression = sequenceAccess;
        }
        
        expression.getImpl().setCurrentScope(this.currentScope);
        return expression;
    }
    
    /**
     * The primary expression is, by default, the left-hand side treated as a 
     * property access expression. (This is overridden for a name left-hand side
     * that has a name without a disambiguation.)
     **/
    public Expression getPrimaryExpression() {
        PropertyAccessExpression propertyAccess = new PropertyAccessExpression();
        propertyAccess.setFeatureReference(this.getFeature());
        return propertyAccess;
    }


    public abstract FeatureReference getFeature();
    
    /**
     * An assignment expression is a data value update if its left hand side is
     * an attribute of a data value held in a local name or parameter.
     **/
    public Boolean isDataValueUpdate() {
        if (this.isDataValueUpdate == null) {
            this.getAssignedName();
        }
        return this.isDataValueUpdate;
    }
    
    public String getAssignedName() {
        if (this.isDataValueUpdate == null) {
            this.assignedName = this.getLocalName();
            this.isDataValueUpdate = false;
            if (this.assignedName == null) {
                FeatureReference feature = this.getFeature();
                if (feature != null) {
                    Expression expression = feature.getExpression();
                    if (expression instanceof NameExpression) {
                        if (((NameExpression)expression).getPropertyAccess() == null) {
                            QualifiedName name = 
                                ((NameExpression)expression).getName();
                            Map<String, AssignedSource> assignmentsBefore = 
                                    this.getAssignmentBeforeMap();
                            String unqualifiedName = name == null? null: 
                                name.getUnqualifiedName().getName();
                            AssignedSource assignment = 
                                unqualifiedName == null || 
                                    assignmentsBefore == null? null:
                                assignmentsBefore.get(unqualifiedName);                            
                            if (assignment != null && 
                                    assignment.getType().getImpl().isDataType()) {
                                this.assignedName = unqualifiedName;
                                this.isDataValueUpdate = true;
                            }
                        }
                   }
                }
            }
        }
        return this.assignedName;
    }
    
    public abstract String getLocalName();
    
    @Override
    public ElementReference getType() {
        ElementReference referent = this.getReferent();
        return referent == null? null: referent.getImpl().getType();
    }

    @Override
    public Integer getLower() {
        if (this.getSelf().getIndex() != null) {
            return 1;
        } else {
            ElementReference referent = this.getReferent();
            return referent == null? 0: referent.getImpl().getLower();
        }
    }

    @Override
    public Integer getUpper() {
        if (this.getSelf().getIndex() != null) {
            return 1;
        } else {
            ElementReference referent = this.getReferent();
            return referent == null? 0: referent.getImpl().getUpper();
        }
    }
    
    public abstract ElementReference getReferent();
    
    public void setCurrentScope(NamespaceDefinition currentScope) {
        this.currentScope = currentScope;
        Expression index = this.getSelf().getIndex();
        if (index != null) {
            index.getImpl().setCurrentScope(currentScope);
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof LeftHandSide) {
            Expression index = 
                ((LeftHandSide)base).getIndex();
            if (index != null) {
                this.getSelf().setIndex((Expression)index.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // LeftHandSideImpl
