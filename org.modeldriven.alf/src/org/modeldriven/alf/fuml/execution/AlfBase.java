/*******************************************************************************
 * Copyright 2015-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.uml.StereotypeApplication;
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
import org.modeldriven.alf.syntax.units.impl.BoundClassifierImpl;
import org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl;
import org.modeldriven.alf.uml.Classifier;

public abstract class AlfBase extends org.modeldriven.alf.execution.AlfBase {
    
    protected RootNamespaceImpl rootScopeImpl = null;
    protected boolean isParseOnly = false;
    protected boolean isPrint = false;
    protected boolean isFileName = false;
    
    public RootNamespaceImpl getRootScopeImpl() {
        return this.rootScopeImpl;
    }
    
    public void setModelDirectory(String modelDirectory) {
        this.getRootScopeImpl().setModelDirectory(modelDirectory);
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        this.getRootScopeImpl().setLibraryDirectory(libraryDirectory);
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
    
    protected FumlMappingFactory createFumlFactory() {
        return new org.modeldriven.alf.fuml.mapping.FumlMappingFactory();
    }
    
   protected abstract ElementFactory createElementFactory();

    public UnitDefinition parse(String unitName, boolean isFileName) {
        BoundClassifierImpl.clearBoundClassifiers();
        
        UnitDefinition unit = null;
        
        if (isFileName) {
            try {
                unit = this.getRootScopeImpl().getModelNamespaceImpl().
                        resolveModelFile(unitName);
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
            StereotypeApplication.clearStereotypeApplications();
            BoundClassifierImpl.makeBoundElements();
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
            if (this.matchesNoArg(option)) {
               this.parseOption(option); 
            } else if (this.matchesWithArg(option) && i < args.length) {
                arg = args[i];
                if (arg.charAt(0) == '-') {
                    return null;
                }
                i++;
                this.parseOptionWithArg(option, arg);
            } else {
                return null;
            }
        }
        
        return i == args.length - 1? args[i]: null;
    }
    
    protected boolean matchesNoArg(String option) {
        return option.matches("[vfpP]");
    }
    
    protected boolean matchesWithArg(String option) {
        return option.matches("[ml]");
    }
    
    protected void parseOption(String option) {
        if (option.equals("v")) {
            this.setIsVerbose(true);
        } else if (option.equals("f")) {
            this.setIsFileName(true);
        } else if (option.equals("p")) {
            this.setIsParseOnly(true);
        } else if (option.equals("P")) {
            this.setIsPrint(true);
        }
    }
    
    protected void parseOptionWithArg(String option, String arg) {
        if (option.equals("m")) {
            this.setModelDirectory(arg);
        } else if (option.equals("l")) {
            this.setLibraryDirectory(arg);
        }
    }
    
    public UnitDefinition process(UnitDefinition unit) {
        if (unit != null) {
            Collection<ConstraintViolation> violations = this.check(unit);
            if (this.isPrint) {
                unit.print(true);
            } else if (!this.isParseOnly && violations.isEmpty()) {
                NamespaceDefinition definition = unit.getDefinition();
                if (definition.getImpl().isTemplate()) {
                    this.println(definition.getName() + " is a template.");
                } else if (this.map(RootNamespace.getRootScope()) != null) {
                    return unit;
                }
            }
        }
        return null;
    }
    
    protected RootNamespaceImpl createRootScopeImpl() {
        return new RootNamespaceImpl();
    }
    
    protected RootNamespaceImpl getCurrentRootScopeImpl() {
        ModelNamespaceImpl rootScopeImpl = RootNamespace.getRootScope().getImpl();
        return rootScopeImpl instanceof RootNamespaceImpl?
                (RootNamespaceImpl)rootScopeImpl: null;
    }
    
    protected void configure() {
        RootNamespaceImpl rootScopeImpl = this.getCurrentRootScopeImpl();
        if (rootScopeImpl == null) {
            this.rootScopeImpl = this.createRootScopeImpl();
        } else {
            rootScopeImpl.resetModelNamespace();
            this.rootScopeImpl = rootScopeImpl;
        }
        FumlMapping.setFumlFactory(this.createFumlFactory());
        FumlMapping.setElementFactory(this.createElementFactory());
    }
    
    protected String getErrorMessageFilePath() {
        return this.getRootScopeImpl().getLibraryDirectory() + "/" + 
                ConstraintViolation.DEFAULT_ERROR_MESSAGE_FILE_PATH;
    }
    
    protected void loadResources() {
        String errorMessageFilePath = this.getErrorMessageFilePath();
        try {
            ConstraintViolation.loadErrorMessageFile(errorMessageFilePath);
            this.printVerbose("Loaded " + errorMessageFilePath);
        } catch (IOException e) {
            this.printVerbose("Error reading error message file: " + errorMessageFilePath);
        }
    }
    
    public void run(String[] args) {
        String unitName = this.parseArgs(args);
        if (unitName != null || args.length == 1 && this.isVerbose) {
            this.printVerbose("Alf Reference Implementation v" + ALF_VERSION);
            if (unitName != null) {
                this.loadResources();
                this.process(this.parse(unitName, this.isFileName));
            }
        } else {
            this.printUsage();
        }         
    }
    
    protected abstract void printUsage();
    
    public AlfBase() {
        super();
        this.configure();
    }
    
}
