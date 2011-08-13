
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

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
