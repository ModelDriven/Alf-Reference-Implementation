
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.SwitchClause;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.*;

import java.util.ArrayList;
import java.util.Collection;

public class SwitchClauseMapping extends SyntaxElementMapping {

    private Clause clause = null;
    private Collection<Element> modelElements = null;
    private ActivityNode switchSource = null;
    private Collection<String> assignedNames = null;
    
    /**
     * A switch clause maps to a concurrent clause of the conditional node.
     * Each clause tests whether the result of the switch expression equals the
     * result of one of the case expressions.
     */

    // NOTE: This should be called before mapping.
    public void setSwitchSource(ActivityNode switchSource) {
        this.switchSource = switchSource;
    }
    
    // NOTE: This should be called before mapping.
    public void setAssignedNames(Collection<String> assignedNames) {
        this.assignedNames = assignedNames;
    }
    
    public void mapClause() throws MappingError {
        SwitchClause switchClause = this.getSwitchClause();
        Block block = switchClause.getBlock();
        
        ActivityGraph testGraph = this.createActivityGraph();
        ActivityNode testSource = null;
        for (Expression switchCase: switchClause.getCase()) {
            FumlMapping mapping = this.fumlMap(switchCase);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping condition: " + 
                        mapping.getErrorMessage());
    
            } else {
                ExpressionMapping caseMapping = (ExpressionMapping)mapping;
                ActivityNode resultSource = caseMapping.getResultSource();
                testGraph.addAll(caseMapping.getGraph());
                TestIdentityAction testAction = testGraph.addTestIdentityAction(
                        "Case(" + resultSource.getName() + ")");
                testGraph.addObjectFlow(this.switchSource, testAction.getFirst());
                testGraph.addObjectFlow(resultSource, testAction.getSecond());
                if (testSource == null) {
                    testSource = testAction.getResult();
                } else {
                    CallBehaviorAction callAction = 
                        testGraph.addCallBehaviorAction(
                                getBehavior(RootNamespace.getBooleanFunctionOr()));
                    testGraph.addObjectFlow(
                            testSource, callAction.getArgument().get(0));
                    testGraph.addObjectFlow(
                            testAction.getResult(), callAction.getArgument().get(1));
                    testSource = callAction.getResult().get(0);
                }
            }
        }
        
        this.modelElements = new ArrayList<Element>();
        
        this.clause = NonFinalClauseMapping.createClause(
                testGraph.getModelElements(), 
                testSource, 
                this.fumlMap(block).getModelElements(), 
                block.getImpl().getAssignmentAfterMap(),
                this.assignedNames, 
                this.modelElements, this);
     }
    
    public Clause getClause() throws MappingError {
        if (this.clause == null) {
            this.mapClause();
            this.map(clause);
        }
        return this.clause;
    }
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
        this.getClause();
        return this.modelElements;
    }

	public SwitchClause getSwitchClause() {
		return (SwitchClause) this.getSource();
	}

    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        SwitchClause switchClause = this.getSwitchClause();
        
        Collection<Expression> switchCases = switchClause.getCase();
        if (!switchCases.isEmpty()) {
            System.out.println(prefix + " case:");
            for (Expression switchCase: switchCases) {
                Mapping mapping = switchCase.getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix);
                }
            }
        }
        
        Block block = switchClause.getBlock();
        if (block != null) {
            System.out.println(prefix + " block:");
            Mapping mapping = block.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
    }

} // SwitchClauseMapping
