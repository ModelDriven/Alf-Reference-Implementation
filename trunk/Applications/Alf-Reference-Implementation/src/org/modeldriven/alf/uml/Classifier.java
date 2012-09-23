package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

import org.modeldriven.uml.Classifier;
import org.modeldriven.uml.Feature;
import org.modeldriven.uml.Generalization;
import org.modeldriven.uml.NamedElement;
import org.modeldriven.uml.Namespace;
import org.modeldriven.uml.Property;
import org.modeldriven.uml.Type;

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
