
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.NonFinalClause;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.BasicActions.Pin;
import fUML.Syntax.Activities.CompleteStructuredActivities.Clause;
import fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.Collection;

public class NonFinalClauseMapping extends SyntaxElementMapping {

    private Clause clause = null;
    private Collection<Element> modelElements = null;
    private Collection<AssignedSource> assignments = null;

    /**
     * 1. Each if clause maps to a clause of the conditional node.
     * 
     * 2. each clause of the conditional node also must have a body output pin
     * from within the clause identified for each result pin of the conditional
     * node. If a name is assigned within a clause and the assigned source for
     * that name within the clause is a pin on an action within the body of the
     * clause, then that pin is used as the clause body output pin corresponding
     * to that local name. Otherwise, a structured activity node is added to the
     * mapping of the clause as follows: The structured activity node has one
     * input pin and one output pin, with an object flow from the input pin to
     * the output pin contained within the structured activity node. There is an
     * object flow from the assigned source for the name after the clause (which
     * may be from inside or outside the clause) to the input pin of the
     * structured activity node. The output pin of the structured activity node
     * is then used as the clause body output pin corresponding to the name.
     */
    
    // NOTE: This should be called before mapping.
    public void setAssignments(Collection<AssignedSource> assignments) {
        this.assignments = assignments;
    }
    
    public void mapClause() 
        throws MappingError {
        NonFinalClause nonFinalClause = this.getNonFinalClause();
        Expression condition = nonFinalClause.getCondition();
        Block body = nonFinalClause.getBody();
        
        FumlMapping mapping = this.fumlMap(condition);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping condition: " + 
                    mapping.getErrorMessage());
        } else {
            this.modelElements = new ArrayList<Element>();
            this.clause = createClause(
                    mapping.getModelElements(), 
                    ((ExpressionMapping)mapping).getResultSource(), 
                    this.fumlMap(body).getModelElements(), 
                    this.assignments, 
                    this.modelElements, this);
        }            
     }
    
    public Clause getClause() 
        throws MappingError {
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

	public NonFinalClause getNonFinalClause() {
		return (NonFinalClause) this.getSource();
	}
	
	public static Clause createClause(
	        Collection<Element> testElements, ActivityNode decider,
	        Collection<Element> bodyElements,
	        Collection<AssignedSource> assignments,
	        Collection<Element> modelElements,
	        FumlMapping parentMapping) throws MappingError {
        Clause clause = new Clause();
        
        modelElements.addAll(testElements);
        for (Element element: testElements) {
            if (element instanceof ExecutableNode) {
                clause.addTest((ExecutableNode)element);
            }
        }
        
        if (testElements.isEmpty() || !(decider instanceof OutputPin)) {
            StructuredActivityNode passthruNode = 
                ActivityGraph.createPassthruNode(
                        decider.name, getBooleanType(), 1, 1);
            clause.addTest(passthruNode);
            modelElements.add(passthruNode);
            modelElements.add(ActivityGraph.createObjectFlow(
                    decider, passthruNode.structuredNodeInput.get(0)));
            decider = passthruNode.structuredNodeOutput.get(0);
        }
        
        clause.setDecider((OutputPin)decider);
        
        modelElements.addAll(bodyElements);
        for (Element element: bodyElements) {
            if (element instanceof ExecutableNode) {
                clause.addBody((ExecutableNode)element);
            }
        }
        
        if (assignments != null) {
            for (AssignedSource assignment: assignments) {
                String name = assignment.getName();
                SyntaxElement source = assignment.getSource();
                ElementReference type = assignment.getType();
                FumlMapping mapping = parentMapping.fumlMap(source);
                if (!(mapping instanceof SyntaxElementMapping)) {
                    parentMapping.throwError("Error mapping source " + 
                            source + ": " + mapping.getErrorMessage());
                } else {
                    ActivityNode bodyOutput = 
                        ((SyntaxElementMapping)mapping).
                        getAssignedValueSource(name);
                    
                    if (!(bodyOutput instanceof OutputPin && 
                            containedIn(bodyOutput, bodyElements))) {
                        Classifier classifier = null;
                        if (type != null) {
                            mapping = parentMapping.fumlMap(type);
                            if (mapping instanceof ElementReferenceMapping) {
                                mapping = ((ElementReferenceMapping)mapping).
                                    getMapping();
                            }
                            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                                parentMapping.throwError("Error mapping type " + 
                                        type + ": " + mapping.getErrorMessage());
                            }
                            classifier = 
                                ((ClassifierDefinitionMapping)mapping).
                                    getClassifier();
                        }
    
                        StructuredActivityNode passthruNode = 
                            ActivityGraph.createPassthruNode(
                                    bodyOutput.name, 
                                    classifier, 
                                    assignment.getLower(), 
                                    assignment.getUpper());
                        modelElements.add(passthruNode);
                        modelElements.add(ActivityGraph.createObjectFlow(
                                decider, passthruNode.structuredNodeInput.get(0)));
                        bodyOutput = passthruNode.structuredNodeOutput.get(0);
                    }
                    
                    clause.addBodyOutput((OutputPin)bodyOutput);
                }
            }
        }
        
        return clause;
	}
	
	public static boolean containedIn(ActivityNode node, Collection<Element> elements) {
	    for (Element element: elements) {
	        if (element == node || 
	                node instanceof Pin || ((Pin)node).owner == element) {
	            return true;
	        }
	    }
	    return false;
	}

} // NonFinalClauseMapping
