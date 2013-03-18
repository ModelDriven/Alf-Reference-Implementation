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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.modeldriven.alf.eclipse.uml.Element;
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
    private ResourceSet resourceSet;
    
    public RootNamespaceImpl() {
        super(RootNamespace.getRootScope());
        RootNamespace.setRootImpl(this);        
    }
    
    public void setContext(org.eclipse.uml2.uml.Element contextElement) {
    	this.contextElement = contextElement;
    	
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
    	if (this.resourceSet != null) {
	    	for (NamedElement element: UMLUtil.findNamedElements(this.resourceSet, name, false,
	    			classifierOnly? UMLPackage.Literals.CLASSIFIER: null)) {
	    		members.add(ImportedMemberImpl.makeImportedMember(
	    				element.getName(), 
	    				org.modeldriven.alf.eclipse.uml.Element.wrap(element), null));
	    	}
    	}
    	return members;
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

}
