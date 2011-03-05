
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlockImpl extends SyntaxElementImpl {

	public AcceptBlockImpl(AcceptBlock self) {
		super(self);
	}

	public AcceptBlock getSelf() {
		return (AcceptBlock) this.self;
	}
	
	/**
	 * The signals of an accept block are the referents of the signal names of
	 * the accept block.
	 **/
	public ArrayList<ElementReference> deriveSignal() {
	    ArrayList<ElementReference> signals = new ArrayList<ElementReference>();
	    QualifiedNameList signalNames = this.getSelf().getSignalNames();
	    if (signalNames != null) {
	        for (QualifiedName signalName: signalNames.getName()) {
	            ElementReference signal = signalName.getImpl().getSignalReferent();
	            if (signal != null) {
	                signals.add(signal);
	            }
	        }
	    }
		return signals;
	}
	
	/*
	 * Derivations
	 */

	public boolean acceptBlockSignalDerivation() {
		this.getSelf().getSignal();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * All signal names in an accept block must resolve to signals.
	 **/
	public boolean acceptBlockSignalNames() {
        QualifiedNameList signalNames = this.getSelf().getSignalNames();
        if (signalNames != null) {
            for (QualifiedName signalName: signalNames.getName()) {
                if (signalName.getImpl().getSignalReferent() == null) {
                    return false;
                }
            }
        }
        return true;
	}
	
	/*
	 * Helper Methods
	 */

    public void setCurrentScope(NamespaceDefinition currentScope) {
        AcceptBlock self = this.getSelf();
        QualifiedNameList signalNames = self.getSignalNames();
        if (signalNames != null) {
            for (QualifiedName signalName: signalNames.getName()) {
                signalName.getImpl().setCurrentScope(currentScope);
            }
        }
        Block block = self.getBlock();
        if (block != null) {
            block.getImpl().setCurrentScope(currentScope);
        }
    }

} // AcceptBlockImpl
