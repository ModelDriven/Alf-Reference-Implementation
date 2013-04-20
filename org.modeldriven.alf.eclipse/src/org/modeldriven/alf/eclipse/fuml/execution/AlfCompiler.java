/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.execution;

import java.io.IOException;

import org.modeldriven.alf.eclipse.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.uml.StereotypeApplication;

import org.eclipse.emf.ecore.resource.Resource;

public class AlfCompiler extends org.modeldriven.alf.fuml.execution.AlfCompiler {
    
	protected String umlDirectory;
    
    @Override
    protected ElementFactory createElementFactory() {
        return new org.modeldriven.alf.eclipse.uml.ElementFactory();
    }
    
    @Override
    public UnitDefinition parse(String unitName, boolean isFileName) {
    	((RootNamespaceImpl)this.getRootScopeImpl()).initialize();
    	return super.parse(unitName, isFileName);
    }
    
    @Override
    protected void saveModel(String name, org.modeldriven.alf.uml.Package model) 
    		throws IOException {
    	Resource resource = ((RootNamespaceImpl)this.getRootScopeImpl()).createResource(
    			this.umlDirectory, name);
    	resource.getContents().add(
    			((org.modeldriven.alf.eclipse.uml.Element)model).getBase());
    	
    	// NOTE: Stereotypes must be applied after the model is put in
    	// a resource, since the resource is what contains the stereotype
    	// applications.
    	StereotypeApplication.applyStereotypes();

    	try {
    		resource.save(null);
    		this.printVerbose("Saved to " + resource.getURI());
    	} catch (IOException ioe) {
    		this.println("Error saving model to " + resource.getURI() + 
    				": " + ioe.getMessage());
    		// ioe.printStackTrace();
    		throw ioe;
    	}
    }
    
    @Override
    protected RootNamespaceImpl createRootScopeImpl() {
    	return new RootNamespaceImpl();
    }
    
    protected void configure() {
    	super.configure();
    	this.umlDirectory = "UML";
    	this.rootScopeImpl = new RootNamespaceImpl();
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
