/*******************************************************************************
 * Copyright 2013 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;

public class MissingMemberMapping extends MemberMapping {
    
    @Override
    public List<Element> getModelElements() {
        return new ArrayList<Element>();
    }

    @Override
    public NamedElement getNamedElement() throws MappingError {
        return (NamedElement)this.getSource();
    }

}
