
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A model of an Alf statement.
 **/

public abstract class Statement extends DocumentedElement implements IStatement {

	private ArrayList<IAnnotation> annotation = new ArrayList<IAnnotation>();

	public ArrayList<IAnnotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(ArrayList<IAnnotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(IAnnotation annotation) {
		this.annotation.add(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (IAnnotation annotation : this.getAnnotation()) {
			if (annotation != null) {
				annotation.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // Statement
