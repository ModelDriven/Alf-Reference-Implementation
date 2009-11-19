
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class MappingFactory {

	public MappingNode getMapping(SyntaxNode syntaxNode) {
		Node target = syntaxNode.getTargetNode();

		if (target == null) {
			target = this.instantiateMapping(syntaxNode);
			target.setSourceNode(syntaxNode);
			((MappingNode) target).setFactory(this);
		}

		// System.out.println("getMapping: target = " + target + "(" +
		// target.getClass().getName() + ")");

		if (target instanceof MappingNode) {
			return (MappingNode) target;
		} else {
			return null;
		}
	} // getMapping

	public MappingNode instantiateMapping(SyntaxNode syntaxNode) {
		String className = syntaxNode.getClass().getName().replace(".syntax",
				".mapping")
				+ "Mapping";

		try {
			return (MappingNode) Class.forName(className).newInstance();
		} catch (Exception e) {
			return new ErrorMapping(syntaxNode, "No mapping: " + className);
		}
	} // instantiateMapping

} // MappingFactory
