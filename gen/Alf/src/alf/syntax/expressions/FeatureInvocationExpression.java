
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class FeatureInvocationExpression extends InvocationExpression {

	private FeatureReference target = null;

	public FeatureInvocationExpression(FeatureReference target, Tuple tuple) {
		super(tuple);
		this.target = target;

	} // FeatureInvocationExpression

	public FeatureReference getTarget() {
		return this.target;
	} // getTarget

	public void printTarget(String prefix) {
		this.getTarget().printChild(prefix);

	} // printTarget

} // FeatureInvocationExpression
