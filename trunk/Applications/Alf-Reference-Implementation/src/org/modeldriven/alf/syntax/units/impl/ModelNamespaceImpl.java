package org.modeldriven.alf.syntax.units.impl;

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
            UnitDefinition unit = this.resolveUnit(qualifiedName);
            if (unit != null && !(unit instanceof MissingUnit)) {
                Member member = unit.getDefinition();
                members.add(member);
                NamespaceDefinition self = this.getSelf();
                self.addOwnedMember(member);
                self.addMember(member);
            }
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
}
