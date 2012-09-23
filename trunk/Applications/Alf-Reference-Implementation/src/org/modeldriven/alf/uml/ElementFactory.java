package org.modeldriven.alf.uml;

public abstract class ElementFactory {
    
    public abstract <T extends Element> T newInstance(Class<T> class_) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException;
    
    @SuppressWarnings("unchecked")
    public <T extends Element> T newInstance(T element) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return (T) this.newInstance(element.getClass());
    }

}
