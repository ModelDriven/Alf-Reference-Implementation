
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

public abstract class ClassifierDeclaration extends Node {

	private String name = "";
	private boolean isAbstract = false;
	private QualifiedNameList specialization = null;

	public void setSpecialization(QualifiedNameList specialization) {
		this.specialization = specialization;
	} // setSpecialization

	public String getName() {
		return this.name;
	} // getName

	public void setName(String name) {
		this.name = name;
	} // setName

	public QualifiedNameList getSpecialization() {
		return this.specialization;
	} // getSpecialization

	public void setIsAbstract() {
		this.isAbstract = true;
	} // setIsAbstract

	public boolean isAbstract() {
		return this.isAbstract;
	} // isAbstract

	public String toString() {
		return super.toString() + " name:" + this.getName() + " isAbstract:"
				+ this.isAbstract();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (this.getSpecialization() != null) {
			this.getSpecialization().printChild(prefix);
		}
	} // print

} // ClassifierDeclaration
