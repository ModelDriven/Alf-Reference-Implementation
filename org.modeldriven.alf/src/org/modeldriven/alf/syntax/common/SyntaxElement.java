/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;

/**
 * A syntax element synthesized in an abstract syntax tree, along with any
 * additional information determined during static semantic analysis.
 **/

public abstract class SyntaxElement extends ParsedElement {

	protected SyntaxElementImpl impl;

    public SyntaxElementImpl getImpl() {
        return (SyntaxElementImpl) this.impl;
    }

    public Collection<ExternalElementReference> getExternalReferences() {
        Collection<ExternalElementReference> references = new ArrayList<ExternalElementReference>();
        this.addExternalReferences(references);
        return references;
    }

    public void addExternalReferences(Collection<ExternalElementReference> references) {
        this.getImpl().addExternalReferences(references);
    }

    public void _addExternalReferences(Collection<ExternalElementReference> references) {
    }
    
    public static void addExternalReference(
            Collection<ExternalElementReference> externalReferences, 
            ElementReference reference) {
        if (reference != null) {
            reference = reference.getImpl().getReferent();
            if (reference instanceof ExternalElementReference &&
                    !reference.getImpl().isContainedIn(externalReferences)) {
                externalReferences.add((ExternalElementReference)reference);
            }
            addExternalReferences(externalReferences, reference.getImpl().getTemplateActuals());
        }
    }
    
    public static void addExternalReferences(
            Collection<ExternalElementReference> externalReferences, 
            Collection<? extends ElementReference> references) {
        if (references != null) {
            for (ElementReference reference: references) {
                addExternalReference(externalReferences, reference);
            }
        }
    }
    
    public static void addExternalReferencesFor(
            Collection<ExternalElementReference> references, 
            SyntaxElement element) {
        if (element != null) {
            element.addExternalReferences(references);
        }
    }

    public static void addExternalReferencesFor(
            Collection<ExternalElementReference> references, 
            Collection<? extends SyntaxElement> elements) {
        if (elements != null) {
            for (SyntaxElement element: elements) {
                element.addExternalReferences(references);
            }
        }
    }

    public void deriveAll() {
		this.getImpl().deriveAll();
	}

	public void _deriveAll() {
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
	}

	public String getId() {
		return Integer.toHexString(this.hashCode());
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.getId() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}
	
    public boolean equals(Object other) {
        return this.getImpl().equals(other instanceof SyntaxElement? 
                ((SyntaxElement)other).getImpl(): other);
    }

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		System.out.println(prefix + "[" + this.getId() + "]"
				+ this._toString(includeDerived));
	}
} // SyntaxElement
