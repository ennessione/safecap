package uk.ac.ncl.safecap.scitus.base;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;

import safecap.Project;
import uk.ac.ncl.safecap.scitus.common.SimulationException;
import uk.ac.ncl.safecap.scitus.stat.SimHistoryRecord;

public interface IWorld {
	Project getProject();

	Set<ITrain> getTrains();

	double getTime();

	void simulation(IProgressMonitor monitor) throws SimulationException;

	void setTimeWindow(double positiveInfinity);

	SimHistoryRecord getWorldAuxRecord();
}
