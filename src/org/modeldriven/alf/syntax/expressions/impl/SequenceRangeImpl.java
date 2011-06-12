
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A specification of the elements of a sequence as a range of integers.
 **/

public class SequenceRangeImpl extends SequenceElementsImpl {

	private Expression rangeLower = null;
	private Expression rangeUpper = null;

	public SequenceRangeImpl(SequenceRange self) {
		super(self);
	}

	@Override
	public SequenceRange getSelf() {
		return (SequenceRange) this.self;
	}

	public Expression getRangeLower() {
		return this.rangeLower;
	}

	public void setRangeLower(Expression rangeLower) {
		this.rangeLower = rangeLower;
	}

	public Expression getRangeUpper() {
		return this.rangeUpper;
	}

	public void setRangeUpper(Expression rangeUpper) {
		this.rangeUpper = rangeUpper;
	}

	/**
	 * The multiplicity lower bound of a sequence range is 0.
	 **/
	@Override
	protected Integer deriveLower() {
	    return 0;
	}
	
	/**
	 * The multiplicity upper bound of a sequence range is * (since it is not
	 * possible, in general, to statically determine a more constrained upper
	 * bound).
	 **/
	@Override
	protected Integer deriveUpper() {
	    return -1;
	}
	
	/*
	 * Derivations
	 */
	
	public boolean sequenceRangeLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean sequenceRangeUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Helper Methods
	 */

    @Override
    public Map<String, AssignedSource> getAssignmentAfterMap(
            Map<String, AssignedSource> assignmentsBefore) {
        SequenceRange self = this.getSelf();
        Expression rangeLower = self.getRangeLower();
        Expression rangeUpper = self.getRangeUpper();
        Map<String, AssignedSource> assignmentsAfter = 
            new HashMap<String, AssignedSource>(assignmentsBefore);
        if (rangeLower != null) {
            rangeLower.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter.putAll(rangeLower.getImpl().getAssignmentAfterMap());
        }
        if (rangeUpper != null) {
            rangeUpper.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter.putAll(rangeUpper.getImpl().getAssignmentAfterMap());
        }
        return assignmentsAfter;
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        SequenceRange self = this.getSelf();
        Expression rangeLower = self.getRangeLower();
        Expression rangeUpper = self.getRangeUpper();
        if (rangeLower != null) {
            rangeLower.getImpl().setCurrentScope(currentScope);
        }
        if (rangeUpper != null) {
            rangeUpper.getImpl().setCurrentScope(currentScope);
        }
    }

} // SequenceRangeImpl
