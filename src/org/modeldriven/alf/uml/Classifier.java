package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Feature;
import org.modeldriven.alf.uml.Generalization;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.Property;
import org.modeldriven.alf.uml.Type;

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
