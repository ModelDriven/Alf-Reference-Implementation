
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.namespaces;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.*;

import fUML.Syntax.Classes.Kernel.*;

public abstract class DocumentedNodeMapping extends MappingNode {

	public void mapTo(Element annotatedElement) {
		super.mapTo(annotatedElement);

		DocumentedNode documentedNode = this.getDocumentedNode();

		for (String documentation : documentedNode.getDocumentations()) {
			Comment comment = new Comment();
			comment.body = documentation;
			annotatedElement.ownedComment.addValue(comment);
		}
	} // mapTo

	public DocumentedNode getDocumentedNode() {
		return (DocumentedNode) this.getSourceNode();
	} // getDocumentedNode

} // DocumentedNodeMapping
