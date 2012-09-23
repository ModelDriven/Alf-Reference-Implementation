/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public abstract class Element implements org.modeldriven.alf.uml.Element {

	protected Object base;

	public Element(Object base) {
		this.setBase(base);
	}

	public Object getBase() {
		return this.base;
	}
	
	private void setBase(Object base) {
	    this.base = base;
	}
	
    public fUML.Syntax.Classes.Kernel.Element getBaseAsElement() {
        return (fUML.Syntax.Classes.Kernel.Element)this.getBase();
    }

    public fUML.Syntax.Classes.Kernel.Comment getBaseAsComment() {
        return (fUML.Syntax.Classes.Kernel.Comment)this.getBase();
    }
    
    public boolean isElement() {
        return this.getBase() instanceof fUML.Syntax.Classes.Kernel.Element;
    }

    public Element wrap(Object base) {
        Element newInstance = ElementFactory.newInstance(base.getClass().getSimpleName());
        if (newInstance != null) {
            newInstance.setBase(base);
        }
        return newInstance;
    }

	public List<org.modeldriven.alf.uml.Element> getOwnedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		if (this.isElement()) {
    		for (fUML.Syntax.Classes.Kernel.Element element: this.getBaseAsElement().ownedElement) {
    			list.add(this.wrap(element));
    		}
		}
		return list;
	}

	public org.modeldriven.alf.uml.Element getOwner() {
		return this.wrap(this.isElement()? 
		        this.getBaseAsElement().owner: 
		        this.getBaseAsComment().annotatedElement.get(0));
}

	public List<org.modeldriven.alf.uml.Comment> getOwnedComment() {
		List<org.modeldriven.alf.uml.Comment> list = new ArrayList<org.modeldriven.alf.uml.Comment>();
		if (this.isElement()) {
    		for (fUML.Syntax.Classes.Kernel.Comment element : this.getBaseAsElement().ownedComment) {
    			list.add(new Comment(element));
    		}
		}
		return list;
	}

	public void addOwnedComment(org.modeldriven.alf.uml.Comment ownedComment) {
	    if (this.isElement()) {
	        this.getBaseAsElement().ownedComment.add(((Comment) ownedComment).getBase());
	    }
	}

    public String toString(boolean includeDerived) {
        return this.toString();
    }

    public void print(String prefix) {
        System.out.println(prefix + this.toString());
    }
}
