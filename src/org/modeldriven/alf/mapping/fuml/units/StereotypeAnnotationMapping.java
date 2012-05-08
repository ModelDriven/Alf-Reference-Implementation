/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.units.StereotypeAnnotation;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.List;

public class StereotypeAnnotationMapping extends SyntaxElementMapping {

    /**
     * A stereotype annotation, other than for the special cases, maps formally
     * to the application of the identified stereotype to the element mapped
     * from the annotated member. However, an implementation may also use such
     * stereotypes to specify special implementation-specific semantics for the
     * annotated element, except for the standard stereotypes «Create» and
     * «Destroy», which are used in the standard Alf mapping for constructors
     * and destructors and «ModelLibrary», which is used to suppress the
     * inclusion of implicit imports.
     */
    
    // Stereotype annotations, other than the special cases and standard <<Create>>
    // and <<Destroy>> stereotypes, are not implemented in the fUML mapping.
    public StereotypeAnnotationMapping() {
        this.setErrorMessage("No mapping for StereotypeAnnotation.");
    }

    @Override
    public Element getElement() {
        return null;
    }

    public List<Element> getModelElements() throws MappingError {
        throw new MappingError(this, this.getErrorMessage());
    }

    public StereotypeAnnotation getStereotypeAnnotation() {
        return (StereotypeAnnotation) this.getSource();
    }

} // StereotypeAnnotationMapping
