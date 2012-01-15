
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
import org.modeldriven.alf.syntax.expressions.QualifiedName;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class NameLeftHandSideMapping extends LeftHandSideMapping {
    
    private ActivityNode assignmentTarget = null;
    
    @Override
    public void mapTo(ActivityNode node) throws MappingError {
        super.mapTo(node);
        NameLeftHandSide lhs = this.getNameLeftHandSide();

        if (lhs.getImpl().getFeature() == null) {
            this.resultSource.setName("Fork(" + lhs.getTarget().getPathName() + ")");
            
            Expression index = lhs.getIndex();
            if (index == null) {
                this.assignmentTarget = this.resultSource;
            } else {
                this.assignmentTarget = this.resultSource;
                this.setErrorMessage("Index mapping not yet implemented.");
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
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        NameLeftHandSide lhs = this.getNameLeftHandSide();
        QualifiedName target = lhs.getTarget();
        return target == null || target.getIsFeatureReference() ||
            !target.getUnqualifiedName().getName().equals(name)? null:
                this.getAssignedValueSource();
    }
    
	@Override
    public ActivityNode getAssignmentTarget() throws MappingError {
	    this.getResultSource();
	    return this.assignmentTarget;
    }

	public NameLeftHandSide getNameLeftHandSide() {
		return (NameLeftHandSide) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.assignmentTarget != null) {
	        System.out.println(prefix + " assignmentTarget: " + 
	                this.assignmentTarget);
	    }
	    
	    if (this.resultSource != null) {
	        System.out.println(prefix + " assignedValueSource: " + 
	                this.resultSource);
	    }
	}

} // NameLeftHandSideMapping
