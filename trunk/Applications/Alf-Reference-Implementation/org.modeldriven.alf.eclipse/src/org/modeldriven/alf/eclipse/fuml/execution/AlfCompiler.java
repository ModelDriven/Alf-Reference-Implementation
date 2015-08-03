/*******************************************************************************
 * Copyright 2013-2015 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.execution;

import java.io.IOException;

import org.modeldriven.alf.eclipse.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.uml.StereotypeApplication;
import org.eclipse.emf.ecore.resource.Resource;

public class AlfCompiler extends org.modeldriven.alf.fuml.execution.AlfCompiler {
    
	protected String umlDirectory = "UML";
	
	protected void setUmlDirectory(String umlDirectory) {
		this.umlDirectory = umlDirectory;
	}
    
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
    public void saveModel(String name, org.modeldriven.alf.uml.Package model) 
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
    
    @Override
    protected boolean matchesWithArg(String option) {
        return super.matchesWithArg(option) || "u".equals(option);
    }
    
    @Override
    protected void parseOptionWithArg(String option, String arg) {
        if (option.equals("u")) {
            this.setUmlDirectory(arg);
        } else {
            super.parseOptionWithArg(option, arg);
        }
    }
    
    @Override
    protected void printUsage() {
        this.println("Usage is");
        this.println("  alfc [options] unit");
        this.println("where unit is the qualified name of an Alf unit and");
        this.println("allowable options are:");
        this.println("  -f        Treat unit as a file name rather than a qualifed name");
        this.println("  -l path   Set UML library directory path (default is \"UML/Libraries\")");
        this.println("  -m path   Set model directory path (default is \"Models\")");
        this.println("  -p        Parse and constraint check only");
        this.println("  -P        Parse, constraint check and print abstract syntax tree");
        this.println("  -u        Set UML output directory path (default is \"UML\")");
        this.println("  -v        Set verbose mode");
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
