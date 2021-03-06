/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression used to test the dynamic type of its operand.
 **/

public class ClassificationExpressionImpl extends UnaryExpressionImpl {

	private ElementReference referent = null; // DERIVED
	private Boolean isDirect = null; // DERIVED
	private QualifiedName typeName = null;

	public ClassificationExpressionImpl(ClassificationExpression self) {
		super(self);
	}

	@Override
	public ClassificationExpression getSelf() {
		return (ClassificationExpression) this.self;
	}

    @Override
    public void addExternalReferences(Collection<ExternalElementReference> references) {
        super.addExternalReferences(references);
        SyntaxElement.addExternalReference(references, this.getSelf().getReferent());
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

	public Boolean getIsDirect() {
		if (this.isDirect == null) {
			this.setIsDirect(this.deriveIsDirect());
		}
		return this.isDirect;
	}

	public void setIsDirect(Boolean isDirect) {
		this.isDirect = isDirect;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	/**
	 * The referent of a classification expression is the classifier to which
	 * the type name resolves.
	 **/
	protected ElementReference deriveReferent() {
	    QualifiedName typeName = this.getSelf().getTypeName();
		return typeName == null? null: typeName.getImpl().getNonTemplateClassifierReferent();
	}

    /**
     * A classification expression is direct if its operator is "hastype".
     **/
	protected Boolean deriveIsDirect() {
	    String operator = this.getSelf().getOperator();
		return operator != null && operator.equals("hastype");
	}

    /**
     * A classification expression has type Boolean.
     **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getBooleanType();
	}
	
    /**
     * A classification expression has a multiplicity lower bound of 1.
     **/
	@Override
	protected Integer deriveLower() {
	    return 1;
	}
	
    /**
     * A classification expression has a multiplicity upper bound of 1.
     **/
	@Override
	protected Integer deriveUpper() {
	    return 1;
	}
	
	/*
	 * Derivations
	 */

	public boolean classificationExpressionIsDirectDerivation() {
		this.getSelf().getIsDirect();
		return true;
	}

	public boolean classificationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	public boolean classificationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean classificationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean classificationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The type name in a classification expression must resolve to a
	 * classifier.
	 **/
	public boolean classificationExpressionTypeName() {
		return this.getSelf().getReferent() != null;
	}

	/**
	 * The operand expression of a classification expression must have
	 * multiplicity lower and upper bounds of 1.
	 **/
	public boolean classificationExpressionOperand() {
	    Expression operand = this.getSelf().getOperand();
		return operand != null && (operand.getType() == null || 
		        operand.getLower() == 1 && operand.getUpper() == 1);
	}
	
	/*
	 * Helper Methods
	 */
	
	/**
	 * If the truth condition is true and the type of the operand of a classification
	 * expression does not conform to the referent type of the classification
	 * expression, then set the known type of the operand of the classification
	 * expression to be the referent type of the classification expression.
	 */
    @Override
    public Map<String, AssignedSource> adjustAssignments(
            Map<String, AssignedSource> assignmentMap, boolean condition) {
        ClassificationExpression self = this.getSelf();
        Expression operand = self.getOperand();
        ElementReference referent = self.getReferent();
        if (condition && operand != null && referent != null) {
            ElementReference operandType = operand.getType();
            if (operandType == null || !operandType.getImpl().conformsTo(referent)) {
                assignmentMap = operand.getImpl().adjustType(assignmentMap, referent);
            }
        }
        return assignmentMap;
    }

	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    super.setCurrentScope(currentScope);
	    QualifiedName typeName = this.getSelf().getTypeName();
	    if (typeName != null) {
	        typeName.getImpl().setCurrentScope(currentScope);
	    }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ClassificationExpression) {
            QualifiedName typeName = 
                ((ClassificationExpression)base).getTypeName();
            this.getSelf().setTypeName(typeName.getImpl().
                    updateForBinding(templateParameters, templateArguments));
        }
    }
    
} // ClassificationExpressionImpl
