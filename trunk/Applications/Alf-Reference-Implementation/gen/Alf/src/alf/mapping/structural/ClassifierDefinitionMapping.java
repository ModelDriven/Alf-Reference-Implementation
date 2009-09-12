
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.namespaces.*;

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
				break;
			} else {
				ClassifierDefinitionMapping mapping = (ClassifierDefinitionMapping) this
						.map(generalDefinition);
				Classifier general = mapping.getClassifier();

				if (mapping.isError()) {
					this.setError(mapping.getError());
					break;
				} else {
					Generalization generalization = new Generalization();
					generalization.setGeneral(general);
					classifier.addGeneralization(generalization);
				}
			}
		}

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
		return (ClassifierDefinition) this.getSource();
	} // getClassifierDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getClassifier());
		return elements;
	} // getModelElements

} // ClassifierDefinitionMapping
