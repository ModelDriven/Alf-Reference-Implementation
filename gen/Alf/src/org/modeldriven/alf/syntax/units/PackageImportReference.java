
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

import org.modeldriven.alf.syntax.units.impl.PackageImportReferenceImpl;

/**
 * An import reference to a package all of whose public members are to be
 * imported.
 **/

public class PackageImportReference extends ImportReference {

	public PackageImportReference() {
		this.impl = new PackageImportReferenceImpl(this);
	}

	public PackageImportReferenceImpl getImpl() {
		return (PackageImportReferenceImpl) this.impl;
	}

	/**
	 * The referent of a package import must be a package.
	 **/
	public boolean packageImportReferenceReferent() {
		return this.getImpl().packageImportReferenceReferent();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // PackageImportReference
