
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
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReferenceImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private String visibility = "";
	private QualifiedName referentName = null;
	private UnitDefinition unit = null;
	private ElementReference referent = null; // DERIVED

	public ImportReferenceImpl(ImportReference self) {
		super(self);
	}

	public ImportReference getSelf() {
		return (ImportReference) this.self;
	}

	public String getVisibility() {
		return this.visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public QualifiedName getReferentName() {
		return this.referentName;
	}

	public void setReferentName(QualifiedName referentName) {
		this.referentName = referentName;
	}

	public UnitDefinition getUnit() {
		return this.unit;
	}

	public void setUnit(UnitDefinition unit) {
		this.unit = unit;
	}

	public ElementReference getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	protected ElementReference deriveReferent() {
		return null; // STUB
	}

	/**
	 * The referent of an import reference is the element denoted by the
	 * referent name.
	 **/
	public boolean importReferenceReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * The referent name of an import reference must resolve to a single element
	 * with public or empty visibility.
	 **/
	public boolean importReferenceReferent() {
		return true;
	}

} // ImportReferenceImpl
