
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.LinkOperationExpressionImpl;

/**
 * An expression used to create or destroy the links of an association.
 **/

public class LinkOperationExpression extends InvocationExpression {

	public LinkOperationExpression() {
		this.impl = new LinkOperationExpressionImpl(this);
	}

	public LinkOperationExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public LinkOperationExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public LinkOperationExpressionImpl getImpl() {
		return (LinkOperationExpressionImpl) this.impl;
	}

	public String getOperation() {
		return this.getImpl().getOperation();
	}

	public void setOperation(String operation) {
		this.getImpl().setOperation(operation);
	}

	public Boolean getIsCreation() {
		return this.getImpl().getIsCreation();
	}

	public void setIsCreation(Boolean isCreation) {
		this.getImpl().setIsCreation(isCreation);
	}

	public Boolean getIsClear() {
		return this.getImpl().getIsClear();
	}

	public void setIsClear(Boolean isClear) {
		this.getImpl().setIsClear(isClear);
	}

	public QualifiedName getAssociationName() {
		return this.getImpl().getAssociationName();
	}

	public void setAssociationName(QualifiedName associationName) {
		this.getImpl().setAssociationName(associationName);
	}

	/**
	 * A link operation expression is for link creation if its operation is
	 * "createLink".
	 **/
	public boolean linkOperationExpressionIsCreationDerivation() {
		return this.getImpl().linkOperationExpressionIsCreationDerivation();
	}

	/**
	 * A link operation expression is for clearing an association if the
	 * operation is "clearAssoc".
	 **/
	public boolean linkOperationExpressionIsClearDerivation() {
		return this.getImpl().linkOperationExpressionIsClearDerivation();
	}

	/**
	 * The referent for a link operation expression is the named association.
	 **/
	public boolean linkOperationExpressionReferentDerivation() {
		return this.getImpl().linkOperationExpressionReferentDerivation();
	}

	/**
	 * There is no feature for a link operation expression.
	 **/
	public boolean linkOperationExpressionFeatureDerivation() {
		return this.getImpl().linkOperationExpressionFeatureDerivation();
	}

	/**
	 * The qualified name of a link operation expression must resolve to a
	 * single association.
	 **/
	public boolean linkOperationExpressionAssociationReference() {
		return this.getImpl().linkOperationExpressionAssociationReference();
	}

	/**
	 * Each argument expression must be assignable to its corresponding
	 * expression.
	 **/
	public boolean linkOperationExpressionArgumentCompatibility() {
		return this.getImpl().linkOperationExpressionArgumentCompatibility();
	}

	/**
	 * For a clear association operation, returns a single, typeless parameter.
	 * Otherwise, returns the ends of the named association.
	 **/
	@Override
    public List<ElementReference> parameterElements() {
		return this.getImpl().parameterElements();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getAssociationName());
    }

	@Override
    public void _deriveAll() {
		this.getIsCreation();
		this.getIsClear();
		super._deriveAll();
		QualifiedName associationName = this.getAssociationName();
		if (associationName != null) {
			associationName.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.linkOperationExpressionIsCreationDerivation()) {
			violations.add(new ConstraintViolation(
					"linkOperationExpressionIsCreationDerivation", this));
		}
		if (!this.linkOperationExpressionIsClearDerivation()) {
			violations.add(new ConstraintViolation(
					"linkOperationExpressionIsClearDerivation", this));
		}
		if (!this.linkOperationExpressionReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"linkOperationExpressionReferentDerivation", this));
		}
		if (!this.linkOperationExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"linkOperationExpressionFeatureDerivation", this));
		}
		if (!this.linkOperationExpressionAssociationReference()) {
			violations.add(new ConstraintViolation(
					"linkOperationExpressionAssociationReference", this));
		}
		if (!this.linkOperationExpressionArgumentCompatibility()) {
			violations.add(new ConstraintViolation(
					"linkOperationExpressionArgumentCompatibility", this));
		}
		QualifiedName associationName = this.getAssociationName();
		if (associationName != null) {
			associationName.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" operation:");
		s.append(this.getOperation());
		if (includeDerived) {
			s.append(" /isCreation:");
			s.append(this.getIsCreation());
		}
		if (includeDerived) {
			s.append(" /isClear:");
			s.append(this.getIsClear());
		}
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedName associationName = this.getAssociationName();
		if (associationName != null) {
			System.out.println(prefix + " associationName:");
			associationName.print(prefix + "  ", includeDerived);
		}
	}
} // LinkOperationExpression
