/*******************************************************************************
 * Copyright 2013 Ivar Jacobson International SA
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License
 * (GPL) version 3 that accompanies this distribution and is available at     
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.execution;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.ISourceLocator;

public class DummyLaunch implements ILaunch {

	public DummyLaunch() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canTerminate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void terminate() throws DebugException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDebugTarget getDebugTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProcess[] getProcesses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDebugTarget[] getDebugTargets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDebugTarget(IDebugTarget target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDebugTarget(IDebugTarget target) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addProcess(IProcess process) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeProcess(IProcess process) {
		// TODO Auto-generated method stub

	}

	@Override
	public ISourceLocator getSourceLocator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSourceLocator(ISourceLocator sourceLocator) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLaunchMode() {
		return ILaunchManager.RUN_MODE;
	}

	@Override
	public ILaunchConfiguration getLaunchConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String key, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAttribute(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}

}
