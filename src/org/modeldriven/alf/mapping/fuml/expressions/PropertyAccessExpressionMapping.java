
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.PropertyDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.PropertyAccessExpression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Property;

import java.util.ArrayList;
import java.util.Collection;

public class PropertyAccessExpressionMapping extends ExpressionMapping {

    private Action action = null;
    private ActivityNode resultSource = null;
    private ActivityNode objectSource = null;

    /**
     * 1. A property access expression is mapped as either a single instance
     * property access or a sequence property access.
     * 
     * 2. A single instance property access expression for an attribute is
     * mapped to a read structural feature action for the named structural
     * feature. The result source element of the mapping of the target
     * expression is connected by an object flow to the object input pin of the
     * read structural feature action. The result pin of the action is the
     * result source element for the property access expression.
     * 
     * 3. A sequence property access expression is mapped as an expansion region
     * similarly to a collect expression.
     */
    
    public Action mapAction() throws MappingError {
        
        PropertyAccessExpression propertyAccess = this.getPropertyAccessExpression();
        ElementReference feature = propertyAccess.getFeature();
        Expression expression = propertyAccess.getFeatureReference().getExpression();
        
        Action action = null;
        
        FumlMapping mapping = this.fumlMap(feature);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (!(mapping instanceof PropertyDefinitionMapping)) {
            this.throwError("Error mapping feature: " + mapping.getErrorMessage());
        } else {
            Property property = 
                ((PropertyDefinitionMapping)mapping).getProperty();
            mapping = this.fumlMap(expression);
            
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping expression: " + 
                        mapping.getErrorMessage());
            } else {
                ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
                this.graph.addAll(expressionMapping.getGraph());
                
                ReadStructuralFeatureAction readAction =
                    this.graph.addReadStructuralFeatureAction(property);

                ActivityNode expressionResult = 
                    expressionMapping.getResultSource();
                
                // Add a fork node that may be used as the source of the feature
                // expression to avoid recomputing it for inout parameters,
                // increment or decrement expressions and compound assignments.
                this.objectSource = this.graph.addForkNode(
                        "Fork(" + expressionResult.name + ")");
                this.graph.addObjectFlow(expressionResult, this.objectSource);
                
                if (!propertyAccess.getImpl().isSequencePropertyAccess()) {
                    action = readAction;
                    this.graph.addObjectFlow(this.objectSource, readAction.object);
                    this.resultSource = readAction.result;
                    
                } else {
                    Collection<Element> elements = new ArrayList<Element>();
                    elements.add(readAction);
                    
                    ExpansionRegion region = this.graph.addExpansionRegion(
                            "Collect(" + readAction.name + ")", 
                            ExpansionKind.parallel, 
                            elements, 
                            this.objectSource, 
                            readAction.object, 
                            readAction.result);

                    action = region;
                    this.resultSource = region.outputElement.get(0);                    
                }
            }
        }
        return action;
    }

    @Override
    public ActivityNode getObjectSource() throws MappingError {
        this.getAction();
        return this.objectSource;
    }

    @Override
    public ActivityNode getResultSource() throws MappingError {
        this.getAction();
        return this.resultSource;
    }
    
    public Action getAction() throws MappingError {
        if (this.action == null) {
            this.action = this.mapAction();
            this.mapTo(this.action);
        }
        return this.action;
    }
    
    @Override
    public Element getElement() {
        return this.action;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
    }

	public PropertyAccessExpression getPropertyAccessExpression() {
		return (PropertyAccessExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.action != null) {
	        System.out.println(prefix + " action: " + this.action);
	        
	        ReadStructuralFeatureAction readAction = null;
	        if (this.action instanceof ReadStructuralFeatureAction) {
	            readAction = (ReadStructuralFeatureAction)this.action;
	        } else if (this.action instanceof ExpansionRegion) {
	            for (ActivityNode node: ((ExpansionRegion)this.action).node) {
	                if (node instanceof ReadStructuralFeatureAction) {
	                    readAction = (ReadStructuralFeatureAction)node;
	                    break;
	                }
	            }
	        }
	        
	        if (readAction != null) {
	            System.out.println(prefix + " structuralFeature: " + 
	                    readAction.structuralFeature);
	        }
	    }
	    
	    if (this.objectSource != null) {
	        System.out.println(prefix + " objectSource: " + this.objectSource);
	    }
	    
	    PropertyAccessExpression expression = this.getPropertyAccessExpression();
	    Expression primary = expression.getFeatureReference().getExpression();
	    if (primary != null) {
	        System.out.println(prefix + " expression:");
	        Mapping mapping = primary.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // PropertyAccessExpressionMapping
