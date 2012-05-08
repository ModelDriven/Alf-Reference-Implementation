
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.syntax.common.*;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElementImpl extends SyntaxElementImpl {

    private Collection<String> documentation = new ArrayList<String>();

	public DocumentedElementImpl(DocumentedElement self) {
		super(self);
	}

	@Override
	public DocumentedElement getSelf() {
		return (DocumentedElement) this.self;
	}

    public Collection<String> getDocumentation() {
        return this.documentation;
    }

    public void setDocumentation(Collection<String> documentation) {
        this.documentation = documentation;
    }

    public void addDocumentation(String documentation) {
        this.documentation.add(documentation);
    }

} // DocumentedElementImpl
