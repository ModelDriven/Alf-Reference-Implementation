
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.CastExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.Pin;
import fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Classifier;

public class CastExpressionMapping extends ExpressionMapping {
    
    private ExpansionRegion region = null;
    private ActivityNode resultSource = null;

    /**
     * 1. If the named type is any or is a supertype of the type of the operand
     * expression, then a cast expression is mapped as its operand expression,
     * except that, if the result source element is a typed element, it is typed
     * with the type of the cast expression, rather than the type of the
     * argument expression.
     * 
     * 2. If the named type is a classifier, then a cast expression is mapped
     * like a select expression whose condition is a read is classified object
     * action for the type of the cast expression ("instanceof" operator).
     */
    
    protected static ActivityNode addClassificationDecision(
            ActivityGraph graph,
            Classifier type,
            ActivityNode trueTarget,
            ActivityNode falseTarget,
            ElementReference conversionFunction) throws MappingError {
        
        ReadIsClassifiedObjectAction isClassifiedAction = 
            graph.addReadIsClassifiedObjectAction(type, false);
        
        ForkNode forkNode = 
            graph.addForkNode("Fork(" + isClassifiedAction.object.name + ")");        
        graph.addObjectFlow(forkNode, isClassifiedAction.object);
        
        if (conversionFunction != null) {
            CallBehaviorAction callAction = 
                graph.addCallBehaviorAction(getBehavior(conversionFunction));
            graph.addObjectFlow(callAction.result.get(0), trueTarget);
            trueTarget = callAction.argument.get(0);
        }
        
        graph.addObjectDecisionNode(
                isClassifiedAction.result.name, 
                forkNode, isClassifiedAction.result, 
                trueTarget, falseTarget);
        
        return forkNode;
    }
    
    protected ActivityNode mapNestedGraph(
            ActivityNode target, 
            ActivityGraph nestedGraph) throws MappingError {
        ActivityNode source = null;
        
        CastExpression expression = this.getCastExpression();
        Expression operand = expression.getOperand();
        ElementReference type = expression.getType();
        ElementReference operandType = operand == null? null: operand.getType();
        if (type != null) {
            
            // Add type classification tests and special conversion for
            // numeric and BitString types.
            // NOTE: The classification tests will actually be executed in the
            // reverse order to that in which they are added using
            // addClassificationDecision.
            
            if (type.getImpl().isInteger()) {
                // NOTE: For Integer, the test for an UnlimitedNatural operand
                // must come after the test of for an Integer operand
                // in order to properly handle Natural values. Even though 
                // Natural is a subtype of both Integer and UnlimitedNatural, 
                // Natural values are represented as integers.
                if (operandType == null || 
                        operandType.getImpl().isUnlimitedNatural()) {
                    source = addClassificationDecision(
                            nestedGraph, getUnlimitedNaturalType(), 
                            target, source, 
                            RootNamespace.getUnlimitedNaturalFunctionToInteger());
                }
                if (operandType == null ||
                        operandType.getImpl().isBitString()) {
                    source = addClassificationDecision(
                            nestedGraph, getBitStringType(), 
                            target, source, 
                            RootNamespace.getBitStringFunctionToInteger());
                }
                if (operandType == null || 
                        !operandType.getImpl().isUnlimitedNatural() && 
                        !operandType.getImpl().isBitString()) {
                    source = addClassificationDecision(
                            nestedGraph, this.getType(), target, source, null);
                }
            } else if (type.getImpl().isUnlimitedNatural()) {
                // NOTE: For UnlimitedNatural, the test for an Integer operand
                // must come before the test of for an UnlimitedNatural operand
                // in order to properly handle Natural values. Even though 
                // Natural is a subtype of both Integer and UnlimitedNatural, 
                // Natural values are represented as integers.
                if (operandType == null || !operandType.getImpl().isInteger()) {
                    source = addClassificationDecision(
                            nestedGraph, this.getType(), target, source, null);
                }
                if (operandType == null || 
                        operandType.getImpl().isInteger()) {
                    source = addClassificationDecision(
                            nestedGraph, getIntegerType(), 
                            target, source, 
                            RootNamespace.getIntegerFunctionToUnlimitedNatural());
                }
            } else if (type.getImpl().isBitString()) {
                if (operandType == null || 
                        operandType.getImpl().isInteger()) {
                    source = addClassificationDecision(
                            nestedGraph, getIntegerType(), target, 
                            source, RootNamespace.getBitStringFunctionToBitString());
                }
                if (operandType == null || !operandType.getImpl().isInteger()) {
                    source = addClassificationDecision(
                            nestedGraph, this.getType(), target, source, null);
                }
                
            // This is the general case.
            } else {
                source = addClassificationDecision(
                        nestedGraph, this.getType(), target, null, null);
            }
        }
        
        return source;
    }
    
