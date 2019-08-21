package uk.ac.ncl.safecap.xdata.testinv;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.ac.ncl.prime.sim.lang.CLBase;

public class STTFactory extends CLBase {
	public static final Map<String, List<IStressTestingTransform>> transforms;

	static {
		transforms = new LinkedHashMap<>();

		List<IStressTestingTransform> tt = new ArrayList<>();
		tt.add(new STTDelete(in(var("x"), varq("subroute_l")), "sub route locked"));
		tt.add(new STTDelete(notin(var("x"), varq("subroute_l")), "sub route free"));
		tt.add(new STTDelete(in(var("x"), varq("overlap_l")), "sub overlap locked"));
		tt.add(new STTDelete(notin(var("x"), varq("overlap_l")), "sub overlap free"));
		tt.add(new STTDelete(in(var("x"), varq("track_o")), "track occupied"));
		tt.add(new STTDelete(notin(var("x"), varq("track_o")), "track free"));
		tt.add(new STTDelete(in(var("x"), varq("route_s")), "route set"));
		tt.add(new STTDelete(notin(var("x"), varq("route_s")), "route not set"));
		tt.add(new STTDelete(in(var("x"), varq("route_a")), "route available"));
		tt.add(new STTDelete(notin(var("x"), varq("route_a")), "route not available"));

		transforms.put("Delete", tt);

		tt = new ArrayList<>();
		tt.add(new STTRewrite(in(var("x"), varq("route_a")), notin(var("x"), var("route_a")), "flip route available"));
		tt.add(new STTRewrite(notin(var("x"), varq("route_a")), in(var("x"), var("route_a")), "flip route not available"));
		tt.add(new STTRewrite(in(var("x"), varq("route_s")), notin(var("x"), var("route_s")), "flip route set"));
		tt.add(new STTRewrite(notin(var("x"), varq("route_s")), in(var("x"), var("route_s")), "flip route not set"));
		tt.add(new STTRewrite(in(var("x"), varq("subroute_l")), notin(var("x"), var("subroute_l")), "flip sub route locked"));
		tt.add(new STTRewrite(notin(var("x"), varq("subroute_l")), in(var("x"), var("subroute_l")), "flip sub route free"));
		tt.add(new STTRewrite(in(var("x"), varq("overlap_l")), notin(var("x"), var("overlap_l")), "flip sub overlap locked"));
		tt.add(new STTRewrite(notin(var("x"), varq("overlap_l")), in(var("x"), var("overlap_l")), "flip sub overlap free"));
		tt.add(new STTRewrite(in(var("x"), varq("track_o")), notin(var("x"), var("track_o")), "flip track occupied"));
		tt.add(new STTRewrite(notin(var("x"), varq("track_o")), in(var("x"), var("track_o")), "flip track free"));

		transforms.put("Flip", tt);
	}
}
