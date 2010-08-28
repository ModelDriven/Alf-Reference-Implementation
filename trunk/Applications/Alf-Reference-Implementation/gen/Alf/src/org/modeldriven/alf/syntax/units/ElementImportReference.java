
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
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReference extends ImportReference {

	private String alias = "";

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" alias:");
		s.append(this.alias);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ElementImportReference
