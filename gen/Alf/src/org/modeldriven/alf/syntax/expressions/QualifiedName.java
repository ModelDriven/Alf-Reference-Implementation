
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

import java.util.ArrayList;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedName extends SyntaxElement {

	private boolean isAmbiguous = false;
	private String pathName = ""; // DERIVED
	private boolean isFeatureReference = false; // DERIVED
	private QualifiedName qualification = null; // DERIVED
	private FeatureReference disambiguation = null; // DERIVED
	private ArrayList<NameBinding> nameBinding = new ArrayList<NameBinding>();
	private ArrayList<ElementReference> referent = new ArrayList<ElementReference>(); // DERIVED
	private NameBinding unqualifiedName = null; // DERIVED
	private QualifiedName templateName = null; // DERIVED

	public boolean getIsAmbiguous() {
		return this.isAmbiguous;
	}

	public void setIsAmbiguous(boolean isAmbiguous) {
		this.isAmbiguous = isAmbiguous;
	}

	public String getPathName() {
		return this.pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public boolean getIsFeatureReference() {
		return this.isFeatureReference;
	}

	public void setIsFeatureReference(boolean isFeatureReference) {
		this.isFeatureReference = isFeatureReference;
	}

	public QualifiedName getQualification() {
		return this.qualification;
	}

	public void setQualification(QualifiedName qualification) {
		this.qualification = qualification;
	}

	public FeatureReference getDisambiguation() {
		return this.disambiguation;
	}

	public void setDisambiguation(FeatureReference disambiguation) {
		this.disambiguation = disambiguation;
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
		return this.referent;
	}

	public void setReferent(ArrayList<ElementReference> referent) {
		this.referent = referent;
	}

	public void addReferent(ElementReference referent) {
		this.referent.add(referent);
	}

	public NameBinding getUnqualifiedName() {
		return this.unqualifiedName;
	}

	public void setUnqualifiedName(NameBinding unqualifiedName) {
		this.unqualifiedName = unqualifiedName;
	}

	public QualifiedName getTemplateName() {
		return this.templateName;
	}

	public void setTemplateName(QualifiedName templateName) {
		this.templateName = templateName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAmbiguous:");
		s.append(this.isAmbiguous);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (NameBinding nameBinding : this.getNameBinding()) {
			if (nameBinding != null) {
				nameBinding.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // QualifiedName
