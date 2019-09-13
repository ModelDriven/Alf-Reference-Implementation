/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.moka.library.channel;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.IValue;
import org.modeldriven.alf.eclipse.moka.library.channel.TextOutputChannelObject;
import org.modeldriven.alf.eclipse.moka.library.common.Status;

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
	public void write(IValue value, Status errorStatus) {
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
	public IValue new_() {
		return new StandardOutputChannelObject();
	}

} // StandardOutputChannelObject
