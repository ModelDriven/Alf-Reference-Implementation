/*******************************************************************************
 * Copyright 2011-2020 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ExpressionStatement;
import org.modeldriven.alf.syntax.statements.Statement;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;
import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * An expression denoting the invocation of a behavior or operation, or the
 * sending of a signal.
 **/

public abstract class InvocationExpressionImpl extends ExpressionImpl {

	private Boolean isBehavior = null; // DERIVED
	private Boolean isAssociationEnd = null; // DERIVED
	private FeatureReference feature = null; // DERIVED
	private Tuple tuple = null;
	private Boolean isOperation = null; // DERIVED
	private Boolean isDestructor = null; // DERIVED
	private Boolean isImplicit = null; // DERIVED
	private ElementReference referent = null; // DERIVED
	private List<ElementReference> parameter = null; // DERIVED
	private Boolean isSignal = null; // DERIVED
    private ElementReference boundReferent = null; // DERIVED
	
	private NamespaceDefinition currentScope = null;
	private Block enclosingBlock = null;

	public InvocationExpressionImpl(InvocationExpression self) {
		super(self);
	}

	@Override
	public InvocationExpression getSelf() {
		return (InvocationExpression) this.self;
	}
	
    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        SyntaxElement.addExternalReference(references, referent);
        if (self.getIsBehavior() || self.getIsOperation()) {
            SyntaxElement.addExternalReferences(references, self.getParameter());
        } else if (self.getIsSignal() || self.getIsAssociationEnd()) {
            SyntaxElement.addExternalReferences(references, referent.getImpl().getPropertiesForParameters()); 
        }
    }
    
	public Boolean getIsBehavior() {
		if (this.isBehavior == null) {
			this.setIsBehavior(this.deriveIsBehavior());
		}
		return this.isBehavior;
	}

	public void setIsBehavior(Boolean isBehavior) {
		this.isBehavior = isBehavior;
	}

	public Boolean getIsAssociationEnd() {
		if (this.isAssociationEnd == null) {
			this.setIsAssociationEnd(this.deriveIsAssociationEnd());
		}
		return this.isAssociationEnd;
	}

	public void setIsAssociationEnd(Boolean isAssociationEnd) {
		this.isAssociationEnd = isAssociationEnd;
	}

	public FeatureReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(FeatureReference feature) {
		this.feature = feature;
	}

	public Tuple getTuple() {
		return this.tuple;
	}

	public void setTuple(Tuple tuple) {
		this.tuple = tuple;
	}

	public Boolean getIsOperation() {
		if (this.isOperation == null) {
			this.setIsOperation(this.deriveIsOperation());
		}
		return this.isOperation;
	}

	public void setIsOperation(Boolean isOperation) {
		this.isOperation = isOperation;
	}

	public Boolean getIsDestructor() {
		if (this.isDestructor == null) {
			this.setIsDestructor(this.deriveIsDestructor());
		}
		return this.isDestructor;
	}

	public void setIsDestructor(Boolean isDestructor) {
		this.isDestructor = isDestructor;
	}

	public Boolean getIsImplicit() {
		if (this.isImplicit == null) {
			this.setIsImplicit(this.deriveIsImplicit());
		}
		return this.isImplicit;
	}

	public void setIsImplicit(Boolean isImplicit) {
		this.isImplicit = isImplicit;
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

	public List<ElementReference> getParameter() {
		if (this.parameter == null) {
			this.setParameter(this.deriveParameter());
		}
		return this.parameter;
	}

	public void setParameter(List<ElementReference> parameter) {
		this.parameter = parameter;
	}

	public void addParameter(ElementReference parameter) {
		this.parameter.add(parameter);
	}

	public Boolean getIsSignal() {
		if (this.isSignal == null) {
			this.setIsSignal(this.deriveIsSignal());
		}
		return this.isSignal;
	}

	public void setIsSignal(Boolean isSignal) {
		this.isSignal = isSignal;
	}

    public ElementReference getBoundReferent() {
        if (this.boundReferent == null) {
            this.setBoundReferent(this.deriveBoundReferent());
        }
        return this.boundReferent;
    }
    
    public void setBoundReferent(ElementReference boundReferent) {
        this.boundReferent = boundReferent;
    }

    /**
     * An invocation expression is a behavior invocation if its referent is a
     * behavior.
     **/
	protected Boolean deriveIsBehavior() {
	    InvocationExpression self = this.getSelf();
	    ElementReference referent = self.getReferent();
		return referent != null && referent.getImpl().isBehavior();
	}

    /**
     * An invocation expression is an association end read if its referent is an
     * association end.
     **/
	protected Boolean deriveIsAssociationEnd() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isAssociationEnd();
	}

	/**
	 * This property is set for a feature invocation expression or for an 
	 * expression initially parsed as a behavior invocation expression that
     * disambiguates to a feature invocation expression.
	 */
	protected abstract FeatureReference deriveFeature();

    /**
     * An invocation expression is an operation call if its referent is an
     * operation.
     **/
	protected Boolean deriveIsOperation() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isOperation();
	}

    /**
     * An invocation expression is a destructor call either implicitly or if it
     * is an explicit operation call to a destructor operation.
     **/
	protected Boolean deriveIsDestructor() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return self.getIsImplicit() || 
                    referent != null && referent.getImpl().isDestructor();
	}

    /**
     * An invocation expression is an implicit object destruction if it has a
     * feature with the name "destroy" and no explicit referents.
     **/
	protected Boolean deriveIsImplicit() {
        InvocationExpression self = this.getSelf();
        FeatureReference feature = self.getFeature();
        NameBinding nameBinding = feature == null? null: feature.getNameBinding();
        ElementReference referent = self.getReferent();
        return nameBinding != null && referent == null &&
                    nameBinding.getName().equals("destroy") && 
                    nameBinding.getBinding() == null;
	}

	/**
	 * The behavior, operation or signal being invoked. The derivation of this 
	 * property is specific to each kind of invocation expression.
	 */
	protected abstract ElementReference deriveReferent();

    /**
     * The parameters of an invocation expression are given by the result of the
     * parameterElements helper operation.
     **/
	protected List<ElementReference> deriveParameter() {
		return this.parameterElements();
	}

    /**
     * An invocation expression is a signal send if its referent is a signal.
     **/
	protected Boolean deriveIsSignal() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent != null && referent.getImpl().isReception();
	}
	
    /**
     * The type of an invocation expression is determined by the return
     * parameter (if any) of the referent.
     **/
	@Override
	protected ElementReference deriveType() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getBoundReferent();
	    return referent == null? null: referent.getImpl().getType();
	}
	
    /**
     * The multiplicity upper bound of an invocation expression is determined by
     * the return parameter (if any) of the referent.
     **/
    @Override
	protected Integer deriveUpper() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent == null? 0: referent.getImpl().getUpper();
	}
	
    /**
     * The multiplicity lower bound of an invocation expression is determined by
     * the return parameter (if any) of the referent.
     **/
    @Override
	protected Integer deriveLower() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        return referent == null? 0: referent.getImpl().getLower();
	}
    
    /**
     * If the referent of an invocation expression is a template behavior, then
     * the bound referent is the implicit template binding of this template;
     * otherwise it is the same as the referent. For an implicit template
     * binding, the type arguments for the template are inferred from the
     * types of the arguments for in and inout parameters of the template
     * behavior. If the resulting implicit template binding would not be a legal
     * binding of the template behavior, then the invocation expression has no
     * bound referent.
     */
    // Overridden by BehaviorInvocationExpressionImpl and SequenceOperationExpressionImpl
    protected ElementReference deriveBoundReferent() {
        return this.getSelf().getReferent();
    }
    
    /*
     * Derivations
     */

	public boolean invocationExpressionIsBehaviorDerivation() {
		this.getSelf().getIsBehavior();
		return true;
	}

	public boolean invocationExpressionIsAssociationEndDerivation() {
		this.getSelf().getIsAssociationEnd();
		return true;
	}

	public boolean invocationExpressionIsOperationDerivation() {
		this.getSelf().getIsOperation();
		return true;
	}

	public boolean invocationExpressionIsDestructorDerivation() {
		this.getSelf().getIsDestructor();
		return true;
	}

	public boolean invocationExpressionIsImplicitDerivation() {
		this.getSelf().getIsImplicit();
		return true;
	}

	public boolean invocationExpressionIsSignalDerivation() {
		this.getSelf().getIsSignal();
		return true;
	}

	public boolean invocationExpressionParameterDerivation() {
		this.getSelf().getParameter();
		return true;
	}

	public boolean invocationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean invocationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean invocationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
    public boolean invocationExpressionBoundReferentDerivation() {
        this.getSelf().getBoundReferent();
        return true;
    }

	/*
	 * Constraints
	 */

	/**
	 * The assignments before the target expression of the feature reference of
	 * an invocation expression (if any) are the same as the assignments before
	 * the invocation expression.
	 **/
	public boolean invocationExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}
	
    /**
     * If the referent of the invocation expression is a template, then all of
     * its template parameters must be classifier template parameters. Note:
     * This allows for the possibility that the referent is not an Alf unit, in
     * which case it could have non-classifier template parameters.
     */
    public boolean invocationExpressionTemplateParameters() {
        ElementReference referent = this.getSelf().getReferent();
        if (referent != null && referent.getImpl().isTemplate()) {
            for (ElementReference templateParameter: 
                    referent.getImpl().getTemplateParameters()) {
                ElementReference element = 
                    templateParameter.getImpl().getParameteredElement();
                if (element == null || !element.getImpl().isClassifier()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * If invocation is a sequence feature invocation, then the assignments
     * after the tuple of the invocation expression must be the same as the
     * assignments before.
     */
    public boolean invocationExpressionAssignmentsAfter() {
        if (!this.isSequenceFeatureInvocation()) {
            return true;
        } else {
            InvocationExpression self = this.getSelf();
            Tuple tuple = self.getTuple();
            this.getAssignmentAfterMap(); // Force computation of assignments.
            return tuple == null || 
                    tuple.getImpl().getNewAssignments().isEmpty();
        }        
    }
    
    /**
     * The referent of an invocation cannot be a signal unless it is a
     * signal reception.
     */
    // NOTE: By ignoring violations of this constraint, a tool can allow signal
    // sends that directly target signals without requiring the use of receptions.
    public boolean invocationExpressionSignalReferent() {
    	ElementReference referent = this.getSelf().getReferent();
    	return referent == null || !referent.getImpl().isSignal() ||
    			referent.getImpl().isReception();
    }

	/*
	 * Helper Methods
	 */

	/**
	 * Returns references to the elements that act as the parameters of the
	 * referent. For a behavior or operation, these are the owned parameters, in
	 * order. Otherwise (by default), they are actually any properties of the
	 * referent (e.g., signal attributes), which are treated as if they were in
	 * parameters. (This is defined as a helper operation, so that it can be
	 * overridden by subclasses of InvocationExpression, if necessary.)
	 **/
	public List<ElementReference> parameterElements() {
        return this.parametersFor(null);
    }
	
    public List<ElementReference> parametersFor(ElementReference referent) {
        if (referent == null) {
            referent = this.getSelf().getBoundReferent();
        }
        return referent == null? new ArrayList<ElementReference>(): 
            referent.getImpl().getEffectiveParameters();
    }

    // Returns the number of parameters, excluding return parameters.
    public int parameterCount() {
        return this.countParametersOf(null);
    }

    public int countParametersOf(ElementReference referent) {
        List<ElementReference> parameters = parametersFor(referent);
        int n = parameters.size();
        for (ElementReference parameter: parameters) {
            if ("return".equals(parameter.getImpl().getDirection())) {
                n--;
            }
        }
        return n;
    }
    
	/**
	 * The assignments after an invocation expression are the same as those
	 * after the tuple of the expression.
	 **/
	public Map<String, AssignedSource> updateAssignmentMap() {
	    // NOTE: Defer computation of referent until the assignments before
	    // the feature and tuple are set.
	    return this.updateAssignmentsFor(null);
	}
	
	public Map<String, AssignedSource> updateAssignmentsFor(ElementReference referent) {
	    InvocationExpression self = this.getSelf();
	    FeatureReference feature = self.getFeature();
	    Tuple tuple = self.getTuple();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    if (feature != null) {
	        feature.getImpl().setAssignmentBefore(assignments);
	        assignments = feature.getImpl().getAssignmentAfterMap();
	    }
	    if (tuple != null) {
	        tuple.getImpl().setAssignmentsBefore(assignments);
	        assignments = tuple.getImpl().getAssignmentsAfterMap(referent);
	    }
	    return assignments;
	}
	
	protected boolean parameterIsAssignableFrom(NamedExpression input) {
	    return this.parameterIsAssignableFrom(input, null);
	}
	
	protected boolean parameterIsAssignableFrom(NamedExpression input, ElementReference referent) {
	    ElementReference namedParameter = this.parameterNamed(input.getName(), referent);
        if (namedParameter == null) {
	        return false;
	    } else {
	        String direction = namedParameter.getImpl().getDirection();
	        return direction != null && 
	                    (direction.equals("in") || direction.equals("inout")) &&
	                    namedParameter.getImpl().isAssignableFrom(input.getExpression());
	    }
	}
	
    protected boolean parameterIsAssignableTo(NamedExpression output) {
        return parameterIsAssignableTo(output, null);
    }

    protected boolean parameterIsAssignableTo(NamedExpression output, ElementReference referent) {
        ElementReference namedParameter = this.parameterNamed(output.getName(), referent);
        if (namedParameter == null || 
                !(output instanceof OutputNamedExpression)) {
            return false;
        } else {
            String direction = namedParameter.getImpl().getDirection();
            LeftHandSide lhs = ((OutputNamedExpression)output).getLeftHandSide();
            return direction != null && lhs != null &&
                        (direction.equals("out") || direction.equals("inout")) &&
                        lhs.getImpl().isAssignableFrom(namedParameter, lhs.getImpl().isNullable());
        }
    }
    
    public ElementReference parameterNamed(String name) {
        return parameterNamed(name, null);
    }
    
    public ElementReference parameterNamed(String name, ElementReference referent) {
        if (referent == null) {
            referent = this.getSelf().getBoundReferent();
        }
        for (ElementReference parameter: this.parametersFor(referent)) {
            if (parameter.getImpl().getName().equals(name)) {
                return parameter;
            }
        }
        return null;
    }
    
    public ElementReference resolveOverloading(Collection<ElementReference> referents) {
        InvocationExpression self = this.getSelf();
        List<ElementReference> features = new ArrayList<ElementReference>();
        for (ElementReference referent: referents) {
            if ((referent.getImpl().isOperation() || 
                    referent.getImpl().isReception() ||
                    // Allow for the possibility of a signal send without targeting a reception.
                    referent.getImpl().isSignal()) &&
            	self.getImpl().isCompatibleWith(referent)) {
            		features.add(referent);
            }
        }
        return selectMostSpecific(features);
    }
    
    public static ElementReference selectMostSpecific(List<ElementReference> features) {
        ElementReference selectedFeature = null;
        if (features.size() > 0) {
            for (ElementReference feature1: features) {
                boolean isMostSpecific = true;
                for (ElementReference feature2: features) {
                    if (!feature1.equals(feature2) && !isMoreSpecificThan(feature1, feature2)) {
                        isMostSpecific = false;
                        break;
                    }
                }
                if (isMostSpecific) {
                	if (selectedFeature == null || 
                			!selectedFeature.getImpl().isFeature() && feature1.getImpl().isFeature()) {
                		selectedFeature = feature1;
                	} else if (!selectedFeature.getImpl().isFeature() || feature1.getImpl().isFeature()) {
                		return null;
                    }
                }
            }
        }
        return selectedFeature;
    }
    
    public static boolean isMoreSpecificThan(ElementReference feature1, ElementReference feature2) {
        List<ElementReference> parameters1 = 
                OperationDefinitionImpl.removeReturnParameter(feature1.getImpl().getEffectiveParameters());
        List<ElementReference> parameters2 = 
                OperationDefinitionImpl.removeReturnParameter(feature2.getImpl().getEffectiveParameters());
        if (parameters1.size() > parameters2.size()) {
            return false;
        } else {
            for (int i = 0; i < parameters1.size(); i++) {
                ElementReference parameter1 = parameters1.get(i);
                String direction = parameter1.getImpl().getDirection();
                ElementReference parameter2 = parameters2.get(i);
                if ("in".equals(direction)) {
                    if (!parameter2.getImpl().isAssignableFrom(parameter1)) {
                        return false;
                    }
                } else if ("out".equals(direction)) {
                    if (!parameter1.getImpl().isAssignableFrom(parameter2)) {
                        return false;
                    }
                }
            }
            ElementReference returnParameter1 = feature1.getImpl().getReturnParameter();
            ElementReference returnParameter2 = feature2.getImpl().getReturnParameter();
            return returnParameter1 == null ||
                    returnParameter2 != null &&
                    returnParameter1.getImpl().isAssignableFrom(returnParameter2);
        }
    }

    public boolean isCompatibleWith(ElementReference referent) {
        // NOTE: If referent is null, then the invocation referent is used,
        // which is cached, along with tuple inputs and outputs.
        InvocationExpression self = this.getSelf();
        Tuple tuple = self.getTuple();
        if (tuple == null || 
                tuple.getImpl().size() > countParametersOf(referent)) {
            return false;
        } else {
            this.updateAssignmentsFor(referent);
            for (NamedExpression input: tuple.getImpl().deriveInput(referent)) {
                if (!parameterIsAssignableFrom(input, referent)) {
                   return false;
                }
            }
            for (NamedExpression output: tuple.getImpl().deriveOutput(referent)) {
                if (!parameterIsAssignableTo(output, referent)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        Tuple tuple = this.getSelf().getTuple();
        if (tuple != null) {
            tuple.getImpl().setCurrentScope(currentScope);
        }
        this.currentScope = currentScope;
    }
    
    public NamespaceDefinition getCurrentScope() {
        return this.currentScope;
    }
    
    public boolean isContainedInDestructor() {
        NamespaceDefinition namespace = this.getCurrentScope();
        if (namespace == null) {
            return false;
        } else {
            UnitDefinition unit = namespace.getUnit();
            Member member = unit == null? namespace: unit.getImpl().getStub();
            return member instanceof OperationDefinition && 
                ((OperationDefinition)member).getIsDestructor();
        }
    }
    
    @Override
    public void setEnclosingBlock(Block enclosingBlock) {
        this.enclosingBlock = enclosingBlock;
    }
    

    /**
     * The referent may only be a constructor (as a result of the target
     * disambiguating to a feature reference) if this behavior invocation
     * expression is the expression of an expression statement that is the first
     * statement in the definition for the method of a constructor operation.
     **/
    public boolean checkAlternativeConstructorValidity() {
        InvocationExpression self = this.getSelf();
        ElementReference referent = self.getReferent();
        NamespaceDefinition currentScope = this.getCurrentScope();
        if (referent == null || !referent.getImpl().isConstructor() || 
                currentScope == null) {
            return true;
        } else {
            // Note: This will work, even if the operation definition is not an
            // Alf unit.
            ElementReference enclosingScope = currentScope.getImpl().getReferent();
            ElementReference operation = enclosingScope == null? null: 
                enclosingScope.getImpl().getSpecification();
            if (operation == null || !operation.getImpl().isConstructor() || this.enclosingBlock == null) {
                return false;
            } else {
                List<Statement> statements = this.enclosingBlock.getStatement();
                if (statements.size() == 0) {
                    return false;
                } else {
                    Statement statement = statements.get(0);
                    return statement instanceof ExpressionStatement &&
                            ((ExpressionStatement)statement).getExpression() == self &&
                            statement.getImpl().getEnclosingStatement() == null &&
                            // NOTE: This ensures that the invoked constructor
                            // the is from the same class as the containing
                            // constructor.
                            operation.getImpl().getNamespace().getImpl().
                                equals(referent.getImpl().getNamespace()) &&
                            // NOTE: An alternative constructor invocation should
                            // only be allowed on "this".
                            self.getFeature().getExpression() instanceof ThisExpression;
                }
            }
        }
    }

    /**
     * Infer the implicit template arguments for a template behavior referent
     * and bind them to template parameters.
     */
    protected ElementReference bindTemplateImplicitArguments(
            QualifiedName target,
            ElementReference referent, 
            Expression primary) {
        InvocationExpression self = this.getSelf();
        List<ElementReference> templateParameters = 
            referent.getImpl().getTemplateParameters();
        
        this.updateAssignmentsFor(referent); // Force computation of assignments.
        
        
        // This is included to handle the primary expression in a sequence
        // operation expression.
        ElementReference firstParameter = null;
        if (primary != null) {
            List<ElementReference> parameters = referent.getImpl().getParameters();
            firstParameter = parameters.size() == 0? null: parameters.get(0);
        }
        
        List<ElementReference> templateArguments = new ArrayList<ElementReference>();
        for (ElementReference templateParameter: templateParameters) {
            Collection<ElementReference> types = new ArrayList<ElementReference>();
            if (firstParameter != null) {
                if (templateParameter.getImpl().getParameteredElement().getImpl().
                        equals(firstParameter.getImpl().getType())) {
                    types.add(effectiveType(firstParameter, primary));
                }
            }
            for (NamedExpression input: self.getTuple().getImpl().getInput(referent)) {
                ElementReference parameter = this.parameterNamed(input.getName(), referent);
                if (templateParameter.getImpl().getParameteredElement().getImpl().
                        equals(parameter.getImpl().getType())) {
                    types.add(effectiveType(parameter, input.getExpression()));                             
                }
            }
            templateArguments.add(ClassifierDefinitionImpl.commonAncestor(types));
        }
        
        return referent.getImpl().bind(templateArguments);
    }
    
    public boolean isSequenceFeatureInvocation() {
        FeatureReference feature = this.getSelf().getFeature();
        Expression primary = feature == null? null: feature.getExpression();
        return primary != null && 
                (primary.getLower() !=1 || primary.getUpper() != 1);
    }
    
    /**
     * If collection conversion would be required, return the toSequence
     * return type, rather than the expression type.
     */
    private static ElementReference effectiveType(
            ElementReference parameter, 
            Expression expression) {
        ElementReference type = expression.getType();
        int expressionUpper = expression.getUpper();
        int parameterUpper = parameter.getImpl().getUpper();
        if ((parameterUpper == -1 || parameterUpper > 1) && 
                expressionUpper == 1 &&
                type != null && type.getImpl().isCollectionClass()) {
            return type.getImpl().getCollectionSequenceType();
        } else {
            return type;
        }
        
    }

    /**
     * Determine whether this is an invocation of the CollectionFunction:add
     * behavior. This is false by default and overridden for behavior 
     * invocations. (Sequence operation expressions handle add invocations
     * separately.)
     */
    public boolean isAddInvocation() {
        return false;
    }
    
    /**
     * Determine whether this is an invocation of a library function the
     * needs adjustment if indexing is from 0.
     */
    public boolean isIndexingInvocation() {
        InvocationExpression self = this.getSelf();
        if (self.getIsBehavior()) {
            ElementReference referent = self.getReferent();
            for (ElementReference function: RootNamespace.getRootScope().getIndexingFunctions()) {
                if (referent.getImpl().equals(function)) {
                    return true;
                }
            }
         }
        return false;
    }
    
    /**
     * Determine whether this is an invocation of a library function
     * whose output needs to be adjusted if indexing is from 0.
     */
    public boolean isIndexResult() {
       InvocationExpression self = this.getSelf();
       if (!self.getIsBehavior()) {
           return false;
       } else {
           ElementReference referent = self.getReferent();
           return referent.getImpl().equals(
                   RootNamespace.getRootScope().getSequenceFunction("IndexOf")) ||
                  referent.getImpl().equals(
                   RootNamespace.getRootScope().getCollectionFunction("indexOf"));
       }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof InvocationExpression) {
            InvocationExpression self = this.getSelf();
            Tuple tuple = ((InvocationExpression)base).getTuple();
            if (tuple != null) {
                tuple = (Tuple)tuple.getImpl().
                    bind(templateParameters, templateArguments);
                tuple.setInvocation(self);
                self.setTuple(tuple);
            }
        }
    }

} // InvocationExpressionImpl
