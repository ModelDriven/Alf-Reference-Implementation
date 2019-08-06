package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.expressions.impl.ExpressionPlaceholderImpl;

public class ExpressionPlaceholder extends Expression {
    public ExpressionPlaceholder() {
        this.impl = new ExpressionPlaceholderImpl(this);
    }
}
