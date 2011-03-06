
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

    private String name = "";
    private SyntaxElement source = null;
    private Integer upper = 0;
    private Integer lower = 0;
    private ElementReference type = null;

    protected AssignedSource self;

	public AssignedSourceImpl(AssignedSource self) {
		this.self = self;
	}

	public AssignedSource getSelf() {
		return (AssignedSource) this.self;
	}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SyntaxElement getSource() {
        return this.source;
    }

    public void setSource(SyntaxElement source) {
        this.source = source;
    }

    public Integer getUpper() {
        return this.upper;
    }

    public void setUpper(Integer upper) {
        this.upper = upper;
    }

    public Integer getLower() {
        return this.lower;
    }

    public void setLower(Integer lower) {
        this.lower = lower;
    }

    public ElementReference getType() {
        return this.type;
    }

    public void setType(ElementReference type) {
        this.type = type;
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
