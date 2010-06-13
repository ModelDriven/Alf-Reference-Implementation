
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.expressions.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Actions.BasicActions.*;

public class ExpressionStatementMapping extends StatementMapping {

	public void mapTo(StructuredActivityNode node) {
		super.mapTo(node);

		ExpressionStatement statement = this.getExpressionStatement();
		ExpressionMapping mapping = (ExpressionMapping) this.map(statement
				.getExpression());
		mapping.setContext(this.getContext());
		ArrayList<Element> elements = mapping.getModelElements();

		if (mapping.isError()) {
			this.setError(mapping.getError());
		} else {
			for (Element element : elements) {
				if (element instanceof ActivityNode) {
					node.addNode((ActivityNode) element);
				} else if (element instanceof ControlFlow) {
					node.addEdge((ActivityEdge) element);
				} else if (element instanceof ObjectFlow) {
					ActivityEdge edge = (ActivityEdge) element;

					if (edge.source.activity != null
							|| edge.target.activity != null) {
						this.addModelElement(edge);
					} else {
						node.addEdge(edge);
					}
				}
			}
		}
	} // mapTo

	public ExpressionStatement getExpressionStatement() {
		return (ExpressionStatement) this.getSourceNode();
	} // getExpressionStatement

} // ExpressionStatementMapping
