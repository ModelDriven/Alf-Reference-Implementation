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

public interface TemplateBinding extends Element {
    
    public TemplateSignature getSignature();
    
    public void setSignature(TemplateSignature signature);
    
    public Collection<TemplateParameterSubstitution> getParameterSubstitution();
    
    public void addParameterSubstitution(TemplateParameterSubstitution parameterSubstitution);
    
}
