package uk.ac.ncl.safecap.common;

public interface IUniqueNameChecker {
	boolean isUnique(String canonical, String name);
}
