package org.modeldriven.alf.uml;

public interface MultiplicityElement extends Element {
    
    public boolean getIsOrdered();
    public boolean getIsUnique();
    public Integer getLower();
    public Integer getUpper();

}
