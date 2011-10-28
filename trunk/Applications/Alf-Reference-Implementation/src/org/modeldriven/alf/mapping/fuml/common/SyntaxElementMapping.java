
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
