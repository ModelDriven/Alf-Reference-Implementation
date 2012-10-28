
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
 * An annotation of a member definition indicating the application of a
 * stereotype (or one of a small number of special-case annotations).
 **/

public class StereotypeAnnotationImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private TaggedValueList taggedValues = null;
	private QualifiedNameList names = null;
	private QualifiedName stereotypeName = null;
	private Stereotype stereotype = null; // DERIVED

	public StereotypeAnnotationImpl(StereotypeAnnotation self) {
		super(self);
	}

	public StereotypeAnnotation getSelf() {
		return (StereotypeAnnotation) this.self;
	}

	public TaggedValueList getTaggedValues() {
		return this.taggedValues;
	}

	public void setTaggedValues(TaggedValueList taggedValues) {
		this.taggedValues = taggedValues;
	}

	public QualifiedNameList getNames() {
		return this.names;
	}

	public void setNames(QualifiedNameList names) {
		this.names = names;
	}

	public QualifiedName getStereotypeName() {
		return this.stereotypeName;
	}

	public void setStereotypeName(QualifiedName stereotypeName) {
		this.stereotypeName = stereotypeName;
	}

	public Stereotype getStereotype() {
		if (this.stereotype == null) {
			this.setStereotype(this.deriveStereotype());
		}
		return this.stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	protected Stereotype deriveStereotype() {
		return null; // STUB
	}

	/**
	 * Unless the stereotype name is "apply", "primitive" or "external" then the
	 * stereotype for a stereotype annotation is the stereotype denoted by the
	 * stereotype name.
	 **/
	public boolean stereotypeAnnotationStereotypeDerivation() {
		this.getSelf().getStereotype();
		return true;
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
		return true;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "apply", then it
	 * must have a name list and all of the names in the list must resolve to
	 * profiles.
	 **/
	public boolean stereotypeAnnotationApply() {
		return true;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "primitive", then it
	 * may not have tagged values or names.
	 **/
	public boolean stereotypeAnnotationPrimitive() {
		return true;
	}

	/**
	 * If the stereotype name of a stereotype annotation is "external", then it
	 * may optionally have a single tagged value with the name "file" and no
	 * operator.
	 **/
	public boolean stereotypeAnnotationExternal() {
		return true;
	}

	/**
	 * If a stereotype annotation has a stereotype and tagged values, then the
	 * each tagged value must have the name of an attribute of the stereotype
	 * and a value that is legally interpretable for the type of that attribute.
	 **/
	public boolean stereotypeAnnotationTaggedValues() {
		return true;
	}

	/**
	 * If a stereotype annotation has a stereotype and a list of names, then all
	 * the names in the list must resolve to visible model elements and the
	 * stereotype must have a single attribute with a (metaclass) type and
	 * multiplicity that are consistent with the types and number of the
	 * elements denoted by the given names.
	 **/
	public boolean stereotypeAnnotationNames() {
		return true;
	}

} // StereotypeAnnotationImpl
