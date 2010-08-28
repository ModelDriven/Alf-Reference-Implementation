
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

/**
 * The definition of a classifier.
 **/

public abstract class ClassifierDefinition extends NamespaceDefinition {

	private boolean isAbstract = false;
	private QualifiedNameList specialization = null;
	private ArrayList<ElementReference> specializationReferent = new ArrayList<ElementReference>(); // DERIVED

	public boolean getIsAbstract() {
		return this.isAbstract;
	}

	public void setIsAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public QualifiedNameList getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(QualifiedNameList specialization) {
		this.specialization = specialization;
	}

	public ArrayList<ElementReference> getSpecializationReferent() {
		return this.specializationReferent;
	}

	public void setSpecializationReferent(
			ArrayList<ElementReference> specializationReferent) {
		this.specializationReferent = specializationReferent;
	}

	public void addSpecializationReferent(
			ElementReference specializationReferent) {
		this.specializationReferent.add(specializationReferent);
	}

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * The namespace definition associated with the given unit definition
		 * must be a classifier definition. The subunit classifier definition
		 * may be abstract if and only if the subunit classifier definition is
		 * abstract. The subunit classifier definition must have the same
		 * specialization referents as the stub classifier definition. (Note
		 * that it is the referents that must match, not the exact names or the
		 * ordering of those names in the specialization list.) The subunit
		 * classifier definition must also have a matching classifier template
		 * parameter for each classifier template parameter of the stub
		 * classifier definition. Two template parameters match if they have
		 * same names and the same specialization referents.
		 */
		return false; // STUB
	} // matchForStub

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAbstract:");
		s.append(this.isAbstract);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.specialization != null) {
			this.specialization.print(prefix + " ");
		}
	}
} // ClassifierDefinition
