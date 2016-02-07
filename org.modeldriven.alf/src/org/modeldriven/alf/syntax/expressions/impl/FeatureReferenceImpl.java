
/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.AssignableTypedElementImpl;
import org.modeldriven.alf.syntax.units.impl.OperationDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A reference to a structural or behavioral feature of the type of its target
 * expression or a binary association end the opposite end of which is typed by
 * the type of its target expression.
 **/

public class FeatureReferenceImpl extends SyntaxElementImpl {

	private Expression expression = null;
	private Collection<ElementReference> referent = null; // DERIVED
	private NameBinding nameBinding = null;
	
	private NamespaceDefinition currentScope = null;

	public FeatureReferenceImpl(FeatureReference self) {
		super(self);
	}

	@Override
	public FeatureReference getSelf() {
		return (FeatureReference) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    FeatureReference self = this.getSelf();
	    return self._toString(includeDerived) + " nameBinding:" + self.getNameBinding() +
	            " expression:(" + self.getExpression() + ")";
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Collection<ElementReference> getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(Collection<ElementReference> referent) {
		this.referent = referent;
	}

	public void addReferent(ElementReference referent) {
		this.referent.add(referent);
	}

	public NameBinding getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(NameBinding nameBinding) {
		this.nameBinding = nameBinding;
	}

	/**
	 * The features referenced by a feature reference include the features of
	 * the type of the target expression and the association ends of any binary
	 * associations whose opposite ends are typed by the type of the target
	 * expression.
	 **/
	protected Collection<ElementReference> deriveReferent() {
	    // TODO Handle feature references with template bindings.
	    FeatureReference self = this.getSelf();
	    Expression target = self.getExpression();
	    NameBinding nameBinding = self.getNameBinding();
	    ElementReference targetType = target == null? null: target.getType();
	    Collection<ElementReference> referents = new ArrayList<ElementReference>();
	    if (targetType != null && nameBinding != null) {
	        String name = nameBinding.getName();
	        if (name != null) {
	            for (Member member: targetType.getImpl().asNamespace().
	                    getImpl().resolveVisible(name, this.currentScope, false)) {
	                if (member.getIsFeature()) {
	                    referents.add(member.getImpl().getReferent());
	                }
	            }
    	        if (this.currentScope != null) {
    	            for (ElementReference referent: this.currentScope.getImpl().
                            resolveAssociationEnd(targetType, name)) {
    	                // NOTE: Even though fUML does not allow associations that don't
    	                // own their ends, this allows for ths possibility that a model
    	                // might have a non-association owned end that is being used
    	                // as a property of the owning classifier.
    	                if (!referent.getImpl().isContainedIn(referents)) {
    	                    referents.add(referent);
    	                }
    	            }
    	        }
	        }
	    }
	    return referents;
	}
	
	/*
	 * Derivations
	 */

	public boolean featureReferenceReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The target expression of the feature reference may not be untyped, nor
	 * may it have a primitive or enumeration type.
	 **/
	public boolean featureReferenceTargetType() {
	    FeatureReference self = this.getSelf();
	    Expression target = self.getExpression();
	    ElementReference targetType = target == null? null: target.getType();
		return targetType != null && 
		            !targetType.getImpl().isPrimitive() && 
		            !targetType.getImpl().isEnumeration();
	}
	
	/*
	 * Helper Methods
	 */

    public ElementReference getStructuralFeatureReferent() {
        FeatureReference self = this.getSelf();
        ElementReference property = null;
        for (ElementReference referent: self.getReferent()) {
            if (referent.getImpl().isProperty()) {
                if (property != null) {
                    return null;
                }
                property = referent;
            }
        }
        return property;
    }

    public ElementReference getBehavioralFeatureReferent(InvocationExpression invocation) {
        FeatureReference self = this.getSelf();
        ElementReference signal = null;
        List<ElementReference> operations = new ArrayList<ElementReference>();
        for (ElementReference referent: self.getReferent()) {
            if (referent.getImpl().isReception()) {
                // NOTE: If the feature is a reception, then the referent should be the
                // signal being received, not the reception itself.
                referent = referent.getImpl().getSignal();
            }
            if (invocation.getImpl().isCompatibleWith(referent)) {
                if (referent.getImpl().isOperation()) {
                    if (signal != null) {
                        return null;
                    }
                    operations.add(referent);
                } else if (referent.getImpl().isSignal()) {
                    if (signal != null || operations.size() > 0) {
                        return null;
                    }
                    signal = referent;
                }
            }
        }
        return signal != null? signal: selectMostSpecificOperation(operations);
    }
    
    public static ElementReference selectMostSpecificOperation(List<ElementReference> operations) {
        ElementReference selectedOperation = null;
        if (operations.size() > 0) {
            for (ElementReference operation1: operations) {
                boolean isMostSpecific = true;
                for (ElementReference operation2: operations) {
                    if (!operation1.equals(operation2) && !isMoreSpecificThan(operation1, operation2)) {
                        isMostSpecific = false;
                        break;
                    }
                }
                if (isMostSpecific) {
                    if (selectedOperation != null) {
                        return null;
                    }
                    selectedOperation = operation1;
                }
            }
        }
        return selectedOperation;
    }
    
    public static boolean isMoreSpecificThan(ElementReference operation1, ElementReference operation2) {
        List<FormalParameter> parameters1 = 
                OperationDefinitionImpl.removeReturnParameter(operation1.getImpl().getParameters());
        List<FormalParameter> parameters2 = 
                OperationDefinitionImpl.removeReturnParameter(operation2.getImpl().getParameters());
        if (parameters1.size() > parameters2.size()) {
            return false;
        } else {
            for (int i = 0; i < parameters1.size(); i++) {
                FormalParameter parameter1 = parameters1.get(i);
                String direction = parameter1.getDirection();
                FormalParameter parameter2 = parameters2.get(i);
                if ("in".equals(direction)) {
                    if (!new AssignableTypedElementImpl(parameter2.getImpl()).isAssignableFrom(
                            new AssignableTypedElementImpl(parameter1.getImpl()))) {
                        return false;
                    }
                } else if ("out".equals(direction)) {
                    if (!new AssignableTypedElementImpl(parameter1.getImpl()).isAssignableFrom(
                            new AssignableTypedElementImpl(parameter2.getImpl()))) {
                        return false;
                    }
                }
            }
            FormalParameter returnParameter1 = operation1.getImpl().getReturnParameter();
            FormalParameter returnParameter2 = operation2.getImpl().getReturnParameter();
            return returnParameter1 == null ||
                    returnParameter2 != null &&
                    new AssignableTypedElementImpl(returnParameter1.getImpl()).isAssignableFrom(
                            new AssignableTypedElementImpl(returnParameter2.getImpl()));
        }
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
        this.currentScope = currentScope;
        
        FeatureReference self = this.getSelf();
        Expression expression = self.getExpression();
        NameBinding nameBinding = self.getNameBinding();
        
        if (expression != null) {
            expression.getImpl().setCurrentScope(currentScope);
        }
        
        if (nameBinding != null) {
            nameBinding.getImpl().setCurrentScope(currentScope);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentsBefore) {
        Expression expression = this.getSelf().getExpression();
        if (expression != null) {
            expression.getImpl().setAssignmentBefore(assignmentsBefore);
        }
    }
    
    public Map<String, AssignedSource> getAssignmentAfterMap() {
        Expression expression = this.getSelf().getExpression();
        return expression == null? null: 
            expression.getImpl().getAssignmentAfterMap();
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof FeatureReference) {
            FeatureReference self = this.getSelf();
            FeatureReference baseFeature = (FeatureReference)base;
            Expression expression = baseFeature.getExpression();
            NameBinding nameBinding = baseFeature.getNameBinding();
            if (expression != null) {
                self.setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (nameBinding != null) {
                self.setNameBinding(nameBinding.getImpl().
                        updateBinding(templateParameters, templateArguments));
            }
        }
    }

} // FeatureReferenceImpl
