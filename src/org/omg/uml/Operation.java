package org.omg.uml;

import java.util.ArrayList;
import java.util.List;

public class Operation extends Feature {

    public List<Parameter> getOwnedParameter() {
        return new ArrayList<Parameter>();
    }

    public Type getType() {
        return null;
    }

    public Integer getLower() {
        return null;
    }

    public Integer getUpper() {
        return null;
    }

}
