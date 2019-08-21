package uk.ac.ncl.safecap.capacity.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import safecap.Project;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

public class SimulationJob extends org.eclipse.core.runtime.jobs.Job {
	private final Project project;

	public SimulationJob(Project project) {
		super("Simulation");
		this.project = project;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			SimulationProvider.simulation(project, monitor);
		} catch (final SimulationException e) {
			final Status s = new Status(IStatus.ERROR, "uk.ac.ncl.safecap.capacity", -1, "Simulation aborted", e);
			return s;
		}
		return Status.OK_STATUS;
	}

}
