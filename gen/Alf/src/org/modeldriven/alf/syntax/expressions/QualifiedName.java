
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.QualifiedNameImpl;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedName extends SyntaxElement {

	public QualifiedName() {
		this.impl = new QualifiedNameImpl(this);
	}

	public QualifiedNameImpl getImpl() {
		return (QualifiedNameImpl) this.impl;
	}

	public Boolean getIsAmbiguous() {
		return this.getImpl().getIsAmbiguous();
	}

	public void setIsAmbiguous(Boolean isAmbiguous) {
		this.getImpl().setIsAmbiguous(isAmbiguous);
	}

	public String getPathName() {
		return this.getImpl().getPathName();
	}

	public void setPathName(String pathName) {
		this.getImpl().setPathName(pathName);
	}

	public Boolean getIsFeatureReference() {
		return this.getImpl().getIsFeatureReference();
	}

	public void setIsFeatureReference(Boolean isFeatureReference) {
		this.getImpl().setIsFeatureReference(isFeatureReference);
	}

	public QualifiedName getQualification() {
		return this.getImpl().getQualification();
	}

	public void setQualification(QualifiedName qualification) {
		this.getImpl().setQualification(qualification);
	}

	public FeatureReference getDisambiguation() {
		return this.getImpl().getDisambiguation();
	}

	public void setDisambiguation(FeatureReference disambiguation) {
		this.getImpl().setDisambiguation(disambiguation);
	}

	public List<NameBinding> getNameBinding() {
		return this.getImpl().getNameBinding();
	}

	public void setNameBinding(List<NameBinding> nameBinding) {
		this.getImpl().setNameBinding(nameBinding);
	}

	public void addNameBinding(NameBinding nameBinding) {
		this.getImpl().addNameBinding(nameBinding);
	}

	public Collection<ElementReference> getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(Collection<ElementReference> referent) {
		this.getImpl().setReferent(referent);
	}

	public void addReferent(ElementReference referent) {
		this.getImpl().addReferent(referent);
	}

	public NameBinding getUnqualifiedName() {
		return this.getImpl().getUnqualifiedName();
	}

	public void setUnqualifiedName(NameBinding unqualifiedName) {
		this.getImpl().setUnqualifiedName(unqualifiedName);
	}

	public QualifiedName getTemplateName() {
		return this.getImpl().getTemplateName();
	}

	public void setTemplateName(QualifiedName templateName) {
		this.getImpl().setTemplateName(templateName);
	}

	/**
	 * The unqualified name of a qualified name is the last name binding.
	 **/
	public boolean qualifiedNameUnqualifiedNameDerivation() {
		return this.getImpl().qualifiedNameUnqualifiedNameDerivation();
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
		return this.getImpl().qualifiedNamePathNameDerivation();
	}

	/**
	 * A qualified name is a feature reference is its disambiguation is not
	 * empty.
	 **/
	public boolean qualifiedNameIsFeatureReferenceDerivation() {
		return this.getImpl().qualifiedNameIsFeatureReferenceDerivation();
	}

	/**
	 * The qualification of a qualified name is a empty if the qualified name
	 * has only one name binding. Otherwise it is the qualified name consisting
	 * of all the name bindings of the original qualified name except for the
	 * last one. The qualification of a qualified name is considered ambiguous
	 * if the qualified name is ambiguous and has more than two name bindings.
	 **/
	public boolean qualifiedNameQualificationDerivation() {
		return this.getImpl().qualifiedNameQualificationDerivation();
	}

	/**
	 * If a qualified name is not ambiguous or it resolves to a namespace, then
	 * it is has no disambiguation. Otherwise, its disambiguation is a feature
	 * reference with a name given by the unqualified name of the qualified name
	 * and a target expression determined by the disambiguation of the
	 * qualification of the qualified name.
	 **/
	public boolean qualifiedNameDisambiguationDerivation() {
		return this.getImpl().qualifiedNameDisambiguationDerivation();
	}

	/**
	 * The referents of a qualified name are the elements to which the name may
	 * resolve in the current scope, according to the UML rules for namespaces
	 * and named elements.
	 **/
	public boolean qualifiedNameReferentDerivation() {
		return this.getImpl().qualifiedNameReferentDerivation();
	}

	/**
	 * If a qualified name is a local name, then the reference must be within
	 * the same local scope as the definition of the named element.
	 **/
	public boolean qualifiedNameLocalName() {
		return this.getImpl().qualifiedNameLocalName();
	}

	/**
	 * If a qualified name is an unqualified, non-local name, then it must be
	 * visible in the current scope of the use of the name.
	 **/
	public boolean qualifiedNameNonLocalUnqualifiedName() {
		return this.getImpl().qualifiedNameNonLocalUnqualifiedName();
	}

	/**
	 * If a qualified name has a qualification, then its unqualified name must
	 * name an element of the namespace named by the qualification, where the
	 * first name in the qualification must name an element of the current
	 * scope.
	 **/
	public boolean qualifiedNameQualifiedResolution() {
		return this.getImpl().qualifiedNameQualifiedResolution();
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
		return this.getImpl().qualifiedNameTemplateBinding();
	}

	/**
	 * If the last name binding in a qualified name has a template binding, then
	 * the template name is a qualified name with the same template bindings as
	 * the original qualified name, but with the template binding removed on the
	 * last name binding.
	 **/
	public boolean qualifiedNameTemplateNameDerivation() {
		return this.getImpl().qualifiedNameTemplateNameDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.qualifiedNameUnqualifiedNameDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameUnqualifiedNameDerivation", this));
		}
		if (!this.qualifiedNamePathNameDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNamePathNameDerivation", this));
		}
		if (!this.qualifiedNameIsFeatureReferenceDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameIsFeatureReferenceDerivation", this));
		}
		if (!this.qualifiedNameQualificationDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameQualificationDerivation", this));
		}
		if (!this.qualifiedNameDisambiguationDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameDisambiguationDerivation", this));
		}
		if (!this.qualifiedNameReferentDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameReferentDerivation", this));
		}
		if (!this.qualifiedNameLocalName()) {
			violations.add(new ConstraintViolation("qualifiedNameLocalName",
					this));
		}
		if (!this.qualifiedNameNonLocalUnqualifiedName()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameNonLocalUnqualifiedName", this));
		}
		if (!this.qualifiedNameQualifiedResolution()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameQualifiedResolution", this));
		}
		if (!this.qualifiedNameTemplateBinding()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameTemplateBinding", this));
		}
		if (!this.qualifiedNameTemplateNameDerivation()) {
			violations.add(new ConstraintViolation(
					"qualifiedNameTemplateNameDerivation", this));
		}
		FeatureReference disambiguation = this.getDisambiguation();
		if (disambiguation != null) {
			disambiguation.checkConstraints(violations);
		}
		for (Object _nameBinding : this.getNameBinding().toArray()) {
			((NameBinding) _nameBinding).checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" isAmbiguous:");
		s.append(this.getIsAmbiguous());
		String pathName = this.getPathName();
		if (pathName != null) {
			s.append(" /pathName:");
			s.append(pathName);
		}
		Boolean isFeatureReference = this.getIsFeatureReference();
		if (isFeatureReference != null) {
			s.append(" /isFeatureReference:");
			s.append(isFeatureReference);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName qualification = this.getQualification();
		if (qualification != null) {
			System.out.println(prefix + " /qualification:" + qualification);
		}
		FeatureReference disambiguation = this.getDisambiguation();
		if (disambiguation != null) {
			System.out.println(prefix + " /disambiguation:");
			disambiguation.print(prefix + "  ");
		}
		List<NameBinding> nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			if (nameBinding.size() > 0) {
				System.out.println(prefix + " nameBinding:");
			}
			for (Object _object : nameBinding.toArray()) {
				NameBinding _nameBinding = (NameBinding) _object;
				if (_nameBinding != null) {
					_nameBinding.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		Collection<ElementReference> referent = this.getReferent();
		if (referent != null) {
			if (referent.size() > 0) {
				System.out.println(prefix + " /referent:");
			}
			for (Object _object : referent.toArray()) {
				ElementReference _referent = (ElementReference) _object;
				System.out.println(prefix + "  " + _referent);
			}
		}
		NameBinding unqualifiedName = this.getUnqualifiedName();
		if (unqualifiedName != null) {
			System.out.println(prefix + " /unqualifiedName:" + unqualifiedName);
		}
		QualifiedName templateName = this.getTemplateName();
		if (templateName != null) {
			System.out.println(prefix + " /templateName:" + templateName);
		}
	}
} // QualifiedName
