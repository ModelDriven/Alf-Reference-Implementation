/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;

public class MissingMemberImpl extends MemberImpl {

    public MissingMemberImpl(Member self) {
        super(self);
    }

    @Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
        return false;
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }

}
