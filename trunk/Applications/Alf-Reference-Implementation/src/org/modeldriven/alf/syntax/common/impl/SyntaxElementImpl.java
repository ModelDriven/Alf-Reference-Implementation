
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.syntax.common.*;

/**
 * A syntax element synthesized in an abstract syntax tree, along with any
 * additional information determined during static semantic analysis.
 **/

public abstract class SyntaxElementImpl {

	protected SyntaxElement self;
	
	public SyntaxElementImpl(SyntaxElement self) {
		this.self = self;
	}

	public SyntaxElement getSelf() {
		return (SyntaxElement) this.self;
	}

    // The base to which this syntax element was bound due to a template binding.
    private SyntaxElement base = null;

    public SyntaxElement getBase() {
        return this.base;
    }
    
    public void setBase(SyntaxElement base) {
        this.base = base;
    }
    
    // The implementation mapping used for this syntax element.
	private Mapping mapping = null;

    public Mapping getMapping() {
        return this.mapping;
    }
    
    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

	@Override
	public String toString() {
	    return this.toString(false);
	}
	
	public String toString(boolean includeDerived) {
	    return this.getSelf()._toString(includeDerived);
	}
	
	public void deriveAll() {
	    this.getSelf()._deriveAll();
	}
	
	/*
	 * Helper Methods
	 */

    /**
     * Create a binding of this element to a given set of template arguments.
     */
    public SyntaxElement bind(
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        SyntaxElement self = this.getSelf();
        SyntaxElement boundElement = null;
        try {
            boundElement = self.getClass().newInstance();
        } catch (Exception e) {
            System.out.println("Error binding " + 
                    self.getClass().getSimpleName() + ": " + e);
            return null;
        }
        boundElement.getImpl().bindTo(self, templateParameters, templateArguments);
        return boundElement;
    }
    
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        this.setBase(base);
        this.getSelf().setParserInfo(
                base.getFileName() + "<" + this.getSelf().hashCode() + ">", 
                base.getLine(), 
                base.getColumn());
    }
    
} // SyntaxElementImpl
