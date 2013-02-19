/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class Element implements org.modeldriven.alf.uml.Element {
	
	private static final ElementFactory factory = new ElementFactory();

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
        Element newInstance = 
        		(Element)factory.newInstance(base.getClass().getSimpleName());
        if (newInstance != null) {
            newInstance.setBase(base);
        }
        return newInstance;
    }
    
    @Override
    public void applyStereotype(org.modeldriven.alf.uml.Stereotype stereotype) {
    	this.getBase().applyStereotype(stereotype == null? null: 
    		((Stereotype)stereotype).getBase());
    }
    
    @Override
    public boolean isStereotypeApplied(org.modeldriven.alf.uml.Stereotype stereotype) {
    	return stereotype != null && this.getBase().isStereotypeApplied(
    			((Stereotype)stereotype).getBase());
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

	@Override
	public void replace(
			org.modeldriven.alf.uml.Element element, 
			org.modeldriven.alf.uml.Element newElement) {
		org.eclipse.uml2.uml.Element elementBase = ((Element)element).getBase();
		org.eclipse.uml2.uml.Element newElementBase = newElement == null? null: 
			((Element)newElement).getBase();
		for (EStructuralFeature.Setting setting: 
			EcoreUtil.UsageCrossReferencer.find(elementBase, this.getBase())) {
			if (setting.getEStructuralFeature().isChangeable()) {
				EcoreUtil.replace(setting, elementBase, newElementBase);
			}
		}
	}
	
	@Override
	public int hashCode() {
		return this.getBase().hashCode();
	}

	public boolean equals(Object other) {
		org.eclipse.uml2.uml.Element base = this.getBase();
		return other instanceof Element && 
				((Element)other).getBase().equals(base) ||
				other instanceof org.eclipse.uml2.uml.Element &&
				other.equals(base);
	}

	public String toString() {
		Object base = this.getBase();
		return base == null? null: base.toString();
	}

   public String toString(boolean includeDerived) {
        return this.toString();
    }

    public void print(String prefix) {
        System.out.println(prefix + this.toString());
    }
}
