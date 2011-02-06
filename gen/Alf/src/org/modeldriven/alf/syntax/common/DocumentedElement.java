
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElement extends SyntaxElement implements
		IDocumentedElement {

	private ArrayList<String> documentation = new ArrayList<String>();

	public ArrayList<String> getDocumentation() {
		return this.documentation;
	}

	public void setDocumentation(ArrayList<String> documentation) {
		this.documentation = documentation;
	}

	public void addDocumentation(String documentation) {
		this.documentation.add(documentation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.getDocumentation().size() > 0) {
			System.out.println(prefix + " documentation:");
		}
		for (String documentation : this.getDocumentation()) {
			System.out.println(prefix + "  " + documentation);
		}
	}
} // DocumentedElement
