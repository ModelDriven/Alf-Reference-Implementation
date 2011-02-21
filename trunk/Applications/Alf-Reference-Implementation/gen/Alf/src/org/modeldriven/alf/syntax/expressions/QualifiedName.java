
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.QualifiedNameImpl;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedName extends SyntaxElement {

	private Boolean isAmbiguous = false;
	private String pathName = null; // DERIVED
	private Boolean isFeatureReference = null; // DERIVED
	private QualifiedName qualification = null; // DERIVED
	private FeatureReference disambiguation = null; // DERIVED
	private ArrayList<NameBinding> nameBinding = new ArrayList<NameBinding>();
	private ArrayList<ElementReference> referent = null; // DERIVED
	private NameBinding unqualifiedName = null; // DERIVED
	private QualifiedName templateName = null; // DERIVED

	public QualifiedName() {
		this.impl = new QualifiedNameImpl(this);
	}

	public QualifiedNameImpl getImpl() {
		return (QualifiedNameImpl) this.impl;
	}

	public Boolean getIsAmbiguous() {
		return this.isAmbiguous;
	}

	public void setIsAmbiguous(Boolean isAmbiguous) {
		this.isAmbiguous = isAmbiguous;
	}

	public String getPathName() {
		if (this.pathName == null) {
			this.pathName = this.getImpl().derivePathName();
		}
		return this.pathName;
	}

	public Boolean getIsFeatureReference() {
		if (this.isFeatureReference == null) {
			this.isFeatureReference = this.getImpl().deriveIsFeatureReference();
		}
		return this.isFeatureReference;
	}

	public QualifiedName getQualification() {
		if (this.qualification == null) {
			this.qualification = this.getImpl().deriveQualification();
		}
		return this.qualification;
	}

	public FeatureReference getDisambiguation() {
		if (this.disambiguation == null) {
			this.disambiguation = this.getImpl().deriveDisambiguation();
		}
		return this.disambiguation;
	}

	public ArrayList<NameBinding> getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(ArrayList<NameBinding> nameBinding) {
		this.nameBinding = nameBinding;
	}

	public void addNameBinding(NameBinding nameBinding) {
		this.nameBinding.add(nameBinding);
	}

	public ArrayList<ElementReference> getReferent() {
		if (this.referent == null) {
			this.referent = this.getImpl().deriveReferent();
		}
		return this.referent;
	}

	public NameBinding getUnqualifiedName() {
		if (this.unqualifiedName == null) {
			this.unqualifiedName = this.getImpl().deriveUnqualifiedName();
		}
		return this.unqualifiedName;
	}

	public QualifiedName getTemplateName() {
		if (this.templateName == null) {
			this.templateName = this.getImpl().deriveTemplateName();
		}
		return this.templateName;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName qualification = this.getQualification();
		if (qualification != null) {
			System.out.println(prefix + " /qualification:" + qualification);
		}
		FeatureReference disambiguation = this.getDisambiguation();
		if (disambiguation != null) {
			System.out.println(prefix + " /disambiguation:" + disambiguation);
		}
		ArrayList<NameBinding> nameBinding = this.getNameBinding();
		if (nameBinding != null) {
			if (nameBinding.size() > 0) {
				System.out.println(prefix + " nameBinding:");
			}
			for (NameBinding item : this.getNameBinding()) {
				if (item != null) {
					item.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		ArrayList<ElementReference> referent = this.getReferent();
		if (referent != null) {
			if (referent.size() > 0) {
				System.out.println(prefix + " /referent:");
			}
			for (ElementReference item : this.getReferent()) {
				System.out.println(prefix + "  " + item);
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
