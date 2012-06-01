
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
import org.modeldriven.alf.mapping.fuml.common.AssignedSourceMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.EnumerationLiteralNameMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.NameExpression;
import org.modeldriven.alf.syntax.expressions.PropertyAccessExpression;

import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

public class NameExpressionMapping extends ExpressionMapping {
    
    private ActivityNode activityNode = null;
    private ValueSpecificationAction action = null;
    private PropertyAccessExpressionMapping propertyAccessMapping = null;

    /**
     * 1. A name expression maps to an activity graph depending on the kind of
     * name referenced.
     * 
     * 2. A name expression for a local name or parameter name is mapped to an
     * object flow. The source of the object flow is given by the assigned
     * source for the name before the name expression. The target of the object
     * flow is determined by the context of the use of the name expression. The
     * assigned source of the name effectively also acts as the result source
     * element for the expression. Note that, if this source is never connected
     * (for example, if the name expression is used by itself as an expression
     * statement), there can be no object flow and the name expression will
     * actually not map to anything (since it will have no effect). If there is
     * a structured activity node that owns (directly or indirectly) both the
     * source and target of the object flow, then the most deeply nested such
     * node owns the object flow. Otherwise it is owned by the enclosing
     * activity.
     * 
     * 3. A name expression for an enumeration literal name is mapped to a value
     * specification action whose value is given by an instance literal
     * specifying the given enumeration literal. The result pin of the value
     * specification action is the result source element for the expression.
     * 
     * 4. A name expression for a name that disambiguates to a feature reference
     * is mapped as a property access expression consisting of that feature
     * reference.
     */

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
                FumlMapping mapping = this.fumlMap(assignment);
                if (!(mapping instanceof AssignedSourceMapping)) {
                    this.throwError("Error mapping assigned source: " + 
                            mapping.getErrorMessage());
                } else {
                    this.activityNode = 
                        ((AssignedSourceMapping)mapping).getActivityNode();
                    if (this.activityNode == null) {
                        this.throwError("Invalid assigned source: " + assignment);
                    } else if (assignment.getImpl().getIsParallelLocalName()) {
                        // If the assignment is for an @parallel local name
                        // within a for statement, then the assigned source node 
                        // must be a fork node attached to an output expansion 
                        // node for the expansion region mapped from the for
                        // statement. Get the expansion node as the result source.
                        this.activityNode = this.activityNode.incoming.get(0).source;
                    }
                }
            } else if (enumerationLiteralReference != null) {
                FumlMapping mapping = this.fumlMap(enumerationLiteralReference);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (mapping instanceof EnumerationLiteralNameMapping) {
                    this.action = this.graph.addDataValueSpecificationAction(
                            ((EnumerationLiteralNameMapping)mapping).
                            getEnumerationLiteral());
                    this.activityNode = this.action.result;
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
                            mapping.getErrorMessage());
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
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getResultSource();
        if (this.propertyAccessMapping != null) {
            return this.propertyAccessMapping.getGraph();
        } else {
            return super.getGraph();	
        }
    }
    
    @Override
    public ActivityNode getObjectSource() throws MappingError {
        PropertyAccessExpression propertyAccess = 
            this.getNameExpression().getPropertyAccess();
        if (propertyAccess == null) {
            return null;
        } else {
            Mapping mapping = propertyAccess.getImpl().getMapping();
            return mapping instanceof ExpressionMapping?
                    ((ExpressionMapping)mapping).getObjectSource(): null;
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
