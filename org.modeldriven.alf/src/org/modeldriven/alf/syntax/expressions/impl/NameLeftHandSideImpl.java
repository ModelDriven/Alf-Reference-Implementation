/*******************************************************************************
 * Copyright 2011, 2017, 2020 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
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
	
    /**
     * If the target of a name left-hand side disambiguates to a structural
     * feature, then the referent of the left-hand side is that feature. If the
     * target resolves to a parameter, then the referent is that parameter. If
     * the target resolves to a local name, then the referent is the assigned
     * source for that local name, if it has one.
     **/
    @Override
    public ElementReference deriveReferent() {
        FeatureReference feature = this.getFeature();
        ElementReference parameter = this.getParameter();
        AssignedSource oldAssignment = this.getOldAssignment();
        if (feature != null) {
            return feature.getImpl().getStructuralFeatureReferent();
        } else if (oldAssignment != null) {
            return oldAssignment.getSource();
        } else if (parameter != null) {
            return parameter;
        } else {
            return null;
        }
    }

    /**
     * If a name left-hand side is for a local name with an assignment, then its
     * type is that of that assignment. Otherwise, if the left-hand side has a
     * referent, then its type is the type of that referent.
     **/
	@Override
	public ElementReference deriveType() {
        AssignedSource oldAssignment = this.getOldAssignment();
        return oldAssignment == null? super.deriveType(): oldAssignment.getType();
	    
	}
	
    /**
     * If a name left-hand side is indexed, then its lower bound is 0.
     * Otherwise, if the left-hand side is for a local name with an assignment,
     * than its lower bound is that of the assignment, else, if it has a
     * referent, then its lower bound is that of the referent.
     **/
	@Override
	public Integer deriveLower() {
	    ElementReference parameter = this.getParameter();
	    AssignedSource oldAssignment = this.getOldAssignment();
	    return this.getSelf().getIndex() != null? 0:
	           parameter != null? parameter.getImpl().getLower():
	           oldAssignment != null? oldAssignment.getLower():
	           super.deriveLower();
	}
	
    /**
     * If a name left-hand side is indexed, then its upper bound is 1.
     * Otherwise, if the left-hand side is for a local name with an assignment,
     * than its upper bound is that of the assignment, else, if it has a
     * referent, then its upper bound is that of the referent.
     **/
    @Override
    public Integer deriveUpper() {
        ElementReference parameter = this.getParameter();
        AssignedSource oldAssignment = this.getOldAssignment();
        return this.getSelf().getIndex() != null? 1:
               parameter != null? parameter.getImpl().getUpper():
               oldAssignment != null? oldAssignment.getUpper():
               super.deriveUpper();
    }
    
	/*
	 * Derivations
	 */
	
	public boolean nameLeftHandSideAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}
	
    public boolean nameLeftHandSideReferentDerivation() {
        this.getSelf().getReferent();
        return true;
    }

    public boolean nameLeftHandSideLowerDerivation() {
        this.getSelf().getLower();
        return true;
    }

    public boolean nameLeftHandSideUpperDerivation() {
        this.getSelf().getUpper();
        return true;
    }

    public boolean nameLeftHandSideTypeDerivation() {
        this.getSelf().getType();
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
	        return !"in".equals(referent.getImpl().getDirection());
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
	
    /**
     * If the target of a name left-hand side is qualified, then, if it does not
     * disambiguate to a feature, it must have a referent that is a parameter of
     * an operation or behavior that is the current scope the left-hand is in,
     * and, if it does disambiguate to a feature, it must have a single referent
     * that is a structural feature.
     **/
    public boolean nameLeftHandSideTargetResolution() {
        NameLeftHandSide self = this.getSelf();
        FeatureReference feature = this.getFeature();
        Expression target = feature == null? null: feature.getExpression();
        ElementReference targetType = target == null? null: target.getType();
        return self.getTarget().getQualification() == null ||
                feature != null && targetType == null ||
                self.getReferent() != null;
    }

    /**
     * If the target of a name left-hand side disambiguates to a feature
     * reference, and the left-hand side has an index, then the referent of the
     * feature reference must be ordered and non-unique.
     **/
    public boolean nameLeftHandSideIndexedFeature() {
        NameLeftHandSide self = this.getSelf();
        if (self.getIndex() == null) {
            return true;
        } else {
            ElementReference referent = self.getReferent();
            return referent == null || !referent.getImpl().isFeature() ||
                referent.getImpl().isOrdered() && !referent.getImpl().isUnique();
        }
    }

    /**
     * The target of a name left-hand side must not have a template binding.
     **/
    public boolean nameLeftHandSideNontemplateTarget() {
        QualifiedName target = this.getSelf().getTarget();
        return target == null || target.getImpl().getTemplateName() == null;
    }

    /**
     * If the target of a name left-hand side disambiguates to a feature
     * reference, then the expression of the feature reference must have a
     * multiplicity upper bound of 1.
     */
    public boolean nameLeftHandSideFeatureExpression() {
        FeatureReference feature = this.getFeature();
        Expression expression = feature == null? null: feature.getExpression();
        ElementReference targetType = expression == null? null: expression.getType();
        return targetType == null || expression.getUpper() == 1;
    }

	/*
	 * Helper Methods
	 */
    
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
        // TODO: The check on the namespace of a parameter needs to be in the spec.               
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
    
    @Override
    public boolean isNullable() {
        return this.getSelf().getIndex() == null &&
               this.getLocalName() != null && 
               this.getParameter() == null;
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
