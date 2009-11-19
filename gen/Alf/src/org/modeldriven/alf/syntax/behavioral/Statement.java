
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class Statement extends DocumentedNode {

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
