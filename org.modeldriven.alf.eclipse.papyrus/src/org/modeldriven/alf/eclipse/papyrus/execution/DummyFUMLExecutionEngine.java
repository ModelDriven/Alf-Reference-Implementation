/*******************************************************************************
 * Copyright 2013 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.execution;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Resume_Request;
import org.eclipse.papyrus.moka.communication.request.isuspendresume.Suspend_Request;
import org.eclipse.papyrus.moka.communication.request.iterminate.Terminate_Request;
import org.eclipse.papyrus.moka.debug.MokaBreakpoint;
import org.eclipse.papyrus.moka.debug.MokaDebugTarget;
import org.eclipse.papyrus.moka.debug.MokaThread;
import org.eclipse.papyrus.moka.fuml.debug.ControlDelegate;

public class DummyFUMLExecutionEngine extends org.eclipse.papyrus.moka.fuml.FUMLExecutionEngine {
	
	protected ControlDelegate controlDelegate = null;

	public DummyFUMLExecutionEngine() throws CoreException {
		new DebugPlugin();
		this.debugTarget = new MokaDebugTarget(new DummyLaunch(), null);
		this.controlDelegate = new ControlDelegate(this);
	}

	@Override
	public ControlDelegate getControlDelegate() {
		return this.controlDelegate;
	}

	@Override
	public MokaThread[] getThreads() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeArguments(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addBreakpoint(MokaBreakpoint breakpoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeBreakpoint(MokaBreakpoint breakpoint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume(Resume_Request request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void suspend(Suspend_Request request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminate(Terminate_Request request) {
		// TODO Auto-generated method stub

	}

	@Override
	public IStackFrame[] getStackFrames(IThread thread) {
		// TODO Auto-generated method stub
		return null;
	}

}
