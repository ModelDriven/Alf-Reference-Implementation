
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2013 Ivar Jacobson International SA
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.units.TypedElementDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;

import org.modeldriven.alf.uml.*;

import java.util.ArrayList;
import java.util.List;

public class FormalParameterMapping extends TypedElementDefinitionMapping {

    private Parameter parameter = null;
    
    /**
     * A formal parameter maps to a parameter of an activity or an operation
     * with the given name and direction. Its type and multiplicity are mapped
     * as for a typed element definition.
     */
    
    public void mapTo(Parameter parameter) throws MappingError {
        super.mapTo(parameter, parameter);
        parameter.setDirection(this.getFormalParameter().getDirection());
    }
    
    @Override
    public NamedElement getNamedElement() throws MappingError {
        return this.getParameter();
    }

    public Parameter getParameter() throws MappingError {
        if (this.parameter == null) {
            this.parameter = this.create(Parameter.class);
            this.mapTo(this.parameter);
        }

        return this.parameter;
    }
    
	public FormalParameter getFormalParameter() {
		return (FormalParameter) this.getSource();
	}
	
	@Override
	public ActivityNode getAssignedValueSource(String name) throws MappingError {
        ActivityNode activityNode = null;
        Activity activity = null;
        Parameter parameter = null;
        FumlMapping mapping = null;
        
        FormalParameter formalParameter = this.getFormalParameter();
        NamespaceDefinition context = formalParameter.getNamespace();
        Member stub = context.getImpl().getStub();
        mapping = this.fumlMap(
                stub instanceof OperationDefinition? stub: context);
        
        if (mapping instanceof OperationDefinitionMapping) {
            Operation operation = 
                ((OperationDefinitionMapping)mapping).getOperation();
            List<Behavior> methods = operation.getMethod();
            if (methods.size() > 0) {
                activity = (Activity)methods.get(0);
                int i = stub == null?
                    operation.getOwnedParameter().indexOf(this.getParameter()):
                    ((ActivityDefinition)context).getImpl().getFormalParameters().
                                indexOf(formalParameter);
                parameter = activity.getOwnedParameter().get(i);
            } else {
                this.throwError("Operation has no method: " + operation);
            }
        } else if (mapping instanceof ActivityDefinitionMapping) {
            activity = (Activity)((ActivityDefinitionMapping)mapping).getBehavior();
            parameter = this.getParameter();        
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
        return this.parameter;
    }

    @Override
    public TypedElement getTypedElement() {
        return this.parameter;
    }

	public List<Element> getModelElements() throws MappingError {
	    ArrayList<Element> elements = new ArrayList<Element>();
	    elements.add(this.getParameter());
	    return elements;
	}
	
	@Override
	public String toString() {
	    return super.toString() + " direction:" + this.parameter.getDirection();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    System.out.println(prefix + " parameter: " + this.parameter);
	}

} // FormalParameterMapping
