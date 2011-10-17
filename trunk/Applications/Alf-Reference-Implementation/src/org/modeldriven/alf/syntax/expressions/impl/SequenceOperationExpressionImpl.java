
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.AssignableTypedElementImpl;

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

	public SequenceOperationExpressionImpl(SequenceOperationExpression self) {
		super(self);
	}

	@Override
	public SequenceOperationExpression getSelf() {
		return (SequenceOperationExpression) this.self;
	}

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
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
     * whose argument type is Integer.
     **/
	protected Boolean deriveIsBitStringConversion() {
	    SequenceOperationExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
	    if (referent == null) {
	        return false;
	    } else {
	        Expression expression = this.getExpression();
	        ElementReference primaryType = expression == null? 
	                                           null: expression.getType();
	        if (self.getIsCollectionConversion()) {
	            primaryType = primaryType.getImpl().getCollectionArgument();
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
	    SequenceOperationExpression self = this.getSelf();
	    QualifiedName operation = self.getOperation();
	    ElementReference referent = null;
	    if (operation != null) {
	        referent = operation.getImpl().getBehaviorReferent();
	        if (referent != null && referent.getImpl().isTemplate()) {
	            referent = bindTemplateImplicitArguments(operation, referent, 
	                    this.getExpression());
	        }
	    }
	    return referent;
	}
	
	/**
	 * There is no feature for a sequence operation expression.
	 **/
	@Override
	protected FeatureReference deriveFeature() {
	    return null;
	}
	
	/**
	 * The left-hand side for an "in-place" sequence operation expression is the
	 * equivalent to the left-hand side for an argument to the initial inout
	 * parameter of the operation. 
	 */
    public LeftHandSide deriveLeftHandSide() {
        LeftHandSide lhs = null;
        if (this.isInPlace()) {
            Expression expression = this.getExpression();
            FormalParameter parameter = this.getFirstParameter();
            if (expression != null && parameter != null) {
                OutputNamedExpression namedExpression = new OutputNamedExpression();
                namedExpression.setName(parameter.getName());
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
        FormalParameter parameter = this.getFirstParameter();
        if (expression == null || parameter == null) {
            return false;
        } else {
            String direction = parameter.getDirection();
            return direction != null && 
                        (direction.equals("in") || direction.equals("inout")) &&
                        parameter.getLower() == 0 && 
                        parameter.getUpper() == -1 &&
                        new AssignableTypedElementImpl(parameter.getImpl()).
                            isAssignableFrom(expression);
    }
	}

    /**
     * If the first parameter of the referent has direction inout, then the
     * parameter type must have the same type as the primary expression.
     * 
     * Note: This constraint also needs to require that the primary expression
     * has the form of a left-hand side and, if for a local name or data value
     * update, the assigned name must already exist.
     **/
	public boolean sequenceOperationExpressionTargetCompatibility() {
        Expression expression = this.getExpression();
        ElementReference expressionType = this.getType();
        LeftHandSide lhs = this.getLeftHandSide();
        ElementReference type = this.getFirstParameterType();
        return expression == null || !this.isInPlace() ||
                    lhs != null && (lhs.getImpl().getAssignedName() == null || 
                            this.getOldAssignment() != null) &&
                    (type != null && type.getImpl().equals(expressionType) ||
                            type == null && expressionType == null);
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
        ElementReference referent = self.getReferent();
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
	    // Note: Setting the assignments before the primary expression is
	    // handled by updateAssignments.
	    //
	    // The following condition checks whether a name is not assigned in both
	    // the primary expression and an argument expression. This needs to be
	    // added to the spec.
	    SequenceOperationExpression self = this.getSelf();
	    Expression expression = this.getExpression();
	    Tuple tuple = self.getTuple();
	    if (expression == null || tuple == null) {
	        return true;
	    } else {
    	    this.getAssignmentAfterMap(); // Force computation of assignments.
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
	    Expression expression = this.getExpression();
	    Tuple tuple = self.getTuple();
	    Map<String, AssignedSource> assignments = new HashMap<String, AssignedSource>();
	    if (expression != null) {
	        expression.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
	        assignments.putAll(expression.getImpl().getAssignmentAfterMap());
	    }
	    if (tuple != null) {
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
	public List<FormalParameter> parameters() {
        SequenceOperationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        if (referent == null) {
            return new ArrayList<FormalParameter>();
        } else {
            List<FormalParameter> parameters = referent.getImpl().getParameters();
            return parameters.size() < 1? parameters: 
                        parameters.subList(1, parameters.size());
        }
	}
	
	/**
	 * A sequence operation expression is "in place" if the first parameter of
	 * the referent is inout.
	 */
	public boolean isInPlace() {
	    FormalParameter firstParameter = this.getFirstParameter();
	    return firstParameter != null && 
	                firstParameter.getDirection().equals("inout");
	}
	
	private FormalParameter getFirstParameter() {
        ElementReference referent = this.getSelf().getReferent();
        if (referent == null) {
            return null;
        } else {
            List<FormalParameter> parameters = referent.getImpl().getParameters();
            return parameters.size() == 0? null: parameters.get(0);
        }
	}
	
    private ElementReference getFirstParameterType() {
        FormalParameter firstParameter = this.getFirstParameter();
        return firstParameter == null? null: firstParameter.getType();
    }
    
    private Expression getExpression() {
        if (this.expression == null) {
            SequenceOperationExpression self = this.getSelf();
            ExtentOrExpression primary = self.getPrimary();
            QualifiedName operation = self.getOperation();
            this.expression = primary == null? null: primary.getExpression();
            
            // Identify the first argument of an invocation of
            // CollectionFunctions::add, since an @parallel local name is 
            // allowed only in this position.
            // Note: The behavior referent of the operation is used here to
            // avoid having to deal with the implicit template binding of the
            // invocation referent.
            if (operation != null && this.expression != null &&
                    RootNamespace.getCollectionFunctionAdd().getImpl().
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

} // SequenceOperationExpressionImpl
