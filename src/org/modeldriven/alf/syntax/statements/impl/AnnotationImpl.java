
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.statements.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An identified modification to the behavior of an annotated statement.
 **/

public class AnnotationImpl extends SyntaxElementImpl {

	private String identifier = "";
	private Collection<String> argument = new ArrayList<String>();

	public AnnotationImpl(Annotation self) {
		super(self);
	}

	@Override
	public Annotation getSelf() {
		return (Annotation) this.self;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Collection<String> getArgument() {
		return this.argument;
	}

	public void setArgument(Collection<String> argument) {
		this.argument = argument;
	}

	public void addArgument(String argument) {
		this.argument.add(argument);
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof Annotation) {
            Annotation self = this.getSelf();
            Annotation baseAnnotation = (Annotation)base;
            self.setIdentifier(baseAnnotation.getIdentifier());
            self.setArgument(baseAnnotation.getArgument());
        }
    }
    
} // AnnotationImpl
