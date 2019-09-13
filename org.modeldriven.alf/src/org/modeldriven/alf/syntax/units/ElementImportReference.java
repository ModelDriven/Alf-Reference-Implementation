/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.ElementImportReferenceImpl;

/**
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReference extends ImportReference {

	public ElementImportReference() {
		this.impl = new ElementImportReferenceImpl(this);
	}

	public ElementImportReference(Parser parser) {
		this();
		this.init(parser);
	}

	public ElementImportReference(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ElementImportReferenceImpl getImpl() {
		return (ElementImportReferenceImpl) this.impl;
	}

	public String getAlias() {
		return this.getImpl().getAlias();
	}

	public void setAlias(String alias) {
		this.getImpl().setAlias(alias);
	}

	/**
	 * The referent of an element import reference must be a packageable
	 * element.
	 **/
	public boolean elementImportReferenceReferent() {
		return this.getImpl().elementImportReferenceReferent();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.elementImportReferenceReferent()) {
			violations.add(new ConstraintViolation(
					"elementImportReferenceReferent", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" alias:");
		s.append(this.getAlias());
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
} // ElementImportReference
