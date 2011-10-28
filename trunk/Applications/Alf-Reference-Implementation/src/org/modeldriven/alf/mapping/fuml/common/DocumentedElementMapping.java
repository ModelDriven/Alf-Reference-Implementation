/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.common;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.DocumentedElement;

import fUML.Syntax.Classes.Kernel.Comment;
import fUML.Syntax.Classes.Kernel.CommentList;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class DocumentedElementMapping extends SyntaxElementMapping {
    
    /**
     * If an element includes documentation, then each documentation string maps
     * to a comment element attached to mapping of the documented element, with
     * the comment body given by the documentation text.
     */
    @Override
    public void mapTo(Element element) throws MappingError {
        super.mapTo(element);

        DocumentedElement documentedElement = this.getDocumentedElement();

        for (String documentation: documentedElement.getDocumentation()) {
          Comment comment = new Comment();
          comment.body = documentation;
          element.ownedComment.addValue(comment);
        }
    }

    public DocumentedElement getDocumentedElement() {
        return (DocumentedElement)this.getSource();
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        Element element = this.getElement();
        if (element != null) {
            CommentList comments = element.ownedComment;
            if (!comments.isEmpty()) {
                System.out.println("ownedComment:");
                for (Comment comment: comments) {
                    System.out.println(prefix + " Comment");
                    System.out.println(comment.body);
                }
            }
        }
    }
    
}
