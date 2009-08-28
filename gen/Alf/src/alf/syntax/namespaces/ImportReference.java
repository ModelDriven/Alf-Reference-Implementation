
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.namespaces;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ImportReference extends Node {

	private String visibility = "";
	private QualifiedName reference = null;
	private String alias = "";
	private boolean packageImport = false;

	public ImportReference(QualifiedName reference) {
		this.reference = reference;
	} // ImportReference

	public QualifiedName getReference() {
		return this.reference;
	} // getReference

	public void setAlias(String alias) {
		this.alias = alias;
	} // setAlias

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	} // setVisibility

	public String getVisibiity() {
		return this.visibility;
	} // getVisibiity

	public String getAlias() {
		return this.alias;
	} // getAlias

	public void setIsPackageImport() {
		this.packageImport = true;
	} // setIsPackageImport

	public boolean isPackageImport() {
		return this.packageImport;
	} // isPackageImport

	public String toString() {
		return super.toString() + " alias:" + this.getAlias()
				+ " packageImport:" + this.isPackageImport();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getReference().printChild(prefix);
	} // print

} // ImportReference
