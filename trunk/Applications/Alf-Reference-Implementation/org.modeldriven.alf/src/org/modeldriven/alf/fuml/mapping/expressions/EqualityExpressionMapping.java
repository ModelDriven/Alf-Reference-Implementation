
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.expressions.BinaryExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.EqualityExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.CallBehaviorAction;
import org.modeldriven.alf.uml.InputPin;
import org.modeldriven.alf.uml.TestIdentityAction;
import org.modeldriven.alf.uml.ValueSpecificationAction;
import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Behavior;

public class EqualityExpressionMapping extends BinaryExpressionMapping {

    /**
     * 1. An equality expression is mapped to a test identity action. If the
     * expression uses the operator ==, and both operand expressions have a
     * multiplicity lower bound of 1, then the input pins of the action are the
     * targets of object flows from the result source elements for the mappings
     * of the argument expressions. The output pin of the action is the result
     * source pin for the equality expression.
     * 
     * 2. If either operand expression has a multiplicity lower bound of 0, then
     * the result of that expression is first tested for being not empty using
     * the library function Alf::Library::
     * PrimitiveBehaviors::SequenceFunctions::NotEmpty. The test identity action
     * is executed only if both argument expressions are non-empty. Otherwise,
     * the equality expression is true only if both argument expressions are
     * empty.
     * 
     * NOTE. Despite the extra checks described above, the mapping for an
     * equality expression still always evaluates the operand expressions
     * exactly once.
     * 
     * 3. An equality expression that uses the operator != is mapped as above,
     * but the result output pin of the test identity action is connected by an
     * object flow to the argument input pin of a call behavior action for the
     * library function Alf::Library::PrimitiveBehaviors::
     * BooleanFunctions::'!'. The result source element is the result output pin
     * of the call behavior action.
     */
    
    @Override
    protected void mapOperator(
            String operator,
            ActivityNode operand1Result, 
            ActivityNode operand2Result) throws MappingError {
        EqualityExpression expression = this.getEqualityExpression();
        
        TestIdentityAction testAction = 
            this.graph.addTestIdentityAction("==");
        this.action = testAction;
        this.resultSource = testAction.getResult();
        
        int operand1Lower = expression.getOperand1().getLower();
        int operand2Lower = expression.getOperand2().getLower();
        if (operand1Lower == 0 && operand2Lower > 0) {
            ActivityNode testResult = 
                this.mapNonEmptyTest(testAction.getFirst(), operand1Result);
            this.mapNonEmptyResult(testAction, testResult);
            this.graph.addObjectFlow(
                    operand2Result, 
                    testAction.getSecond());
        } else if (operand2Lower > 0 && operand2Lower == 0) {
            this.graph.addObjectFlow(
                    operand1Result, 
                    testAction.getFirst());
            ActivityNode testResult = 
                this.mapNonEmptyTest(testAction.getSecond(), operand2Result);
            this.mapNonEmptyResult(testAction, testResult);
        } else if (operand1Lower == 0 && operand2Lower == 0) {
            ActivityNode test1Result = 
                this.mapNonEmptyTest(testAction.getFirst(), operand1Result);
            ActivityNode test2Result = 
                this.mapNonEmptyTest(testAction.getSecond(), operand2Result);
            this.mapDoubleEmptyResult(testAction, test1Result, test2Result);
        } else {
            this.graph.addObjectFlow(
                    operand1Result, 
                    testAction.getFirst());
            this.graph.addObjectFlow(
                    operand2Result, 
                    testAction.getSecond());
        }
        
        if (expression.getIsNegated()) {
            CallBehaviorAction callAction = this.graph.addCallBehaviorAction(
                    getBehavior(RootNamespace.getBooleanFunctionNot()));
            this.graph.addObjectFlow(this.resultSource, callAction.getArgument().get(0));
            this.resultSource = callAction.getResult().get(0);
        }
    }
    
