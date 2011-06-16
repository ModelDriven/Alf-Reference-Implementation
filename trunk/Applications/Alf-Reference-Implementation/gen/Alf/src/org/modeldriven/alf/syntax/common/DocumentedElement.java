
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElement extends SyntaxElement {

	public DocumentedElementImpl getImpl() {
		return (DocumentedElementImpl) this.impl;
	}

	public Collection<String> getDocumentation() {
		return this.getImpl().getDocumentation();
	}

	public void setDocumentation(Collection<String> documentation) {
		this.getImpl().setDocumentation(documentation);
	}

	public void addDocumentation(String documentation) {
		this.getImpl().addDocumentation(documentation);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<String> documentation = this.getDocumentation();
		if (documentation != null) {
			if (documentation.size() > 0) {
				System.out.println(prefix + " documentation:");
			}
			for (Object _object : documentation.toArray()) {
				String _documentation = (String) _object;
				System.out.println(prefix + "  " + _documentation);
			}
		}
	}
} // DocumentedElement
