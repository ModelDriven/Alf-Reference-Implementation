
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A list of expressions used to provide the arguments for an invocation.
 **/

public abstract class TupleImpl extends SyntaxElementImpl {

	private InvocationExpression invocation = null;
	
	// Inputs and outputs are listed in the same order as their corresponding
	// parameters.
    private List<NamedExpression> input = null; // DERIVED
	private List<OutputNamedExpression> output = null; // DERIVED
	
	private Map<String, AssignedSource> assignmentsBefore = new HashMap<String, AssignedSource>();
	private Map<String, AssignedSource> assignmentsAfter = null;
	
	private NamespaceDefinition currentScope = null;

	public TupleImpl(Tuple self) {
		super(self);
	}

	public Tuple getSelf() {
		return (Tuple) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    return super.toString(includeDerived) + " invocation:(" + this.getSelf().getInvocation() + ")";
	}

	public Collection<NamedExpression> getInput() {
		if (this.input == null) {
			this.setInput(this.deriveInput());
		}
		return this.input;
	}

	public void setInput(Collection<NamedExpression> input) {
	    this.input = new ArrayList<NamedExpression>();
		this.input.addAll(input);
	}

	public void addInput(NamedExpression input) {
		this.input.add(input);
	}

	public InvocationExpression getInvocation() {
		return this.invocation;
	}

	public void setInvocation(InvocationExpression invocation) {
		this.invocation = invocation;
	}

	public Collection<OutputNamedExpression> getOutput() {
		if (this.output == null) {
			this.setOutput(this.deriveOutput());
		}
		return this.output;
	}

	public void setOutput(Collection<OutputNamedExpression> output) {
	    this.output = new ArrayList<OutputNamedExpression>();
		this.output.addAll(output);
	}

	public void addOutput(OutputNamedExpression output) {
		this.output.add(output);
	}

	/**
	 * A tuple has the same number of inputs as its invocation has input
	 * parameters. For each input parameter, the tuple has a corresponding input
	 * with the same name as the parameter and an expression that is the
	 * matching argument from the tuple, or an empty sequence construction
	 * expression if there is no matching argument.
	 **/
	protected abstract Collection<NamedExpression> deriveInput();

	/**
	 * A tuple has the same number of outputs as its invocation has output
	 * parameters. For each output parameter, the tuple has a corresponding
	 * output with the same name as the parameter and an expression that is the
	 * matching argument from the tuple, or an empty sequence construction
	 * expression if there is no matching argument.
	 **/
	protected abstract Collection<OutputNamedExpression> deriveOutput();

	/*
	 * Derivations
	 */
	
	public boolean tupleInputDerivation() {
		this.getSelf().getInput();
		return true;
	}

