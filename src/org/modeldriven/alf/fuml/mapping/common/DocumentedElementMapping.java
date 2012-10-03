/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.common;

import java.util.List;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.DocumentedElement;

import org.modeldriven.alf.uml.Comment;
import org.modeldriven.alf.uml.Element;

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
          Comment comment = this.create(Comment.class);
          comment.setBody(documentation);
          element.addOwnedComment(comment);
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
            List<Comment> comments = element.getOwnedComment();
            if (!comments.isEmpty()) {
                System.out.println("ownedComment:");
                for (Comment comment: comments) {
                    System.out.println(prefix + " Comment");
                    System.out.println(comment.getBody());
                }
            }
        }
    }
    
}
