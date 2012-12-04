package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Association extends Classifier implements
		org.modeldriven.alf.uml.Association {
	public Association() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createAssociation());
	}

	public Association(org.eclipse.uml2.uml.Association base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Association getBase() {
		return (org.eclipse.uml2.uml.Association) this.base;
	}

	public boolean getIsDerived() {
		return this.getBase().getIsDerived();
	}

	public void setIsDerived(boolean isDerived) {
		this.getBase().setIsDerived(isDerived);
	}

	public List< org.modeldriven.alf.uml.Property> getOwnedEnd
() {
		List< org.modeldriven.alf.uml.Property> list = new ArrayList< org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property
 element: this.getBase().getOwnedEnd
s()) {
			list.add( new Property(element)
);
		}
		return list;
	}

	public void addOwnedEnd
( org.modeldriven.alf.uml.Property ownedEnd) {
		this.getBase().getOwnedEnd
s.add( ownedEnd == null? null: ((Property)ownedEnd).getBase()
);
	}

	public List< org.modeldriven.alf.uml.Type> getEndType
() {
		List< org.modeldriven.alf.uml.Type> list = new ArrayList< org.modeldriven.alf.uml.Type>();
		for (org.eclipse.uml2.uml.Type
 element: this.getBase().getEndType
s()) {
			list.add( new Type(element)
);
		}
		return list;
	}

	public List< org.modeldriven.alf.uml.Property> getMemberEnd
() {
		List< org.modeldriven.alf.uml.Property> list = new ArrayList< org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property
 element: this.getBase().getMemberEnd
s()) {
			list.add( new Property(element)
);
		}
		return list;
	}

	public void addMemberEnd
( org.modeldriven.alf.uml.Property memberEnd) {
		this.getBase().getMemberEnd
s.add( memberEnd == null? null: ((Property)memberEnd).getBase()
);
	}

	public List< org.modeldriven.alf.uml.Property> getNavigableOwnedEnd
() {
		List< org.modeldriven.alf.uml.Property> list = new ArrayList< org.modeldriven.alf.uml.Property>();
		for (org.eclipse.uml2.uml.Property
 element: this.getBase().getNavigableOwnedEnd
s()) {
			list.add( new Property(element)
);
		}
		return list;
	}

	public void addNavigableOwnedEnd
( org.modeldriven.alf.uml.Property navigableOwnedEnd) {
		this.getBase().getNavigableOwnedEnd
s.add( navigableOwnedEnd == null? null: ((Property)navigableOwnedEnd).getBase()
);
	}

}
