package uk.ac.ncl.safecap.xdata.verification.errorinjection;

import java.util.Collection;

import uk.ac.ncl.safecap.xdata.verification.Conjecture;

public class ErrorInjectionConfiguration {
	private Collection<Conjecture> conjectures;
	private Collection<IErrorInjector> injectors;
}
