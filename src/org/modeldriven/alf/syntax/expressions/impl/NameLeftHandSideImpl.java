
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import java.util.List;
import java.util.Map;

/**
 * A left-hand side that is a name.
 **/

public class NameLeftHandSideImpl extends LeftHandSideImpl {

	private QualifiedName target = null;	
	private NamespaceDefinition currentScope = null;

	public NameLeftHandSideImpl(NameLeftHandSide self) {
		super(self);
	}

	@Override
	public NameLeftHandSide getSelf() {
		return (NameLeftHandSide) this.self;
	}
	
	@Override 
	public String toString(boolean includeDerived) {
	    return super.toString(includeDerived) + " target:" + target.getPathName();
	}

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
	}
	

	/**
	 * The assignments after a name left-hand side are the same as the
	 * assignments before or, if there is an index, those after the index 
	 * expression.
	 **/
	@Override
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
	    NameLeftHandSide self = this.getSelf();
	    
	    // Note: If the name disambiguates to a feature reference, then the
	    // left hand side needs to be treated essentially as if it were a
	    // feature left hand side.
        FeatureReference feature = this.getFeature();
        Expression index = self.getIndex();
        Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
        if (feature != null) {
            Expression expression = feature.getExpression();
            if (expression != null) {
                expression.getImpl().setAssignmentBefore(assignments);
                assignments = expression.getImpl().getAssignmentAfterMap();
            }
        }
        
        if (index != null) {
            index.getImpl().setAssignmentBefore(assignments);
            assignments = index.getImpl().getAssignmentAfterMap();
        }
        return assignments;
	}
	
	@Override
	public ElementReference getType() {
        AssignedSource oldAssignment = this.getOldAssignment();
        return oldAssignment == null? super.getType(): oldAssignment.getType();
	    
	}
	
	@Override
	public Integer getLower() {
	    AssignedSource oldAssignment = this.getOldAssignment();
	    return this.getSelf().getIndex() != null || oldAssignment == null? 
	            super.getLower(): oldAssignment.getLower();
	}
	
    @Override
    public Integer getUpper() {
        AssignedSource oldAssignment = this.getOldAssignment();
        return this.getSelf().getIndex() != null || oldAssignment == null? 
                super.getUpper(): oldAssignment.getUpper();
    }
    
	/*
	 * Derivations
	 */
	
	public boolean nameLeftHandSideAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The target of a name left hand side may not already have an assigned
	 * source that is a loop variable definition, an annotation, a sequence
	 * expansion expression or a parameter that is an in parameter.
	 **/
	public boolean nameLeftHandSideTargetAssignment() {
	    ElementReference referent = this.getReferent();
	    if (referent == null) {
	        return true;
	    } else if (referent.getImpl().isParameter()) {
	        return !"in".equals(referent.getImpl().asParameter().getDirection());
	    } else if (referent.getImpl().isProperty()) {
            // Note: This constraint ensures that there will be an
            // assigned name for an assignment to an attribute of a
            // data type.
	        FeatureReference feature = this.getFeature();
	        Expression expression = feature == null? null: feature.getExpression();
	        return expression != null &&
                    (!expression.getType().getImpl().isDataType() ||
                            this.isDataValueUpdate());
	    } else {
	        SyntaxElement source = referent.getImpl().getAlf();
            return !(source instanceof LoopVariableDefinition ||
                     source instanceof Annotation ||
                     source instanceof SequenceExpansionExpression);
	    }
	}

	/**
	 * If a name left-hand side has an index, then the target name must already
	 * have an assigned source and the assignments before the index expression
	 * are the assignments before the left-hand side.
	 **/
	public boolean nameLeftHandSideAssignmentsBefore() {
	    NameLeftHandSide self = this.getSelf();
	    return self.getIndex() == null || this.getFeature() != null || 
	        this.getOldAssignment() != null;
	}
	
	/*
	 * Helper Methods
	 */
    
	@Override
    public ElementReference getReferent() {
	    FeatureReference feature = this.getFeature();
	    ElementReference parameter = this.getParameter();
	    AssignedSource oldAssignment = this.getOldAssignment();
	    if (feature != null) {
	        return feature.getImpl().getStructuralFeatureReferent();
	    } else if (oldAssignment != null) {
	        InternalElementReference referent = new InternalElementReference();
	        referent.setElement(oldAssignment.getSource());
	        return referent;
	    } else if (parameter != null) {
	        return parameter;
	    } else {
	        return null;
	    }
    }

    /**
     * If the target does not have a disambiguation, the effective expression is
     * the name left-hand side treated as a name expression.
     */
    public Expression getPrimaryExpression() {
        QualifiedName target = this.getSelf().getTarget();
        if (target == null) {
            return null;
        } else if (target.getIsFeatureReference()) {
            return super.getPrimaryExpression();
        } else {
            NameExpression nameExpression = new NameExpression();
            nameExpression.setName(target);
            return nameExpression;
        }

    }

    @Override
    public FeatureReference getFeature() {
        QualifiedName target = this.getSelf().getTarget();
        return target == null? null: target.getDisambiguation();
    }
    
    public ElementReference getParameter() {
        QualifiedName target = this.getSelf().getTarget();
        ElementReference parameter = target == null? null: 
                                        target.getImpl().getParameterReferent();
        // Note: The check on the namespace of a parameter needs to be in the spec.               
        return parameter == null || 
                    !parameter.getImpl().isInNamespace(this.currentScope)? null: 
                        parameter;
    }

    @Override
    public String getLocalName() {
        if (this.getFeature() != null) {
            return null;
        } else {
            QualifiedName target = this.getSelf().getTarget();
            return (target == null || 
                    target.getQualification() != null && 
                    this.getParameter() == null ||
                    target.getUnqualifiedName().getBinding() != null)? null: 
                        target.getUnqualifiedName().getName();
        }
    }
    
    public AssignedSource getOldAssignment() {
        String localName = this.getLocalName();
        return localName == null? null: this.getAssignmentBefore(localName);
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        super.setCurrentScope(currentScope);
        QualifiedName target = this.getSelf().getTarget();
        if (target != null) {
            target.getImpl().setCurrentScope(currentScope);
        }
        this.currentScope = currentScope;
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NameLeftHandSide) {
           QualifiedName target = ((NameLeftHandSide)base).getTarget();
            if (target != null) {
                this.getSelf().setTarget(target.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }
    
} // NameLeftHandSideImpl
