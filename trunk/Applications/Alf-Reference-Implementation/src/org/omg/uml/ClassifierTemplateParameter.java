package org.omg.uml;

import java.util.ArrayList;
import java.util.Collection;

public class ClassifierTemplateParameter extends TemplateParameter {

    public Collection<Classifier> getConstrainingClassifier() {
        return new ArrayList<Classifier>();
    }

}
