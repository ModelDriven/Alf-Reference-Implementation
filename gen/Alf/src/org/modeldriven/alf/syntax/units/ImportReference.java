
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl;

/**
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReference extends SyntaxElement {

	public ImportReferenceImpl getImpl() {
		return (ImportReferenceImpl) this.impl;
	}

	public String getVisibility() {
		return this.getImpl().getVisibility();
	}

	public void setVisibility(String visibility) {
		this.getImpl().setVisibility(visibility);
	}

	public QualifiedName getReferentName() {
		return this.getImpl().getReferentName();
	}

	public void setReferentName(QualifiedName referentName) {
		this.getImpl().setReferentName(referentName);
	}

	public UnitDefinition getUnit() {
		return this.getImpl().getUnit();
	}

	public void setUnit(UnitDefinition unit) {
		this.getImpl().setUnit(unit);
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
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
			System.out.println(prefix + " referentName:");
			referentName.print(prefix + "  ");
		}
		UnitDefinition unit = this.getUnit();
		if (unit != null) {
			System.out.println(prefix + " unit:" + unit);
		}
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " /referent:" + referent);
		}
	}
} // ImportReference
