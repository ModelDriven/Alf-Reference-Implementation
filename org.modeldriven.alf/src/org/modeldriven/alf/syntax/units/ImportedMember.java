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
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;

public class ImportedMember extends Member {

	public ImportedMember() {
		this.impl = new ImportedMemberImpl(this);
	}

	public ImportedMember(Parser parser) {
		this();
		this.init(parser);
	}

	public ImportedMember(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ImportedMemberImpl getImpl() {
		return (ImportedMemberImpl) this.impl;
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	/**
	 * An imported element is not a stub.
	 **/
	public boolean importedMemberNotStub() {
		return this.getImpl().importedMemberNotStub();
	}

	/**
	 * An imported element is a feature if its referent is a feature.
	 **/
	public boolean importedMemberIsFeatureDerivation() {
		return this.getImpl().importedMemberIsFeatureDerivation();
	}

	/**
	 * Returns false. (Imported members do not have annotations.)
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * If the given member is not an imported member, then return the result of
	 * checking whether the given member is the same kind as this member. Else,
	 * if the element of the referent for this member is an Alf member, then
	 * return the result of checking whether that element is the same kind as
	 * the given member. Else, if the element of the referent for the given
	 * member is an Alf member, then return the result of checking whether that
	 * element is the same kind as this member. Else, the referents for both
	 * this and the given member are UML elements, so return the result of
	 * checking their distinguishability according to the rules of the UML
	 * superstructure.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.importedMemberNotStub()) {
			violations.add(new ConstraintViolation("importedMemberNotStub",
					this));
		}
		if (!this.importedMemberIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"importedMemberIsFeatureDerivation", this));
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
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " referent:"
					+ referent.toString(includeDerived));
		}
	}
} // ImportedMember
