
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.*;

/**
 * An assignment of a source element that gives the value of a local name, along
 * with a record of the defined type (if any) and multiplicity of the local
 * name.
 **/

public class AssignedSourceImpl {

	protected AssignedSource self;

	public AssignedSourceImpl(AssignedSource self) {
		this.self = self;
	}

	public AssignedSource getSelf() {
		return (AssignedSource) this.self;
	}

    public static AssignedSource getAssignment(String name, Collection<AssignedSource> assignments) {
        for (AssignedSource assignment: assignments) {
            if (assignment.getName().equals(name)) {
                return assignment;
            }
        }
        return null;
    }

} // AssignedSourceImpl
