package org.modeldriven.alf.syntax.units.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.parser.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

public class RootNamespaceImpl extends NamespaceDefinitionImpl {
    
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

        StringBuffer path = new StringBuffer();
         for (NameBinding nameBinding: qualifiedName.getNameBinding()) {
            path.append("/" + nameBinding.getName());
        }
        path.append(".alf");

        AlfParser parser;
        boolean fromModel = true;

        try {
            // System.out.println("Looking for Model" + path + "...");
            parser = new AlfParser(new java.io.FileInputStream("Root/Model" + path));
        } catch (java.io.FileNotFoundException e0) {
            try {
                // System.out.println("Looking for Library" + path + "...");
                parser = new AlfParser(new java.io.FileInputStream("Root/Library" + path));
                fromModel = false;
            } catch (java.io.FileNotFoundException e) {
                System.out.println("Unit not found: " + qualifiedName.getPathName());
                return new MissingUnit(qualifiedName);
            }
        }

        try {
            UnitDefinition subunit = parser.UnitDefinition();
            if (fromModel) {
                System.out.println("Parsed Model" + path);
            }
            subunit.getImpl().addImplicitImports();
            return subunit;           
        } catch (ParseException e) {
            System.out.println("Parse failed: " + 
                    (fromModel? "Model": "Library") + path);
            System.out.println(e.getMessage());
            return new MissingUnit(qualifiedName);
        }
    }

}
