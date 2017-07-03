/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import java.util.Collection;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.impl.BoundElementReferenceImpl;

/**
 * A reference to an element as bound within a template instantiation.
 *
 */
public class BoundElementReference extends ElementReference {

    public BoundElementReference() {
        this.impl = new BoundElementReferenceImpl(this);
    }

    public BoundElementReference(Parser parser) {
        this();
        this.init(parser);
    }

    public BoundElementReference(ParsedElement element) {
        this();
        this.init(element);
    }

    public BoundElementReferenceImpl getImpl() {
        return (BoundElementReferenceImpl) this.impl;
    }

    public ElementReference getReferent() {
        return this.getImpl().getReferent();
    }
    
    public void setReferent(ElementReference referent) {
        this.getImpl().setReferent(referent);
    }

    public ElementReference getNamespace() {
        return this.getImpl().getNamespace();
    }
    
    public void setNamespace(ElementReference namespace) {
        this.getImpl().setNamespace(namespace);
    }

    public ElementReference getTemplateBinding() {
        return this.getImpl().getTemplateBinding();
    }
    
    public void setTemplateBinding(ElementReference templateBinding) {
        this.getImpl().setTemplateBinding(templateBinding);
    }
    
    public void _deriveAll() {
        super._deriveAll();
    }

    public void checkConstraints(Collection<ConstraintViolation> violations) {
        super.checkConstraints(violations);
    }
    
    @Override
    public void print(String prefix, boolean includeDerived) {
        super.print(prefix, includeDerived);
        ElementReference referent = this.getReferent();
        ElementReference namespace = this.getNamespace();
        ElementReference templateBinding = this.getTemplateBinding();
        if (referent != null) {
            System.out.println(prefix + " referent:"
                    + referent.toString(includeDerived));
        }
        if (namespace != null) {
            System.out.println(prefix + " namespace:"
                    + namespace.toString(includeDerived));
        }
        if (templateBinding != null) {
            System.out.println(prefix + " templateBinding:"
                    + templateBinding.toString(includeDerived));
        }
    }
}
