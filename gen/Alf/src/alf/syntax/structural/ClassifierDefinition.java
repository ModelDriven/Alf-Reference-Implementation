
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
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

} // ClassifierDefinition
