
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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
import java.util.TreeSet;

/**
 * An import reference to a package all of whose public members are to be
 * imported.
 **/

public class PackageImportReferenceImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ImportReferenceImpl {

	public PackageImportReferenceImpl(PackageImportReference self) {
		super(self);
	}

	public PackageImportReference getSelf() {
		return (PackageImportReference) this.self;
	}

	/**
	 * The referent of a package import must be a package.
	 **/
	public boolean packageImportReferenceReferent() {
		return true;
	}

} // PackageImportReferenceImpl
