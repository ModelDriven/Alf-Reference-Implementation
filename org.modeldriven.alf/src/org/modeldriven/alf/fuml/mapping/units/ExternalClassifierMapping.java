/*******************************************************************************
 * Copyright $(date) Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.NamedElement;

public class ExternalClassifierMapping extends ClassifierDefinitionMapping {
    
    private Classifier classifier = null;
    
    public ExternalClassifierMapping(ExternalElementReference reference) {
        this.classifier = (Classifier)reference.getElement();
    }
    
    @Override
    public void mapTo(Classifier classifier) {
    }

    @Override
    public Classifier mapClassifier() {
        return this.classifier;
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
    }

}
