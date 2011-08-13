
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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

	/**
	 * Collection conversion is required if the type of the corresponding
	 * parameter is a collection class and the type of the argument expression
	 * is not.
	 **/
	protected Boolean deriveIsCollectionConversion() {
	    // This needs to be set externally.
		return null;
	}

	/**
	 * Bit string conversion is required if the type of the type of the
	 * corresponding parameter is BitString, or a collection class instantiated
	 * with a BitString type, and the type of the argument expression is not
	 * BitString.
	 **/
	protected Boolean deriveIsBitStringConversion() {
	    // This needs to be set externally.
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
