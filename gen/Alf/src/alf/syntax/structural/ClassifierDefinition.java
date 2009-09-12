
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class ClassifierDefinition extends NamespaceDefinition {

	private boolean isAbstract = false;
	private QualifiedNameList specialization = null;

	public void setIsAbstract() {
		this.isAbstract = true;
	} // setIsAbstract

	public boolean isAbstract() {
		return this.isAbstract;
	} // isAbstract

	public void setSpecialization(QualifiedNameList specialization) {
		this.specialization = specialization;
	} // setSpecialization

	public QualifiedNameList getSpecialization() {
		return this.specialization;
	} // getSpecialization

	public String toString() {
		return super.toString() + " isAbstract: " + this.isAbstract();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		QualifiedNameList specialization = this.getSpecialization();
		if (specialization != null) {
			this.getSpecialization().printChild(prefix);
		}
	} // print

	public boolean isCompletedBy(Member member) {
		if (!(member instanceof ClassifierDefinition)) {
			return false;
		} else {
			ClassDefinition classDef = (ClassDefinition) member;
			NamespaceDefinition namespace = this.getNamespace();

			return classDef.isAbstract() == this.isAbstract()
					&& (classDef.getSpecialization() == null
							&& this.getSpecialization() == null || classDef
							.getSpecialization().equals(
									this.getSpecialization(),
									this.getNamespace()));
		}
	} // isCompletedBy

	public ArrayList<Member> getGeneralizations() {
		ArrayList<Member> generalizations = new ArrayList<Member>();
		ArrayList<QualifiedName> qualifiedNames = this.getSpecialization()
				.getList();
		NamespaceDefinition namespace = this.getNamespace();

		for (QualifiedName qualifiedName : qualifiedNames) {
			ArrayList<Member> members = qualifiedName.resolve(namespace);
			Member member;
			if (members.size() == 0) {
				member = new ErrorMember(this,
						"Cannot resolve generalization: " + qualifiedName);
			} else if (members.size() == 1 && members.get(0).isError()) {
				member = members.get(0);
			} else {
				for (Object m : members.toArray()) {
					if (!this.canSpecialize((Member) m)) {
						members.remove(m);
					}
				}
				if (members.size() == 0) {
					member = new ErrorMember(this, "Cannot specialize: "
							+ qualifiedName);
				} else if (members.size() > 1) {
					member = new ErrorMember(this,
							"Ambiguous generalization reference: "
									+ qualifiedName);
				} else {
					member = members.get(0);
				}
			}
			generalizations.add(member);
		}

		return generalizations;

	} // getGeneralizations

	public abstract boolean canSpecialize(Member member);
} // ClassifierDefinition
