
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.mapping.namespaces;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

import alf.mapping.*;

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
