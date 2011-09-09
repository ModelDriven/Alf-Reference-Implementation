
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

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

import org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl;

/**
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReference extends SyntaxElement {

	public ImportReference() {
	}

	public ImportReference(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public ImportReference(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

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

	public void _deriveAll() {
		this.getReferent();
		super._deriveAll();
		QualifiedName referentName = this.getReferentName();
		if (referentName != null) {
			referentName.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.importReferenceReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"importReferenceReferentDerivation", this));
		}
		if (!this.importReferenceReferent()) {
			violations.add(new ConstraintViolation("importReferenceReferent",
					this));
		}
		QualifiedName referentName = this.getReferentName();
		if (referentName != null) {
			referentName.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" visibility:");
		s.append(this.getVisibility());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedName referentName = this.getReferentName();
		if (referentName != null) {
			System.out.println(prefix + " referentName:");
			referentName.print(prefix + "  ", includeDerived);
		}
		UnitDefinition unit = this.getUnit();
		if (unit != null) {
			System.out.println(prefix + " unit:"
					+ unit.toString(includeDerived));
		}
		if (includeDerived) {
			ElementReference referent = this.getReferent();
			if (referent != null) {
				System.out.println(prefix + " /referent:"
						+ referent.toString(includeDerived));
			}
		}
	}
} // ImportReference
