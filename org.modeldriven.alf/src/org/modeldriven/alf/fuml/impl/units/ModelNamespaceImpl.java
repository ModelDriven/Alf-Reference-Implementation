/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.units;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.expressions.NameBinding;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ModelNamespaceImpl extends 
    org.modeldriven.alf.syntax.units.impl.ModelNamespaceImpl {
    
    private String modelDirectory = "Models";
    private boolean isVerbose = false;
    
    // Cache ensures that no unit is parsed more than once.
    private Map<String, UnitDefinition> parsedUnitCache = 
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
    
    @Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
        return false;
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
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
            return this.resolveModelFile(pathBuilder.toString());
        } catch(java.io.FileNotFoundException e) {
            return new MissingUnit(qualifiedName);
        }
    }
    
    public UnitDefinition resolveModelFile(String path) 
            throws java.io.FileNotFoundException {
        UnitDefinition unit = this.parsedUnitCache.get(path);
        if (unit != null) {
            return unit;
        } else {
            Parser parser = new Parser(path);
    
            try {
                unit = parser.UnitDefinition();
                if (isVerbose) {
                    System.out.println("Parsed " + path);
                }
                unit.getImpl().addImplicitImports();
                this.parsedUnitCache.put(path, unit);
                return unit;           
            } catch (TokenMgrError e) {
                System.out.println("Parse failed: " + path);
                System.out.println(e.getMessage());
                return null;
            } catch (ParseException e) {
                System.out.println("Parse failed: " + path);
                System.out.println(e.getMessage());
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
