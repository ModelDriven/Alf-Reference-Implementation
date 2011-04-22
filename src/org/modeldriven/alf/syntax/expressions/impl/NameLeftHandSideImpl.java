
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
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

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
	    Expression index = self.getIndex();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    if (index != null) {
	        index.getImpl().setAssignmentBefore(assignments);
	        assignments = index.getImpl().getAssignmentAfterMap();
	    }
	    return assignments;
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
	        // Note: The constraint below needs to be added to the spec.
	        return this.getSelf().getTarget().getQualification() == null;
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
	    return self.getIndex() == null || this.getReferent() != null;
	}
	
	/*
	 * Helper Methods
	 */
    
	@Override
    protected ElementReference getReferent() {
        NameLeftHandSide self = this.getSelf();
        QualifiedName target = self.getTarget();
        if (target == null) {
            return null;
        } else if (target.getQualification() != null) {
            if (target.getIsFeatureReference()) {
                return target.getDisambiguation().getImpl().getStructuralFeatureReferent();
            } else {
                ElementReference parameter = target.getImpl().getParameterReferent();
                // Note: The check on the namespace of a parameter needs to be in the spec.               
                return parameter == null ||
                        !parameter.getImpl().isInNamespace(this.currentScope)? 
                                null: parameter;
            }
        } else {
            AssignedSource assignment = 
                self.getImpl().getAssignmentBefore(this.getLocalName());
            if (assignment == null) {
                return null;
            } else {
                InternalElementReference referent = new InternalElementReference();
                referent.setElement(assignment.getSource());
                return referent;
            }
        }
    }

    /**
     * The effective expression is the name left-hand side treated as a name 
     * expression.
     */
    protected Expression getPrimaryExpression() {
        QualifiedName target = this.getSelf().getTarget();
        if (target == null) {
            return null;
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

    @Override
    public String getLocalName() {
        if (this.getFeature() != null) {
            return null;
        } else {
            QualifiedName target = this.getSelf().getTarget();
            return target == null? null: target.getUnqualifiedName().getName();
        }
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

} // NameLeftHandSideImpl