    public void map() throws MappingError {
        CastExpression expression = this.getCastExpression();
        Expression operand = expression.getOperand();
        ElementReference type = expression.getType();
        ElementReference operandType = operand == null? null: operand.getType();
        
        FumlMapping mapping = this.fumlMap(operand);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping operandMapping = (ExpressionMapping)mapping;
            this.graph.addAll(operandMapping.getGraph());
            if (type == null || 
                    operandType != null && operandType.getImpl().conformsTo(type) &&
                    
                    // The following condition is to treat up-casting of
                    // naturals to unlimited naturals as a real conversion, 
                    // because naturals are represented as integers.
                    !(operandType.getImpl().isNatural() && 
                            type.getImpl().isUnlimitedNatural()) ||
                    
                    // The following condition is an optimization to avoid 
                    // generating an expansion region when the operand is known
                    // to be null.
                    operand.getImpl().isNull()) {
                
                this.resultSource = operandMapping.getResultSource();
                if (this.resultSource instanceof Pin) {
                    ((Pin)this.resultSource).setType(this.getType());
                } else if (this.resultSource instanceof ExpansionNode) {
                    ((ExpansionNode)this.resultSource).setType(this.getType());
                }
            } else {
                String label = expression.getClass().getSimpleName()+ 
                    "@" + expression.getId();
                
                ActivityGraph nestedGraph = new ActivityGraph();
                
                ActivityNode inputSource = operandMapping.getResultSource();
                ActivityNode nestedResultSource = nestedGraph.addMergeNode(
                        "Merge(" + label + ".operand)");
                ActivityNode inputTarget = this.mapNestedGraph(
                        nestedResultSource, 
                        nestedGraph);
                
                this.region = this.graph.addExpansionRegion(
                        label, 
                        ExpansionKind.parallel, 
                        nestedGraph.getModelElements(), 
                        inputSource, 
                        inputTarget, 
                        nestedResultSource);
                this.region.inputElement.get(0).setType(operandMapping.getType());
                this.region.outputElement.get(0).setType(this.getType());
                
                this.resultSource = this.region.outputElement.get(0);
                
                this.mapTo(this.region);
            }
        }
    }
    
    public ExpansionRegion getRegion() throws MappingError {
        this.getResultSource();
        return this.region;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        if (this.resultSource == null) {
            this.map();
            this.mapTo(this.region);
        }
        return this.resultSource;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getResultSource();
        return super.getGraph();
    }

	public CastExpression getCastExpression() {
		return (CastExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
        super.print(prefix);
        
	    CastExpression expression = this.getCastExpression();
	    
        if (this.region != null) {
            System.out.println(prefix + " region: " + this.region);
        }
        
        if (this.resultSource != null) {
            System.out.println(prefix + " resultSource: " + this.resultSource);
        }
        
	    Expression operand = expression.getOperand();
	    if (operand != null) {
	        System.out.println(prefix + " operand:");
	        Mapping mapping = operand.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // CastExpressionMapping
