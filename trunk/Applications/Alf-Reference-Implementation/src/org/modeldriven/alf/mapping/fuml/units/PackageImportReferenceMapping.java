/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.ImportReferenceMapping;

import org.modeldriven.alf.syntax.units.PackageImportReference;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.PackageImport;

import java.util.ArrayList;
import java.util.List;

public class PackageImportReferenceMapping extends ImportReferenceMapping {

    private PackageImport packageImport = null;

    /**
     * A package import reference maps to a package import from the namespace to
     * the named package. The package import visibility is as given by the
     * import visibility indicator.
     */

    public void mapTo(PackageImport packageImport) throws MappingError {
        super.mapTo(packageImport);

        PackageImportReference importReference = this.getPackageImportReference();
        FumlMapping mapping = this.fumlMap(importReference.getReferent());
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (!(mapping instanceof PackageDefinitionMapping)) {
            this.throwError("Error mapping referent: " + 
                    mapping.getErrorMessage());
        } else {
            packageImport.setImportedPackage(
                    ((PackageDefinitionMapping)mapping).getPackage());
        }
    }

    @Override
    public Element getElement() {
        return this.packageImport;
    }

    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> modelElements = new ArrayList<Element>();
        modelElements.add(this.getPackageImport());
        return modelElements;
    }

    public PackageImport getPackageImport() throws MappingError {
        if (this.packageImport == null) {
            this.packageImport = new PackageImport();
            this.mapTo(packageImport);
        }
        return this.packageImport;
    }

    public PackageImportReference getPackageImportReference() {
        return (PackageImportReference) this.getSource();
    }

    @Override
    public void print(String prefix) {
        super.print(prefix);

        PackageImportReference importReference = this.getPackageImportReference();
        Mapping mapping = this.fumlMap(importReference.getReferent());
        System.out.println(prefix + " package:");
        mapping.printChild(prefix);

        System.out.println(prefix + " packageImport:" + this.packageImport);
    }

} // PackageImportReferenceMapping
