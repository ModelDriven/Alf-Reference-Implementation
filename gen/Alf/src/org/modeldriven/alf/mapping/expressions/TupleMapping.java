
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Actions.BasicActions.*;

public abstract class TupleMapping extends MappingNode {

	private ArrayList<Element> modelElements = new ArrayList<Element>();

	public abstract void mapTo(InvocationAction action,
			ArrayList<Parameter> parameters, NamespaceDefinition context);

	public void addModelElement(Element element) {
		this.modelElements.add(element);
	} // addModelElement

	public ArrayList<Element> getModelElements() {
		return this.modelElements;
	} // getModelElements

} // TupleMapping
