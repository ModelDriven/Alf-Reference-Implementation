
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.LeftHandSideMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.NameLeftHandSide;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.List;

public class NameLeftHandSideMapping extends LeftHandSideMapping {
    
    private ActivityNode assignmentTarget = null;
    private CallBehaviorAction action = null;
    private List<Element> modelElements = null;
    
    /*
    private static QualifiedNameImpl sequenceFunctions = null;    
    private static ElementReference sequenceFunctionReplacingAll = null;
    
    public static QualifiedNameImpl getSequenceFunctions() {
        if (sequenceFunctions == null) {
            sequenceFunctions = 
                RootNamespace.getAlfStandardLibrary().getImpl().copy().
                    addName("SequenceFunctions").getImpl();
            sequenceFunctions.setCurrentScope(RootNamespace.getRootScope());
        }
        return sequenceFunctions.copy();
    }
    
    public static ElementReference getSequenceFunctionReplacingAll() {
        if (sequenceFunctionReplacingAll == null) {
            sequenceFunctionReplacingAll = getSequenceFunctions().
                addName("ReplacingAt").getImpl().getBehaviorReferent();
        }
        return sequenceFunctionReplacingAll;
    }
    */
    
    @Override
    public void mapTo(ForkNode resultSource) throws MappingError {
        super.mapTo(resultSource);
        
        NameLeftHandSide lhs = this.getNameLeftHandSide();
        if (lhs.getImpl().getFeature() == null) {
            resultSource.setName("Fork(" + lhs.getTarget().getPathName() + ")");
            
            Expression index = lhs.getIndex();
            if (index == null) {
                this.assignmentTarget = resultSource;
            } else {
                this.throwError("Index mapping not yet implemented.");
                /*
                FumlMapping indexMapping = this.fumlMap(index);
                if (!(indexMapping instanceof ExpressionMapping)) {
                    this.throwError("Error mapping index: " + indexMapping);
                } else {
                    this.modelElements = new ArrayList<Element>();
                    this.modelElements.addAll(indexMapping.getModelElements());
                    
                    FumlMapping behaviorMapping = 
                        this.fumlMap(getSequenceFunctionReplacingAll());
                    if (behaviorMapping instanceof ElementReferenceMapping) {
                        behaviorMapping = 
                            ((ElementReferenceMapping)behaviorMapping).getMapping();
                    }
                    if (!(behaviorMapping instanceof ActivityDefinitionMapping)) {
                        this.throwError("Error mapping ReplacingAt behavior: " + 
                                behaviorMapping);
                    } else {
                    
                        this.action = new CallBehaviorAction();
                        this.action.setName("LeftHandSide@" + 
                                Integer.toHexString(lhs.hashCode()));
                        this.action.setBehavior(
                                ((ActivityDefinitionMapping)behaviorMapping).
                                    getBehavior()
                        );
                        
                        InputPin inputPin = new InputPin();
                        inputPin.setName(this.action.name + ".argument(seq)");
                        this.action.addArgument(inputPin);
                        
                    }
                }
                */
            }
        }
    }
    
    

	@Override
    public ActivityNode getAssignmentTarget() throws MappingError {
	    this.getResultSource();
	    return this.assignmentTarget;
    }

	@Override
	public Element getElement() {
	    if (this.action == null) {
	        return super.getElement();
	    } else {
	        return this.action;
	    }
	}
	
	@Override
	public List<Element> getModelElements() throws MappingError {
		List<Element> elements = super.getModelElements();
		if (this.action != null) {
		    elements.add(this.action);
		}
		if (this.modelElements != null) {
		    elements.addAll(this.modelElements);
		}
		return elements;
	}

	public NameLeftHandSide getNameLeftHandSide() {
		return (NameLeftHandSide) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.action != null) {
	        System.out.println(prefix + " action: " + this.action);
	    }
	    
	    if (this.assignmentTarget != null) {
	        System.out.println(prefix + " assignmentTarget: " + 
	                this.assignmentTarget);
	    }	    
	}

} // NameLeftHandSideMapping
