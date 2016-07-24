/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.modeldriven.alf.syntax.common.*;

/**
 * An assignment of a source element that gives the value of a local name or
 * input parameter, along with a record of the defined type (if any) and
 * multiplicity of the name.
 **/

public class AssignedSourceImpl {

    private String name = "";
    private ElementReference source = null;
    private Integer upper = 0;
    private Integer lower = 0;
    private ElementReference type = null;
    private ElementReference subtype = null;
    private ElementReference knownType = null; // DERIVED
    
    private boolean isOutParameter = true;
    
    // Indicates whether this assignment is for a local name listed in an
    // @parallel annotation of a for statement.
    private boolean isParallelLocalName = false;

    protected AssignedSource self;

	public AssignedSourceImpl(AssignedSource self) {
		this.self = self;
	}

	public AssignedSource getSelf() {
		return (AssignedSource) this.self;
	}
	
	@Override
	public String toString() {
	    return this.toString(false);
	}
	
	public String toString(boolean includeDerived) {
	    ElementReference type = this.getType();
	    ElementReference subtype = this.getSubtype();
	    ElementReference knownType = this.getKnownType();
	    ElementReference source = this.getSource();
	    return this.getSelf()._toString(includeDerived) + 
                    (type == null? "": " type:(" + type + ")") +
                    (subtype == null? "": " subtype:(" + subtype + ")") +
                    (knownType == null || !includeDerived? "": " /knownType:(" + knownType + ")") +
	                (source == null? "": " source:(" + source + ")") +
	                " isParallelLocalName:" + this.isParallelLocalName;
	}

    public void deriveAll() {
        this.getSelf()._deriveAll();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementReference getSource() {
        return this.source;
    }

    public void setSource(ElementReference source) {
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
    
    public ElementReference getSubtype() {
        return this.subtype;
    }

    public void setSubtype(ElementReference subtype) {
        this.subtype = subtype;
    }
    
    public boolean getIsParallelLocalName() {
        return this.isParallelLocalName;
    }
    
    public void setIsParallelLocalName(boolean isParallelLocalName) {
        this.isParallelLocalName = isParallelLocalName;
    }
    
    public ElementReference getKnownType() {
        if (this.knownType == null) {
            this.setKnownType(this.deriveKnownType());
        }
        return this.knownType;
    }

    public void setKnownType(ElementReference knownType) {
        this.knownType = knownType;
    }
    
    /**
     * If an assigned source has a subtype set, then this is the known type
     * for the assigned source. Otherwise the type of the assigned source is
     * also the known type.
     */
    protected ElementReference deriveKnownType() {
        AssignedSource self = this.getSelf();
        ElementReference subtype = self.getSubtype();
        return subtype == null? self.getType(): subtype;
    }
    
    /*
     * Derivations
     */
    
    public boolean assignedSourceKnownTypeDerivation() {
        this.getSelf().getKnownType();
        return true;
    }
    
    /*
     * Helper Methods
     */
    
    public boolean isOutParameter() {
        return this.isOutParameter;
    }
    
    public void setIsOutParameter(boolean isOutParameter) {
        this.isOutParameter = isOutParameter;
    }
    
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

   public static AssignedSource makeAssignment
        (String name, ElementReference source, 
         ElementReference type, int lower, int upper) {
        AssignedSource assignment = new AssignedSource();
        assignment.setName(name);
        assignment.setSource(source);
        assignment.setType(type);
        assignment.setLower(lower);
        assignment.setUpper(upper);
        return assignment;
    }

   public static AssignedSource makeAssignment
       (String name, SyntaxElement source, 
           ElementReference type, ElementReference subtype,
           int lower, int upper,
           boolean isOutParameter) {
       AssignedSource assignment = new AssignedSource();
       assignment.setName(name);
       assignment.setSource(source);
       assignment.setType(type);
       assignment.setSubtype(subtype);
       assignment.setLower(lower);
       assignment.setUpper(upper);
       assignment.getImpl().setIsOutParameter(isOutParameter);
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
                    !assignment.getSource().getImpl().equals(assignmentBefore.getSource())) {
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

} // AssignedSourceImpl
