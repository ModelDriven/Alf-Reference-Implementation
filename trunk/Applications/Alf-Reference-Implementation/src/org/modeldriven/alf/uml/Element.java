package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Comment;
import org.modeldriven.alf.uml.Element;

public interface Element {
    public List<Element> getOwnedElement();

    public Element getOwner();

    public List<Comment> getOwnedComment();

    public void addOwnedComment(Comment ownedComment);

    public String toString(boolean includeDerived);

    public void print(String prefix);
}
