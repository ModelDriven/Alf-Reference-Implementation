/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.library.channel;

import org.modeldriven.alf.eclipse.papyrus.library.channel.TextOutputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.library.common.Status;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;

public class StandardOutputChannelObject extends TextOutputChannelObject {

	private boolean opened = true;

	@Override
	public String getName() {
		return "StandardOutput";
	}

	@Override
	public void open(Status errorStatus) {
		this.opened = true;
	}

	@Override
	public void close(Status errorStatus) {
		this.opened = false;
	}

	@Override
	public boolean isOpen() {
		return this.opened;
	}
	
	@Override
	public boolean isFull() {
		return false;
	}

	@Override
	public void write(Value value, Status errorStatus) {
		this.writeString(value.toString(), errorStatus);
	}

	@Override
	public void writeString(String value, Status errorStatus) {
		if (this.isOpen()) {
			System.out.print(value);
		} else {
			errorStatus.setStatus("StandardOutputChannel", -1, "Not open");
		}
	}

	@Override
	public void writeNewLine(Status errorStatus) {
		if (this.isOpen()) {
			System.out.println();
		} else {
			errorStatus.setStatus("StandardOutputChannel", -1, "Not open");
		}
	}

	@Override
	public Value new_() {
		return (Value)new StandardOutputChannelObject();
	}

} // StandardOutputChannelObject
