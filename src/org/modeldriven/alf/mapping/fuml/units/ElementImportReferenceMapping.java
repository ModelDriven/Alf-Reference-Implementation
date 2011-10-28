
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

import org.modeldriven.alf.syntax.units.ElementImportReference;

import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.ElementImport;
import fUML.Syntax.Classes.Kernel.PackageableElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ElementImportReferenceMapping extends ImportReferenceMapping {

    private ElementImport elementImport = null;
    
    public void mapTo(ElementImport elementImport) throws MappingError {
        super.mapTo(elementImport);
        
        ElementImportReference importReference = this.getElementImportReference();
        FumlMapping mapping = 
            ((ElementReferenceMapping)this.fumlMap(importReference.getReferent())).
                getMapping();
        Collection<Element> modelElements = mapping.getModelElements();
        if (modelElements.size() != 1 || 
                !(mapping.getElement() instanceof PackageableElement)) {
            this.throwError("Invalid imported element mapping: " + mapping);
        } else {
            elementImport.setImportedElement((PackageableElement)mapping.getElement());
        }
    }
    
    @Override
    public Element getElement() {
        return this.elementImport;
    }

    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> modelElements = new ArrayList<Element>();
        modelElements.add(this.getElementImport());
        return modelElements;
    }
    
    public ElementImport getElementImport() throws MappingError {
        if (this.elementImport == null) {
            this.elementImport = new ElementImport();
            this.mapTo(elementImport);
        }
        return this.elementImport;
    }

    public ElementImportReference getElementImportReference() {
        return (ElementImportReference) this.getSource();
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        ElementImportReference importReference = this.getElementImportReference();
        Mapping mapping = this.fumlMap(importReference.getReferent());
        System.out.println(prefix + " element:");
        mapping.printChild(prefix);
        
        System.out.println(prefix + " elementImport:" + this.elementImport);
    }

} // ElementImportReferenceMapping
