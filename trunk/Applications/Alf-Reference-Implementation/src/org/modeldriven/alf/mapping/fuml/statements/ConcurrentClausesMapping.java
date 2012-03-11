
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.statements.ConcurrentClauses;
import org.modeldriven.alf.syntax.statements.NonFinalClause;

import fUML.Syntax.Activities.CompleteStructuredActivities.Clause;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.Collection;

public class ConcurrentClausesMapping extends SyntaxElementMapping {
    
    Collection<Clause> clauses = null;
    Collection<Element> modelElements = null;
    Collection<AssignedSource> assignments = null;
    
    public void setAssignments(Collection<AssignedSource> assignments) {
        this.assignments = assignments;
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
                nonFinalClauseMapping.setAssignments(this.assignments);
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

} // ConcurrentClausesMapping
