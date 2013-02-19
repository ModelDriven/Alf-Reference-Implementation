/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.units;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionUtils;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.modeldriven.alf.eclipse.uml.Element;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Namespace;

public class RootNamespaceImpl extends org.modeldriven.alf.fuml.units.RootNamespaceImpl {
    
    private ResourceSet resourceSet;
    
    public RootNamespaceImpl() {
    	this("UML");
    }
    
    public RootNamespaceImpl(String libraryDirectory) {
        super();
        this.resourceSet = new ResourceSetImpl();
        UMLResourcesUtil.init(this.resourceSet);
        this.setLibraryDirectory(libraryDirectory);
        this.getResource(libraryDirectory,"StandardL2.profile");
    }
    
    public URI createURI(String directory, String name) {
        return this.resourceSet.getURIConverter().normalize(
        		URI.createURI(directory).
                appendSegment(name).
                appendFileExtension(UMLResource.FILE_EXTENSION));
    }
    
    public Resource createResource(String directory, String name) {
        return this.resourceSet.createResource(
                this.createURI(directory, name), 
                UMLResource.UML_CONTENT_TYPE_IDENTIFIER);
    }
    
    public Resource getResource(String directory, String name) {
        return this.resourceSet.getResource(
                this.createURI(directory, name), true);
    }
    
    public Resource getLibraryResource(String name) {
    	return this.getResource(UMLResource.LIBRARIES_PATHMAP, name);
    }
    
    @Override
    public void setLibraryDirectory(String libraryDirectory) {
    	super.setLibraryDirectory(libraryDirectory);
    	if (this.resourceSet != null) {
	    	Map<URI, URI> uriMap = this.resourceSet.getURIConverter().getURIMap();
	    	uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), 
	    			URI.createFileURI(libraryDirectory +"/"));
	    	uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), 
	    			URI.createFileURI(libraryDirectory +"/"));
	    	uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), 
	    			URI.createFileURI(libraryDirectory +"/"));
    	}
    }
    
    @Override
    public void setIsVerbose(boolean isVerbose) {
    	super.setIsVerbose(isVerbose);
    	this.isVerbose = isVerbose;
    }
    
    @Override
    public UnitDefinition resolveModelUnit(QualifiedName qualifiedName) {
    	UnitDefinition unit = new MissingUnit(qualifiedName);
    	String pathName = qualifiedName.getImpl().getPathName();
    	int n = qualifiedName.getNameBinding().size();
    	if (n > 0) {
    		String name = qualifiedName.getNameBinding().get(0).getName();
        	Collection<org.eclipse.uml2.uml.NamedElement> elements = 
        			UMLUtil.findNamedElements(this.resourceSet, name);
        	if (elements.size() == 0) {
    	        Resource resource = null;
    	        try {
    	        	resource = this.getLibraryResource(name);
    	        	if (this.isVerbose) {
    	        		System.out.println("Loaded " + 
    	        				this.resourceSet.getURIConverter().normalize(
    	        						resource.getURI()));
    	        	}
    	        } catch (Exception e) {
    	        	System.out.println("Error loading " + name + ": " + e.getMessage());
    	        }
    	        if (resource != null) {
    	        	elements = UMLUtil.findNamedElements(resource, name);
    	        }
        	}
        	
        	if (elements.size() == 1) {
        		org.eclipse.uml2.uml.NamedElement element = 
        				(org.eclipse.uml2.uml.NamedElement)elements.toArray()[0];
        		/*
        		if (!(element instanceof org.eclipse.uml2.uml.Model)) {
        			org.eclipse.uml2.uml.Model model = UMLFactory.eINSTANCE.createModel();
        			org.eclipse.uml2.uml.ElementImport elementImport = UMLFactory.eINSTANCE.createElementImport();
        			elementImport.setImportedElement((org.eclipse.uml2.uml.PackageableElement)element);
        			model.getElementImports().add(elementImport);
        			element = model;
        		}
        		*/
        		if (n > 1) {
        			qualifiedName = qualifiedName.getImpl().copy().getSelf();
        			qualifiedName.getNameBinding().remove(0);
        			elements = NameResolutionUtils.getNamedElements(
        					qualifiedName.getImpl().getPathName(), element, null);
        			element = elements.size() != 1? null:
        				(org.eclipse.uml2.uml.NamedElement)elements.toArray()[0];
        		}
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
            System.out.println("Unit not found: " + pathName);
        }
        
        return unit;
    }
    
}
