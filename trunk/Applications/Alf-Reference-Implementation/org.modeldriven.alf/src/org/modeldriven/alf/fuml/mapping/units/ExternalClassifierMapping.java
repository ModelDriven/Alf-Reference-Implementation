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
