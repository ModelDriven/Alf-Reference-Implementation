
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.statements.ConcurrentClauses;
import org.modeldriven.alf.syntax.statements.NonFinalClause;

import fUML.Syntax.Activities.CompleteStructuredActivities.Clause;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.Collection;

public class ConcurrentClausesMapping extends SyntaxElementMapping {
    
    Collection<Clause> clauses = null;
    Collection<Element> modelElements = null;
    Collection<String> assignedNames = null;
    
    // NOTE: This should be called before mapping.
    public void setAssignedNames(Collection<String> assignedNames) {
        this.assignedNames = assignedNames;
    }
    
    public void map() throws MappingError {
        super.map(null);
        
        this.clauses = new ArrayList<Clause>();
        this.modelElements = new ArrayList<Element>();
        ConcurrentClauses concurrentClauses = this.getConcurrentClauses();
        for (NonFinalClause clause: concurrentClauses.getClause()) {
            FumlMapping mapping = this.fumlMap(clause);
            if (!(mapping instanceof NonFinalClauseMapping)) {
                this.throwError("Error mapping clause " + clause + ": " + 
                        mapping.getErrorMessage());
            } else {
                NonFinalClauseMapping nonFinalClauseMapping =
                    (NonFinalClauseMapping)mapping;
                nonFinalClauseMapping.setAssignedNames(this.assignedNames);
                this.clauses.add(nonFinalClauseMapping.getClause());
                this.modelElements.addAll(mapping.getModelElements());
            }
        }
    }
    
    public Collection<Clause> getClauses() throws MappingError {
        if (this.clauses == null) {
            map();
        }
        return this.clauses;
    }

	public Collection<Element> getModelElements() throws MappingError {
	    this.getClauses();
		return this.modelElements;
	}

	public ConcurrentClauses getConcurrentClauses() {
		return (ConcurrentClauses) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    ConcurrentClauses clauses = this.getConcurrentClauses();
	    Collection<NonFinalClause> nonFinalClauses = clauses.getClause();
	    if (!nonFinalClauses.isEmpty()) {
	        System.out.println(prefix + " clause:");
	        for (NonFinalClause clause: nonFinalClauses) {
	            Mapping mapping = clause.getImpl().getMapping();
	            if (mapping != null) {
	                mapping.printChild(prefix);
	            }
	        }
	    }
	}

} // ConcurrentClausesMapping
