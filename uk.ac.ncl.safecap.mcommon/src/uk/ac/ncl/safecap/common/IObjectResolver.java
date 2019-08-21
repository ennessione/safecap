package uk.ac.ncl.safecap.common;

import java.util.Collection;

public interface IObjectResolver {
	Collection<Object> resolve(String expression);
}
