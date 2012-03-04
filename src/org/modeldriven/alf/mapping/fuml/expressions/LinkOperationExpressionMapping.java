
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
import org.modeldriven.alf.mapping.fuml.expressions.InvocationExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.AssociationDefinitionMapping;

import org.modeldriven.alf.syntax.expressions.LinkOperationExpression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Classes.Kernel.Association;

public class LinkOperationExpressionMapping extends InvocationExpressionMapping {

    /**
     * 1. A link operation expression for the operation createLink maps to a
     * create link action for the named association with isReplaceAll=false for
     * all ends. The value input pin of the end creation data for each end of
     * the association is the target of an object flow from the result source
     * element of the mapping of the corresponding argument expression. If an
     * association end is ordered, then the insertAt input pin for that end is
     * the target of an object flow from the result source element of the
     * mapping of the corresponding index expression (which defaults to * if not
     * given explicitly).
     * 
     * 2. A link operation expression for the operation destroyLink maps to a
     * destroy link action for the named association. The value input pin of the
     * end creation data for each end of the association is the target of an
     * object flow from the result source element of the mapping of the
     * corresponding argument expression. If an association end is unordered,
     * then isDestroyDuplicates=true. If an association end is ordered, then
     * isDestroyDuplicates=false and the insertAt input pin for that end is the
     * target of an object flow from the result source element of the mapping of
     * the corresponding index expression (which defaults to * if not given
     * explicitly).
     * 
     * 3. A link operation expression for the link operation clearAssoc maps to
     * a clear association action for the named association. The object input
     * pin of clear association action is the target of an object flow from the
     * result source element of the the mapping of the argument expression.
     */

    @Override
    public Action mapTarget() throws MappingError {
        LinkOperationExpression expression = this.getLinkOperationExpression();
        Action action = null;
        FumlMapping mapping = this.fumlMap(expression.getReferent());
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (!(mapping instanceof AssociationDefinitionMapping)) {
            this.throwError("Error mapping association: " + mapping.getErrorMessage());
        } else {
            Association association = (Association)
                ((AssociationDefinitionMapping)mapping).getClassifier();
            action = 
                expression.getIsClear()?
                    this.graph.addClearAssociationAction(association):
                expression.getIsCreation()?
                    // TODO: Handle association ends with multiplicity upper bound of 1.
                    this.graph.addCreateLinkAction(association):
                    this.graph.addDestroyLinkAction(association);
        }
        return action;
    }

    public LinkOperationExpression getLinkOperationExpression() {
		return (LinkOperationExpression) this.getSource();
	}

} // LinkOperationExpressionMapping
