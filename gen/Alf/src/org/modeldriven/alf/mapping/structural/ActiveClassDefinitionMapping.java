
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.behavioral.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.Communications.*;
import fUML.Syntax.Activities.IntermediateActivities.*;

public class ActiveClassDefinitionMapping extends ClassDefinitionMapping {

	public void mapTo(Classifier classifier) {
		super.mapTo(classifier);

		Class_ class_ = (Class_) classifier;
		class_.setIsActive(true);

		ActiveClassDefinition definition = this.getActiveClassDefinition();
		ActivityDefinition classifierBehavior = definition
				.getClassifierBehavior();

		if (classifierBehavior != null) {
			ActivityDefinitionMapping mapping = (ActivityDefinitionMapping) this
					.map(classifierBehavior);
			Activity activity = mapping.getActivity();

			if (mapping.isError()) {
				this.setError(mapping.getError());
			} else {
				class_.addOwnedBehavior(activity);
				class_.setClassifierBehavior(activity);
			}
		}

	} // mapTo

	public void addMemberTo(Element element, NamedElement namespace) {
		Class_ class_ = (Class_) namespace;

		if (element instanceof Reception) {
			class_.addOwnedReception((Reception) element);
		} else {
			super.addMemberTo(element, namespace);
		}
	} // addMemberTo

	public ActiveClassDefinition getActiveClassDefinition() {
		return (ActiveClassDefinition) this.getSourceNode();
	} // getActiveClassDefinition

} // ActiveClassDefinitionMapping
