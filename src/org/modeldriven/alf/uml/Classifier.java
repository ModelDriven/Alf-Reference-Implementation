/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

public interface Classifier extends 
    Type, Namespace, RedefinableElement, 
    TemplateableElement, ParameterableElement {
    
    public boolean getIsAbstract();
    public List<Feature> getFeature();
    public List<Property> getAttribute();
    
    public List<NamedElement> inheritableMembers();
    public Set<Classifier> parents();
    public Set<Classifier> allParents();
    public boolean conformsTo(Classifier other);

}
