
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
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReference extends SyntaxElement implements
		IImportReference {

	private String visibility = "";
	private IQualifiedName referentName = null;
	private IUnitDefinition unit = null;

	public String getVisibility() {
		return this.visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public IQualifiedName getReferentName() {
		return this.referentName;
	}

	public void setReferentName(IQualifiedName referentName) {
		this.referentName = referentName;
	}

	public IUnitDefinition getUnit() {
		return this.unit;
	}

	public void setUnit(IUnitDefinition unit) {
		this.unit = unit;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" visibility:");
		s.append(this.getVisibility());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName referentName = this.getReferentName();
		if (referentName != null) {
			referentName.print(prefix + " ");
		}
		IUnitDefinition unit = this.getUnit();
		if (unit != null) {
			unit.print(prefix + " ");
		}
	}
} // ImportReference
