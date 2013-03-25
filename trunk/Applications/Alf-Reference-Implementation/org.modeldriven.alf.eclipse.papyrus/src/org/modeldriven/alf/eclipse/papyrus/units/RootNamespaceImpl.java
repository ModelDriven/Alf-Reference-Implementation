/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.modeldriven.alf.eclipse.uml.Element;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.InternalElementReference;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;
import org.modeldriven.alf.uml.Namespace;

public class RootNamespaceImpl extends 
	org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl {
    
	private org.eclipse.uml2.uml.Element contextElement;
	private Model model;
	private ElementReference modelReference;
    private ResourceSet resourceSet;
    
    public RootNamespaceImpl() {
        super(RootNamespace.getRootScope());
        RootNamespace.setRootImpl(this);        
    }
    
    public void setContext(org.eclipse.uml2.uml.Element contextElement) {
    	this.contextElement = contextElement;
    	this.model = this.contextElement.getModel();
    	this.modelReference = ElementReferenceImpl.makeElementReference(
    			Element.wrap(this.model));
    	
    	Resource resource = contextElement.eResource();
        this.resourceSet = resource == null? null: resource.getResourceSet();
    }
    
    @Override
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        NamespaceDefinition definition = unit.getDefinition();
        NamespaceDefinition modelScope = definition.getNamespace();
        if (modelScope == null) {
            // NOTE: The model scope for a unit must include the unit itself,
            // so that it can refer to itself recursively.
            ModelNamespace modelNamespace = new ModelNamespace();
            ModelNamespaceImpl modelNamespaceImpl = 
            		new ModelNamespaceImpl(modelNamespace);
            modelNamespaceImpl.setContext(this.contextElement);
            modelNamespace.setImpl(modelNamespaceImpl);
            modelNamespace.getMember(); // To ensure computation of derived attributes.
            modelNamespace.addOwnedMember(definition);
            modelNamespace.addMember(definition);
            definition.setNamespace(modelNamespace);
            
            modelScope = modelNamespace;
        }
        return modelScope;
    }
    
    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
    	Collection<Member> members = new ArrayList<Member>();
    	for (NamedElement element: this.findNamedElements(name, classifierOnly)) {
    		members.add(ImportedMemberImpl.makeImportedMember(
    				element.getName(), 
    				org.modeldriven.alf.eclipse.uml.Element.wrap(element), null));
    	}
    	return members;
    }
    
    public Collection<NamedElement> findNamedElements(String name, boolean classifierOnly) {
    	return this.resourceSet == null? new ArrayList<NamedElement>(): 
    		UMLUtil.findNamedElements(this.resourceSet, name, false,
    			classifierOnly? UMLPackage.Literals.CLASSIFIER: 
    			UMLPackage.Literals.NAMED_ELEMENT);
    }

    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
    	UnitDefinition unit = new MissingUnit(qualifiedName);
    	String pathName = qualifiedName.getPathName();
    	if (this.resourceSet != null) {
	    	Collection<org.eclipse.uml2.uml.NamedElement> elements = 
	    			UMLUtil.findNamedElements(this.resourceSet, pathName);
	    	if (elements.size() == 1) {
	    		org.eclipse.uml2.uml.NamedElement element = 
	    				(org.eclipse.uml2.uml.NamedElement)elements.toArray()[0];
	    		if (element instanceof org.eclipse.uml2.uml.Namespace) {
	    			NamespaceDefinition namespace = 
	    					ExternalNamespace.makeExternalNamespace(
	    							(Namespace)Element.wrap(
	    									(org.eclipse.uml2.uml.Namespace)element),
	    									this.getSelf());
	    			unit = new UnitDefinition();
	    			unit.setIsModelLibrary(true);
	    			unit.setDefinition(namespace);
	    			namespace.setUnit(unit);
	    		}
	    	}
    	}

    	if (unit instanceof MissingUnit) {
    		System.err.println("Unit not found: " + pathName);
    	}

    	return unit;
    }

    @Override
    public String makeBoundElementNameFor(
            ElementReference templateReferent, 
            List<ElementReference> templateArguments) {
    	return super.makeBoundElementNameFor(
    			templateReferent instanceof InternalElementReference? 
    					templateReferent.getImpl().getName(): 
    				    templateReferent.getImpl().getQualifiedName().
    				    	getPathName().replace("::", "$"), templateArguments);
    }
    
    @Override
    public ElementReference getInstantiationNamespaceFor(
            ElementReference templateReferent) {
        return templateReferent instanceof InternalElementReference? 
        		super.getInstantiationNamespaceFor(templateReferent): 
        		this.modelReference;
    }
    
    public void replaceTemplateBindings() {
    	ElementReferenceImpl.replaceTemplateBindingsIn(
    			this.modelReference.getImpl().getUml());
    }
    
    public void addToModel(NamedElement element) {
    	if (element instanceof PackageableElement) {
    		this.model.getPackagedElements().add((PackageableElement)element);
    	}
    }

}
