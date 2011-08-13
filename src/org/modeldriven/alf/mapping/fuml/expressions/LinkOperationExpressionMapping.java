
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
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Parameter;

import java.util.ArrayList;
import java.util.List;

public class LinkOperationExpressionMapping extends InvocationExpressionMapping {

	public LinkOperationExpressionMapping() {
		this
				.setErrorMessage("LinkOperationExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public LinkOperationExpression getLinkOperationExpression() {
		return (LinkOperationExpression) this.getSource();
	}

    @Override
    public List<Parameter> getParameters(Action action) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Action mapAction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void mapTargetTo(Action action) {
        // TODO Auto-generated method stub
        
    }

} // LinkOperationExpressionMapping
