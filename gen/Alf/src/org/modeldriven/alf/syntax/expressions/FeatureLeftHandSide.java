
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

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
