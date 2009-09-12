
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class MappingFactory {

	public MappingNode getMapping(SyntaxNode syntaxNode) {
		Node target = syntaxNode.getTarget();

		if (target == null) {
			target = this.instantiateMapping(syntaxNode);
			target.setSource(syntaxNode);
			((MappingNode) target).setFactory(this);
		}

		if (target instanceof MappingNode) {
			return (MappingNode) target;
		} else {
			return null;
		}
	} // getMapping

	public MappingNode instantiateMapping(SyntaxNode syntaxNode) {
		String className = syntaxNode.getClass().getName().replace(".Syntax",
				".Mapping")
				+ "Mapping";

		try {
			return (MappingNode) Class.forName(className).newInstance();
		} catch (Exception e) {
			return new ErrorMapping(syntaxNode, "No mapping: " + className);
		}
	} // instantiateMapping

} // MappingFactory