    @Override
    protected void map() throws MappingError {
        // The following optimizes the cases when one or both operands are
        // known to be null.
        
        EqualityExpression expression = this.getEqualityExpression();
        boolean operand1IsNull = expression.getOperand1().getImpl().isNull();
        boolean operand2IsNull = expression.getOperand2().getImpl().isNull();
        
        if (operand1IsNull && operand2IsNull) {
            this.action = this.graph.addBooleanValueSpecificationAction(
                    !expression.getIsNegated());
            this.resultSource = ((ValueSpecificationAction)this.action).getResult();
        } else if (operand1IsNull || operand2IsNull) {
            ActivityNode operandResult = this.mapOperand(operand2IsNull? 
                    expression.getOperand1(): expression.getOperand2());
            ElementReference function = expression.getIsNegated()?
                    RootNamespace.getSequenceFunctionNotEmpty():
                    RootNamespace.getSequenceFunctionIsEmpty();
            CallBehaviorAction callAction = this.graph.addCallBehaviorAction(
                    getBehavior(function));
            this.graph.addObjectFlow(operandResult, callAction.getArgument().get(0));
            this.action = callAction;
            this.resultSource = callAction.getResult().get(0);
        } else {
            super.map();
        }
    }

    private ActivityNode mapNonEmptyTest(
	        InputPin inputPin,
            ActivityNode operandResult) throws MappingError {
        ActivityNode forkNode = 
            this.graph.addForkNode("Fork(" + operandResult.getName() + ")");
        this.graph.addObjectFlow(operandResult, forkNode);
        this.graph.addObjectFlow(forkNode, inputPin);
        
        CallBehaviorAction callAction = this.graph.addCallBehaviorAction(
                getBehavior(RootNamespace.getSequenceFunctionNotEmpty()));
        this.graph.addObjectFlow(forkNode, callAction.getArgument().get(0));
        
        return callAction.getResult().get(0);
    }

    private void mapNonEmptyResult(
            TestIdentityAction testAction, 
            ActivityNode testResult) {
        ActivityNode forkNode = 
            this.graph.addForkNode("Fork(" + testResult.getName() + ")");
        this.graph.addObjectFlow(testResult, forkNode);

        ActivityNode mergeNode = this.graph.addMergeNode(
                "Merge(" + testAction.getResult().getName() + ", " + testResult.getName() + ")");
        this.graph.addObjectFlow(testAction.getResult(), mergeNode);
        
        this.graph.addObjectDecisionNode(
                testResult.getName(), forkNode, forkNode, null, mergeNode);
        
        this.resultSource = mergeNode;
    }

    private void mapDoubleEmptyResult(
            TestIdentityAction testAction, 
            ActivityNode test1Result,
            ActivityNode test2Result) throws MappingError {
        ActivityNode initialNode = 
            this.graph.addInitialNode("Initial(" + testAction.getName() + ")");
        
        ActivityNode fork1 = 
            this.graph.addForkNode("Fork(" + test1Result.getName() + ")");
        this.graph.addObjectFlow(test1Result, fork1);
        
        ActivityNode fork2 = 
            this.graph.addForkNode("Fork(" + test2Result.getName() + ")");
        this.graph.addObjectFlow(test2Result, fork2);

        Behavior booleanFunctionNot = 
            getBehavior(RootNamespace.getBooleanFunctionNot());
        CallBehaviorAction not1Action = 
            this.graph.addCallBehaviorAction(booleanFunctionNot);
        this.graph.addObjectFlow(fork1, not1Action.getArgument().get(0));        
        CallBehaviorAction not2Action = 
            this.graph.addCallBehaviorAction(booleanFunctionNot);
        this.graph.addObjectFlow(fork2, not2Action.getArgument().get(0));        

        ActivityNode decision2 = this.graph.addControlDecisionNode(
                test2Result.getName(), null, fork2, testAction, not1Action);
        this.graph.addControlDecisionNode(
                test1Result.getName(), initialNode, fork1, decision2, not2Action);
        
        ActivityNode mergeNode = this.graph.addMergeNode(
                "Merge(" + testAction.getResult().getName() + ", !" + 
                test1Result.getName() + " && !" + test2Result.getName() + ")");
        this.graph.addObjectFlow(testAction.getResult(), mergeNode);
        this.graph.addObjectFlow(not1Action.getResult().get(0), mergeNode);
        this.graph.addObjectFlow(not2Action.getResult().get(0), mergeNode);
        
        this.resultSource = mergeNode;        
    }

    public EqualityExpression getEqualityExpression() {
		return (EqualityExpression) this.getSource();
	}

} // EqualityExpressionMapping
