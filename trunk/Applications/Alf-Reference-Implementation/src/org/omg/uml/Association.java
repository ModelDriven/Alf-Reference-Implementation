package org.omg.uml;

import java.util.ArrayList;
import java.util.List;

public class Association extends Classifier {

    public List<Property> getMemberEnd() {
        return new ArrayList<Property>();
    }

}
