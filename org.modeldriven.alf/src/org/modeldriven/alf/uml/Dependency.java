/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms,
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.uml;

public interface Dependency extends PackageableElement {
    
    public NamedElement getClient();
    
    public void setClient(NamedElement client);
    
    public NamedElement getSupplier();
    
    public void setSupplier(NamedElement supplier);

}
