
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.SequenceConstructionExpression;
import org.modeldriven.alf.syntax.expressions.SequenceElements;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.Collection;

public class SequenceConstructionExpressionMapping extends ExpressionMapping {
    
    private InstanceCreationExpressionMapping instanceCreationMapping = null;
    private Action action = null;
    private ActivityNode resultSource = null;

    /**
     * Collection Object Creation Expression
     * 
     * 1. A sequence construction expression that does not have multiplicity is
     * mapped as an instance creation expression with a constructor for the
     * collection class given by the type name. The argument expression for the
     * constructor is mapped as below for a sequence construction expression
     * with multiplicity for the argument type of the collection class and the
     * sequence elements from the original expression.
     * 
     * Sequence Element List
     * 
     * 2. A sequence construction expression that has multiplicity and a
     * sequence list expression with a nonempty expression list is mapped to a
     * structured activity node with a single output pin whose type and
     * multiplicity, are as specified for the expression. The output pin is the
     * result source element for the expression.
     * 
     * 3. Each element expression is mapped inside the structured activity node,
     * with an object flow from its result source element to the structured
     * activity node output pin. If there is more than one element expression,
     * then the mapping for each element expression is wrapped in its own
     * structured activity node and they are connected sequentially by control
     * flows.
     * 
     * Sequence Range
     * 
     * 4. A sequence construction expression that has multiplicity and a
     * sequence range expression is mapped to a structured activity node with
     * the range upper and lower expressions mapped inside it. The result source
     * elements of the upper and lower expressions are connected by object flows
     * to input pins of a loop node in the structured activity node. The loop
     * node also has a third input pin that has a multiplicity lower bound of 0.
     * The output pin corresponding to this third input pin is the result source
     * element for the sequence range expression
     * 
     * 5. The loop node is iterative, continually incrementing the value in its
     * first loop variable until it reaches the value of its second loop
     * variable. On each iteration, it appends the value of its first loop
     * variable to the list in its third loop variable, which, at the end of the
     * iteration, thus contains the desired sequence.
     * 
     * Empty Collections
     * 
     * 6. A sequence construction expression that has multiplicity and an empty
     * expression list maps to a value specification action for a literal null.
     * The result output pin of the value specification has the type given for
     * the sequence list expression and the multiplicity [1..1]. It is the
     * result source element for the expression.
     * 
     * 7. The keyword null is mapped as any[]{}.
     */
    
    // This is only called if the expression has multiplicity.
    private void mapToAction() throws MappingError {
        SequenceConstructionExpression expression = 
            this.getSequenceConstructionExpression();
        if (!expression.getHasMultiplicity()) {
            FumlMapping mapping = this.fumlMap(
                    expression.getImpl().getInstanceCreationExpression());
            if (!(mapping instanceof InstanceCreationExpressionMapping)) {
                this.throwError("Error mapping collection instance creation: " + 
                        mapping.getErrorMessage());
            } else {
                this.instanceCreationMapping = 
                    (InstanceCreationExpressionMapping)mapping;
                this.action = this.instanceCreationMapping.getAction();
                this.resultSource = this.instanceCreationMapping.getResultSource();
            }
        } else {
            SequenceElements elements = expression.getElements();
            if (elements == null || elements.getImpl().isEmpty()) {
                this.action = this.graph.addNullValueSpecificationAction();
                this.resultSource = ((ValueSpecificationAction)this.action).result;
            } else {
                FumlMapping mapping = this.fumlMap(elements);
                if (!(mapping instanceof SequenceElementsMapping)) {
                    this.throwError("Error mapping sequence elements: " + 
                            mapping.getErrorMessage());
                } else {
                    StructuredActivityNode node = 
                        this.graph.addStructuredActivityNode(
                            "SequenceConstructionExpression@" + expression.getId(), 
                            mapping.getModelElements());
                    OutputPin outputPin = ActivityGraph.createOutputPin(
                            node.name + ".output", 
                            this.getType(), 
                            expression.getLower(), expression.getUpper());
                    node.addStructuredNodeOutput(outputPin);
                    for (ActivityNode resultSource: 
                        ((SequenceElementsMapping)mapping).getResultSources()) {
                        node.addEdge(ActivityGraph.createObjectFlow(
                                resultSource, outputPin));
                    }
                    this.action = node;
                    this.resultSource = outputPin;
                }
            }
        }
    }
    
    public Action getAction() throws MappingError {
        if (this.action == null) {
            this.mapToAction();
            this.mapTo(this.action);
        }
        return this.action;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        this.getAction();
        return this.resultSource;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
    }

    @Override
    public Collection<Element> getModelElements() throws MappingError {
        this.getAction();
		if (this.instanceCreationMapping == null) {
		    return super.getModelElements();
		} else {
		    return this.instanceCreationMapping.getModelElements();
		}
	}

	public SequenceConstructionExpression getSequenceConstructionExpression() {
		return (SequenceConstructionExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
        super.print(prefix);
	    if (this.instanceCreationMapping != null) {
	        System.out.println(prefix + " instanceCreationExpression:");
	        instanceCreationMapping.printChild(prefix);
	    } else {
	        System.out.println(prefix + " action: " + this.action);
    	    SequenceElements elements = 
    	        this.getSequenceConstructionExpression().getElements();
    	    if (elements != null) {
    	        System.out.println(prefix + " elements:");
    	        Mapping mapping = elements.getImpl().getMapping();
    	        if (mapping != null) {
    	            mapping.printChild(prefix);
    	        }
    	    }
	    }
	}

} // SequenceConstructionExpressionMapping
