package org.modeldriven.alf.uml;

import java.util.Collection;

public interface ClassifierTemplateParameter extends TemplateParameter {

    public Collection<Classifier> getConstrainingClassifier();

}
