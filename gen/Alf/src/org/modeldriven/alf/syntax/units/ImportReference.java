
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class ImportReference extends SyntaxNode {

	private String visibility = "";
	private QualifiedName reference = null;
	private String alias = "";
	private boolean packageImport = false;
	private Member referent = null;
	private UnitDefinition unit = null;

	public ImportReference(QualifiedName reference) {
		this.reference = reference;
	} // ImportReference

	public QualifiedName getReference() {
		return this.reference;
	} // getReference

	public void setAlias(String alias) {
		this.alias = alias;
	} // setAlias

	public String getAlias() {
		return this.alias;
	} // getAlias

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	} // setVisibility

	public String getVisibility() {
		return this.visibility;
	} // getVisibility

	public void setIsPackageImport() {
		this.packageImport = true;
	} // setIsPackageImport

	public boolean isPackageImport() {
		return this.packageImport;
	} // isPackageImport

	public void setUnit(UnitDefinition unit) {
		this.unit = unit;
	} // setUnit

	public UnitDefinition getUnit() {
		return this.unit;
	} // getUnit

	public boolean isPublic() {
		String visibility = this.getVisibility();
		return visibility != null && visibility.equals("public");
	} // isPublic

	public String toString() {
		return super.toString() + " visibility:" + this.getVisibility()
				+ " alias:" + this.getAlias() + " packageImport:"
				+ this.isPackageImport();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getReference().printChild(prefix);
	} // print

	public ArrayList<Member> getMembers() {
		if (this.isPackageImport()) {
			return this.getReferent().getPublicMembers();
		} else {
			ArrayList<Member> members = new ArrayList<Member>();
			members.add(this.getReferent());
			return members;
		}
	} // getMembers

	public ArrayList<Member> resolve(String name) {
		Member referent = this.getReferent();
		ArrayList<Member> members = new ArrayList<Member>();

		if (referent.isError() || alias != null && name.equals(alias)
				|| !this.isPackageImport() && referent.getName().equals(name)) {
			members.add(referent);
		} else if (this.isPackageImport()) {
			members = referent.resolvePublic(name, false);
		}

		return members;

	} // resolve

	public Member getReferent() {
		if (this.referent == null) {
			ArrayList<Member> members = this.getReference().resolve(
					this.getUnit().getModelNamespace());
			if (members.size() == 1) {
				Member member = members.get(0);
				if (member.isError()) {
					this.referent = new ErrorMember(this, (ErrorMember) member);
				} else if (this.isPackageImport()
						&& !(member instanceof PackageDefinition)) {
					this.referent = new ErrorMember(this, "Not a package: "
							+ this.getReference());
				} else {
					this.referent = member;
				}
			} else if (members.size() == 0) {
				this.referent = new ErrorMember(this, "Cannot find: "
						+ this.getReference());
			} else
				this.referent = new ErrorMember(this, "Ambiguous reference: "
						+ this.getReference());
		}

		return this.referent;
	} // getReferent

	public String getName() {
		String alias = this.getAlias();

		if (alias != null && !alias.equals("")) {
			return alias;
		} else {
			return this.getReferent().getName();
		}
	} // getName

} // ImportReference
