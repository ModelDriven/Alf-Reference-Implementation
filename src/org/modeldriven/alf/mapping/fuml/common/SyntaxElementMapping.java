
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.common;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;

import org.modeldriven.alf.syntax.common.SyntaxElement;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public abstract class SyntaxElementMapping extends FumlMapping {
    
    /**
     * Returns the activity node to be used as the source of the value for a
     * given local name that has this syntax element as its assigned source.
     */
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        return null;
    }

	public SyntaxElement getSyntaxElement() {
		return (SyntaxElement) this.getSource();
	}

} // SyntaxElementMapping
