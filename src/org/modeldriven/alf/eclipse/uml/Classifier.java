package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Classifier extends Type implements
		org.modeldriven.alf.uml.Classifier {

	public Classifier(org.eclipse.uml2.uml.Classifier base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Classifier getBase() {
		return (org.eclipse.uml2.uml.Classifier) this.base;
	}

	public boolean getIsAbstract() {
		return this.getBase().isAbstract();
	}

	public void setIsAbstract(boolean isAbstract) {
		this.getBase().setIsAbstract(isAbstract);
	}

	public List<org.modeldriven.alf.uml.Generalization> getGeneralization() {
		List<org.modeldriven.alf.uml.Generalization> list = new ArrayList<org.modeldriven.alf.uml.Generalization>();
		for (org.eclipse.uml2.uml.Generalization element : this.getBase()
				.getGeneralizations()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addGeneralization(
			org.modeldriven.alf.uml.Generalization generalization) {
		this.getBase().getGeneralizations().add(
				generalization == null ? null
						: ((Generalization) generalization).getBase());
	}

	public List<org.modeldriven.alf.uml.Feature> getFeature() {
		List<org.modeldriven.alf.uml.Feature> list = new ArrayList<org.modeldriven.alf.uml.Feature>();
		for (org.eclipse.uml2.uml.Feature element : this.getBase()
				.getFeatures()) {
			list.add(wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getInheritedMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement element : this.getBase()
				.getInheritedMembers()) {
			list.add(wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Property> getAttribute() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property element : this.getBase()
				.getAttributes()) {
			list.add(wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Classifier> getGeneral() {
		List<org.modeldriven.alf.uml.Classifier> list = new ArrayList<org.modeldriven.alf.uml.Classifier>();
		for (org.eclipse.uml2.uml.Classifier element : this.getBase()
				.getGenerals()) {
			list.add(wrap(element));
		}
		return list;
	}

	public boolean getIsFinalSpecialization() {
		return this.getBase().isFinalSpecialization();
	}

	public void setIsFinalSpecialization(boolean isFinalSpecialization) {
		this.getBase().setIsFinalSpecialization(isFinalSpecialization);
	}

	public List<org.modeldriven.alf.uml.NamedElement> getMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement element : this.getBase()
				.getMembers()) {
			list.add(wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.NamedElement> getOwnedMember() {
		List<org.modeldriven.alf.uml.NamedElement> list = new ArrayList<org.modeldriven.alf.uml.NamedElement>();
		for (org.eclipse.uml2.uml.NamedElement element : this.getBase()
				.getOwnedMembers()) {
			list.add(wrap(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.ElementImport> getElementImport() {
		List<org.modeldriven.alf.uml.ElementImport> list = new ArrayList<org.modeldriven.alf.uml.ElementImport>();
		for (org.eclipse.uml2.uml.ElementImport element : this.getBase()
				.getElementImports()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addElementImport(
			org.modeldriven.alf.uml.ElementImport elementImport) {
		this.getBase().getElementImports().add(
				elementImport == null ? null : ((ElementImport) elementImport)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.PackageImport> getPackageImport() {
		List<org.modeldriven.alf.uml.PackageImport> list = new ArrayList<org.modeldriven.alf.uml.PackageImport>();
		for (org.eclipse.uml2.uml.PackageImport element : this.getBase()
				.getPackageImports()) {
			list.add(wrap(element));
		}
		return list;
	}

	public void addPackageImport(
			org.modeldriven.alf.uml.PackageImport packageImport) {
		this.getBase().getPackageImports().add(
				packageImport == null ? null : ((PackageImport) packageImport)
						.getBase());
	}

	public List<org.modeldriven.alf.uml.PackageableElement> getImportedMember() {
		List<org.modeldriven.alf.uml.PackageableElement> list = new ArrayList<org.modeldriven.alf.uml.PackageableElement>();
		for (org.eclipse.uml2.uml.PackageableElement element : this.getBase()
				.getImportedMembers()) {
			list.add(wrap(element));
		}
		return list;
	}

}
