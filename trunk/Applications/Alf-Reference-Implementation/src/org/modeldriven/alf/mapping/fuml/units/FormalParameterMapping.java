
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.units.TypedElementDefinitionMapping;

import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.MultiplicityElement;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.Classes.Kernel.TypedElement;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.BehaviorList;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterMapping extends TypedElementDefinitionMapping {

    private Parameter parameter = null;
    
    public void mapTo(Parameter parameter) throws MappingError {
        super.mapTo(parameter, parameter.multiplicityElement);

        String direction = this.getFormalParameter().getDirection();
        if (direction.equals("in")) {
            parameter.setDirection(ParameterDirectionKind.in);
        } else if (direction.equals("out")) {
            parameter.setDirection(ParameterDirectionKind.out);
        } else if (direction.equals("inout")) {
            parameter.setDirection(ParameterDirectionKind.inout);
        } else if (direction.equals("return")) {
            parameter.setDirection(ParameterDirectionKind.return_);
        }
    }
    
    @Override
    public NamedElement getNamedElement() throws MappingError {
        return this.getParameter();
    }

    public Parameter getParameter() throws MappingError {
        if (this.parameter == null) {
            this.parameter = new Parameter();
            this.parameter.multiplicityElement = new MultiplicityElement();
            this.mapTo(this.parameter);
        }

        return this.parameter;
    }
    
	public FormalParameter getFormalParameter() {
		return (FormalParameter) this.getSource();
	}
	
	@Override
	public ActivityNode getAssignedValueSource(String name) throws MappingError {
        FormalParameter formalParameter = this.getFormalParameter();
        NamespaceDefinition context = formalParameter.getNamespace();
        ActivityNode activityNode = null;
        Activity activity = null;
        Parameter parameter = this.getParameter();        
        
        FumlMapping mapping = this.fumlMap(context);
        if (mapping instanceof OperationDefinitionMapping) {
            Operation operation = 
                ((OperationDefinitionMapping)mapping).getOperation();
            BehaviorList methods = operation.method;
            if (methods.size() > 0) {
                activity = (Activity)methods.get(0);
                parameter = activity.ownedParameter.
                    get(operation.ownedParameter.indexOf(parameter));
            } else {
                this.throwError("Operation has no method: " + operation);
            }
        } else if (mapping instanceof ActivityDefinitionMapping) {
            activity = (Activity)((ActivityDefinitionMapping)mapping).getBehavior();
        } else {
            this.throwError("Error mapping context: " + mapping.getErrorMessage());
        }
        
        activityNode = 
            ActivityDefinitionMapping.getInputParameterFork(activity, parameter);
        
        if (activityNode == null) {
            this.throwError("No input parameter node: " + parameter);
        }
        
        return activityNode;
	}

    @Override
    public Element getElement() {
        return this.parameter;
    }

    @Override
    public MultiplicityElement getMultiplicityElement() {
        return this.parameter == null? null: this.parameter.multiplicityElement;
    }

    @Override
    public TypedElement getTypedElement() {
        return this.parameter == null? null: this.parameter;
    }

	public List<Element> getModelElements() throws MappingError {
	    ArrayList<Element> elements = new ArrayList<Element>();
	    elements.add(this.getParameter());
	    return elements;
	}
	
	@Override
	public String toString() {
	    return super.toString() + " direction:" + this.parameter.direction;
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " parameter: " + this.parameter);
	}

} // FormalParameterMapping
