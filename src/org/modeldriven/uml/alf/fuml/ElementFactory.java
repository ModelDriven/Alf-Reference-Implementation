package org.modeldriven.uml.alf.fuml;

import org.modeldriven.alf.uml.Element;

public class ElementFactory extends org.modeldriven.alf.uml.ElementFactory {

    @SuppressWarnings("unchecked")
    public <T extends Element> T newInstance(Class<T> class_) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return (T)Class.forName("org.modeldriven.uml.alf.fuml" + class_.getSimpleName()).newInstance();
    }

}
