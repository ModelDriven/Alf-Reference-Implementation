
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

import java.util.ArrayList;

/**
 * The definition of a classifier.
 **/

public abstract class ClassifierDefinition extends NamespaceDefinition
		implements IClassifierDefinition {

	private Boolean isAbstract = false;
	private IQualifiedNameList specialization = null;

	public Boolean getIsAbstract() {
		return this.isAbstract;
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public IQualifiedNameList getSpecialization() {
		return this.specialization;
	}

	public void setSpecialization(IQualifiedNameList specialization) {
		this.specialization = specialization;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAbstract:");
		s.append(this.getIsAbstract());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedNameList specialization = this.getSpecialization();
		if (specialization != null) {
			specialization.print(prefix + " ");
		}
	}
} // ClassifierDefinition
