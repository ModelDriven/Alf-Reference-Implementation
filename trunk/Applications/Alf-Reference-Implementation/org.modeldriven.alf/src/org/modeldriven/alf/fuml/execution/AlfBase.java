/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.fuml.units.RootNamespaceImpl;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.PrimitiveType;

public abstract class AlfBase extends org.modeldriven.alf.execution.AlfBase {
    
    protected RootNamespaceImpl rootScopeImpl = null;    
    protected boolean isParseOnly = false;
    protected boolean isPrint = false;
    protected boolean isFileName = false;
    protected Level debugLevel = Level.OFF;
    
    public RootNamespaceImpl getRootScopeImpl() {
        if (this.rootScopeImpl == null) {
            this.rootScopeImpl = new RootNamespaceImpl();
        }
        return this.rootScopeImpl;
    }
    
    public void setModelDirectory(String modelDirectory) {
        this.getRootScopeImpl().setModelDirectory(modelDirectory);
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        this.getRootScopeImpl().setLibraryDirectory(libraryDirectory);
    }
    
    public void setDebugLevel(Level level) {
        this.debugLevel = level;
    }
    
    public void setIsParseOnly(boolean isParseOnly) {
        this.isParseOnly = isParseOnly;
    }
    
    public void setIsPrint(boolean isPrint) {
        this.isPrint = isPrint;
    }

   public void setIsFileName(boolean isFileName) {
        this.isFileName = isFileName;
    }
    
    @Override
    public void setIsVerbose(boolean isVerbose) {
        super.setIsVerbose(isVerbose);
        this.getRootScopeImpl().setIsVerbose(isVerbose);
    }
    
    private Locus locus = null;
    
    public Locus getLocus() {
        if (this.locus == null) {
            this.locus = this.createLocus();
        }
        return this.locus;
    }
    
    protected abstract Locus createLocus();
    protected abstract FumlMappingFactory createFumlFactory();
    protected abstract ElementFactory createElementFactory();
    protected void createSystemServices() { }
    
    protected void addPrimitiveTypes(Collection<PrimitiveType> primitiveTypes) {
        ExecutionFactory executionFactory = this.getLocus().getFactory();
        for (PrimitiveType primitiveType: primitiveTypes) {
            for (PrimitiveType builtInType: executionFactory.getBuiltInTypes()) {
                if (builtInType.getName() != null && builtInType.getName().equals(primitiveType.getName())) {
                    this.println("Duplicate primitive type: " + primitiveType.getName());
                }
            }
            executionFactory.addBuiltInType(primitiveType);
        }
    }
    
    protected void addPrimitiveBehaviorPrototypes(
            Collection<OpaqueBehaviorExecution> primitiveBehaviorPrototypes) {
        ExecutionFactory executionFactory = this.getLocus().getFactory();
        for (OpaqueBehaviorExecution primitiveBehaviorPrototype: primitiveBehaviorPrototypes) {
            executionFactory.addPrimitiveBehaviorPrototype(primitiveBehaviorPrototype);
        }
    }
    
    public UnitDefinition parse(String unitName, boolean isFileName) {
        UnitDefinition unit = null;
        
        if (isFileName) {
            try {
                unit = ((RootNamespaceImpl) RootNamespace.getRootScope().getImpl()).
                        getModelScopeImpl().resolveModelFile(unitName);
            } catch (FileNotFoundException e) {
                this.println("File not found: " + unitName);
            }
        } else {        
            unit = this.resolve(unitName);
        }
        
        return unit instanceof MissingUnit? null: unit;        
    }
    
    public FumlMapping map(NamespaceDefinition definition) {
        FumlMapping mapping = null;
        if (definition.getImpl().isTemplate()) { 
            this.println(definition.getName() + " is a template.");
        } else {
            FumlMapping.setFumlFactory(this.createFumlFactory());
            FumlMapping.setElementFactory(this.createElementFactory());
            mapping = FumlMapping.getMapping(definition);
            try {
                mapping.getModelElements();
                printVerbose("Mapped successfully.");
            } catch (MappingError e) {
                this.println("Mapping failed.");
                this.println(e.getMapping().toString());                  
                this.println(" error: " + e.getMessage());
                mapping = null;
             }
        }
        return mapping;
    }
    
    public static Classifier getClassifier(QualifiedName name) {
        Classifier classifier = null;
        ElementReference referent = 
            name.getImpl().getClassifierReferent();
        FumlMapping mapping = FumlMapping.getMapping(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (mapping instanceof ClassifierDefinitionMapping) {
            try {
                classifier = ((ClassifierDefinitionMapping)mapping).getClassifier();
            } catch (MappingError e) {
                System.out.println("Cannot map " + name.getPathName());
                System.out.println(" error: " + e.getMessage());
            }
        }
        return classifier;
    }
    
    public String parseArgs(String[] args) {
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
                        this.setDebugLevel(Level.toLevel(arg, this.debugLevel));
                    }
                } else {
                    return null;
                }
            }
        }
        
        return i == args.length - 1? args[i]: null;
    }
    
    public UnitDefinition process(UnitDefinition unit) {
        if (unit != null) {
            Collection<ConstraintViolation> violations = this.check(unit);
            if (this.isPrint) {
                unit.print(true);
            } else if (!this.isParseOnly && violations.isEmpty()) {
                if (this.map(RootNamespace.getRootScope()) != null) {
                    return unit;
                }
            }
        }
        return null;
    }
    
    protected void configure() {
        PropertyConfigurator.configure("log4j.properties");
    }
    
    public AlfBase() {
        super();
        this.configure();
    }
    
    public AlfBase(String[] args) {
        this();
        
        String unitName = this.parseArgs(args);        
        if (unitName != null) {
            this.process(this.parse(unitName, this.isFileName));
            
        } else {
            this.println("Usage is");
            this.println("  alf [options] unit");
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
    
}
