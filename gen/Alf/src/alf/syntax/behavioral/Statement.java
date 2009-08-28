
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class Statement extends DocumentedElement {

	private ArrayList<Annotation> annotations = new ArrayList<Annotation>();

	public void addAnnotation(Annotation annotation) {
		this.annotations.add(annotation);
	} // addAnnotation

	public ArrayList<Annotation> getAnnotations() {
		return this.annotations;
	} // getAnnotations

	public void print(String prefix) {
		super.print(prefix);

		for (Annotation annotation : this.getAnnotations()) {
			annotation.printChild(prefix);
		}
	} // print

} // Statement
