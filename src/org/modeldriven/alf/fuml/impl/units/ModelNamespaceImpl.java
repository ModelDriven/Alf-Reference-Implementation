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

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.ParseException;
import org.modeldriven.alf.parser.TokenMgrError;
import org.modeldriven.alf.syntax.expressions.NameBinding;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.PackageDefinition;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.alf.syntax.units.impl.PackageDefinitionImpl;

public class ModelNamespaceImpl extends PackageDefinitionImpl {
    
    private String modelDirectory = "Models";
    private boolean isVerbose = false;
    
    public ModelNamespaceImpl(PackageDefinition self) {
        super(self);
    }
    
    @Override
    public ModelNamespace getSelf() {
        return (ModelNamespace)this.self;
    }

    public void setModelDirectory(String modelDirectory) {
        this.modelDirectory = modelDirectory;
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
        // System.out.println("Resolving unit " + qualifiedName.getPathName());

        StringBuilder path = new StringBuilder();
        for (NameBinding nameBinding: qualifiedName.getNameBinding()) {
            path.append("/" + nameBinding.getName());
        }
        path.append(".alf");

        AlfParser parser;

        try {
            // System.out.println("Looking for " + this.modelDirectory + path + "...");
            parser = new AlfParser(this.modelDirectory + path);
        } catch (java.io.FileNotFoundException e) {
            return new MissingUnit(qualifiedName);
        }

        try {
            UnitDefinition subunit = parser.UnitDefinition();
            if (isVerbose) {
                System.out.println("Parsed " + this.modelDirectory + path);
            }
            subunit.getImpl().addImplicitImports();
            return subunit;           
        } catch (TokenMgrError e) {
            System.out.println("Parse failed: " + this.modelDirectory + path);
            System.out.println(e.getMessage());
            return null;
        } catch (ParseException e) {
            System.out.println("Parse failed: " + this.modelDirectory + path);
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        // ModelNamespace modelScope = RootNamespace.getModelScope();
        
        // Look for the unit in the model first.
        UnitDefinition unit = this.resolveModelUnit(qualifiedName);
        
        // If not found in the model, look for the unit in the library.
        if (unit instanceof MissingUnit) {
            unit = ((RootNamespace)this.getSelf().getNamespace()).getImpl().resolveModelUnit(qualifiedName);
            // unit = RootNamespace.getRootScope().getImpl().resolveModelUnit(qualifiedName);
            if (unit instanceof MissingUnit) {
                System.out.println("Unit not found: " + qualifiedName.getPathName());
            } 
        }
        
        // Return a MissingUnit rather than null if parsing failed.
        return unit == null? new MissingUnit(qualifiedName): unit;
    }
}
