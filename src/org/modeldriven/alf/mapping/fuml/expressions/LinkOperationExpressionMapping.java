
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.InvocationExpressionMapping;

import org.modeldriven.alf.syntax.expressions.LinkOperationExpression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.IntermediateActions.ClearAssociationAction;
import fUML.Syntax.Actions.IntermediateActions.CreateLinkAction;
import fUML.Syntax.Actions.IntermediateActions.DestroyLinkAction;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

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
    public Action mapAction() {
        LinkOperationExpression expression = this.getLinkOperationExpression();
        return expression.getIsClear()? new ClearAssociationAction():
               expression.getIsCreation()? new CreateLinkAction():
                   new DestroyLinkAction();
    }

    @Override
    public void mapTargetTo(Action action) {
        // TODO Auto-generated method stub
        
    }

    public LinkOperationExpression getLinkOperationExpression() {
		return (LinkOperationExpression) this.getSource();
	}

} // LinkOperationExpressionMapping
