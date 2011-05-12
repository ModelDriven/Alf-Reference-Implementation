
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	
	@Override
	public String toString() {
	    return this.getSelf()._toString() + 
	                " type:(" + this.getType() + ")" +
	                " source:(" + this.getSource() + ")";
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
    
    /*
     * Helper Methods
     */
    
    public static AssignedSource makeAssignment
        (String name, SyntaxElement source, 
         ElementReference type, int lower, int upper) {
        AssignedSource assignment = new AssignedSource();
        assignment.setName(name);
        assignment.setSource(source);
        assignment.setType(type);
        assignment.setLower(lower);
        assignment.setUpper(upper);
        return assignment;
    }

    public static AssignedSource makeAssignment(AssignedSource assignment) {
        return makeAssignment(
                assignment.getName(), 
                assignment.getSource(), 
                assignment.getType(), 
                assignment.getLower(), 
                assignment.getUpper());
    }

    public static Collection<AssignedSource> selectNewAssignments(
            Map<String, AssignedSource> assignmentsBefore,
            Collection<AssignedSource> assignmentsAfter) {
        Set<AssignedSource> newAssignments = new HashSet<AssignedSource>();
        for (AssignedSource assignment: assignmentsAfter) {
            AssignedSource assignmentBefore = assignmentsBefore.get(assignment.getName());
            if (assignmentBefore == null ||
                    assignment.getSource() != assignmentBefore.getSource()) {
                newAssignments.add(assignment);
            }
        }
        return newAssignments;
    }
    
    public boolean isAssignedIn(Collection<AssignedSource> assignments) {
        for (AssignedSource assignment: assignments) {
            if (this.equals(assignment)) {
                return true;
            }
        }
        return false;
    }
    
    // Note: Equality is based solely on the AssignedSource name.
    @Override
    public boolean equals(Object other) {
        String name = this.getSelf().getName();
        return name != null && 
               (other instanceof AssignedSource &&
                    name.equals(((AssignedSource)other).getName()) ||
                other instanceof AssignedSourceImpl &&
                    name.equals(((AssignedSourceImpl)other).getSelf().getName()) ||
                other instanceof String && name.equals(other));
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

} // AssignedSourceImpl
