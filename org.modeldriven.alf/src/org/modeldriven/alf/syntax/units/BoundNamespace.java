/*******************************************************************************
 * Copyright 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.BoundElementReference;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.units.impl.BoundNamespaceImpl;

/**
 * A namespace that is bound within the context of a containing template.
 */
public class BoundNamespace extends NamespaceDefinition {

    public BoundNamespace() {
        this.impl = new BoundNamespaceImpl(this);
    }

    public BoundNamespace(Parser parser) {
        this();
        this.init(parser);
    }

    public BoundNamespace(ParsedElement element) {
        this();
        this.init(element);
    }

    @Override
    public BoundNamespaceImpl getImpl() {
        return (BoundNamespaceImpl) this.impl;
    }
    
    public BoundElementReference getReferent() {
        return this.getImpl().getReferent();
    }
    
    public void setReferent(BoundElementReference referent) {
        this.getImpl().setReferent(referent);
    }

    @Override
    public String _toString(boolean includeDerived) {
        return "BoundNamsepace referent:" + this.getReferent().toString(includeDerived);
    }
}
