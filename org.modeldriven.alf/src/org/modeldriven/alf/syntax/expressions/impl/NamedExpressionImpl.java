
/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A pairing of a parameter name and an argument expression in a tuple.
 **/

public class NamedExpressionImpl extends SyntaxElementImpl {

	private String name = "";
	private Expression expression = null;
	private Expression index = null;
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED
	private Boolean isRealConversion = null; // DERIVED

	public NamedExpressionImpl(NamedExpression self) {
		super(self);
	}

	@Override
	public NamedExpression getSelf() {
		return (NamedExpression) this.self;
	}
	
	@Override
	public String toString(boolean includeDerived) {
	    NamedExpression self = this.getSelf();
	    Expression index = self.getIndex();
	    return super.toString(includeDerived) 
	                + " expression:(" + self.getExpression() + ")" 
	                + (index == null? "": " index:(" + index +")");
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = NameBindingImpl.processName(name);
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Expression getIndex() {
		return this.index;
	}

	public void setIndex(Expression index) {
		this.index = index;
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

    public Boolean getIsRealConversion() {
        if (this.isRealConversion == null) {
            this.setIsRealConversion(this.deriveIsRealConversion());
        }
        return this.isRealConversion;
    }

    public void setIsRealConversion(Boolean isRealConversion) {
        this.isRealConversion = isRealConversion;
    }

	/**
	 * Collection conversion is required if the type of the corresponding
	 * parameter is a collection class and the type of the argument expression
	 * is not.
	 **/
	protected Boolean deriveIsCollectionConversion() {
	    // This needs to be set externally using setIsCollectionConversion.
		return null;
	}

    /**
     * Bit string conversion is required if the type of the corresponding
     * parameter is BitString, or a collection class whose sequence type is
     * BitString, and the type of the argument expression is not BitString.
     **/
    protected Boolean deriveIsBitStringConversion() {
        // This needs to be set externally using setIsBitStringConversion.
        return null;
    }
    
    /**
     * Real conversion is required if the type of the corresponding parameter is
     * Real, or a collection class whose sequence type is Real, and the type of
     * the argument expression is not Real.
     **/
    protected Boolean deriveIsRealConversion() {
        // This needs to be set externally using setIsRealConversion.
        return null;
    }
    
	/*
	 * Derivations
	 */

	public boolean namedExpressionIsCollectionConversionDerivation() {
		this.getSelf().getIsCollectionConversion();
		return true;
	}

    public boolean namedExpressionIsBitStringConversionDerivation() {
        this.getSelf().getIsBitStringConversion();
        return true;
    }
    
    public boolean namedExpressionIsRealConversionDerivation() {
        this.getSelf().getIsRealConversion();
        return true;
    }
    
	/*
	 * Helper Methods
	 */
	
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    NamedExpression self = this.getSelf();
	    Expression expression = self.getExpression();
	    Expression index = self.getIndex();
	    if (expression != null) {
	        expression.getImpl().setCurrentScope(currentScope);
	    }
	    if (index != null) {
	        index.getImpl().setCurrentScope(currentScope);
	    }
	}

    public OutputNamedExpression asOutput() {
        NamedExpression self = this.getSelf();
        OutputNamedExpression output = new OutputNamedExpression();
        output.setName(self.getName());
        output.setExpression(self.getExpression());
        output.setIndex(self.getIndex());
        return output;
    }
    
    // Checks whether this named expression requires collection,
    // bit string and/or real conversion for the given parameter.
    public boolean hasConversions(ElementReference parameter) {
        return this.getIsCollectionConversion(parameter) ||
               this.getIsBitStringConversion(parameter) ||
               this.getIsRealConversion(parameter);
    }
    
    // Derives isCollectionConversion for this named expression as an input for
    // the given parameter.
    public boolean getIsCollectionConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        Expression expression = self.getExpression();
        ElementReference lhsType = parameter.getImpl().getType();
        ElementReference rhsType = expression.getType(); 
        return rhsType != null && lhsType != null &&
            AssignableElementImpl.isCollectionConformant(
                    lhsType, parameter.getImpl().getUpper(), rhsType, expression.getUpper());
    }
    
    // Derives isBitStringConversion for this named expression as an input for
    // the given parameter.
    public boolean getIsBitStringConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        ElementReference lhsType = parameter.getImpl().getType();
        ElementReference rhsType = self.getExpression().getType(); 
        return rhsType != null && lhsType != null && 
            lhsType.getImpl().isBitString() &&
            (rhsType.getImpl().isInteger() ||
                    this.getIsCollectionConversion(parameter) &&
                    rhsType.getImpl().isIntegerCollection());
    }

    // Derives isRealConversion for this named expression as an input for
    // the given parameter.
    public boolean getIsRealConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        ElementReference lhsType = parameter.getImpl().getType();
        ElementReference rhsType = self.getExpression().getType(); 
        return rhsType != null && lhsType != null && 
            lhsType.getImpl().isReal() &&
            (rhsType.getImpl().isInteger() ||
                    this.getIsCollectionConversion(parameter) &&
                    rhsType.getImpl().isIntegerCollection());
    }
    
    // Checks whether Natural to UnlimitedNatural conversion is required
    // for this named expression as an input for the given parameter.
    public boolean isUnlimitedNaturalConversion(ElementReference parameter) {
        NamedExpression self = this.getSelf();
        ElementReference lhsType = parameter.getImpl().getType();
        ElementReference rhsType = self.getExpression().getType(); 
        return rhsType != null && lhsType != null && 
            lhsType.getImpl().isUnlimitedNatural() &&
            !lhsType.getImpl().isNatural() &&
            (rhsType.getImpl().isNatural() ||
                    this.getIsCollectionConversion(parameter) &&
                    rhsType.getImpl().isNaturalCollection());
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NamedExpression) {
            NamedExpression self = this.getSelf();
            NamedExpression baseNamedExpression = (NamedExpression)base;
            Expression expression = baseNamedExpression.getExpression();
            Expression index = baseNamedExpression.getIndex();
            self.setName(baseNamedExpression.getName());
            if (expression != null) {
                self.setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (index != null) {
                self.setIndex((Expression)index.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // NamedExpressionImpl
