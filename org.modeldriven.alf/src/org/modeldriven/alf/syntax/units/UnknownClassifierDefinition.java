/*******************************************************************************
 *  Copyright 2019 Model Driven Solutions, Inc.
 *  All rights reserved worldwide. This program and the accompanying materials
 *  are made available for use under the terms of the GNU General Public License 
 *  (GPL) version 3 that accompanies this distribution and is available at 
 *  http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 *  contact Model Driven Solutions.
 *******************************************************************************/
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
