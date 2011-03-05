
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElement extends SyntaxElement {

	private ArrayList<String> documentation = new ArrayList<String>();

	public DocumentedElementImpl getImpl() {
		return (DocumentedElementImpl) this.impl;
	}

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
		ArrayList<String> documentation = this.getDocumentation();
		if (documentation != null) {
			if (documentation.size() > 0) {
				System.out.println(prefix + " documentation:");
			}
			for (String _documentation : documentation) {
				System.out.println(prefix + "  " + _documentation);
			}
		}
	}
} // DocumentedElement
