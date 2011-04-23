
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

import org.modeldriven.alf.syntax.units.impl.StereotypeAnnotationImpl;

/**
 * An annotation of a member definition indicating the application of a
 * stereotype (or one of a small number of special-case annotations).
 **/

public class StereotypeAnnotation extends SyntaxElement {

	public StereotypeAnnotation() {
		this.impl = new StereotypeAnnotationImpl(this);
	}

	public StereotypeAnnotationImpl getImpl() {
		return (StereotypeAnnotationImpl) this.impl;
	}

	public TaggedValueList getTaggedValues() {
		return this.getImpl().getTaggedValues();
	}

	public void setTaggedValues(TaggedValueList taggedValues) {
		this.getImpl().setTaggedValues(taggedValues);
	}

	public QualifiedNameList getNames() {
		return this.getImpl().getNames();
	}

	public void setNames(QualifiedNameList names) {
		this.getImpl().setNames(names);
	}

	public QualifiedName getStereotypeName() {
		return this.getImpl().getStereotypeName();
	}

	public void setStereotypeName(QualifiedName stereotypeName) {
		this.getImpl().setStereotypeName(stereotypeName);
	}

	public Stereotype getStereotype() {
		return this.getImpl().getStereotype();
	}

	public void setStereotype(Stereotype stereotype) {
		this.getImpl().setStereotype(stereotype);
	}

	/**
	 * Unless the stereotype name is "apply", "primitive" or "external" then the
	 * stereotype for a stereotype annotation is the stereotype denoted by the
	 * stereotype name.
	 **/
	public boolean stereotypeAnnotationStereotypeDerivation() {
		return this.getImpl().stereotypeAnnotationStereotypeDerivation();
	}

	/**
	 * The stereotype name of a stereotype annotation must either be one of
	 * "apply", "primitive" or "external", or it must denote a single stereotype
	 * from a profile applied to an enclosing package. The stereotype name does
	 * not need to be qualified if there is only one applied profile with a
	 * stereotype of that name or if the there is a standard UML profile with
	 * the name.
	 **/
	public boolean stereotypeAnnotationStereotypeName() {
		return this.getImpl().stereotypeAnnotationStereotypeName();
	}

	/**
	 * If the stereotype name of a stereotype annotation is "apply", then it
	 * must have a name list and all of the names in the list must resolve to
	 * profiles.
	 **/
	public boolean stereotypeAnnotationApply() {
		return this.getImpl().stereotypeAnnotationApply();
	}

	/**
	 * If the stereotype name of a stereotype annotation is "primitive", then it
	 * may not have tagged values or names.
	 **/
	public boolean stereotypeAnnotationPrimitive() {
		return this.getImpl().stereotypeAnnotationPrimitive();
	}

	/**
	 * If the stereotype name of a stereotype annotation is "external", then it
	 * may optionally have a single tagged value with the name "file" and no
	 * operator.
	 **/
	public boolean stereotypeAnnotationExternal() {
		return this.getImpl().stereotypeAnnotationExternal();
	}

	/**
	 * If a stereotype annotation has a stereotype and tagged values, then the
	 * each tagged value must have the name of an attribute of the stereotype
	 * and a value that is legally interpretable for the type of that attribute.
	 **/
	public boolean stereotypeAnnotationTaggedValues() {
		return this.getImpl().stereotypeAnnotationTaggedValues();
	}

	/**
	 * If a stereotype annotation has a stereotype and a list of names, then all
	 * the names in the list must resolve to visible model elements and the
	 * stereotype must have a single attribute with a (metaclass) type and
	 * multiplicity that are consistent with the types and number of the
	 * elements denoted by the given names.
	 **/
	public boolean stereotypeAnnotationNames() {
		return this.getImpl().stereotypeAnnotationNames();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.stereotypeAnnotationStereotypeDerivation()) {
			violations.add(new ConstraintViolation(
					"stereotypeAnnotationStereotypeDerivation", this));
		}
		if (!this.stereotypeAnnotationStereotypeName()) {
			violations.add(new ConstraintViolation(
					"stereotypeAnnotationStereotypeName", this));
		}
		if (!this.stereotypeAnnotationApply()) {
			violations.add(new ConstraintViolation("stereotypeAnnotationApply",
					this));
		}
		if (!this.stereotypeAnnotationPrimitive()) {
			violations.add(new ConstraintViolation(
					"stereotypeAnnotationPrimitive", this));
		}
		if (!this.stereotypeAnnotationExternal()) {
			violations.add(new ConstraintViolation(
					"stereotypeAnnotationExternal", this));
		}
		if (!this.stereotypeAnnotationTaggedValues()) {
			violations.add(new ConstraintViolation(
					"stereotypeAnnotationTaggedValues", this));
		}
		if (!this.stereotypeAnnotationNames()) {
			violations.add(new ConstraintViolation("stereotypeAnnotationNames",
					this));
		}
		TaggedValueList taggedValues = this.getTaggedValues();
		if (taggedValues != null) {
			taggedValues.checkConstraints(violations);
		}
		QualifiedNameList names = this.getNames();
		if (names != null) {
			names.checkConstraints(violations);
		}
		QualifiedName stereotypeName = this.getStereotypeName();
		if (stereotypeName != null) {
			stereotypeName.checkConstraints(violations);
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		TaggedValueList taggedValues = this.getTaggedValues();
		if (taggedValues != null) {
			System.out.println(prefix + " taggedValues:");
			taggedValues.print(prefix + "  ");
		}
		QualifiedNameList names = this.getNames();
		if (names != null) {
			System.out.println(prefix + " names:");
			names.print(prefix + "  ");
		}
		QualifiedName stereotypeName = this.getStereotypeName();
		if (stereotypeName != null) {
			System.out.println(prefix + " stereotypeName:");
			stereotypeName.print(prefix + "  ");
		}
		Stereotype stereotype = this.getStereotype();
		if (stereotype != null) {
			System.out.println(prefix + " /stereotype:" + stereotype);
		}
	}
} // StereotypeAnnotation
