package org.modeldriven.alf.uml;

public interface Operation extends BehavioralFeature {

    public Type getType();
    public Integer getLower();
    public Integer getUpper();
    
    // NOTE: These operations are necessary because there is no standard way
    // in the UML abstract syntax to access stereotype applications.
    public boolean isConstructor(); // i.e, has <<Create>> stereotype applied
    public boolean isDestructor(); // i.e., has <<Destroy>> stereotype applied

}
