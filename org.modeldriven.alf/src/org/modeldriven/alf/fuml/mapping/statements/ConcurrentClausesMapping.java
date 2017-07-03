/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.statements.ConcurrentClauses;
import org.modeldriven.alf.syntax.statements.NonFinalClause;

import org.modeldriven.alf.uml.Clause;
import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConcurrentClausesMapping extends SyntaxElementMapping {
    
    private Collection<Clause> clauses = null;
    private Collection<Element> modelElements = null;
    private List<AssignedSource> assignmentsAfter = null;
    private boolean isIndexFrom0 = false;
    
    
    // NOTE: This should be called before mapping.
    public void setAssignmentsAfter(List<AssignedSource> assignmentsAfter) {
        this.assignmentsAfter = assignmentsAfter;
    }
    
    // NOTE: This should be called before mapping.
    public void setIsIndexFrom0(boolean isIndexFrom0) {
        this.isIndexFrom0 = isIndexFrom0;
    }
    
    public void map() throws MappingError {
        super.map(null);
        
        this.clauses = new ArrayList<Clause>();
        this.modelElements = new ArrayList<Element>();
        ConcurrentClauses concurrentClauses = this.getConcurrentClauses();
        for (NonFinalClause clause: concurrentClauses.getClause()) {
            FumlMapping mapping = this.fumlMap(clause);
            if (!(mapping instanceof NonFinalClauseMapping)) {
                this.throwError("Error mapping clause " + clause, mapping);
            } else {
                NonFinalClauseMapping nonFinalClauseMapping =
                    (NonFinalClauseMapping)mapping;
                nonFinalClauseMapping.setIsIndexFrom0(this.isIndexFrom0);
                nonFinalClauseMapping.setAssignmentsAfter(this.assignmentsAfter);
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
