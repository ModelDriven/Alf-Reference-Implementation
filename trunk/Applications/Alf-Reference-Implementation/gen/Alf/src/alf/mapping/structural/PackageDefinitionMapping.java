
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.namespaces.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Classes.Kernel.Package;

public class PackageDefinitionMapping extends NamespaceDefinitionMapping {

	private Package package_ = null;

	public Package getPackage() {
		if (this.package_ == null) {
			this.package_ = new Package();
			this.mapTo(this.package_);
		}

		return this.package_;
	} // getPackage

	public PackageDefinition getPackageDefinition() {
		return (PackageDefinition) this.getSourceNode();
	} // getPackageDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getPackage());
		return elements;
	} // getModelElements

	public void addMemberTo(Element element, NamedElement namespace) {
		if (!(element instanceof PackageableElement)) {
			this.setError(new ErrorNode(this.getSourceNode(),
					"Member is not packageable."));
		} else {
			((Package) namespace)
					.addPackagedElement((PackageableElement) element);
		}
	} // addMemberTo

} // PackageDefinitionMapping
