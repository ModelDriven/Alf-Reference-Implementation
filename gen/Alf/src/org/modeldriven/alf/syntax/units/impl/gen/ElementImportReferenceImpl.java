
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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
import java.util.TreeSet;

/**
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReferenceImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ImportReferenceImpl {

	private String alias = "";

	public ElementImportReferenceImpl(ElementImportReference self) {
		super(self);
	}

	public ElementImportReference getSelf() {
		return (ElementImportReference) this.self;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * The referent of an element import reference must be a packageable
	 * element.
	 **/
	public boolean elementImportReferenceReferent() {
		return true;
	}

} // ElementImportReferenceImpl