	public boolean tupleOutputDerivation() {
		this.getSelf().getOutput();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * An input parameter may only have a null argument if it has a multiplicity
	 * lower bound of 0.
	 **/
	public boolean tupleNullInputs() {
	    Tuple self = this.getSelf();
	    InvocationExpression invocation = self.getInvocation();
	    if (invocation != null) {
    	    for (NamedExpression argument: self.getInput()){
    	        if (argument.getExpression().getImpl().isNull()) {
    	            if (invocation.getImpl().parameterNamed
    	                    (argument.getName()).getLower() > 0) { 
    	                return false;
    	            }
    	        }
    	    }
	    }
		return true;
	}

	/**
	 * An output parameter may only have a null argument if it is an out
	 * parameter.
	 **/
	public boolean tupleOutputs() {
        Tuple self = this.getSelf();
        InvocationExpression invocation = self.getInvocation();
        if (invocation != null) {
            for (NamedExpression argument: self.getOutput()){
                if (argument.getExpression().getImpl().isNull()) {
                    if (!"out".equals(invocation.getImpl().parameterNamed
                            (argument.getName()).getDirection())) { 
                        return false;
                    }
                }
            }
        }
        return true;
	}

	/**
	 * The assignments before each expression in a tuple are the same as the
	 * assignments before the tuple, except in the case of a name expression
	 * that defines a new local name, in which case the assigned source for the
	 * new name is included in the assignments before the name expression. (Note
	 * that the assigned source for a new name is included before the name
	 * expression so that the nameExpressionResolution constraint is not
	 * violated.) The assignments before the tuple are the same as the
	 * assignments after the feature reference of the invocation of the tuple,
	 * if the invocation has one, or otherwise the assignments before the
	 * invocation.
	 **/
	public boolean tupleAssignmentsBefore() {
	    // Note: This is handled by getAssignmentsAfterMap()
		return true;
	}

	/**
	 * A name may be assigned in at most one argument expression of a tuple.
	 **/
	public boolean tupleAssignmentsAfter() {
        Tuple self = this.getSelf();
        Collection<NamedExpression> inputs = self.getInput();
        Collection<OutputNamedExpression> outputs = self.getOutput();
        Set<Expression> expressions = new HashSet<Expression>();
        for (NamedExpression input: inputs) {
            expressions.add(input.getExpression());
            Expression index = input.getIndex();
            if (index != null) {
                expressions.add(index);
            }
        }
        for (NamedExpression output: outputs) {
            expressions.add(output.getExpression());
        }
        this.getAssignmentsAfterMap(); // Force computation of assignments.
        Set<AssignedSource> assignments = new HashSet<AssignedSource>();
        for (Expression expression: expressions) {
            Collection<AssignedSource> newAssignments = 
                expression.getImpl().getNewAssignments();
            for (AssignedSource newAssignment: newAssignments) {
                if (newAssignment.getImpl().isAssignedIn(assignments)) {
                    return false;
                }
            }
            assignments.addAll(newAssignments);
        }
        return true;
	}
	
	/*
	 * Helper Methods
	 */

    public abstract boolean isEmpty();
    
    public abstract int size();
    
    public Expression getInput(String name) {
        for (NamedExpression namedExpression: this.getSelf().getInput()) {
            if (namedExpression.getName().equals(name)) {
                return namedExpression.getExpression();
            }
        }
        return null;
    }
    
    public void setAssignmentsBefore(Map<String, AssignedSource> assignmentsBefore) {
        this.assignmentsBefore = assignmentsBefore;
    }
    
    public Map<String, AssignedSource> getAssignmentsBeforeMap() {
        return this.assignmentsBefore;
    }

    public Map<String, AssignedSource> getAssignmentsAfterMap() {
        Tuple self = this.getSelf();
        InvocationExpression invocation = self.getInvocation();
        if (invocation == null) {
            return new HashMap<String, AssignedSource>();
        } else {
            if (this.assignmentsAfter == null) {
                Map<String, AssignedSource> assignmentsBefore = 
                    this.getAssignmentsBeforeMap();
                Collection<NamedExpression> inputs = self.getInput();
                Collection<OutputNamedExpression> outputs = self.getOutput();
                Set<Expression> expressions = new HashSet<Expression>();
                for (NamedExpression input: inputs) {
                    expressions.add(input.getExpression());
                    Expression index = input.getIndex();
                    if (index != null) {
                        expressions.add(index);
                    }
                }
                Map<String, AssignedSource> newLocalAssignments = 
                    new HashMap<String, AssignedSource>();
                for (OutputNamedExpression output: outputs) {
                    Expression expression = output.getExpression();
                    expressions.add(expression);
                                        
                    LeftHandSide lhs = output.getLeftHandSide();
                    
                    if (lhs != null) {
                        lhs.getImpl().setAssignmentBefore(assignmentsBefore);
                        lhs.getImpl().setCurrentScope(this.currentScope);
                        
                        FormalParameter parameter = 
                            invocation.getImpl().parameterNamed(output.getName());
                        String direction = 
                            parameter == null? null: parameter.getDirection();
                        
                        // A new assignment is created for a local name 
                        // defined as an output argument or a parameter that is
                        // assigned for the first time as an output argument.
                        if ("out".equals(direction)) {
                            String localName = lhs.getImpl().getLocalName();
                            if (localName != null && lhs.getIndex() == null) {
                                ElementReference referent = lhs.getImpl().getReferent();
                                AssignedSource assignment = null;
                                if (referent == null) {
                                    assignment = AssignedSourceImpl.makeAssignment
                                    (localName, invocation, 
                                            parameter.getType(), 
                                            0, 
                                            parameter.getUpper() == 1? 1: -1);
                                    newLocalAssignments.put(localName, assignment);
                                } else if (referent.getImpl().isParameter() &&
                                        !assignmentsBefore.containsKey(localName)){
                                    assignment = AssignedSourceImpl.
                                        makeAssignment(localName, self, 
                                                referent.getImpl().getType(),
                                                referent.getImpl().getLower(), 
                                                referent.getImpl().getUpper());                                    
                                    newLocalAssignments.put(localName, assignment);
                                }
                            }
                        }
                        
                    }
                }
                if (expressions.isEmpty()) {
                    this.assignmentsAfter = assignmentsBefore;
                } else {
                    this.assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsBefore);
                    // Only copy assignmentsBefore if it is necessary to add new local
                    // assignments.
                    if (!newLocalAssignments.isEmpty()) {
                        assignmentsBefore = new HashMap<String, AssignedSource>(assignmentsBefore);
                        assignmentsBefore.putAll(newLocalAssignments);
                    }
                    for (Expression expression: expressions) {
                        expression.getImpl().setAssignmentBefore(assignmentsBefore);
                        this.assignmentsAfter.putAll(expression.getImpl().getAssignmentAfterMap());
                    }
                    for (OutputNamedExpression output: outputs) {
                        LeftHandSide lhs = output.getLeftHandSide();
                        if (lhs != null) {
                            lhs.getImpl().setAssignmentBefore(assignmentsBefore);
                            String assignedName = lhs == null? null: lhs.getImpl().getAssignedName();
                            if (assignedName != null) {
                                AssignedSource assignmentBefore = 
                                    assignmentsBefore.get(assignedName);
                                if (assignmentBefore != null && 
                                        !assignmentBefore.getImpl().getIsParallelLocalName()){
                                    // Update the assignment of an already existing
                                    // local name, unless it is an @parallel local
                                    // name of a for statement.
                                    AssignedSource assignment = AssignedSourceImpl.
                                        makeAssignment(assignmentBefore);
                                    assignment.setSource(self.getInvocation());
                                    this.assignmentsAfter.put(assignedName, assignment);
                                }
                            }
                        }
                    }
                }
            }
            return this.assignmentsAfter;
        }
    }
    
    public Collection<AssignedSource> getNewAssignments() {
        Tuple self = this.getSelf();
        InvocationExpression invocation = self.getInvocation();
        if (invocation == null) {
            return new ArrayList<AssignedSource>();
        } else {
            return AssignedSourceImpl.selectNewAssignments(
                    this.getAssignmentsBeforeMap(), 
                    this.getAssignmentsAfterMap().values());
        }
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
        this.currentScope = currentScope;
    }

} // TupleImpl
