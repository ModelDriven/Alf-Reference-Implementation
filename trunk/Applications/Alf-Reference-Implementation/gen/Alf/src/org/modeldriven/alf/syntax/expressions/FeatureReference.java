
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

public class FeatureReference extends Expression {

	private Expression expression = null;
	public NameBinding nameBinding = null;

	public FeatureReference(Expression expression, NameBinding nameBinding) {
		this.expression = expression;
		this.nameBinding = nameBinding;
	} // FeatureReference

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public NameBinding getNameBinding() {
		return this.nameBinding;
	} // getNameBinding

	public String getName() {
		NameBinding nameBinding = this.getNameBinding();

		if (nameBinding == null) {
			return null;
		} else {
			return nameBinding.getName();
		}
	} // getName

	public String toString() {
		return super.toString() + " nameBinding:" + this.getNameBinding();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression().printChild(prefix);
	} // print

} // FeatureReference
