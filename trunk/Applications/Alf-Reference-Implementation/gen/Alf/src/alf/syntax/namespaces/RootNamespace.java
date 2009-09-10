
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

public class RootNamespace extends NamespaceDefinition {

	public ArrayList<Member> resolvePublic(String name, boolean allowPackageOnly) {
		return this.resolve(name);
	} // resolvePublic

	public NamespaceDefinition getRootNamespace() {
		return this;
	} // getRootNamespace

	public NamespaceDefinition getModelNamespace() {
		ArrayList<Member> members = this.resolve("Model");

		if (members.size() > 0) {
			return (ModelNamespace) members.get(0);
		} else {
			return new ModelNamespace(this);
		}
	} // getModelNamespace

	public QualifiedName getQualifiedName() {
		QualifiedName qualifiedName = new QualifiedName();
		qualifiedName.setIsAbsolute();
		return qualifiedName;
	} // getQualifiedName

} // RootNamespace
