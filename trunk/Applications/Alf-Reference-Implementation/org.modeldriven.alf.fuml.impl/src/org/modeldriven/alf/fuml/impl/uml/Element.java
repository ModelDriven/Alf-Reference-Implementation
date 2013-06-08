/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.uml;

import java.util.ArrayList;
import java.util.List;

public abstract class Element implements org.modeldriven.alf.uml.Element {
    
    private static final ElementFactory factory = new ElementFactory();

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
    
    @Override
    public void applyStereotype(org.modeldriven.alf.uml.Stereotype stereotype) {
    }
    
    @Override
    public boolean isStereotypeApplied(org.modeldriven.alf.uml.Stereotype stereotype) {
        return false;
    }

    public static Element wrap(Object base) {
        if (base == null) {
            return null;
        }
        Element newInstance = 
                (Element)factory.newInstance(base.getClass().getSimpleName());
        if (newInstance != null) {
            newInstance.setBase(base);
        }
        return newInstance;
    }
    
   @Override
	public List<org.modeldriven.alf.uml.Element> getOwnedElement() {
		List<org.modeldriven.alf.uml.Element> list = new ArrayList<org.modeldriven.alf.uml.Element>();
		if (this.isElement()) {
    		for (fUML.Syntax.Classes.Kernel.Element element: this.getBaseAsElement().ownedElement) {
    			list.add(wrap(element));
    		}
		}
		return list;
	}

    @Override
	public org.modeldriven.alf.uml.Element getOwner() {
		return wrap(this.isElement()? 
		        this.getBaseAsElement().owner: 
		        this.getBaseAsComment().annotatedElement.get(0));
}

    @Override
	public List<org.modeldriven.alf.uml.Comment> getOwnedComment() {
		List<org.modeldriven.alf.uml.Comment> list = new ArrayList<org.modeldriven.alf.uml.Comment>();
		if (this.isElement()) {
    		for (fUML.Syntax.Classes.Kernel.Comment element : this.getBaseAsElement().ownedComment) {
    			list.add(new Comment(element));
    		}
		}
		return list;
	}

    @Override
	public void addOwnedComment(org.modeldriven.alf.uml.Comment ownedComment) {
	    if (this.isElement()) {
	        this.getBaseAsElement().ownedComment.add(ownedComment==null? null: ((Comment) ownedComment).getBase());
	    }
	}
    
    @Override
    public void replaceAll(
            List<? extends org.modeldriven.alf.uml.Element> elements, 
            List<? extends org.modeldriven.alf.uml.Element> newElements) {    
    }

    @Override
    public int hashCode() {
        return this.getBase().hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
        Object base = this.getBase();
        return other == base ||
               other instanceof Element && 
                ((Element)other).getBase() == base;
    }
    
    @Override
    public String toString() {
        Object base = this.getBase();
        return base == null? null: base.toString();
    }

    @Override
    public String toString(boolean includeDerived) {
        return this.toString();
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix + this.toString());
    }
}
