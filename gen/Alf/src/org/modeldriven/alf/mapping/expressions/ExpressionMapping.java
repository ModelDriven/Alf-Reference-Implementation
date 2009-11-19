
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

import org.modeldriven.alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.IntermediateActivities.*;

public abstract class ExpressionMapping extends MappingNode {

	private NamespaceDefinition context = null;

	public void setContext(NamespaceDefinition context) {
		this.context = context;
	} // setContext

	public NamespaceDefinition getContext() {
		return this.context;
	} // getContext

	public MappingNode map(SyntaxNode syntaxNode) {
		MappingNode mapping = super.map(syntaxNode);

		if (mapping instanceof ExpressionMapping) {
			((ExpressionMapping) mapping).setContext(this.getContext());
		}

		return mapping;
	} // map

	public abstract ActivityNode getResultSource();

	public abstract Classifier getType();
} // ExpressionMapping
