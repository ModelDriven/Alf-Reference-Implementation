package org.modeldriven.alf.uml;

public interface Operation extends BehavioralFeature {

    public Type getType();
    public Integer getLower();
    public Integer getUpper();

}
