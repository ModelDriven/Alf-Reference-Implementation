package org.modeldriven.alf.uml;

import java.util.List;

public interface Association extends Classifier {

    public List<Property> getMemberEnd();

}
