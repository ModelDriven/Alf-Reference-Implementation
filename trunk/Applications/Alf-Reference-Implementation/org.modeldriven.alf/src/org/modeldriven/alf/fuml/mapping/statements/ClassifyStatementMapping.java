
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.statements.StatementMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.ClassifyStatement;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.ForkNode;
import org.modeldriven.alf.uml.ReclassifyObjectAction;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.StartObjectBehaviorAction;

import java.util.ArrayList;
import java.util.Collection;

public class ClassifyStatementMapping extends StatementMapping {
    
    private ReclassifyObjectAction reclassifyAction = null;
    
    /**
     * A classify statement maps to a structured activity node containing a
     * reclassify object action and the mapping of the target object expression,
     * the result source element of which is connected by an object flow to the
     * object input pin of the reclassify object action. The from classes for
     * the classify statement are the old classifiers for the reclassify object
     * action and the to classes are the new classifiers. If the classify
     * statement is reclassify all, then the reclassify object action has
     * isReplaceAll=true.
     */
    // If any of the new classifiers are active, add a start behavior action to
    // start the new classifier behaviors.
    
    private Collection<Classifier> mapClassifiers(
            Collection<ElementReference> references) throws MappingError {
        Collection<Classifier> classifiers = new ArrayList<Classifier>();
        for (ElementReference reference: references) {
            FumlMapping mapping = this.fumlMap(reference);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                this.throwError("Error mapping classifier " + 
                        reference.getImpl().getName() + ": " + 
                        mapping.getErrorMessage());
            } else {
                classifiers.add(
                        ((ClassifierDefinitionMapping)mapping).getClassifier());
            }
        }
        return classifiers;
    }
    
    public void map() throws MappingError {
        super.map();
        
        ClassifyStatement statement = this.getClassifyStatement();
        
        FumlMapping mapping = this.fumlMap(statement.getExpression());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping expression: " + mapping.getErrorMessage());
        } else {
            ActivityGraph subgraph = this.createActivityGraph();
            
            ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
            subgraph.addAll(expressionMapping.getGraph());
            
            this.reclassifyAction = 
                    subgraph.addReclassifyObjectAction(
                            expressionMapping.getType(), 
                            this.mapClassifiers(statement.getFromClass()), 
                            this.mapClassifiers(statement.getToClass()),
                            statement.getIsReclassifyAll());
            
            ActivityNode resultSource = expressionMapping.getResultSource();
            subgraph.addObjectFlow(
                    expressionMapping.getResultSource(), 
                    this.reclassifyAction.getObject());
            
            for (Classifier classifier: this.reclassifyAction.getNewClassifier()) {
                if (((Class_)classifier).getIsActive()) {
                    ForkNode fork = 
                            subgraph.addForkNode("Fork(" + resultSource.getName() + ")");                
                    subgraph.addObjectFlow(resultSource, fork);

                    StartObjectBehaviorAction startAction = 
                            subgraph.addStartObjectBehaviorAction((Class_)classifier);                
                    subgraph.addControlFlow(this.reclassifyAction, startAction);
                    subgraph.addObjectFlow(fork, startAction.getObject());
                    
                    for (Classifier parent: classifier.allParents()) {
                        if (parent instanceof Class_ && 
                                ((Class_) parent).getClassifierBehavior() != null) {
                            startAction = this.graph.addStartObjectBehaviorAction(
                                    (Class_)parent);         
                            subgraph.addControlFlow(this.reclassifyAction, startAction);
                            subgraph.addObjectFlow(fork, startAction.getObject());
                        }
                    }
                    
                    break;
                }
            }
            
            this.addToNode(subgraph.getModelElements());            
        }
    }

	public ClassifyStatement getClassifyStatement() {
		return (ClassifyStatement) this.getSource();
	}
	
	public String toString() {
	    return super.toString() + 
	            (this.reclassifyAction == null? "": 
	                " isReplaceAll:" + this.reclassifyAction.getIsReplaceAll());
	}
	
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.reclassifyAction != null) {
            Collection<Classifier> oldClassifiers = 
                    this.reclassifyAction.getOldClassifier();
            if (!oldClassifiers.isEmpty()) {
                System.out.println(prefix + " oldClassifier:");
                for (Classifier oldClassifier: oldClassifiers) {
                    System.out.println(prefix + "  " + oldClassifier);
                }
            }
            Collection<Classifier> newClassifiers = 
                    this.reclassifyAction.getNewClassifier();
            if (!newClassifiers.isEmpty()) {
                System.out.println(prefix + " newClassifier:");
                for (Classifier newClassifier: newClassifiers) {
                    System.out.println(prefix + "  " + newClassifier);
                }
            }
	    }
	    
        ClassifyStatement statement = this.getClassifyStatement();
        
	    Expression expression = statement.getExpression();
	    if (expression != null) {
	        System.out.println(prefix + " expression:");
	        Mapping mapping = expression.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // ClassifyStatementMapping
