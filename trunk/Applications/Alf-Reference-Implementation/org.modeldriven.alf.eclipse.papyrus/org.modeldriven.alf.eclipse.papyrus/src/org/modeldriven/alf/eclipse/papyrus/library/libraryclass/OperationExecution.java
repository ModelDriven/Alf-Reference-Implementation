/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/


package org.modeldriven.alf.eclipse.papyrus.library.libraryclass;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.eclipse.papyrus.library.libraryclass.ImplementationObject;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.UMLFactory;

public class OperationExecution extends Execution {

    public void set(ImplementationObject context, Operation operation) {
        this.context = context;

        OpaqueBehavior method = UMLFactory.eINSTANCE.createOpaqueBehavior();
        method.setSpecification(operation);

        for (Parameter operationParameter: operation.getOwnedParameters()) {
            Parameter methodParameter = UMLFactory.eINSTANCE.createParameter();
            methodParameter.setName(operationParameter.getName());
            methodParameter.setDirection(operationParameter.getDirection());
            methodParameter.setType(operationParameter.getType());
            methodParameter.setUpper(operationParameter.getUpper());
            methodParameter.setLower(operationParameter.getLower());
            methodParameter.setIsOrdered(operationParameter.isOrdered());
            methodParameter.setIsUnique(operationParameter.isUnique());
            method.getOwnedParameters().add(methodParameter);
        }

        this.types.add(method);

    }

    @Override
    public Value new_() {
        return (Value) (new OperationExecution());
    }

    @Override
    public void execute() {
    	
    	// Note: The following ensures that this operation execution has
    	// output parameter values for all output parameters in the correct
    	// order. (Except inout parameters may still be out of order.)
    	for (Parameter parameter: this.getBehavior().getOwnedParameters()){
    		if (parameter.getDirection() == ParameterDirectionKind.OUT_LITERAL ||
    				parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
    			this.setParameterValue(parameter, new ArrayList<Value>());
    		}
    	}
    	
        ((ImplementationObject) (this.context)).execute(this);
    }

    public String getOperationName() {
        return this.getBehavior().getSpecification().getName();
    }

    public Parameter getParameter(String parameterName) {
        Behavior method = this.getBehavior();

        for (Parameter parameter: method.getOwnedParameters()) {
            if (parameter.getName().equals(parameterName)) {
                return parameter;
            }
        }

        return null;
    }

    public ParameterValue getParameterValue(
            String parameterName) {
        return this.getParameterValue(this.getParameter(parameterName));
    }
    
    public void setParameterValue(Parameter parameter, List<Value> values) {
    	if (parameter != null) {
	        ParameterValue parameterValue = new ParameterValue();
	        parameterValue.parameter = parameter;
	        parameterValue.values = values;
	
	        this.setParameterValue(parameterValue);
    	}
    }

    public void setParameterValue(String parameterName, List<Value> values) {
        this.setParameterValue(this.getParameter(parameterName), values);
    }

    public void setParameterValue(String parameterName, Value value) {
        List<Value> valueList = new ArrayList<Value>();
        valueList.add(value);
        this.setParameterValue(parameterName, valueList);
    }
    
    public void setReturnParameterValue(List<Value> values) {
        Behavior method = this.getBehavior();

        for (Parameter parameter: method.getOwnedParameters()) {
            if (parameter.getDirection() == ParameterDirectionKind.RETURN_LITERAL) {
                this.setParameterValue(parameter, values);
                return;
            }
        }
    }
    
    public void setReturnParameterValue(Value value) {
        List<Value> valueList = new ArrayList<Value>();
        valueList.add(value);
        this.setReturnParameterValue(valueList);
    }

} // OperationExecution
