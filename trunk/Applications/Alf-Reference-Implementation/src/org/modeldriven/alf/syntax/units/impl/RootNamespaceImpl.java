/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.parser.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

public class RootNamespaceImpl extends NamespaceDefinitionImpl {
    
    private String modelDirectory = "Models";
    private String libraryDirectory = "Libraries";
    
    private boolean isVerbose = true;
    
    public RootNamespaceImpl(RootNamespace self) {
        super(self);
    }

    @Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
        return false;
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }
    
    public void setModelDirectory(String modelDirectory) {
        this.modelDirectory = modelDirectory;
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        this.libraryDirectory = libraryDirectory;
    }
    
    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }
    
    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
        Collection<Member> members = super.resolve(name, classifierOnly);
        if (members.size() == 0) {
            QualifiedName qualifiedName = new QualifiedName().getImpl().addName(name);
            UnitDefinition unit = this.resolveUnit(qualifiedName);
            Member member;
            if (unit == null) {
                member = new MissingMember(name);
            } else {
                member = unit.getDefinition();
                if (member == null) {
                    member = new MissingMember(name);
                } else {
                    members.add(member);
                }
            }
            NamespaceDefinition self = this.getSelf();
            self.addOwnedMember(member);
            self.addMember(member);
        } else if (members.toArray()[0] instanceof MissingMember) {
            members = new ArrayList<Member>();
        }
        return members;
    }

    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        // System.out.println("Resolving unit " + qualifiedName.getPathName());

        StringBuilder path = new StringBuilder();
        for (NameBinding nameBinding: qualifiedName.getNameBinding()) {
            path.append("/" + nameBinding.getName());
        }
        path.append(".alf");

        AlfParser parser;
        boolean fromModel = true;

        try {
            // System.out.println("Looking for " + this.modelDirectory + path + "...");
            parser = new AlfParser(this.modelDirectory + path);
        } catch (java.io.FileNotFoundException e0) {
            try {
                // System.out.println("Looking for " + this.libraryDirectory + path + "...");
                parser = new AlfParser(this.libraryDirectory + path);
                fromModel = false;
            } catch (java.io.FileNotFoundException e) {
                System.out.println("Unit not found: " + qualifiedName.getPathName());
                return new MissingUnit(qualifiedName);
            }
        }

        try {
            UnitDefinition subunit = parser.UnitDefinition();
            if (fromModel && isVerbose) {
                System.out.println("Parsed " + this.modelDirectory + path);
            }
            subunit.getImpl().addImplicitImports();
            return subunit;           
        } catch (ParseException e) {
            System.out.println("Parse failed: " + 
                    (fromModel? this.modelDirectory: this.libraryDirectory) + path);
            System.out.println(e.getMessage());
            return new MissingUnit(qualifiedName);
        }
    }

}
