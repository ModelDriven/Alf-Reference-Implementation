
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.common;

import org.modeldriven.alf.fuml.mapping.ErrorMapping;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.SyntaxElement;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class AssignedSourceMapping extends FumlMapping {
    
    ActivityNode activityNode = null;
    
    public ActivityNode getActivityNode() throws MappingError {
        if (this.activityNode == null) {
            AssignedSource assignment = this.getAssignedSource();
            SyntaxElement source = assignment.getSource();
            FumlMapping mapping = this.fumlMap(source);
            if (mapping instanceof SyntaxElementMapping) {
                this.activityNode = ((SyntaxElementMapping)mapping).
                        getAssignedValueSource(assignment.getName());
            } else if (mapping instanceof ErrorMapping) {
                this.throwError("Error mapping source: " + mapping.getErrorMessage());
            }
        }
        return this.activityNode;
    }

    @Override
	public List<Element> getModelElements() throws MappingError {
		ArrayList<Element> modelElements = new ArrayList<Element>();
		ActivityNode activityNode = this.getActivityNode();
		if (activityNode != null) {
		    modelElements.add(activityNode);
		}
		return modelElements;
	}

	public AssignedSource getAssignedSource() {
		return (AssignedSource) this.getSource();
	}

} // AssignedSourceMapping
