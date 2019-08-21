package uk.ac.ncl.safecap.sim3.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import safecap.Project;
import uk.ac.ncl.safecap.scitus.common.SimulationException;

public class Sim3Job extends org.eclipse.core.runtime.jobs.Job {
	private final Project project;

	public Sim3Job(Project project) {
		super("S3 Simulation");
		this.project = project;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			Sim3Provider.simulation(project, monitor);
		} catch (final SimulationException e) {
			final Status s = new Status(IStatus.ERROR, "uk.ac.ncl.safecap.capacity", -1, "Simulation aborted", e);
			return s;
		}
		return Status.OK_STATUS;
	}

}
