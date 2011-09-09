
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl.gen;

import org.modeldriven.alf.parser.AlfParser;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElementImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Collection<String> documentation = new ArrayList<String>();

	public DocumentedElementImpl(DocumentedElement self) {
		super(self);
	}

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
