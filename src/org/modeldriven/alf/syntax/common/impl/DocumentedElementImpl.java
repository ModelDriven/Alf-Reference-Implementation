
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
