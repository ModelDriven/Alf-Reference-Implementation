package org.omg.uml;

import java.util.ArrayList;
import java.util.Collection;

public class TemplateBinding extends Element {
    
    public TemplateSignature getSignature() {
        return null;
    }
    
    public Collection<TemplateParameterSubstitution> getParameterSubstitution() {
        return new ArrayList<TemplateParameterSubstitution>();
    }

}
