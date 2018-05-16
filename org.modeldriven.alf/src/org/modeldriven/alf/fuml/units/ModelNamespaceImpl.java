/*******************************************************************************
 * Copyright 2011-2018 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.units;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.parser.ParserImpl;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.expressions.NameBinding;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ExternalNamespace;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ModelNamespaceImpl extends 
    org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl {
    
    public static final String DEFAULT_MODEL_DIRECTORY = "Models";
    
    private String modelDirectory = DEFAULT_MODEL_DIRECTORY;
    protected boolean isVerbose = false;
    
    // Cache ensures that no unit is parsed more than once.
    protected Map<String, UnitDefinition> parsedUnitCache = 
            new HashMap<String, UnitDefinition>();
    
    public ModelNamespaceImpl(ModelNamespace self) {
        super(self);
    }
    
    @Override
    public ModelNamespace getSelf() {
        return (ModelNamespace)this.self;
    }

    public void setModelDirectory(String modelDirectory) {
        this.modelDirectory = modelDirectory;
    }
    
    public String getModelDirectory() {
        return this.modelDirectory;
    }
    
    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }
    
    protected Parser createParser(String path) throws FileNotFoundException {
        return new ParserImpl(path);
    }
    
    @Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
        return false;
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    @Override
    public boolean hasSubunitFor(UnitDefinition unit) {
        return unit.getDefinition() instanceof ExternalNamespace;
    }

    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
        // System.out.println("[resolve] Model scope name=" + name);
        Collection<Member> members = super.resolveInScope(name, classifierOnly);
        if (members.size() == 0) {
            QualifiedName qualifiedName = new QualifiedName().getImpl().addName(name);
            UnitDefinition unit = this.resolveModelUnit(qualifiedName);
            if (unit != null && !(unit instanceof MissingUnit)) {
                Member member = unit.getDefinition();
                members.add(member);
                NamespaceDefinition self = this.getSelf();
                self.addOwnedMember(member);
                self.addMember(member);
                member.setNamespace(self);
            }
        }
        return members;
    }
    
    public UnitDefinition resolveModelUnit(QualifiedName qualifiedName) {
        StringBuilder pathBuilder = new StringBuilder(this.modelDirectory);
        for (NameBinding nameBinding: qualifiedName.getNameBinding()) {
            pathBuilder.append("/" + nameBinding.getName());
        }
        pathBuilder.append(".alf");
        try {
            UnitDefinition unit = this.resolveModelFile(pathBuilder.toString());
            
            // Exclude a unit if its name does not match that of the file name (besides
            // just an improperly named file, this can also happen, if, for example,
            // the file system treats file names as case insensitive).
            if (unit != null && unit.getDefinition() != null && 
                !unit.getDefinition().getName().equals(qualifiedName.getUnqualifiedName().getName())) {
                unit = new MissingUnit(qualifiedName);
            }
            
            return unit;
        } catch(java.io.FileNotFoundException e) {
            return new MissingUnit(qualifiedName);
        }
    }
    
    public UnitDefinition resolveModelFile(String path) 
            throws java.io.FileNotFoundException {
        UnitDefinition unit = this.parsedUnitCache.get(path);
        if (unit != null) {
            return unit instanceof MissingUnit? null: unit;
        } else {
            Parser parser = this.createParser(path);
    
            try {
                unit = parser.UnitDefinitionEOF();
                if (isVerbose) {
                    System.out.println("Parsed " + path);
                }
                unit.getImpl().addImplicitImports();
                this.parsedUnitCache.put(path, unit);
                return unit;           
            } catch (TokenMgrError e) {
                System.out.println("Parse failed: " + path);
                System.out.println(e.getMessage());
                this.parsedUnitCache.put(path, new MissingUnit(path));
                return null;
            } catch (ParseException e) {
                System.out.println("Parse failed: " + path);
                System.out.println(e.getMessage());
                this.parsedUnitCache.put(path, new MissingUnit(path));
                return null;
            }
        }
    }
    
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        UnitDefinition unit = null;
        
        // Look for the unit in the model first.
        unit = this.resolveModelUnit(qualifiedName);
        
        if (unit instanceof MissingUnit) {
            // If not found in the model, look for the unit in the library.
            unit = ((RootNamespaceImpl)this.getSelf().getNamespace().getImpl()).
                    resolveModelUnit(qualifiedName);
        }
        
        // Return a MissingUnit rather than null if parsing failed.
        return unit == null? new MissingUnit(qualifiedName): unit;
    }
}
