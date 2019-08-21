package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import uk.ac.ncl.prime.sim.sets.BMap;
import uk.ac.ncl.safecap.xdata.verification.core.IVerificationProgressMonitor;
import uk.ac.ncl.safecap.xdata.verification.core.SDAContext;
import uk.ac.ncl.safecap.xmldata.IXFunction;
import uk.ac.ncl.safecap.xmldata.types.XEnumType;
import uk.ac.ncl.safecap.xmldata.types.XIntegerType;
import uk.ac.ncl.safecap.xmldata.types.XProductType;
import uk.ac.ncl.safecap.xmldata.types.XRelationType;
import uk.ac.ncl.safecap.xmldata.types.XType;

public class PoolingInjector implements IErrorInjectorManager {
	private static final int MAX_TRIES = 60;
	private final List<IXFunction> functions;
	private final List<IErrorInjector> injections;
	private final List<BMap<Double, IInjectionGenerator>> generators;
	private final SDAContext context;
	private final int injectionStrength;
	private int points;
	private int injectionStep; // injection step index, outer
	private int subInjectionStep; // injection step index, innner
	private int experimentStep; // experiment step index

	public PoolingInjector(SDAContext context, List<IXFunction> functions, int points, int injectionStrength) {
		this.functions = functions;
		this.points = points;
		this.context = context;
		this.injectionStrength = injectionStrength;
		injections = new ArrayList<>(points);
		generators = new ArrayList<>(5);
		filterFunctions();
	}

	@Override
	public List<IXFunction> getFunctions() {
		return functions;
	}

	@Override
	public int getPoints() {
		return points;
	}

	public int getInjectionStep() {
		return injectionStep;
	}

	public void addGenerator(double weight, IInjectionGenerator generator) {
		if (weight > 0 && generator != null) {
			generators.add(new BMap<>(weight, generator));
		}
	}

	private void filterFunctions() {
		final Collection<IXFunction> toremove = new ArrayList<>();

		for (final IXFunction f : functions) {
			if (f.getFunctionType() != null) {
				final XType range = ((XRelationType) f.getFunctionType()).getRange();
				if (range instanceof XProductType) {
					final XProductType ptype = (XProductType) range;
					if (ptype.getSize() != 2 || !(ptype.get(0) instanceof XIntegerType) || !(ptype.get(1) instanceof XEnumType)) {
						toremove.add(f);
					}
				} else if (!(range instanceof XEnumType) && !(range instanceof XIntegerType)) {
					toremove.add(f);
				}
			} else {
				toremove.add(f);
			}
		}

		// System.err.println("PoolingInjector: removing concepts " + toremove);
		functions.removeAll(toremove);
		// System.err.println("PoolingInjector: left concepts " + functions);
	}

	public void buildInjections(IVerificationProgressMonitor monitor) {
		if (generators.isEmpty() || functions.isEmpty()) {
			points = 0;
			return;
		}

		// scale weights
		double scale = 0;

		for (final BMap<Double, IInjectionGenerator> gmap : generators) {
			scale += gmap.prj1();
		}

		double base = 0;
		for (final BMap<Double, IInjectionGenerator> gmap : generators) {
			base += gmap.prj1() / scale;
			gmap.setFirst(base);
		}

		final Random rangen = new Random();
		
		monitor.beginTask("Generating mutations", points);
		monitor.subTask("Generating mutations");
		// run injectors
		for (int i = 0; i < points; i++) {
			boolean somethingDone = false;
			int outer_tries = MAX_TRIES;
			while (!somethingDone && outer_tries-- > 0) {
				final double d = rangen.nextDouble();
				for (final BMap<Double, IInjectionGenerator> gmap : generators) {
					if (gmap.prj1() > d) {
						int tries = MAX_TRIES;
						while (tries-- > 0) {
							final IErrorInjector inj = gmap.prj2().generate(context, rangen, functions.get(i % functions.size()));
							if (inj != null && !injections.contains(inj)) {
								injections.add(inj);
								//System.out.println("PoolingInjector: added point[" + i + "] = " + inj);
								somethingDone = true;
								break;
							}
							// System.out.println("PoolingInjector: skipping injection " + inj);
						}
						// if (!somethingDone)
						// System.err.println("PoolingInjector: failed generator");
						if (somethingDone) {
							break;
						}
					}
				}
			}

			if (!somethingDone) {
				System.err.println("PoolingInjector: reduced points to " + i + " with " + generators.size() + " generators");
				points = i;
				break;
			}
			
			monitor.worked(1);
		}
		monitor.done();

		//Collections.shuffle(injections, rangen);
		injectionStep = 0;
		subInjectionStep = 0;
	}

	@Override
	public List<Object> injectError(IXFunction f, Object domain) {
		if (injectionStep + subInjectionStep < points && subInjectionStep < injectionStrength) {
			final List<Object> result = injections.get(injectionStep + subInjectionStep).injectError(f, domain);
			if (result != null) {
//				 System.err.println("### INJECTOR[" + (injectionStep + subInjectionStep) + "/" + experimentStep + "] " + f.getName() + "(" + domain + ") = "
//						+ f.get(domain) + " -> " + result);
				// subInjectionStep++;
				// injectionStep++;
				return result;
			}
		}
		return f.get(domain);
	}

	@Override
	public List<Object> getExtraDomain(IXFunction f) {
		if (injectionStep + subInjectionStep < points && subInjectionStep < injectionStrength) {
			return injections.get(injectionStep + subInjectionStep).getExtraDomain(f);
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public boolean nextExperimentStep() {
		experimentStep++;
		// injectionStep += subInjectionStep;
		injectionStep += 1;
		subInjectionStep = 0;
		return injectionStep < points;
		// if (injectionStep < experimentStep)
		// injectionStep++;
	}

	@Override
	public IErrorInjector getCurrentInjection() {
		if (injectionStep + subInjectionStep < injections.size()) {
			return injections.get(injectionStep + subInjectionStep);
		} else {
			return null;
		}
	}

}
