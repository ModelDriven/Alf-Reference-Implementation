
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ActivityDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.EnumerationLiteralNameMapping;
import org.modeldriven.alf.mapping.fuml.units.FormalParameterMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.NameExpression;
import org.modeldriven.alf.syntax.expressions.PropertyAccessExpression;
import org.modeldriven.alf.syntax.units.ActivityDefinition;
import org.modeldriven.alf.syntax.units.FormalParameter;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityParameterNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.InstanceValue;
import fUML.Syntax.Classes.Kernel.Parameter;

import java.util.ArrayList;
import java.util.List;

public class NameExpressionMapping extends ExpressionMapping {
    
    private ActivityNode activityNode = null;
    private ValueSpecificationAction action = null;
    private PropertyAccessExpressionMapping propertyAccessMapping = null;

    public FormalParameter getFormalParameter() {
        return null;
    }
    
    public ActivityNode getResultSource() throws MappingError {
        if (this.activityNode == null) {
            this.mapTo(null);

            NameExpression nameExpression = this.getNameExpression();
            AssignedSource assignment = 
                nameExpression.getAssignment();
            ElementReference enumerationLiteralReference = 
                nameExpression.getEnumerationLiteral();
            PropertyAccessExpression propertyAccess = 
                nameExpression.getPropertyAccess();

            if (assignment != null) {
                SyntaxElement source = assignment.getSource();
                FumlMapping mapping = this.fumlMap(source);
                if (mapping instanceof ExpressionMapping) {
                    this.activityNode = 
                        ((ExpressionMapping)mapping).getResultSource();
                } else if (mapping instanceof FormalParameterMapping) {
                    Parameter parameter = 
                        ((FormalParameterMapping)mapping).getParameter();

                    NamespaceDefinition context = 
                        this.getNameExpression().getImpl().getCurrentScope();
                    if (!(context instanceof ActivityDefinition)) {
                        this.throwError("Formal parameter not for an activity: " + 
                                source);
                    } else {
                        ActivityDefinitionMapping activityMapping = 
                            (ActivityDefinitionMapping)this.map(context);
                        ActivityParameterNode parameterNode = 
                            activityMapping.getParameterNode(parameter);

                        if (parameterNode == null) {
                            this.throwError("Activity does not contain parameter: " + 
                                    parameter);
                        } else if (parameterNode.outgoing.size() == 0) {
                            this.throwError("Reference to output parameter: " + 
                                    parameter);
                        } else {
                            this.activityNode = 
                                parameterNode.outgoing.getValue(0).target;
                        }
                    }
                } else {
                    this.setErrorMessage
                        ("Assigned source mapping not yet implemented:" + source);
                }
            } else if (enumerationLiteralReference != null) {
                FumlMapping mapping = this.fumlMap(enumerationLiteralReference);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (mapping instanceof EnumerationLiteralNameMapping) {
                    InstanceValue value = new InstanceValue();
                    value.setInstance(((EnumerationLiteralNameMapping)mapping).
                            getEnumerationLiteral());
                    value.setName("Value(" + value.instance.name + ")");
                    
                    this.action = new ValueSpecificationAction();
                    this.action.setName(value.name);
                    this.action.setValue(value);
                    

                    OutputPin result = new OutputPin();
                    result.setName(action.name+".result");
                    result.setType(value.type);
                    result.setLower(1);
                    result.setUpper(1);
                    action.setResult(result);
                    
                    this.activityNode = result;
                } else {
                    this.throwError("Error mapping enumeration literal:" + 
                            enumerationLiteralReference);
                }
            } else if (propertyAccess != null) {
                FumlMapping mapping = this.fumlMap(propertyAccess);
                if (mapping instanceof PropertyAccessExpressionMapping) {
                    this.propertyAccessMapping = 
                        (PropertyAccessExpressionMapping)mapping;
                    this.activityNode = 
                        this.propertyAccessMapping.getResultSource();
                } else {
                    this.throwError("Error mapping property access expression:" +
                            propertyAccess);
                }
            } else {
               this.throwError("Name expression has no referent.");
            }
        }

        return this.activityNode;
    }
    
    @Override
    public Element getElement() {
        return this.propertyAccessMapping != null? this.propertyAccessMapping.getElement():
               this.action != null? this.action: this.activityNode;
    }
    
    public List<Element> getModelElements() throws MappingError {
        this.getResultSource();
        if (this.propertyAccessMapping != null) {
            return this.propertyAccessMapping.getModelElements();
        } else {
            List<Element> elements = new ArrayList<Element>();
            if (this.action != null) {
                elements.add(this.action);
            }
            return elements;	
        }
    }

	public NameExpression getNameExpression() {
		return (NameExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    if (this.propertyAccessMapping != null) {
	        System.out.println(prefix + " propertyAccess:");
	        propertyAccessMapping.printChild(prefix);
	    } else if (this.action != null) {
	        System.out.println(prefix + " enumerationLiteral: " + this.action);
	    } else {
            System.out.println(prefix + " activityNode: " + this.activityNode);
	    }
	}

} // NameExpressionMapping
