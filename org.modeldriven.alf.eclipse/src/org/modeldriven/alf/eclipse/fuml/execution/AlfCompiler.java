/*******************************************************************************
 * Copyright 2013-2016 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.eclipse.fuml.execution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.eclipse.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.uml.StereotypeApplication;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;

public class AlfCompiler extends org.modeldriven.alf.fuml.execution.AlfCompiler {
	
	public static final String DEFAULT_UML_DIRECTORY = "UML";
    
	protected String umlDirectory = DEFAULT_UML_DIRECTORY;
	
	protected void setUmlDirectory(String umlDirectory) {
		this.umlDirectory = umlDirectory;
	}
    
    @Override
    protected ElementFactory createElementFactory() {
        return org.modeldriven.alf.eclipse.uml.Element.FACTORY;
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
    		Map<String, String> options = new HashMap<String, String>();
    		options.put(XMLResource.OPTION_XML_VERSION, "1.1");
    		resource.save(options);
    		this.printVerbose("Saved to " + resource.getURI());
    	} catch (IOException ioe) {
    		this.println("Error saving model to " + resource.getURI() + 
    				": " + ioe.getMessage());
    		// ioe.printStackTrace();
    		throw ioe;
    	} finally {
    		resource.unload();
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
        this.println("  -l path   Set library directory path (default is \"" +
        							RootNamespaceImpl.DEFAULT_LIBRARY_DIRECTORY + "\")");
        this.println("  -m path   Set model directory path (default is \"" +
        							RootNamespaceImpl.DEFAULT_MODEL_DIRECTORY + "\")");
        this.println("  -p        Parse and constraint check only");
        this.println("  -P        Parse, constraint check and print abstract syntax tree");
        this.println("  -u path   Set UML output directory path (default is \"" +
        							DEFAULT_UML_DIRECTORY + "\")");
        this.println("  -v        Set verbose mode (if used alone without unit, prints version info)");
    }
    
    public AlfCompiler() {
    	super();
    }
    
    public static void main(String[] args) {
        new AlfCompiler().run(args);
    }
}
