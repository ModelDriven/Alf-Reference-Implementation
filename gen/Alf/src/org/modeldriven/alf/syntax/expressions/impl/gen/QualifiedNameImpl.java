
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

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

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedNameImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Boolean isAmbiguous = false;
	private String pathName = null; // DERIVED
	private Boolean isFeatureReference = null; // DERIVED
	private QualifiedName qualification = null; // DERIVED
	private FeatureReference disambiguation = null; // DERIVED
	private List<NameBinding> nameBinding = new ArrayList<NameBinding>();
	private Collection<ElementReference> referent = null; // DERIVED
	private NameBinding unqualifiedName = null; // DERIVED
	private QualifiedName templateName = null; // DERIVED

	public QualifiedNameImpl(QualifiedName self) {
		super(self);
	}

	public QualifiedName getSelf() {
		return (QualifiedName) this.self;
	}

	public Boolean getIsAmbiguous() {
		return this.isAmbiguous;
	}

	public void setIsAmbiguous(Boolean isAmbiguous) {
		this.isAmbiguous = isAmbiguous;
	}

	public String getPathName() {
		if (this.pathName == null) {
			this.setPathName(this.derivePathName());
		}
		return this.pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public Boolean getIsFeatureReference() {
		if (this.isFeatureReference == null) {
			this.setIsFeatureReference(this.deriveIsFeatureReference());
		}
		return this.isFeatureReference;
	}

	public void setIsFeatureReference(Boolean isFeatureReference) {
		this.isFeatureReference = isFeatureReference;
	}

	public QualifiedName getQualification() {
		if (this.qualification == null) {
			this.setQualification(this.deriveQualification());
		}
		return this.qualification;
	}

	public void setQualification(QualifiedName qualification) {
		this.qualification = qualification;
	}

	public FeatureReference getDisambiguation() {
		if (this.disambiguation == null) {
			this.setDisambiguation(this.deriveDisambiguation());
		}
		return this.disambiguation;
	}

	public void setDisambiguation(FeatureReference disambiguation) {
		this.disambiguation = disambiguation;
	}

	public List<NameBinding> getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(List<NameBinding> nameBinding) {
		this.nameBinding = nameBinding;
	}

	public void addNameBinding(NameBinding nameBinding) {
		this.nameBinding.add(nameBinding);
	}

	public Collection<ElementReference> getReferent() {
		if (this.referent == null) {
			this.setReferent(this.deriveReferent());
		}
		return this.referent;
	}

	public void setReferent(Collection<ElementReference> referent) {
		this.referent = referent;
	}

	public void addReferent(ElementReference referent) {
		this.referent.add(referent);
	}

	public NameBinding getUnqualifiedName() {
		if (this.unqualifiedName == null) {
			this.setUnqualifiedName(this.deriveUnqualifiedName());
		}
		return this.unqualifiedName;
	}

	public void setUnqualifiedName(NameBinding unqualifiedName) {
		this.unqualifiedName = unqualifiedName;
	}

	public QualifiedName getTemplateName() {
		if (this.templateName == null) {
			this.setTemplateName(this.deriveTemplateName());
		}
		return this.templateName;
	}

	public void setTemplateName(QualifiedName templateName) {
		this.templateName = templateName;
	}

	protected String derivePathName() {
		return null; // STUB
	}

	protected Boolean deriveIsFeatureReference() {
		return null; // STUB
	}

	protected QualifiedName deriveQualification() {
		return null; // STUB
	}

	protected FeatureReference deriveDisambiguation() {
		return null; // STUB
	}

	protected Collection<ElementReference> deriveReferent() {
		return null; // STUB
	}

	protected NameBinding deriveUnqualifiedName() {
		return null; // STUB
	}

	protected QualifiedName deriveTemplateName() {
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
