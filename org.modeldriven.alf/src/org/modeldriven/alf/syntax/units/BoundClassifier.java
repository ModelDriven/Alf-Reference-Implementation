/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import java.util.List;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.units.impl.BoundClassifierImpl;

/**
 * A classifier resulting from the binding of a template to its actual parameters.
 */
public class BoundClassifier extends ClassifierDefinition {

    public BoundClassifier() {
        this.impl = new BoundClassifierImpl(this);
    }

    public BoundClassifier(Parser parser) {
        this();
        this.init(parser);
    }

    public BoundClassifier(ParsedElement element) {
        this();
        this.init(element);
    }

    @Override
    public BoundClassifierImpl getImpl() {
        return (BoundClassifierImpl) this.impl;
    }
    
    public ElementReference getTemplate() {
        return this.getImpl().getTemplate();
    }
    
    public void setTemplate(ElementReference template) {
        this.getImpl().setTemplate(template);
    }
    
    public List<ElementReference> getActual() {
        return this.getImpl().getActual();
    }
    
    public void setActual(List<ElementReference> actual) {
        this.getImpl().setActual(actual);
    }
    
    public void addActual(ElementReference actual) {
        this.getImpl().addActual(actual);
    }
    
    public ElementReference getReferent() {
        return this.getImpl().getReferent();
    }
    
    public void setReferent(ElementReference referent) {
        this.getImpl().setReferent(referent);
    }

    @Override
    public String _toString(boolean includeDerived) {
        return "BoundClassifier template:" + 
                this.getTemplate().toString(includeDerived) +
                (includeDerived? " /referent:" + this.getReferent(): "");
    }

    @Override
    public void print(String prefix, boolean includeDerived) {
        super.print(prefix, includeDerived);
        List<ElementReference> actual = this.getActual();
        if (actual != null && actual.size() > 0) {
            System.out.println(prefix + " actual:");
            for (ElementReference _actual: actual) {
                System.out.println(prefix + " " + _actual._toString(includeDerived));
            }
        }
    }
}
