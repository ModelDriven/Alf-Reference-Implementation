/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.execution;

import java.io.IOException;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.ElementFactory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.FirstChoiceStrategy;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

public class AlfCompiler extends org.modeldriven.alf.fuml.execution.AlfCompiler {
    
	protected String umlDirectory;
    
	@Override
	protected Locus createLocus() {
		Locus locus = new Locus();
		org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ExecutionFactory factory = 
				locus.getFactory().getBase(); 
		factory.setStrategy(new RedefinitionBasedDispatchStrategy());
		factory.setStrategy(new FIFOGetNextEventStrategy());
		factory.setStrategy(new FirstChoiceStrategy());       

		return locus;
	}

   @Override
    protected FumlMappingFactory createFumlFactory() {
        return new org.modeldriven.alf.eclipse.papyrus.fuml.mapping.FumlMappingFactory();
    }
    
    @Override
    protected ElementFactory createElementFactory() {
        return new org.modeldriven.alf.eclipse.uml.ElementFactory();
    }
    
    @Override
    protected NamespaceDefinition saveModel(NamespaceDefinition definition) {
    	if (definition != null) {
	        FumlMapping mapping = FumlMapping.getMapping(RootNamespace.getRootScope());
	        Element element = mapping.getElement();
	        ResourceSet resourceSet = new ResourceSetImpl();
	        UMLResourcesUtil.init(resourceSet);
	        URI uri = URI.createFileURI(umlDirectory).
	                    appendSegment(definition.getName()).
	                    appendFileExtension(UMLResource.FILE_EXTENSION);
	        Resource resource = resourceSet.createResource(
	                uri, UMLResource.UML_CONTENT_TYPE_IDENTIFIER);
	        resource.getContents().add(
	                ((org.modeldriven.alf.eclipse.uml.Element)element).getBase());
	        try {
	            resource.save(null);
	            this.printVerbose("Saved to " + uri);
	        } catch (IOException ioe) {
	            this.println("Error saving model: " + ioe.getMessage());
	            definition = null;
	            // ioe.printStackTrace();
	        }
    	}
    	return definition;
    }
    
    protected void configure() {
    	super.configure();
    	this.umlDirectory = "UML";
    }
    
    public AlfCompiler() {
    	super();
    }
    
    public AlfCompiler(String[] args) {
        super(args);
    }
    
    public static void main(String[] args) {
        new AlfCompiler(args);
    }
}
