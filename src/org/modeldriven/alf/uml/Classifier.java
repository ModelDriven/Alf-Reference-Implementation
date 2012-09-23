package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

public interface Classifier extends Type, Namespace {
	public boolean getIsAbstract();

	public void setIsAbstract(boolean isAbstract);

	public List<Generalization> getGeneralization();

	public void addGeneralization(Generalization generalization);

	public List<Feature> getFeature();

	public List<NamedElement> getInheritedMember();

	public List<Property> getAttribute();

	public List<Classifier> getGeneral();

	public boolean getIsFinalSpecialization();

	public void setIsFinalSpecialization(boolean isFinalSpecialization);

    public Set<Classifier> parents();

    public Set<Classifier> allParents();

    public List<NamedElement> inheritableMembers();

    public boolean conformsTo(Classifier classifier);
}
