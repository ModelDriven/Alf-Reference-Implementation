
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
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
     * Constraints
     */
    
    /**
     * If an active class definition is not abstract, then it must have a
     * classifier behavior.
     **/
    public boolean activeClassDefinitionClassifierBehavior() {
        ActiveClassDefinition self = this.getSelf();
        return self.getIsAbstract() || self.getClassifierBehavior() != null;
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

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ActiveClassDefinition) {
            // Note: The classifier behavior will have been already bound at
            // this point as a namespace owned member.
            ActiveClassDefinition self = this.getSelf();
            ActivityDefinition classifierBehavior = 
                ((ActiveClassDefinition)base).getClassifierBehavior();
            for (Member member: self.getOwnedMember()) {
                if (member.getImpl().getBase() == classifierBehavior) {
                    self.setClassifierBehavior((ActivityDefinition)member);
                }
            }
        }
    }

} // ActiveClassDefinitionImpl
