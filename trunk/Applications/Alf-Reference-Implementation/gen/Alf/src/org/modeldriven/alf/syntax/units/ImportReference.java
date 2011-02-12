
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl;

/**
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReference extends SyntaxElement {

	private String visibility = "";
	private QualifiedName referentName = null;
	private UnitDefinition unit = null;
	private ElementReference referent = null; // DERIVED

	public ImportReferenceImpl getImpl() {
		return (ImportReferenceImpl) this.impl;
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
			this.referent = this.getImpl().deriveReferent();
		}
		return this.referent;
	}

	/**
	 * The referent of an import reference is the element denoted by the
	 * referent name.
	 **/
	public boolean importReferenceReferentDerivation() {
		return this.getImpl().importReferenceReferentDerivation();
	}

	/**
	 * The referent name of an import reference must resolve to a single element
	 * with public or empty visibility.
	 **/
	public boolean importReferenceReferent() {
		return this.getImpl().importReferenceReferent();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" visibility:");
		s.append(this.getVisibility());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName referentName = this.getReferentName();
		if (referentName != null) {
			referentName.print(prefix + " ");
		}
		UnitDefinition unit = this.getUnit();
		if (unit != null) {
			unit.print(prefix + " ");
		}
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " /" + referent);
		}
	}
} // ImportReference
