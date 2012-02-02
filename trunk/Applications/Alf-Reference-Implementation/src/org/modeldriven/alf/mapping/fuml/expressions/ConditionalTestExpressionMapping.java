
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.ConditionalTestExpression;
import org.modeldriven.alf.syntax.expressions.Expression;

import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.MergeNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

public class ConditionalTestExpressionMapping extends ExpressionMapping {
    
    protected ActivityNode resultSource = null;
    private Map<String, ActivityNode> assignedValueSourceMap = 
        new HashMap<String, ActivityNode>();

    /**
     * 1. A conditional-test expression maps to a decision node with an incoming
     * control flow from an initial node. The decision input flow for the
     * decision node has as its source the result source element from the
     * mapping of the first operand expression. The decision node has two
     * outgoing control flows with the guards true and false. The true flow has
     * as its target a structured activity node that contains the mapping of the
     * second operand expression. The false flow has as its target a structured
     * activity node that contains the mapping of the third operand expression.
     * The result source elements from the mapping of the second and third
     * operand expressions are connected by object flows to a merge node
     * (outside either structured activity node). This merge node is the result
     * source element for the conditional-test expression.
     * 
     * 2. For any name assigned in either (or both) of the second and third
     * operand expressions, an output pin is added to the structured activity
     * nodes for both the second and third operand expressions. Within each
     * structured activity node, if the name is assigned in the corresponding
     * operand expression, then the assigned source for the name after the
     * operand expression is connected to the output pin. If the name is not
     * assigned in the corresponding operand expression, then an additional
     * structured activity node is added to the mapping of the operand
     * expression as follows: 
     * 
     * - The structured activity node has one input pin and one output pin, with
     * an object flow from the input pin to the output pin contained within the
     * structured activity node.
     * 
     * - There is an object flow from the assigned source for the name before
     * the operand expression to the input pin of the structured activity node.
     * 
     * The output pin of the added structured activity node is then connected by
     * an object flow to the output pin corresponding to the name on the
     * enclosing structured activity node for the argument expression. Each pair
     * of output pins on the structured activity nodes for the operand
     * expressions corresponding to the same name are connected by object flows
     * to a merge node. This merge node is the source for the assigned value of
     * the name after the conditional-test expression.
     */
    
