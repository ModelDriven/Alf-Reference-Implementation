package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class Association extends Classifier implements
		org.modeldriven.alf.uml.Association {
	public Association() {
		this(new fUML.Syntax.Classes.Kernel.Association());
	}

	public Association(fUML.Syntax.Classes.Kernel.Association base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Association getBase() {
		return (fUML.Syntax.Classes.Kernel.Association) this.base;
	}

	public boolean getIsDerived() {
		return this.getBase().isDerived;
	}

	public void setIsDerived(boolean isDerived) {
		this.getBase().isDerived = isDerived;
	}

	public List<org.modeldriven.alf.uml.Property> getOwnedEnd() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().ownedEnd) {
			list.add(new Property(element));
		}
		return list;
	}

	public void addOwnedEnd(org.modeldriven.alf.uml.Property ownedEnd) {
		this.getBase().addOwnedEnd(((Property) ownedEnd).getBase());
	}

	public List<org.modeldriven.alf.uml.Type> getEndType() {
		List<org.modeldriven.alf.uml.Type> list = new ArrayList<org.modeldriven.alf.uml.Type>();
		for (fUML.Syntax.Classes.Kernel.Type element : this.getBase().endType) {
			list.add(new Type(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Property> getMemberEnd() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().memberEnd) {
			list.add(new Property(element));
		}
		return list;
	}

	public List<org.modeldriven.alf.uml.Property> getNavigableOwnedEnd() {
		List<org.modeldriven.alf.uml.Property> list = new ArrayList<org.modeldriven.alf.uml.Property>();
		for (fUML.Syntax.Classes.Kernel.Property element : this.getBase().navigableOwnedEnd) {
			list.add(new Property(element));
		}
		return list;
	}

	public void addNavigableOwnedEnd(
			org.modeldriven.alf.uml.Property navigableOwnedEnd) {
		this.getBase().addNavigableOwnedEnd(
				((Property) navigableOwnedEnd).getBase());
	}

}
