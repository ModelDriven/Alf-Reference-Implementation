
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedNameImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public QualifiedNameImpl(QualifiedName self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.QualifiedName getSelf() {
		return (QualifiedName) this.self;
	}

	public String derivePathName() {
		return null; // STUB
	}

	public Boolean deriveIsFeatureReference() {
		return null; // STUB
	}

	public QualifiedName deriveQualification() {
		return null; // STUB
	}

	public FeatureReference deriveDisambiguation() {
		return null; // STUB
	}

	public ArrayList<ElementReference> deriveReferent() {
		return null; // STUB
	}

	public NameBinding deriveUnqualifiedName() {
		return null; // STUB
	}

	public QualifiedName deriveTemplateName() {
		return null; // STUB
	}

	/**
	 * The unqualified name of a qualified name is the last name binding.
	 **/
	public boolean qualifiedNameUnqualifiedNameDerivation() {
		this.getSelf().getUnqualifiedName();
		return true;
	}

	/**
	 * The path name for a qualified name consists of the string representation
	 * of each of the name bindings, separated by "::" punctuation. The string
	 * representation of a name binding is its name followed by the
	 * representation of its template binding, if it has one. The string
	 * representation of a positional template binding consists of an ordered
	 * list of the path names of its argument qualified names separated by
	 * commas, all surrounded by the angle brackets "<" and ">". The string
	 * representation of a named template binding consists of an ordered list of
	 * its template parameter substitutions, each consisting of the formal
	 * parameter name followed by "=>" followed by the path name of the argument
	 * qualified name, separated by commas, all surrounded by the angle brackets
	 * "<" and ">".
	 **/
	public boolean qualifiedNamePathNameDerivation() {
		this.getSelf().getPathName();
		return true;
	}

	/**
	 * A qualified name is a feature reference is its disambiguation is not
	 * empty.
	 **/
	public boolean qualifiedNameIsFeatureReferenceDerivation() {
		this.getSelf().getIsFeatureReference();
		return true;
	}

	/**
	 * The qualification of a qualified name is a empty if the qualified name
	 * has only one name binding. Otherwise it is the qualified name consisting
	 * of all the name bindings of the original qualified name except for the
	 * last one. The qualification of a qualified name is considered ambiguous
	 * if the qualified name is ambiguous and has more than two name bindings.
	 **/
	public boolean qualifiedNameQualificationDerivation() {
		this.getSelf().getQualification();
		return true;
	}

	/**
	 * If a qualified name is not ambiguous or it resolves to a namespace, then
	 * it is has no disambiguation. Otherwise, its disambiguation is a feature
	 * reference with a name given by the unqualified name of the qualified name
	 * and a target expression determined by the disambiguation of the
	 * qualification of the qualified name.
	 **/
	public boolean qualifiedNameDisambiguationDerivation() {
		this.getSelf().getDisambiguation();
		return true;
	}

	/**
	 * The referents of a qualified name are the elements to which the name may
	 * resolve in the current scope, according to the UML rules for namespaces
	 * and named elements.
	 **/
	public boolean qualifiedNameReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * If a qualified name is a local name, then the reference must be within
	 * the same local scope as the definition of the named element.
	 **/
	public boolean qualifiedNameLocalName() {
		return true;
	}

	/**
	 * If a qualified name is an unqualified, non-local name, then it must be
	 * visible in the current scope of the use of the name.
	 **/
	public boolean qualifiedNameNonLocalUnqualifiedName() {
		return true;
	}

	/**
	 * If a qualified name has a qualification, then its unqualified name must
	 * name an element of the namespace named by the qualification, where the
	 * first name in the qualification must name an element of the current
	 * scope.
	 **/
	public boolean qualifiedNameQualifiedResolution() {
		return true;
	}

	/**
	 * If the unqualified name of a qualified name has a template binding, then
	 * the template name must resolve to a template. The template binding must
	 * have an argument name for each of the template parameters and each
	 * argument name must resolve to a classifier. If the template parameter has
	 * constraining classifiers, then the referent of the corresponding argument
	 * name must conform to all those constraining classifiers.
	 **/
	public boolean qualifiedNameTemplateBinding() {
		return true;
	}

	/**
	 * If the last name binding in a qualified name has a template binding, then
	 * the template name is a qualified name with the same template bindings as
	 * the original qualified name, but with the template binding removed on the
	 * last name binding.
	 **/
	public boolean qualifiedNameTemplateNameDerivation() {
		this.getSelf().getTemplateName();
		return true;
	}

} // QualifiedNameImpl
