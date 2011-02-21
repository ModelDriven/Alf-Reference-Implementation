package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.parser.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.expressions.impl.QualifiedNameImpl;
import org.modeldriven.alf.syntax.units.*;

public class ModelNamespaceImpl extends NamespaceDefinitionImpl {
    
    public ModelNamespaceImpl(ModelNamespace self) {
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
    public List<Member> resolve(String name) {
        List<Member> members = super.resolve(name);
        if (members.size() == 0) {
            NameBinding nameBinding = new NameBinding();
            nameBinding.setName(name);
            QualifiedName qualifiedName = new QualifiedName();
            qualifiedName.addNameBinding(nameBinding);
            UnitDefinition unit = this.resolveUnit(qualifiedName);
            if (unit != null) {
                NamespaceDefinition member = unit.getDefinition();
                this.getSelf().addOwnedMember(member);
                members.add(member);
            }
        }
        return members;
    }

    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        System.out.println("Resolving unit " + qualifiedName.getPathName());

        StringBuffer path = new StringBuffer();
         for (NameBinding nameBinding: qualifiedName.getNameBinding()) {
            path.append("/" + QualifiedNameImpl.processNameBinding(nameBinding));
        }
        path.append(".alf");

        AlfParser parser;

        try {
            System.out.println("Looking for Model" + path + "...");
            parser = new AlfParser(new java.io.FileInputStream("Root/Model" + path));
        } catch (java.io.FileNotFoundException e0) {
            try {
                System.out.println("Looking for Library" + path + "...");
                parser = new AlfParser(new java.io.FileInputStream("Root/Library" + path));
            } catch (java.io.FileNotFoundException e) {
                System.out.println("Unit not found.");
                return null;
            }
        }

        try {
            UnitDefinition subunit = parser.UnitDefinition();
            System.out.println("Parsed successfully.");
            subunit.getImpl().addImplicitImports();
            return subunit;           
        } catch (ParseException e) {
            System.out.println("Parse failed.");
            System.out.println(e.getMessage());
            return null;
        }
    }

}