    protected ExpressionMapping mapOperand(Expression operand) 
        throws MappingError {
        ExpressionMapping operandMapping = null;
        FumlMapping mapping = this.fumlMap(operand);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand expression: " + 
                    mapping.getErrorMessage());
        } else {
            operandMapping = (ExpressionMapping)mapping;
        }
        return operandMapping;
    }
    
    protected StructuredActivityNode mapOperandNode (
            String label,
            List<AssignedSource> assignments,
            ExpressionMapping operandMapping) throws MappingError {
        
        Collection<Element> modelElements = operandMapping.getModelElements();
        ActivityNode resultSource = operandMapping.getResultSource();
        if (modelElements.isEmpty()) {
            // This ensures that, even if the operand mapping is empty, there is
            // something by which to control the flow through the operand node.
            MergeNode mergeNode = new MergeNode();
            mergeNode.setName("Merge(" + resultSource.name + ")");
            modelElements = new ArrayList<Element>();
            modelElements.add(mergeNode);
            modelElements.add(
                    ActivityGraph.createObjectFlow(resultSource, mergeNode));
            resultSource = mergeNode;
        }
        
        StructuredActivityNode operandNode =
            this.graph.addStructuredActivityNode(label, modelElements);
        OutputPin outputPin = ActivityGraph.createOutputPin(
                label + ".result", null, 0, -1);
        operandNode.addStructuredNodeOutput(outputPin);
        operandNode.addEdge(ActivityGraph.createObjectFlow(resultSource, outputPin));
        
        // Map local name assignments.
        for (AssignedSource assignment: assignments) {
            String name = assignment.getName();
            ElementReference type = assignment.getType();
            int lower = assignment.getLower();
            int upper = assignment.getUpper();
            SyntaxElement source = assignment.getSource();
            
            FumlMapping mapping = this.fumlMap(type);
            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                this.throwError("Error mapping type for " + name + ": " +
                        mapping.getErrorMessage());
            } else {
                Classifier classifier = ((ClassifierDefinitionMapping)mapping).
                    getClassifierOnly();
                
                outputPin = ActivityGraph.createOutputPin(
                            assignment.getName(), classifier, lower, upper);
                operandNode.addStructuredNodeOutput(outputPin);
                mapping = this.fumlMap(source);
                if (!(mapping instanceof SyntaxElementMapping)) {
                    this.throwError("Error mapping source for " + name + ": " + 
                            mapping.getErrorMessage());
                } else {
                    ActivityNode sourceNode = ((SyntaxElementMapping)mapping).
                        getAssignedValueSource(name);
                    
                    if (sourceNode != null) {
                        
                        // Check if the local name was assigned within the
                        // operand expression.
                        // NOTE: If the name was assigned in the operand, then
                        // the source node for the name will be one of the
                        // elements mapped from the operand.
                        if (!ActivityGraph.isContainedIn(sourceNode, operandNode)) {
                            StructuredActivityNode dummyNode = 
                                new StructuredActivityNode();
                            dummyNode.setName("StructuredNode(" + name + ")");
                            InputPin input = ActivityGraph.createInputPin(
                                    dummyNode.name + ".input", 
                                    classifier, lower, upper);
                            dummyNode.addStructuredNodeInput(input);
                            OutputPin output = ActivityGraph.createOutputPin(
                                    dummyNode.name + ".output", 
                                    classifier, lower, upper);
                            dummyNode.addStructuredNodeOutput(output);
                            dummyNode.addEdge(ActivityGraph.createObjectFlow(
                                    input, output));
                            operandNode.addNode(dummyNode);
                            this.graph.addObjectFlow(sourceNode, input);
                            sourceNode = output;
                        }
                        
                        operandNode.addEdge(ActivityGraph.createObjectFlow(
                                sourceNode, outputPin));
                    }
                }
            }
        }
        
        return operandNode;
    }
    
    protected static void addToNewAssignments(
            Map<String, AssignedSource> newAssignments, 
            Map<String, AssignedSource> assignmentsAfter,
            Expression operand) {
        for (AssignedSource assignment: operand.getImpl().getNewAssignments()) {
            String name = assignment.getName();
            AssignedSource assignmentAfter = assignmentsAfter.get(name);
            if (assignmentAfter != null && !newAssignments.containsKey(name)) {
                newAssignments.put(name, assignmentAfter);
            }
        }
    }
    
    protected void map() throws MappingError {
        ConditionalTestExpression expression = this.getConditionalTestExpression();
        Expression operand1 = expression.getOperand1();
        Expression operand2 = expression.getOperand2();
        Expression operand3 = expression.getOperand3();
        
        // NOTE: The use of getExpression here gets the original expression,
        // even when getConditionalTestExpression is overridden in
        // ConditionalLogicalExpressionMapping.
        String label = 
            this.getExpression().getClass().getSimpleName() + "@" + expression.getId();
        
        // Get assigned sources for local names assigned in the second or third
        // operands.
        Map<String, AssignedSource> newAssignments = 
            new HashMap<String, AssignedSource>();
        Map<String, AssignedSource> assignmentsAfter = 
            expression.getImpl().getAssignmentAfterMap();
        addToNewAssignments(newAssignments, assignmentsAfter, operand2);
        addToNewAssignments(newAssignments, assignmentsAfter, operand3);
        
        // Create an ordered list of the new assignments
        List<AssignedSource> assignments = 
            new ArrayList<AssignedSource>(newAssignments.values());
        
        // Map the operands.
        ExpressionMapping operand1Mapping = this.mapOperand(operand1);
        ExpressionMapping operand2Mapping = this.mapOperand(operand2);
        ExpressionMapping operand3Mapping = this.mapOperand(operand3);
        
        this.graph.addAll(operand1Mapping.getGraph());
        StructuredActivityNode operand2Node =
            mapOperandNode(label + ".operand2", assignments, operand2Mapping);
        StructuredActivityNode operand3Node =
            mapOperandNode(label + ".operand3", assignments, operand3Mapping);
        
        // Map the decision.
        ActivityNode initialNode = 
            this.graph.addInitialNode("Initial(" + label + ")");
        this.graph.addControlDecisionNode(
                label, initialNode, 
                operand1Mapping.getResultSource(), operand2Node, operand3Node);        
        
        // Create the merge of the operand results.
        ActivityNode operand2Result = operand2Node.structuredNodeOutput.get(0);
        ActivityNode operand3Result = operand3Node.structuredNodeOutput.get(0);
        this.resultSource = this.graph.addMergeNode("Merge(" + label + ".result)");
        this.graph.addObjectFlow(operand2Result, this.resultSource);
        this.graph.addObjectFlow(operand3Result, this.resultSource);
        
        // Create merge nodes as assigned value sources for names assigned in
        // the second or third operands.
        for (int i=0; i < assignments.size(); i++) {
            AssignedSource assignment = assignments.get(i);
            String name = assignment.getName();
            ActivityNode mergeNode = 
                this.graph.addMergeNode("Merge(" + label + "." + name + ")");
            this.graph.addObjectFlow(
                    operand2Node.structuredNodeOutput.get(i+1), mergeNode);
            this.graph.addObjectFlow(
                    operand3Node.structuredNodeOutput.get(i+1), mergeNode);
            this.assignedValueSourceMap.put(name, mergeNode);
        }
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        ActivityNode resultSource = this.getResultSource();
        ActivityNode sourceNode = this.assignedValueSourceMap.get(name);
        return sourceNode == null? resultSource: sourceNode;
    }

    @Override
    public ActivityNode getResultSource() throws MappingError {
        if (this.resultSource == null) {
            this.map();            
            this.mapTo(null);
        }
        return this.resultSource;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getResultSource();
        return super.getGraph();
    }

	public ConditionalTestExpression getConditionalTestExpression() {
		return (ConditionalTestExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.resultSource != null) {
	        System.out.println(prefix + " resultSource: " + this.resultSource);
	    }
	    
	    ConditionalTestExpression expression = this.getConditionalTestExpression();
        
        Expression operand1 = expression.getOperand1();
        if (operand1 != null) {
            System.out.println(prefix + " operand1:");
            Mapping mapping = operand1.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
        
        Expression operand2 = expression.getOperand2();
        if (operand2 != null) {
            System.out.println(prefix + " operand2:");
            Mapping mapping = operand2.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
        
        Expression operand3 = expression.getOperand3();
        if (operand3 != null) {
            System.out.println(prefix + " operand3:");
            Mapping mapping = operand3.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
	}

} // ConditionalTestExpressionMapping
