
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
 * The definition of an operation, with any formal parameters defined as owned
 * members.
 **/

public class OperationDefinition extends NamespaceDefinition implements
		IOperationDefinition {

	private IQualifiedNameList redefinition = null;
	private Boolean isAbstract = false;
	private IBlock body = null;

	public IQualifiedNameList getRedefinition() {
		return this.redefinition;
	}

	public void setRedefinition(IQualifiedNameList redefinition) {
		this.redefinition = redefinition;
	}

	public Boolean getIsAbstract() {
		return this.isAbstract;
	}

	public void setIsAbstract(Boolean isAbstract) {
		this.isAbstract = isAbstract;
	}

	public IBlock getBody() {
		return this.body;
	}

	public void setBody(IBlock body) {
		this.body = body;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isAbstract:");
		s.append(this.getIsAbstract());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedNameList redefinition = this.getRedefinition();
		if (redefinition != null) {
			redefinition.print(prefix + " ");
		}
		IBlock body = this.getBody();
		if (body != null) {
			body.print(prefix + " ");
		}
	}
} // OperationDefinition
