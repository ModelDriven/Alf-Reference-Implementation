
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class TemplateParameterSubstitution extends SyntaxNode {

	private String parameterName = "";
	private QualifiedName argumentName = null;

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	} // setParameterName

	public String getParameterName() {
		return this.parameterName;
	} // getParameterName

	public void setArgumentName(QualifiedName argumentName) {
		this.argumentName = argumentName;
	} // setArgumentName

	public QualifiedName getArgumentName() {
		return this.argumentName;
	} // getArgumentName

	public String toString() {
		return this.getParameterName() + "=>" + this.getArgumentName();
	} // toString

} // TemplateParameterSubstitution
