/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Element implements org.modeldriven.alf.uml.Element {

	protected org.eclipse.uml2.uml.Element base;

	public Element(org.eclipse.uml2.uml.Element base) {
		this.base = base;
	}

	public org.eclipse.uml2.uml.Element getBase() {
		return (org.eclipse.uml2.uml.Element) this.base;
	}

    private void setBase(org.eclipse.uml2.uml.Element base) {
        this.base = base;
    }
    
    public static Element wrap(org.eclipse.uml2.uml.Element base) {
        if (base == null) {
            return null;
        }
        Element newInstance = ElementFactory.newInstance(base.getClass().getSimpleName());
        if (newInstance != null) {
            newInstance.setBase(base);
        }
        return newInstance;
    }
    
    public boolean equals(Object other) {
        return other instanceof Element && 
                ((Element)other).getBase() == this.getBase();
    }
    
    public String toString() {
        Object base = this.getBase();
        return base == null? null: base.toString();
    }

	public List<org.modeldriven.alf.uml.Element> getOwnedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		for (org.eclipse.uml2.uml.Element element : this.getBase()
				.getOwnedElements()) {
			list.add((org.modeldriven.alf.uml.Element) wrap(element));
		}
		return list;
	}

	public org.modeldriven.alf.uml.Element getOwner() {
		return (org.modeldriven.alf.uml.Element) wrap(this.getBase().getOwner());
	}

	public List<org.modeldriven.alf.uml.Comment> getOwnedComment() {
		List<org.modeldriven.alf.uml.Comment> list = new ArrayList<org.modeldriven.alf.uml.Comment>();
		for (org.eclipse.uml2.uml.Comment element : this.getBase()
				.getOwnedComments()) {
			list.add((org.modeldriven.alf.uml.Comment) wrap(element));
		}
		return list;
	}

	public void addOwnedComment(org.modeldriven.alf.uml.Comment ownedComment) {
		this.getBase().getOwnedComments().add(
				ownedComment == null ? null : ((Comment) ownedComment)
						.getBase());
	}

    public String toString(boolean includeDerived) {
        return this.toString();
    }

    public void print(String prefix) {
        System.out.println(prefix + this.toString());
    }
}
