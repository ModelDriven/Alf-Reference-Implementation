/*******************************************************************************
 * Copyright 2011-2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An expression used to invoke a behavior as if it was an operation on a target
 * sequence as a whole.
 **/

public class SequenceOperationExpressionImpl
		extends InvocationExpressionImpl {

	private ExtentOrExpression primary = null;
	private QualifiedName operation = null;
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED
	private LeftHandSide leftHandSide = null; // DERIVED
	
	private Expression expression = null;
	
	private BehaviorInvocationExpression invocation = null;

	public SequenceOperationExpressionImpl(SequenceOperationExpression self) {
		super(self);
	}

	@Override
	public SequenceOperationExpression getSelf() {
		return (SequenceOperationExpression) this.self;
	}
	
	@Override
	public void deriveAll() {
	    super.deriveAll();
	    this.getInvocation().deriveAll();
	}

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
		if (primary != null) {
		    primary.getImpl().setContainingExpression(this.getSelf());
		}
	}

	public QualifiedName getOperation() {
		return this.operation;
	}

	public void setOperation(QualifiedName operation) {
		this.operation = operation;
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.setIsCollectionConversion(this.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

    public LeftHandSide getLeftHandSide() {
        if (this.leftHandSide == null) {
            this.setLeftHandSide(this.deriveLeftHandSide());
        }
        return this.leftHandSide;
    }

    public void setLeftHandSide(LeftHandSide leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    /**
     * Collection conversion is required if the type of the primary expression
     * of a sequence operation expression is a collection class.
     **/
    /*
     * And the multiplicity upper bound must be 1.
     */
	protected Boolean deriveIsCollectionConversion() {
	    Expression expression = this.getExpression();
	    if (expression == null) {
	        return false;
	    } else {
    	    ElementReference type = expression.getType();
    	    int upper = expression.getUpper();
    		return upper == 1 && 
    		    type != null && type.getImpl().isCollectionClass();
	    }
	}

    /**
     * BitString conversion is required if type of the first parameter of the
     * referent of a sequence operation expression is BitString and either the
     * type of its primary expression is Integer or collection conversion is
     * required and the type of its primary expression is a collection class
     * whose sequence type is Integer.
     **/
	protected Boolean deriveIsBitStringConversion() {
	    SequenceOperationExpression self = this.getSelf();
	    ElementReference referent = self.getBoundReferent();
	    if (referent == null) {
	        return false;
	    } else {
	        Expression expression = this.getExpression();
	        ElementReference primaryType = expression == null? 
	                                           null: expression.getType();
	        if (self.getIsCollectionConversion()) {
	            primaryType = primaryType.getImpl().getCollectionSequenceType();
	        }
	        ElementReference parameterType = this.getFirstParameterType();
	        return parameterType != null && 
	                   parameterType.getImpl().isBitString() &&
	               primaryType != null && 
	                   primaryType.getImpl().isInteger();
	    }
	}

	/**
	 * The referent for a sequence operation expression is the behavior named by
	 * the operation for the expression.
	 **/
	@Override
	protected ElementReference deriveReferent() {
	    QualifiedName operation = this.getSelf().getOperation();
	    return operation == null? null: operation.getImpl().getBehaviorReferent();
	}
	
    @Override
    protected ElementReference deriveBoundReferent() {
        SequenceOperationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent == null || !referent.getImpl().isTemplate()? referent:
               this.bindTemplateImplicitArguments(self.getOperation(), referent, this.getExpression());
    }
    
	/**
	 * There is no feature for a sequence operation expression.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
	    return null;
	}
	
    /**
     * If the operation of a sequence operation expression has a first parameter
     * whose direction is inout, then the effective left-hand side for the
     * expression is constructed as follows: If the primary is a name
     * expression, then the left-hand side is a name left-hand side with the
     * name from the name expression as its target. If the primary is a property
     * access expression, then the left-hand side is a feature left hand side
     * with the feature reference from the property access expression as its
     * feature. If the primary is a sequence access expression whose primary is
     * a name expression or a property access expression, then the left-hand
     * side is constructed from the primary of the sequence access expression as
     * given previously and the index of the sequence access expression becomes
     * the index of the left-hand side.
     **/
    public LeftHandSide deriveLeftHandSide() {
        LeftHandSide lhs = null;
        if (this.isInPlace()) {
            Expression expression = this.getExpression();
            ElementReference parameter = this.getFirstParameter();
            if (expression != null && parameter != null) {
                OutputNamedExpression namedExpression = new OutputNamedExpression();
                namedExpression.setName(parameter.getImpl().getName());
                namedExpression.setExpression(expression);
                lhs = namedExpression.getLeftHandSide();
            }
        }
        return lhs;
    }

	/*
	 * Derivations
	 */
	
    public boolean sequenceOperationExpressionIsCollectionConversionDerivation() {
        this.getSelf().getIsCollectionConversion();
        return true;
    }

    public boolean sequenceOperationExpressionIsBitStringConversionDerivation() {
        this.getSelf().getIsBitStringConversion();
        return true;
    }

	public boolean sequenceOperationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean sequenceOperationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}
	
    public boolean sequenceOperationExpressionLeftHandSideDerivation() {
        this.getSelf().getLeftHandSide();
        return true;
    }

	/*
	 * Constraints
	 */

	/**
	 * There must be a single behavior that is a resolution of the operation
	 * qualified name of a sequence operation expression with a least one
	 * parameter, whose first parameter has direction in or inout, has
	 * multiplicity [0..*] and to which the target primary expression is
	 * assignable.
	 **/
	public boolean sequenceOperationExpressionOperationReferent() {
        Expression expression = this.getExpression();
        ElementReference parameter = this.getFirstParameter();
        if (expression == null || parameter == null) {
            return false;
        } else {
            String direction = parameter.getImpl().getDirection();
            return direction != null && 
                        (direction.equals("in") || direction.equals("inout")) &&
                        parameter.getImpl().getLower() == 0 && 
                        parameter.getImpl().getUpper() == -1 &&
                        parameter.getImpl().isAssignableFrom(expression);
        }
	}

    /**
     * If the first parameter of the referent has direction inout, then the
     * parameter type must have the same type as the primary expression, the
     * primary expression must have the form of a left-hand side and, if the
     * equivalent left-hand side is for a local name, that name must already
     * exist. The first parameter must be assignable to the effective left-
     * hand side.
     **/
	public boolean sequenceOperationExpressionTargetCompatibility() {
	    if (!this.isInPlace()) {
	        return true;
	    } else {
            LeftHandSide lhs = this.getLeftHandSide();
            ElementReference parameter = this.getFirstParameter();
                return lhs != null && (lhs.getType() == null ||
                            (lhs.getImpl().getAssignedName() == null || this.getOldAssignment() != null) &&
                            lhs.getImpl().isAssignableFrom(parameter, lhs.getImpl().isNullable()));
	    }
	}

    /**
	 * The type of an input argument expression of a sequence operation
	 * parameter must be assignable to its corresponding parameter. The type of
	 * an output parameter must be assignable to its corresponding argument
	 * expression. (Note that this implies that the type of an argument
	 * expression for an inout parameter must be the same as the type of that
	 * parameter.)
	 **/
	public boolean sequenceOperationExpressionArgumentCompatibility() {
        SequenceOperationExpression self = this.getSelf();
        ElementReference referent = self.getBoundReferent();
        if (referent != null) {
            Tuple tuple = self.getTuple();
            if (tuple == null) {
                return false;
            } else {
                for (NamedExpression input: tuple.getInput()) {
                    if (!this.parameterIsAssignableFrom(input)) {
                        return false;
                    }
                }
                for (NamedExpression output: tuple.getOutput()) {
                    if (!this.parameterIsAssignableTo(output)) {
                        return false;
                    }
                }
            }
        }
        return true;
	}

	/**
	 * The assignments before the primary expression of a sequence operation
	 * expression are the same as the assignments before the sequence operation
	 * expression.
	 **/
	public boolean sequenceOperationExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
	    return true;
	}
	
    /**
     * A local name that is assigned in the primary expression of a sequence
     * operation expression may not be assigned in any expression in the tuple
     * of the sequence operation expression.
     **/
    public boolean sequenceOperationExpressionAssignmentsAfter() {
	    SequenceOperationExpression self = this.getSelf();
	    Expression expression = this.getExpression();
	    Tuple tuple = self.getTuple();
	    if (expression == null || tuple == null) {
	        return true;
	    } else {
    	    // NOTE: getExpression() will already have forced computation of 
	        // assignments.
    	    Collection<AssignedSource> assignments = 
    	        expression.getImpl().getNewAssignments();
     	    assignments.retainAll(tuple.getImpl().getNewAssignments());
    		return assignments.isEmpty();
	    }
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * The assignments after a sequence operation expression include those made
	 * in the primary expression and those made in the tuple and, for an
	 * "in place" operation (one whose first parameter is inout), that made by
	 * the sequence operation expression itself.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
	    SequenceOperationExpression self = this.getSelf();
	    ExtentOrExpression primary = self.getPrimary();
	    Tuple tuple = self.getTuple();
	    Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    Map<String, AssignedSource> assignments = new HashMap<String, AssignedSource>();
	    if (primary != null) {
            this.expression = this.getExpression();
            if (this.expression != null) {
                assignments.putAll(this.expression.getImpl().getAssignmentAfterMap());
            }
	    }
	    if (tuple != null) {
	        tuple.getImpl().setAssignmentsBefore(assignmentsBefore);
	        assignments.putAll(tuple.getImpl().getAssignmentsAfterMap());
	    }
	    if (this.isInPlace()) {
    	    AssignedSource oldAssignment = this.getOldAssignment();
    	    if (oldAssignment != null && 
    	            !oldAssignment.getImpl().getIsParallelLocalName()) {
                // Update the assignment of an already existing local name, 
    	        // unless it is an @parallel local name of a for statement.
    	        AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(oldAssignment);
    	        newAssignment.setSource(self);
    	        assignments.put(newAssignment.getName(), newAssignment);
    	    }
	    }
	    return assignments;
	} // updateAssignments
	
    /**
     * If the invoked behavior is CollectionFunctions::isEmpty or
     * SequenceFunctions::IsEmpty, then check the primary expression for known
     * nulls and non-nulls using the given truth condition. If the invoked
     * behavior is CollectionFunctions::notEmpty or SequenceFunctions::NotEmpty,
     * then check the primary expression for known nulls and non-nulls using the
     * negation of the given truth condition.
     */
	@Override
    public Map<String, AssignedSource> adjustAssignments(
            Map<String, AssignedSource> assignmentsMap, boolean condition) {
	    SequenceOperationExpression self = this.getSelf();
	    ExtentOrExpression primary = self.getPrimary();
	    Expression expression = primary == null? null: primary.getExpression();
	    ElementReference referent = self.getReferent();
	    RootNamespace rootScope = RootNamespace.getRootScope();
	    if (expression != null && referent != null) {
	        if (referent.getImpl().equals(rootScope.getSequenceFunctionIsEmpty()) ||
	            referent.getImpl().equals(rootScope.getCollectionFunctionIsEmpty())) {
	            assignmentsMap = expression.getImpl().adjustMultiplicity(
	                    assignmentsMap, condition);
	        } else if (referent.getImpl().equals(rootScope.getSequenceFunctionNotEmpty()) ||
	                   referent.getImpl().equals(rootScope.getCollectionFunctionNotEmpty())) {
	            assignmentsMap = expression.getImpl().adjustMultiplicity(
	                    assignmentsMap, !condition);
	        }       
	    }
	    return assignmentsMap;
	}
	
	private AssignedSource getOldAssignment() {
        LeftHandSide lhs = this.getLeftHandSide();
        String name = lhs == null? null: lhs.getImpl().getAssignedName();
        return name == null? null: this.getAssignmentBefore(name);
    }
	
    /**
	 * The parameters matched to the tuple of a sequence operation expression
	 * do not include the first parameter of the behavior of the expression.
	 **/
	@Override
	public List<ElementReference> parametersFor(ElementReference referent) {
	    List<ElementReference> parameters = super.parametersFor(referent);
	    return parameters.size() < 1? parameters: 
	        parameters.subList(1, parameters.size());
	}
	
	/**
	 * A sequence operation expression is "in place" if the first parameter of
	 * the referent is inout.
	 */
	public boolean isInPlace() {
	    ElementReference firstParameter = this.getFirstParameter();
	    return firstParameter != null && 
	                firstParameter.getImpl().getDirection().equals("inout");
	}
	
	private ElementReference getFirstParameter() {
        ElementReference referent = this.getSelf().getBoundReferent();
        if (referent == null) {
            return null;
        } else {
            List<ElementReference> parameters = referent.getImpl().getParameters();
            return parameters.isEmpty()? null: parameters.get(0);
        }
	}
	
    private ElementReference getFirstParameterType() {
        ElementReference firstParameter = this.getFirstParameter();
        return firstParameter == null? null: firstParameter.getImpl().getType();
    }
    
    private Expression getExpression() {
        if (this.expression == null) {
            SequenceOperationExpression self = this.getSelf();
            ExtentOrExpression primary = self.getPrimary();
            QualifiedName operation = self.getOperation();
            
            if (primary != null) {
                // NOTE: Setting assignments is necessary for proper derivation 
                // of the primary expression.
                primary.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
                this.expression = primary.getExpression();
            }
            
            // Identify the first argument of an invocation of
            // CollectionFunctions::add, since an @parallel local name is 
            // allowed only in this position.
            // Note: The behavior referent of the operation is used here to
            // avoid having to deal with the implicit template binding of the
            // invocation referent.
            ElementReference collectionFunctionAdd = 
                    RootNamespace.getRootScope().getCollectionFunctionAdd();
            if (operation != null && this.expression != null &&
                    collectionFunctionAdd != null && 
                    collectionFunctionAdd.getImpl().
                        equals(operation.getImpl().getBehaviorReferent())) {
                this.expression.getImpl().setIsAddTarget();
            }
        }
        return this.expression;
    }
    
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
        SequenceOperationExpression self = this.getSelf();
        ExtentOrExpression primary = self.getPrimary();
        QualifiedName operation = self.getOperation();
        if (primary != null) {
            primary.getImpl().setCurrentScope(currentScope);
        }
        if (operation != null) {
            operation.getImpl().setCurrentScope(currentScope);
        }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SequenceOperationExpression) {
            SequenceOperationExpression self = this.getSelf();
            SequenceOperationExpression baseExpression = 
                (SequenceOperationExpression)base;
            ExtentOrExpression primary = baseExpression.getPrimary();
            QualifiedName operation = baseExpression.getOperation();
            self.setOperation(baseExpression.getOperation());
            if (primary != null) {
                self.setPrimary((ExtentOrExpression)primary.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (operation != null) {
                self.setOperation(operation.getImpl().
                        updateBindings(templateParameters, templateArguments));
            }
        }
    }
    
    /**
     * Returns the behavior invocation expression for that is equivalent to this 
     * sequence operation expression.
     */
    public BehaviorInvocationExpression getInvocation() {
        if (this.invocation == null) {
            this.invocation = this.deriveInvocation();
        }
        return this.invocation;
    }

    private BehaviorInvocationExpression deriveInvocation() {
        SequenceOperationExpression self = this.getSelf();
        
        ElementReference firstParameter = this.getFirstParameter();
        NamedExpression namedExpression = new NamedExpression();
        if (firstParameter != null) {
            namedExpression.setName(firstParameter.getImpl().getName());
        }
        namedExpression.setExpression(this.getExpression());
        
        Tuple tuple = self.getTuple();
        List<NamedExpression> namedExpressions = new ArrayList<NamedExpression>();
        namedExpressions.add(namedExpression);
        namedExpressions.addAll(tuple.getInput());
        namedExpressions.addAll(tuple.getOutput());
        
        NamedTuple namedTuple = new NamedTuple();
        namedTuple.setNamedExpression(namedExpressions);
        
        BehaviorInvocationExpression behaviorInvocationExpression = 
            new BehaviorInvocationExpression();
        behaviorInvocationExpression.setTarget(self.getOperation());
        behaviorInvocationExpression.setReferent(self.getReferent());
        behaviorInvocationExpression.setBoundReferent(self.getBoundReferent());
        behaviorInvocationExpression.setTuple(namedTuple);
        namedTuple.setInvocation(behaviorInvocationExpression);       
        behaviorInvocationExpression.getImpl().setAssignmentBefore(
                this.getAssignmentBeforeMap());
        behaviorInvocationExpression.getImpl().setCurrentScope(
                this.getCurrentScope());
        
        return behaviorInvocationExpression;
    }

} // SequenceOperationExpressionImpl
