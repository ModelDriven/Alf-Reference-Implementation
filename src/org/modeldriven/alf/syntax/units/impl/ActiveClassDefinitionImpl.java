
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.units.*;

/**
 * The definition of an active class.
 **/

public class ActiveClassDefinitionImpl extends ClassDefinitionImpl {

    private ActivityDefinition classifierBehavior = null;

	public ActiveClassDefinitionImpl(ActiveClassDefinition self) {
		super(self);
	}

    @Override
	public ActiveClassDefinition getSelf() {
		return (ActiveClassDefinition) this.self;
	}
    
    public ActivityDefinition getClassifierBehavior() {
        return this.classifierBehavior;
    }

    public void setClassifierBehavior(ActivityDefinition classifierBehavior) {
        this.classifierBehavior = classifierBehavior;
    }

    /*
     * Helper Methods
     */

	/**
	 * Returns true if the given unit definition matches this active class
	 * definition considered as a class definition and the subunit is for an
	 * active class definition.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof ActiveClassDefinition &&
		    super.matchForStub(unit);
	} // matchForStub

	@Override
    public boolean isActive() {
        return true;
    }

} // ActiveClassDefinitionImpl
