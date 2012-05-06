/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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

    /**
     * An element import reference maps to an element import from the namespace
     * to the named imported element. The element import visibility is as given
     * by the import visibility indicator. If there is an alias part, then the
     * given unqualified name becomes the element import alias.
     */

    public void mapTo(ElementImport elementImport) throws MappingError {
        super.mapTo(elementImport);

        ElementImportReference importReference = this.getElementImportReference();
        FumlMapping mapping = this.fumlMap(importReference.getReferent());
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        Collection<Element> modelElements = mapping.getModelElements();
        if (modelElements.size() != 1 || 
                !(mapping.getElement() instanceof PackageableElement)) {
            this.throwError("Invalid imported element mapping: " + mapping);
        } else {
            elementImport.setImportedElement(
                    (PackageableElement) mapping.getElement());
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
