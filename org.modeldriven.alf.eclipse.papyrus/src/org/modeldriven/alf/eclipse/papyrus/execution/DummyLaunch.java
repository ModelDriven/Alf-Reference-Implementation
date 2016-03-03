/*******************************************************************************
 * Copyright 2016 Data Access Technologies (Model Driven Solutions)
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
	}

	@Override
	public boolean canTerminate() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public void terminate() throws DebugException {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		return null;
	}

	@Override
	public Object[] getChildren() {
		return null;
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return null;
	}

	@Override
	public IProcess[] getProcesses() {
		return null;
	}

	@Override
	public IDebugTarget[] getDebugTargets() {
		return null;
	}

	@Override
	public void addDebugTarget(IDebugTarget target) {
	}

	@Override
	public void removeDebugTarget(IDebugTarget target) {
	}

	@Override
	public void addProcess(IProcess process) {
	}

	@Override
	public void removeProcess(IProcess process) {
	}

	@Override
	public ISourceLocator getSourceLocator() {
		return null;
	}

	@Override
	public void setSourceLocator(ISourceLocator sourceLocator) {
	}

	@Override
	public String getLaunchMode() {
		return ILaunchManager.RUN_MODE;
	}

	@Override
	public ILaunchConfiguration getLaunchConfiguration() {
		return null;
	}

	@Override
	public void setAttribute(String key, String value) {
	}

	@Override
	public String getAttribute(String key) {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

}
