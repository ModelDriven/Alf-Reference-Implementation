/*******************************************************************************
 * Copyright 2013-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.eclipse.units;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.modeldriven.alf.eclipse.uml.Element;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.InternalElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Namespace;

public class RootNamespaceImpl extends org.modeldriven.alf.fuml.units.RootNamespaceImpl {

	public static final String ALF_LIBRARIES_PATHMAP = "pathmap://ALF_LIBRARIES/";
	public static final String DEFAULT_LIBRARY_DIRECTORY = "Libraries";
	public static final String UML_LIBRARY_SUBDIRECTORY = "uml";
	public static final String STANDARD_PROFILE_FILE_NAME = "Standard.profile";
	public static final String FUML_LIBRARY_FILE_NAME = "fUML.library";
	public static final String ALF_LIBRARY_FILE_NAME = "Alf.library";

	private ResourceSet resourceSet;
	private URI baseURI;
	private boolean uninitialized = true;

	public RootNamespaceImpl() {
		this(System.getProperty("user.dir"));
	}

	public RootNamespaceImpl(String baseDirectory) {
		this(URI.createFileURI(baseDirectory + "/"));
	}

	public RootNamespaceImpl(URI baseURI) {
		this(new ResourceSetImpl(), baseURI);
		UMLResourcesUtil.init(this.resourceSet);
	}

	// NOTE: The resourceSet must be initialized for UML resources.
	public RootNamespaceImpl(ResourceSet resourceSet, URI baseURI) {
		super();
		this.resourceSet = resourceSet;
		this.baseURI = baseURI;
	}

	public void initialize() {
		if (this.uninitialized) {
			if (this.getLibraryDirectory() == null) {
				this.setLibraryDirectory(DEFAULT_LIBRARY_DIRECTORY);
			}
			this.getProfileResource(STANDARD_PROFILE_FILE_NAME);
			this.getAlfLibraryResource(FUML_LIBRARY_FILE_NAME);
			this.getAlfLibraryResource(ALF_LIBRARY_FILE_NAME);
			this.uninitialized = false;
		}
	}

	public ResourceSet getResourceSet() {
		return this.resourceSet;
	}
	
	public URI normalize(URI uri) {
		return this.resourceSet.getURIConverter().normalize(uri);
	}

	public URI createNormalizedURI(String directory, String name) {
		return this.normalize(this.createURI(directory, name));
	}

	public URI createURI(String directory, String name) {
		String path;
		if (directory == null) {
			path = "/";
		} else if (directory.endsWith("/")) {
			path = directory + name;
		} else {
			path = directory + "/" + name;
		}   	
		return URI.createURI(path).appendFileExtension(UMLResource.FILE_EXTENSION);
	}

	public Resource createResource(String directory, String name) {
		return this.resourceSet.createResource(
				this.createNormalizedURI(directory, name), 
				UMLResource.UML_CONTENT_TYPE_IDENTIFIER);
	}

	public Resource getNormalizedResource(String directory, String name) {
		URI uri = this.createNormalizedURI(directory, name);
		Resource resource = this.resourceSet.getResource(uri, true);
		if (this.isVerbose) {
			System.out.println("Loaded " + uri);
		}
		return resource;
	}

	public Resource getResource(URI uri) {
		Resource resource = this.resourceSet.getResource(uri, true);
		if (this.isVerbose) {
			System.out.println("Loaded " + this.normalize(uri));
		}
		return resource;
	}

	public Resource getResource(String uri) {
		return this.getResource(URI.createURI(uri));
	}

	public Resource getResource(String directory, String name) {
		return this.getResource(this.createURI(directory, name));
	}

	public Resource getMetamodelResource(String name) {
		return this.getResource(UMLResource.METAMODELS_PATHMAP, name);
	}

	public Resource getProfileResource(String name) {
		return this.getResource(UMLResource.PROFILES_PATHMAP, name);
	}

	public Resource getUMLLibraryResource(String name) {
		return this.getResource(UMLResource.LIBRARIES_PATHMAP, name);
	}
	public Resource getAlfLibraryResource(String name) {
		return this.getResource(ALF_LIBRARIES_PATHMAP, name);
	}

	@Override
	public void setLibraryDirectory(String libraryDirectory) {
		super.setLibraryDirectory(libraryDirectory);
		URI libraryURI = URI.createFileURI(libraryDirectory + "/" + UML_LIBRARY_SUBDIRECTORY + "/");
		if (this.baseURI != null) {
			libraryURI = libraryURI.resolve(this.baseURI);
		}
		if (this.resourceSet != null) {
			Map<URI, URI> uriMap = this.resourceSet.getURIConverter().getURIMap();
			uriMap.put(URI.createURI(UMLResource.METAMODELS_PATHMAP), libraryURI);
			uriMap.put(URI.createURI(UMLResource.PROFILES_PATHMAP), libraryURI);
			uriMap.put(URI.createURI(UMLResource.LIBRARIES_PATHMAP), libraryURI);
			uriMap.put(URI.createURI(ALF_LIBRARIES_PATHMAP), libraryURI);
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
		String pathName = qualifiedName.getPathName();
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

		if (unit instanceof MissingUnit) {
			System.err.println("Unit not found: " + pathName);
		}

		return unit;
	}

	@Override
	public String makeBoundElementName(
			ElementReference templateReferent, 
			List<ElementReference> templateArguments) {
		return super.makeBoundElementName(
				templateReferent instanceof InternalElementReference? 
						templateReferent.getImpl().getName(): 
							templateReferent.getImpl().getQualifiedName().
							getPathName().replace("::", "$"), templateArguments);
	}

	@Override
	public ElementReference getInstantiationNamespace(
			ElementReference templateReferent) {
		return templateReferent instanceof InternalElementReference? 
				super.getInstantiationNamespace(templateReferent): 
					this.getModelNamespace().getImpl().getReferent();
	}

}
