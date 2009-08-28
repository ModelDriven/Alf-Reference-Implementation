
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ParameterSetDeclaration extends TypedElementDeclaration {

	private ArrayList<FormalParameter> parameters = new ArrayList<FormalParameter>();

	public void addParameter(FormalParameter parameter) {
		this.parameters.add(parameter);
	} // addParameter

	public ArrayList<FormalParameter> getParameters() {
		return this.parameters;
	} // getParameters

	public void print(String prefix) {
		super.print(prefix);

		ArrayList<FormalParameter> params = this.getParameters();
		for (FormalParameter param : params) {
			param.printChild(prefix);
		}
	} // print

} // ParameterSetDeclaration
