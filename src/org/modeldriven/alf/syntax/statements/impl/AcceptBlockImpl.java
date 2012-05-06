
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlockImpl extends SyntaxElementImpl {

    private String name = "";
    private Block block = null;
    private QualifiedNameList signalNames = null;
    private Collection<ElementReference> signal = null; // DERIVED

    public AcceptBlockImpl(AcceptBlock self) {
        super(self);
    }

    public AcceptBlock getSelf() {
        return (AcceptBlock) this.self;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public QualifiedNameList getSignalNames() {
        return this.signalNames;
    }

    public void setSignalNames(QualifiedNameList signalNames) {
        this.signalNames = signalNames;
    }

    public Collection<ElementReference> getSignal() {
        if (this.signal == null) {
            this.setSignal(this.deriveSignal());
        }
        return this.signal;
    }

    public void setSignal(Collection<ElementReference> signal) {
        this.signal = signal;
    }

    public void addSignal(ElementReference signal) {
        this.signal.add(signal);
    }

	/**
	 * The signals of an accept block are the referents of the signal names of
	 * the accept block.
	 **/
    protected Collection<ElementReference> deriveSignal() {
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

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof AcceptBlock) {
            AcceptBlock self = this.getSelf();
            AcceptBlock baseBlock = (AcceptBlock)base;
            Block block = baseBlock.getBlock();
            QualifiedNameList signalNames = baseBlock.getSignalNames();
            self.setName(baseBlock.getName());
            if (block != null) {
                self.setBlock((Block)block.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (signalNames != null) {
                self.setSignalNames((QualifiedNameList)signalNames.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // AcceptBlockImpl
