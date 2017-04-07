/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.PackageImportReferenceImpl;

/**
 * An import reference to a package all of whose public members are to be
 * imported.
 **/

public class PackageImportReference extends ImportReference {

	public PackageImportReference() {
		this.impl = new PackageImportReferenceImpl(this);
	}

	public PackageImportReference(Parser parser) {
		this();
		this.init(parser);
	}

	public PackageImportReference(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public PackageImportReferenceImpl getImpl() {
		return (PackageImportReferenceImpl) this.impl;
	}

	/**
	 * The referent of a package import must be a package.
	 **/
	public boolean packageImportReferenceReferent() {
		return this.getImpl().packageImportReferenceReferent();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.packageImportReferenceReferent()) {
			violations.add(new ConstraintViolation(
					"packageImportReferenceReferent", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // PackageImportReference
