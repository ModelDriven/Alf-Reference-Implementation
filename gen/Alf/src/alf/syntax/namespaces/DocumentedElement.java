
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

public abstract class DocumentedElement extends Node {

	private ArrayList<String> documentations = new ArrayList<String>();

	public void addDocumentation(String text) {
		if (text != null) {
			this.documentations.add(text);
		}
	} // addDocumentation

	public ArrayList<String> getDocumentations() {
		return this.documentations;
	} // getDocumentations

	public void print(String prefix) {
		super.print(prefix);
		for (String doc : this.getDocumentations()) {
			System.out.println(prefix + doc);
		}
	} // print

} // DocumentedElement
