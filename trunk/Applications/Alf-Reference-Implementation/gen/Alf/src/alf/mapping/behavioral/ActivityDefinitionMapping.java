
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.behavioral;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.namespaces.*;
import alf.mapping.structural.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;

public class ActivityDefinitionMapping extends NamespaceDefinitionMapping {

	private Activity activity = null;

	public void mapTo(Activity activity) {
		super.mapTo(activity);

		ActivityDefinition definition = this.getActivityDefinition();

		BlockMapping bodyMapping = (BlockMapping) this
				.map(definition.getBody());
		bodyMapping.setContext(definition);
		ArrayList<Element> elements = bodyMapping.getModelElements();

		if (bodyMapping.isError()) {
			this.setError(bodyMapping.getError());
		} else {
			for (Element element : elements) {
				if (element instanceof ActivityNode) {
					activity.addNode((ActivityNode) element);
				} else if (element instanceof ActivityEdge) {
					activity.addEdge((ActivityEdge) element);
				} else {
					this.setError(new ErrorNode(definition.getBody(),
							"Element not an activity node."));
				}
			}
		}
	} // mapTo

	public void addMemberTo(Element element, NamedElement namespace) {
		if (!(element instanceof Parameter)) {
			this.setError(new ErrorNode(this.getSource(),
					"Member that is not a parameter."));
		} else {
			Activity activity = (Activity) namespace;
			Parameter parameter = (Parameter) element;
			activity.addOwnedParameter(parameter);

			ActivityParameterNode node = new ActivityParameterNode();
			node.setParameter(parameter);
			activity.addNode(node);

			if (parameter.direction == ParameterDirectionKind.in
					|| parameter.direction == ParameterDirectionKind.out) {
				node.setName("Input(" + parameter.name + ")");

				ForkNode fork = new ForkNode();
				fork.setName(parameter.name);
				activity.addNode(fork);

				ObjectFlow flow = new ObjectFlow();
				flow.setSource(node);
				flow.setTarget(fork);
				activity.addEdge(flow);

				if (parameter.direction == ParameterDirectionKind.out) {
					node = new ActivityParameterNode();
					node.setParameter(parameter);
					activity.addNode(node);
				}
			}

			if (parameter.direction == ParameterDirectionKind.inout
					|| parameter.direction == ParameterDirectionKind.out
					|| parameter.direction == ParameterDirectionKind.return_) {
				node.setName("Output(" + parameter.name + ")");
			}
		}

	} // addMemberTo

	public Activity getActivity() {
		if (this.activity == null) {
			this.activity = new Activity();
			this.mapTo(this.activity);
		}

		return this.activity;
	} // getActivity

	public ActivityDefinition getActivityDefinition() {
		return (ActivityDefinition) this.getSource();
	} // getActivityDefinition

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		elements.add(this.getActivity());
		return elements;
	} // getModelElements

	public ActivityParameterNode getParameterNode(Parameter parameter) {
		for (ActivityNode node : this.getActivity().node) {
			if (node instanceof ActivityParameterNode
					&& ((ActivityParameterNode) node).parameter == parameter) {
				return (ActivityParameterNode) node;
			}
		}

		return null;
	} // getParameterNode

} // ActivityDefinitionMapping
