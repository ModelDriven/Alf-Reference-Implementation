package org.modeldriven.uml;

import java.util.List;

public interface Element {
    public List<Element> getOwnedElement();

    public Element getOwner();

    public List<Comment> getOwnedComment();

    public void addOwnedComment(Comment ownedComment);

    public String toString(boolean includeDerived);

    public void print(String prefix);
}
