/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ImportReferenceMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.impl.NameBindingImpl;
import org.modeldriven.alf.syntax.units.ElementImportReference;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.ElementImport;
import org.modeldriven.alf.uml.PackageableElement;

import java.util.ArrayList;
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
        ElementReference referent = importReference.getReferent();
        Element element = referent.getImpl().getUml();
        if (element == null) {
            FumlMapping mapping = this.fumlMap(importReference.getReferent());
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            mapping.getModelElements(); // To force mapping to actually take place.
            element = mapping.getElement();
            if (!(element instanceof PackageableElement)) {
                this.throwError("Invalid imported element mapping: " + mapping);
            }
        }
        
        elementImport.setImportedElement((PackageableElement) element);
        elementImport.setAlias(NameBindingImpl.processName(importReference.getAlias()));
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
            this.elementImport = this.create(ElementImport.class);
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
