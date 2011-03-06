
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.ElementImportReferenceImpl;

/**
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReference extends ImportReference {

	public ElementImportReference() {
		this.impl = new ElementImportReferenceImpl(this);
	}

	public ElementImportReferenceImpl getImpl() {
		return (ElementImportReferenceImpl) this.impl;
	}

	public String getAlias() {
		return this.getImpl().getAlias();
	}

	public void setAlias(String alias) {
		this.getImpl().setAlias(alias);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" alias:");
		s.append(this.getAlias());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ElementImportReference
