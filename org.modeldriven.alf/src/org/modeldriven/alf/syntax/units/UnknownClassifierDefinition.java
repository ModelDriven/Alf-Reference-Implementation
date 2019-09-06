package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.units.impl.UnknownClassifierDefinitionImpl;

/**
 * A placeholder for classifiers that are not recognized.
 */

public class UnknownClassifierDefinition extends ClassifierDefinition {
    public UnknownClassifierDefinition(Parser parser) {
        this.init(parser);
        this.impl = new UnknownClassifierDefinitionImpl(this);
    }
    
    @Override
    protected boolean getInfoFromNextToken() {
        return false;
    }
}
