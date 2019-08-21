package uk.ac.ncl.safecap.mcommon.conf;

public class RouteNameDescriber {
	private final String prefix;
	private final String signal;
	private final String path;
	private final String code;
	private final String routeClass;
	private final String routeSubClass;
	private final String suffix;

	public RouteNameDescriber(String prefix, String signal, String path, String code, String routeClass, String routeSubClass, String suffix) {
		this.prefix = prefix;
		this.signal = signal;
		this.path = path;
		this.code = code;
		this.routeClass = routeClass;
		this.routeSubClass = routeSubClass;
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getSignal() {
		return signal;
	}

	public String getPath() {
		return path;
	}

	public String getCode() {
		return code;
	}

	public String getRouteClass() {
		return routeClass;
	}

	public String getRouteSubClass() {
		return routeSubClass;
	}

}
