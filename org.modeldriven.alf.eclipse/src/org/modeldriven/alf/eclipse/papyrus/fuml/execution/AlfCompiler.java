/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.execution;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.fuml.impl.units.RootNamespaceImpl;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.ElementFactory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

public class AlfCompiler extends org.modeldriven.alf.execution.AlfCompiler {
    
    protected RootNamespaceImpl rootScopeImpl = null;    
    protected boolean isFileName = false;
    protected String umlDirectory = "UML";
    
    public RootNamespaceImpl getRootScopeImpl() {
        return this.rootScopeImpl;
    }
    
    public void setModelDirectory(String modelDirectory) {
        this.getRootScopeImpl().setModelDirectory(modelDirectory);
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        this.getRootScopeImpl().setLibraryDirectory(libraryDirectory);
    }
    
    public static void setDebugLevel(Level level) {
        Logger logger = Logger.getLogger(org.eclipse.papyrus.moka.fuml.debug.Debug.class);
        logger.setLevel(level);
    }
    
    public void setIsFileName(boolean isFileName) {
        this.isFileName = isFileName;
    }
    
    @Override
    public void setIsVerbose(boolean isVerbose) {
        super.setIsVerbose(isVerbose);
        this.getRootScopeImpl().setIsVerbose(isVerbose);
    }
    
    @Override
    protected Locus createLocus() {
        Locus locus = new Locus();
        // org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ExecutionFactory factory = locus.getFactory().getBase(); 
        // factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        // factory.setStrategy(new FIFOGetNextEventStrategy());
        // factory.setStrategy(new FirstChoiceStrategy());       
        
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
    protected void saveModel(NamespaceDefinition definition) {
        FumlMapping mapping = FumlMapping.getMapping(RootNamespace.getRootScope());
        Element element = mapping.getElement();
        ResourceSet resourceSet = new ResourceSetImpl();
        UMLResourcesUtil.init(resourceSet);
        URI uri = URI.createFileURI(this.umlDirectory).
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
            // ioe.printStackTrace();
        }
    }
    
    public String parseArgs(String[] args) {
        Logger logger = Logger.getLogger(org.eclipse.papyrus.moka.fuml.debug.Debug.class);
        Level level = logger.getLevel();

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (arg.charAt(0) != '-') {
                break;
            }
            String option = arg.substring(1);
            i++;
            if (i < args.length) {
                if (option.equals("v")) {
                    this.setIsVerbose(true);
                } else if (option.equals("f")) {
                    this.setIsFileName(true);
                } else if (option.equals("p")) {
                    this.setIsParseOnly(true);
                } else if (option.equals("P")) {
                    this.setIsPrint(true);
                } else if (option.matches("[mld]")) {
                    arg = args[i];
                    if (arg.charAt(0) == '-') {
                        return null;
                    }
                    i++;
                    if (option.equals("m")) {
                        this.setModelDirectory(arg);
                    } else if (option.equals("l")) {
                        this.setLibraryDirectory(arg);
                    } else if (option.equals("d")) {
                        setDebugLevel(Level.toLevel(arg, level));
                        level = logger.getLevel();
                    }
                } else {
                    return null;
                }
            }
        }
        
        return i == args.length - 1? args[i]: null;
    }
    
    public AlfCompiler(String[] args) {
        super();
        this.rootScopeImpl = new RootNamespaceImpl();
        
        PropertyConfigurator.configure("log4j.properties");
        
        String unitName = this.parseArgs(args);
        UnitDefinition unit = null;
        
        if (unitName != null) {
            QualifiedName qualifiedName = new QualifiedName();
            
            if (this.isFileName) {
                try {
                    unit = ((RootNamespaceImpl) RootNamespace.getRootScope().getImpl()).
                            getModelScopeImpl().resolveModelFile(unitName);
                } catch (FileNotFoundException e) {
                    this.println("File not found: " + unitName);
                }
                if (unit == null) {
                    unit = new MissingUnit(new QualifiedName());
                }
            } else {        
                String[] names = unitName.replace(".","::").split("::");
                for (String name: names) {
                    qualifiedName.getImpl().addName(name);
                }
                unit = RootNamespace.resolve(qualifiedName);
            }

            this.processUnit(unit);
            
        } else {
            this.println("Usage is");
            this.println("  alfc [options] unit");
            this.println("where unit is the qualified name of an Alf unit and");
            this.println("allowable options are:");
            this.println("  -d OFF|FATAL|ERROR|WARN|INFO|DEBUG|ALL");
            this.println("            Set debug logging level (default is as configured)");
            this.println("  -f        Treat unit as a file name rather than a qualifed name");
            this.println("  -l path   Set library directory path (default is \"Library\")");
            this.println("  -m path   Set model directory path (default is \"Models\")");
            this.println("  -p        Parse and constraint check only");
            this.println("  -P        Parse, constraint check and print abstract syntax tree");
            this.println("  -v        Set verbose mode");
        }         
    }
    
    public static void main(String[] args) {
        new AlfCompiler(args);
    }
}
