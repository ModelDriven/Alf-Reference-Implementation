
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.namespaces.*;

import fUML.Syntax.Classes.Kernel.*;

public abstract class ClassifierDefinitionMapping extends
		NamespaceDefinitionMapping {

	private Classifier classifier = null;

	public void mapTo(Classifier classifier) {
		super.mapTo(classifier);

		ClassifierDefinition definition = this.getClassifierDefinition();
		classifier.setIsAbstract(definition.isAbstract());

		ArrayList<Member> generalDefinitions = definition.getGeneralizations();

		for (Member generalDefinition : generalDefinitions) {
			if (generalDefinition.isError()) {
				this.setError(((ErrorMember) generalDefinition).getError());
				return;
			} else {
				ClassifierDefinitionMapping mapping = (ClassifierDefinitionMapping) this
						.map(generalDefinition);
				Classifier general = mapping.getClassifier();

				if (mapping.isError()) {
					this.setError(mapping.getError());
					return;
				} else {
					Generalization generalization = new Generalization();
					generalization.setGeneral(general);
					classifier.addGeneralization(generalization);
				}
			}
		}

		/*
		 * System.out.println("mapTo: " + classifier.name + " members:"); for
		 * (int i = 0; i < classifier.member.size(); i++) {
		 * System.out.println(classifier.member.getValue(i).name); }
		 */
	} // mapTo

	public abstract Classifier mapClassifier();

	public Classifier getClassifier() {
		if (this.classifier == null) {
			this.classifier = this.mapClassifier();
			if (!this.isError()) {
				this.mapTo(classifier);
			}
		}

		return this.classifier;
	} // getClassifier

	public ClassifierDefinition getClassifierDefinition() {
		return (ClassifierDefinition) this.getSourceNode();
	} // getClassifierDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getClassifier());
		return elements;
	} // getModelElements

} // ClassifierDefinitionMapping
