
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;

public class FeatureReferenceMapping extends ExpressionMapping {

	public ArrayList<Feature> getFeatures() {
		ExpressionMapping mapping = this.getExpressionMapping();
		Classifier type = mapping.getType();

		ArrayList<Feature> features = new ArrayList<Feature>();

		if (mapping.isError()) {
			this.setError(mapping.getError());
		} else {
			String name = this.getFeatureReference().getName();

			// System.out.println("getFeatures: type = " + type.name);

			NamedElementList members = type.member;

			for (int i = 0; i < members.size(); i++) {
				NamedElement member = members.getValue(i);
				// System.out.println("getFeatures: member = " + member);
				if (member instanceof Feature && member.name.equals(name)) {
					// System.out.println("Found feature!");
					features.add((Feature) member);
				}
			}
		}

		return features;
	} // getFeatures

	public ArrayList<Element> getExpressionElements() {
		this.mapTo(null);
		ExpressionMapping mapping = this.getExpressionMapping();
		ArrayList<Element> elements = mapping.getModelElements();

		if (mapping.isError()) {
			this.setError(mapping.getError());
		}

		return elements;
	} // getExpressionElements

	public ExpressionMapping getExpressionMapping() {
		Expression expression = this.getFeatureReference().getExpression();
		return (ExpressionMapping) this.map(expression);
	} // getExpressionMapping

	public FeatureReference getFeatureReference() {
		return (FeatureReference) this.getSourceNode();
	} // getFeatureReference

	public ActivityNode getResultSource() {
		return null;
	} // getResultSource

	public Classifier getType() {
		return null;
	} // getType

	public ArrayList<Element> getModelElements() {
		// Mapping to read structural feature action not yet supported.
		return this.getExpressionElements();
	} // getModelElements

} // FeatureReferenceMapping
