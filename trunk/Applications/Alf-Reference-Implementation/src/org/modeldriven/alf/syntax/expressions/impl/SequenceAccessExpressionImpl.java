
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression used to access a specific element of a sequence.
 **/

public class SequenceAccessExpressionImpl extends ExpressionImpl {

	private Expression primary = null;
	private Expression index = null;

	public SequenceAccessExpressionImpl(SequenceAccessExpression self) {
		super(self);
	}

	@Override
	public SequenceAccessExpression getSelf() {
		return (SequenceAccessExpression) this.self;
	}

	public Expression getPrimary() {
		return this.primary;
	}

	public void setPrimary(Expression primary) {
		this.primary = primary;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
	}

	/**
	 * The type of a sequence access expression is the same as the type of its
	 * primary expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    Expression primary = this.getSelf().getPrimary();
	    return primary == null? null: primary.getType();
	}
	
	/**
	 * The multiplicity lower bound of a sequence access expression is 0.
	 **/
	@Override
	protected Integer deriveLower() {
	    return 0;
	}
	
	/**
	 * The multiplicity upper bound of a sequence access expression is 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
	
	/*
	 * Derivations
	 */
	
	public boolean sequenceAccessExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean sequenceAccessExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean sequenceAccessExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The type of the index of a sequence access expression must be Integer.
	 **/
	public boolean sequenceAccessExpressionIndexType() {
	    Expression index = this.getSelf().getIndex();
	    ElementReference type = index == null? null: index.getType();
		return type != null && type.getImpl().isInteger();
	}

	/**
	 * The multiplicity upper bound of the index of a sequence access expression
	 * must be 1.
	 **/
	public boolean sequenceAccessExpressionIndexMultiplicity() {
        Expression index = this.getSelf().getIndex();
        return index != null && index.getUpper() == 1;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
	    SequenceAccessExpression self = this.getSelf();
	    Expression primary = self.getPrimary();
	    Expression index = self.getIndex();
	    Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    Map<String, AssignedSource> assignmentsAfter = assignmentsBefore;
	    Collection<AssignedSource> newAssignments = new HashSet<AssignedSource>();
	    if (primary != null) {
	        primary.getImpl().setAssignmentBefore(assignmentsBefore);
	        newAssignments.addAll(primary.getImpl().getNewAssignments());
	    }
	    if (index != null) {
	        index.getImpl().setAssignmentBefore(assignmentsBefore);
	        newAssignments.addAll(index.getImpl().getNewAssignments());
	    }
	    if (!newAssignments.isEmpty()) {
	        assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsAfter);
	        for (AssignedSource assignment: newAssignments) {
	            assignmentsAfter.put(assignment.getName(), assignment);
	        }
	    }
	    return assignmentsAfter;
	}
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    SequenceAccessExpression self = this.getSelf();
	    Expression primary = self.getPrimary();
	    Expression index = self.getIndex();
	    if (primary != null) {
	        primary.getImpl().setCurrentScope(currentScope);
	    }
	    if (index != null) {
	        index.getImpl().setCurrentScope(currentScope);
	    }
	}
	
	/**
	 * Returns the behavior invocation expression for the standard library At
	 * behavior that is equivalent to this sequence access expression.
	 */
	public BehaviorInvocationExpression getInvocation() {
	    SequenceAccessExpression self = this.getSelf();
	    
	    BehaviorInvocationExpression invocation = new BehaviorInvocationExpression();
	    invocation.setTarget
	        (RootNamespace.getSequenceFunctions().getImpl().copy().addName("At"));
	    
	    PositionalTuple tuple = new PositionalTuple();
	    tuple.setInvocation(invocation);
	    tuple.addExpression(self.getPrimary());
	    tuple.addExpression(self.getIndex());
	    invocation.setTuple(tuple);
	    
	    return invocation;
	}

} // SequenceAccessExpressionImpl
