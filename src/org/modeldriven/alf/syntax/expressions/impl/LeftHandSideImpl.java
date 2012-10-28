
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
    private ElementReference referent = null; // DERIVED
    private ElementReference type = null; // DERIVED
    private Integer lower = null; // DERIVED
    private Integer upper = null; // DERIVED
	
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

    public ElementReference getReferent() {
        if (this.referent == null) {
            this.setReferent(this.deriveReferent());
        }
        return this.referent;
    }

    public void setReferent(ElementReference referent) {
        this.referent = referent;
    }

    public ElementReference getType() {
        if (this.type == null) {
            this.setType(this.deriveType());
        }
        return this.type;
    }

    public void setType(ElementReference type) {
        this.type = type;
    }

    public Integer getLower() {
        if (this.lower == null) {
            this.setLower(this.deriveLower());
        }
        return this.lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public Integer getUpper() {
        if (this.upper == null) {
            this.setUpper(this.deriveUpper());
        }
        return this.upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }

    /**
     * The assignments before are usually set externally.
     */
    protected Map<String, AssignedSource> deriveAssignmentBefore() {
        return new HashMap<String, AssignedSource>();
    }

	protected abstract Map<String, AssignedSource> deriveAssignmentAfter();
	
    protected abstract ElementReference deriveReferent();

    /**
     * The type of a feature left-hand side is the type of its referent.
     **/
    // NOTE: This is overridden for a name left-hand side.
    protected ElementReference deriveType() {
        ElementReference referent = this.getSelf().getReferent();
        return referent == null? null: referent.getImpl().getType();
    }

   /**
    * If a feature left-hand side is indexed, then its lower bound is 1.
    * Otherwise, its lower bound is that of its referent.
    **/
   // NOTE: This is overridden for a name left-hand side.
   protected Integer deriveLower() {
        LeftHandSide self = this.getSelf();
        if (self.getIndex() != null) {
            return 1;
        } else {
            ElementReference referent = self.getReferent();
            return referent == null? 0: referent.getImpl().getLower();
        }
    }

    /**
     * If a feature left-hand side is indexed, then its upper bound is 1.
     * Otherwise, its upper bound is that of its referent.
     **/
    // NOTE: This is overridden for a name left-hand side.
    protected Integer deriveUpper() {
        LeftHandSide self = this.getSelf();
        if (self.getIndex() != null) {
            return 1;
        } else {
            ElementReference referent = self.getReferent();
            return referent == null? 0: referent.getImpl().getUpper();
        }
    }
    
	/*
	 * Constraints
	 */

	/**
	 * If a left-hand side has an index, then the index expression must have a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean leftHandSideIndexExpression() {
	    Expression index = this.getSelf().getIndex();
		return index == null || index.getUpper() <= 1;
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
                            if (assignment != null && assignment.getType() != null &&
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
