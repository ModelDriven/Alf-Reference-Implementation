
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

import alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;

public class BlockMapping extends MappingNode {

	private boolean isParallel = false;
	private NamespaceDefinition context = null;

	public void setIsParallel() {
		this.isParallel = true;
	} // setIsParallel

	public boolean isParallel() {
		return this.isParallel;
	} // isParallel

	public void setContext(NamespaceDefinition context) {
		this.context = context;
	} // setContext

	public NamespaceDefinition getContext() {
		return this.context;
	} // getContext

	public void mapTo(ArrayList<Element> elements) {
		super.mapTo(null);

		ArrayList<Statement> statements = this.getBlock().getStatements();
		StructuredActivityNode previousNode = null;

		for (Statement statement : statements) {
			StatementMapping mapping = (StatementMapping) this.map(statement);
			mapping.setContext(this.getContext());
			StructuredActivityNode node = mapping.getNode();

			if (mapping.isError()) {
				this.setError(mapping.getError());
				break;
			} else {
				elements.add(node);

				if (!isParallel && previousNode != null) {
					ControlFlow flow = new ControlFlow();
					flow.setSource(previousNode);
					flow.setTarget(node);
					elements.add(flow);
				}
			}

			previousNode = node;
		}
	} // mapTo

	public Block getBlock() {
		return (Block) this.getSourceNode();
	} // getBlock

	public ArrayList<Element> getModelElements() {
		ArrayList<Element> elements = new ArrayList<Element>();
		this.mapTo(elements);
		return elements;
	} // getModelElements

} // BlockMapping
