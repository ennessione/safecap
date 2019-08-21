package uk.ac.ncl.safecap.capacity.experiment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import safecap.Project;

public class ExperimentRegistry {
	public interface IExperimentListener {
		void experimentUpdated(Project project);
	}

	private static ExperimentRegistry _instance;

	public static ExperimentRegistry getInstance() {
		if (_instance == null) {
			_instance = new ExperimentRegistry();
		}
		return _instance;
	}

	private final Map<Project, List<SimulationExperiment>> _map = new Hashtable<>();
	private final List<IExperimentListener> _listeners = new ArrayList<>();

	public void putExperiment(Project project, SimulationExperiment exp) {
		List<SimulationExperiment> list = _map.get(project);
		if (list == null) {
			list = new ArrayList<>();
			_map.put(project, list);
		}

		remove(project, exp.kind);
		list.add(exp);
		for (final IExperimentListener listener : _listeners) {
			listener.experimentUpdated(project);
		}
	}

	public SimulationExperiment getExperiment(Project project) {
		final List<SimulationExperiment> list = _map.get(project);
		if (list != null && list.size() > 0) {
			return list.get(list.size() - 1);
		}

		return null;
	}

	public SimulationExperiment getExperiment(Project project, String kind) {
		final List<SimulationExperiment> list = _map.get(project);
		if (list != null && list.size() > 0) {
			for (final SimulationExperiment exp : list) {
				if (exp.getKind().equals(kind)) {
					return exp;
				}
			}
		}

		return null;
	}

	public void remove(Project project, String kind) {
		final List<SimulationExperiment> list = _map.get(project);
		if (list != null && list.size() > 0) {
			final List<SimulationExperiment> toRemove = new ArrayList<>();
			for (final SimulationExperiment exp : list) {
				if (exp.getKind().equals(kind)) {
					toRemove.add(exp);
				}
			}

			list.removeAll(toRemove);
		}
	}

	public void addListener(IExperimentListener listener) {
		if (!_listeners.contains(listener)) {
			_listeners.add(listener);
		}
	}

	public void removeListener(IExperimentListener listener) {
		_listeners.remove(listener);
	}
}
