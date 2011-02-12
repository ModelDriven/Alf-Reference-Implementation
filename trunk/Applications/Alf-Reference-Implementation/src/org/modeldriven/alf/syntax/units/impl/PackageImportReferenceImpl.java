
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An import reference to a package all of whose public members are to be
 * imported.
 **/

public class PackageImportReferenceImpl extends
		org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl {

	public PackageImportReferenceImpl(PackageImportReference self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.PackageImportReference getSelf() {
		return (PackageImportReference) this.self;
	}

	/**
	 * The referent of a package import must be a package.
	 **/
	public boolean packageImportReferenceReferent() {
		return true;
	}

} // PackageImportReferenceImpl
