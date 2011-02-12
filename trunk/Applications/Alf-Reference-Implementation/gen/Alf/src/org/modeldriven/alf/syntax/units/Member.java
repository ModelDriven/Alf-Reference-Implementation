
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class Member extends DocumentedElement implements IMember {

	private String name = "";
	private String visibility = "";
	private Boolean isStub = false;
	private INamespaceDefinition namespace = null;
	private ArrayList<IStereotypeAnnotation> annotation = new ArrayList<IStereotypeAnnotation>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisibility() {
		return this.visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public Boolean getIsStub() {
		return this.isStub;
	}

	public void setIsStub(Boolean isStub) {
		this.isStub = isStub;
	}

	public INamespaceDefinition getNamespace() {
		return this.namespace;
	}

	public void setNamespace(INamespaceDefinition namespace) {
		this.namespace = namespace;
	}

	public ArrayList<IStereotypeAnnotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(ArrayList<IStereotypeAnnotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(IStereotypeAnnotation annotation) {
		this.annotation.add(annotation);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		s.append(" visibility:");
		s.append(this.getVisibility());
		s.append(" isStub:");
		s.append(this.getIsStub());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		INamespaceDefinition namespace = this.getNamespace();
		if (namespace != null) {
			namespace.print(prefix + " ");
		}
		ArrayList<IStereotypeAnnotation> annotation = this.getAnnotation();
		if (annotation != null) {
			for (IStereotypeAnnotation item : this.getAnnotation()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // Member
