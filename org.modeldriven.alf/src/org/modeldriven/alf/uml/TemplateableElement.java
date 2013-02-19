/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TemplateableElement extends Element {
    
    public TemplateSignature getOwnedTemplateSignature();
    
    public void setOwnedTemplateSignature(TemplateSignature signature);
    
    public boolean isTemplate();
    
    public List<TemplateBinding> getTemplateBinding();
    
    public void addTemplateBinding(TemplateBinding templateBinding);
    
    // Return a copy of this templateable element. Update the first collection
    // with any stereotype applications to be made on elements within the
    // copy and the second collection with an references to elements not
    // contained in this templateable element.
    public TemplateableElement instantiate(
            Collection<StereotypeApplication> stereotypeApplications,
            Set<Element> externalReferences);
    
    // Create a realization from this templateable element to a new
    // templateable element with a template binding to the given template. The
    // first element in the list is the bound element and the second element
    // is a realization from the this templatable element to the bound element.
    public List<NamedElement> bindTo(TemplateableElement template);

}
