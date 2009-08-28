
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

public class FeatureLeftHandSide extends LeftHandSide {

	private Expression index = null;
	private FeatureReference feature = null;

	public FeatureLeftHandSide(FeatureReference feature, Expression index) {
		this.feature = feature;
		this.index = index;
	} // FeatureLeftHandSide

	public FeatureReference getFeature() {
		return this.feature;
	} // getFeature

	public Expression getIndex() {
		return this.index;
	} // getIndex

	public void print(String prefix) {
		super.print(prefix);
		this.getFeature().printChild(prefix);

		if (this.getIndex() != null) {
			this.getIndex().printChild(prefix);
		}
	} // print

} // FeatureLeftHandSide
