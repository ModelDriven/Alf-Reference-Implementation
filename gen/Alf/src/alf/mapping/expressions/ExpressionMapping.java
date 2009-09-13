
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.expressions;

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
