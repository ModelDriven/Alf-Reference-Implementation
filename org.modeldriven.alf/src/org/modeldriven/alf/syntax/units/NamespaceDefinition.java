/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import java.util.List;
import org.modeldriven.alf.syntax.units.impl.NamespaceDefinitionImpl;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinition extends Member {

	@Override
    public NamespaceDefinitionImpl getImpl() {
		return (NamespaceDefinitionImpl) this.impl;
	}

	public List<Member> getOwnedMember() {
		return this.getImpl().getOwnedMember();
	}

	public void setOwnedMember(List<Member> ownedMember) {
		this.getImpl().setOwnedMember(ownedMember);
	}

	public void addOwnedMember(Member ownedMember) {
		this.getImpl().addOwnedMember(ownedMember);
	}

	public UnitDefinition getUnit() {
		return this.getImpl().getUnit();
	}

	public void setUnit(UnitDefinition unit) {
		this.getImpl().setUnit(unit);
	}

	public Collection<Member> getMember() {
		return this.getImpl().getMember();
	}

	public void setMember(Collection<Member> member) {
		this.getImpl().setMember(member);
	}

	public void addMember(Member member) {
		this.getImpl().addMember(member);
	}

	/**
	 * The members of a namespace definition include references to all owned
	 * members. Also, if the namespace definition has a unit with imports, then
	 * the members include imported members with referents to all imported
	 * elements. The imported elements and their visibility are determined as
	 * given in the UML Superstructure. The name of an imported member is the
	 * name of the imported element or its alias, if one has been given for it.
	 * Elements that would be indistinguishable from each other or from an owned
	 * member (as determined by the Member::isDistinguishableFrom operation) are
	 * not imported.
	 **/
	public boolean namespaceDefinitionMemberDerivation() {
		return this.getImpl().namespaceDefinitionMemberDerivation();
	}

	/**
	 * The members of a namespace must be distinguishable as determined by the
	 * Member::isDistinguishableFrom operation.
	 **/
	public boolean namespaceDefinitionMemberDistinguishability() {
		return this.getImpl().namespaceDefinitionMemberDistinguishability();
	}

	/**
	 * Returns true if the annotation is @external.
	 **/
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getOwnedMember());
    }

	@Override
    public void _deriveAll() {
		this.getMember();
		super._deriveAll();
		Collection<Member> ownedMember = this.getOwnedMember();
		if (ownedMember != null) {
			for (Object _ownedMember : ownedMember.toArray()) {
				((Member) _ownedMember).deriveAll();
			}
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.namespaceDefinitionMemberDerivation()) {
			violations.add(new ConstraintViolation(
					"namespaceDefinitionMemberDerivation", this));
		}
		if (!this.namespaceDefinitionMemberDistinguishability()) {
			violations.add(new ConstraintViolation(
					"namespaceDefinitionMemberDistinguishability", this));
		}
		Collection<Member> ownedMember = this.getOwnedMember();
		if (ownedMember != null) {
			for (Object _ownedMember : ownedMember.toArray()) {
				((Member) _ownedMember).checkConstraints(violations);
			}
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
		List<Member> ownedMember = this.getOwnedMember();
		if (ownedMember != null && ownedMember.size() > 0) {
			System.out.println(prefix + " ownedMember:");
			for (Object _object : ownedMember.toArray()) {
				Member _ownedMember = (Member) _object;
				if (_ownedMember != null) {
					_ownedMember.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		UnitDefinition unit = this.getUnit();
		if (unit != null) {
			System.out.println(prefix + " unit:"
					+ unit.toString(includeDerived));
		}
		if (includeDerived) {
			Collection<Member> member = this.getMember();
			if (member != null && member.size() > 0) {
				System.out.println(prefix + " /member:");
				for (Object _object : member.toArray()) {
					Member _member = (Member) _object;
					System.out.println(prefix + "  "
							+ _member.toString(includeDerived));
				}
			}
		}
	}
} // NamespaceDefinition
