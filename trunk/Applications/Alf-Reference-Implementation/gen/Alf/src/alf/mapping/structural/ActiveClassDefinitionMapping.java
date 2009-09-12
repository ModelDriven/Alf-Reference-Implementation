
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

import alf.mapping.behavioral.*;

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
		return (ActiveClassDefinition) this.getSource();
	} // getActiveClassDefinition

} // ActiveClassDefinitionMapping
