/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class ProfileApplication extends Element implements org.modeldriven.alf.uml.ProfileApplication {
	
	public ProfileApplication() {
		this(UMLFactory.eINSTANCE.createProfileApplication());
	}

	public ProfileApplication(org.eclipse.uml2.uml.ProfileApplication base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.ProfileApplication getBase() {
		return (org.eclipse.uml2.uml.ProfileApplication)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.Profile getAppliedProfile() {
		return (org.modeldriven.alf.uml.Profile)wrap(this.getBase().getAppliedProfile());
	}

	@Override
	public void setAppliedProfile(org.modeldriven.alf.uml.Profile appliedProfile) {
		this.getBase().setAppliedProfile(appliedProfile == null? null:
				(org.eclipse.uml2.uml.Profile)((Profile)appliedProfile).getBase());
	}

}
