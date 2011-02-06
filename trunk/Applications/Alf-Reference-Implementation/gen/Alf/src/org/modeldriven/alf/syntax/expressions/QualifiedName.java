
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The representation of a qualified name as a sequence of individual simple
 * names.
 **/

public class QualifiedName extends SyntaxElement implements IQualifiedName {

	private Boolean isAmbiguous = false;
	private ArrayList<INameBinding> nameBinding = new ArrayList<INameBinding>();

	public Boolean getIsAmbiguous() {
		return this.isAmbiguous;
	}

	public void setIsAmbiguous(Boolean isAmbiguous) {
		this.isAmbiguous = isAmbiguous;
	}

	public ArrayList<INameBinding> getNameBinding() {
		return this.nameBinding;
	}

	public void setNameBinding(ArrayList<INameBinding> nameBinding) {
		this.nameBinding = nameBinding;
	}

	public void addNameBinding(INameBinding nameBinding) {
		this.nameBinding.add(nameBinding);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAmbiguous:");
		s.append(this.getIsAmbiguous());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (INameBinding nameBinding : this.getNameBinding()) {
			if (nameBinding != null) {
				nameBinding.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // QualifiedName
