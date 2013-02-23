/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.uml;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.UMLFactory;

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
	public void replaceAll(
			List<? extends org.modeldriven.alf.uml.Element> elements, 
			List<? extends org.modeldriven.alf.uml.Element> newElements) {
		List<org.eclipse.uml2.uml.Element> umlElements = getBases(elements);
		List<org.eclipse.uml2.uml.Element> newUmlElements = getBases(newElements);
		Map<EObject, Collection<EStructuralFeature.Setting>> map =
				EcoreUtil.UsageCrossReferencer.findAll(umlElements, this.getBase());
		for (int i = 0; i < umlElements.size(); i++) {
			org.eclipse.uml2.uml.Element umlElement = umlElements.get(i);
			org.eclipse.uml2.uml.Element newUmlElement = newUmlElements.get(i);
			replace(map.get(umlElement), umlElement, newUmlElement);
		}

	}
	
	private static List<org.eclipse.uml2.uml.Element> getBases(
			List<? extends org.modeldriven.alf.uml.Element> elements) {
		List<org.eclipse.uml2.uml.Element> umlElements = 
				new ArrayList<org.eclipse.uml2.uml.Element>();		
		for (org.modeldriven.alf.uml.Element element: elements) {
			umlElements.add(element == null? null: ((Element)element).getBase());
		}
		return umlElements;
	}
	
	private static void replace(
			Collection<EStructuralFeature.Setting> settings, 
			org.eclipse.uml2.uml.Element element,
			org.eclipse.uml2.uml.Element newElement) {
		for (EStructuralFeature.Setting setting: settings) {
			EObject object = setting.getEObject();
			EStructuralFeature feature = setting.getEStructuralFeature();
			if (feature.isChangeable()) {
				EcoreUtil.replace(setting, element, newElement);
				if (newElement == null && feature.isRequired()) {
					fixRequiredFeature(object, feature);
				}
			}
		}
	}
	
	private static void replace(
			org.eclipse.uml2.uml.Element context, 
			org.eclipse.uml2.uml.Element element, 
			org.eclipse.uml2.uml.Element newElement) {
		replace(EcoreUtil.UsageCrossReferencer.find(element, context), 
				element, newElement);
	}
	
	// This method handles the case when a required feature is supposed to be
	// replaced with a null value.
	// NOTE: Currently, the only case that is handled is
	// ReadIsClassifierObjectAction.classifier. ReadExtent.classifer is not
	// a possibility since, in Alf, a non-constrained template parameter always
	// has a data type as its templateable parameter and fUML requires that
	// ReadExtent.classifier be a Class.
	private static void fixRequiredFeature(EObject object, EStructuralFeature feature) {
		String name = feature.getName();
		if (object instanceof org.eclipse.uml2.uml.ReadIsClassifiedObjectAction && 
				"classifier".equals(name)) {
			org.eclipse.uml2.uml.ReadIsClassifiedObjectAction action = 
					(org.eclipse.uml2.uml.ReadIsClassifiedObjectAction)object;
			org.eclipse.uml2.uml.StructuredActivityNode node = 
					UMLFactory.eINSTANCE.createStructuredActivityNode();
			node.setName(action.getName());
			node.getStructuredNodeInputs().add(action.getObject());
			org.eclipse.uml2.uml.LiteralBoolean literal =
					UMLFactory.eINSTANCE.createLiteralBoolean();
			literal.setValue(true);
			org.eclipse.uml2.uml.ValueSpecificationAction valueAction =
					UMLFactory.eINSTANCE.createValueSpecificationAction();
			valueAction.setResult(action.getResult());
			valueAction.setValue(literal);
			valueAction.setName("Value(true)");
			node.getNodes().add(valueAction);
			org.eclipse.uml2.uml.Element owner = action.getOwner();
			List<org.eclipse.uml2.uml.ActivityNode> nodes = owner instanceof Activity? 
					((org.eclipse.uml2.uml.Activity)owner).getNodes():
				    ((org.eclipse.uml2.uml.StructuredActivityNode)owner).getNodes();
			nodes.remove(action);
			nodes.add(node);
			replace(owner, action, node);
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
