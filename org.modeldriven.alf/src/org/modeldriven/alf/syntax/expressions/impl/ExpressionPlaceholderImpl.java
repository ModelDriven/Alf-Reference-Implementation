package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;

public class ExpressionPlaceholderImpl extends ExpressionImpl {

    public ExpressionPlaceholderImpl(Expression self) {
        super(self);
    }

    @Override
    protected Integer deriveUpper() {
        return null;
    }

    @Override
    protected Integer deriveLower() {
        return null;
    }

    @Override
    protected ElementReference deriveType() {
        return null;
    }

}
