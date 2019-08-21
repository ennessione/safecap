package uk.ac.ncl.safecap.diagram.misc.parts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import safecap.HighlightedInterval;
import safecap.Orientation;
import safecap.Project;
import safecap.Style;
import safecap.VisualMarker;
import safecap.derived.MergedAmbit;
import safecap.model.Ambit;
import safecap.model.Route;
import safecap.model.Section;
import safecap.schema.Segment;
import safecap.schema.SegmentRole;
import uk.ac.ncl.safecap.diagram.misc.fa.FAUtils;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackColours;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackConstants;
import uk.ac.ncl.safecap.diagram.misc.visual.TrackFonts;
import uk.ac.ncl.safecap.diagram.misc.visual.ZoomLevels;
import uk.ac.ncl.safecap.misc.core.AmbitUtil;
import uk.ac.ncl.safecap.misc.core.EmfUtil;
import uk.ac.ncl.safecap.misc.core.ExtensionUtil;
import uk.ac.ncl.safecap.misc.core.SafecapConstants;
import uk.ac.ncl.safecap.misc.core.SegmentUtil;
import uk.ac.ncl.safecap.misc.core.SubRouteUtil;

public class FSegment extends PolylineConnectionEx {
	private static final int MAX_ANGLE = 80; // max vertical slope for a track,
												// in degrees

	private static final DecimalFormat df = new DecimalFormat("#0.0");
	private Segment segment;
	// private Ambit ambit;

	protected ZoomManager zoomManager = null;

	public void setZoomManager(ZoomManager zoomManager) {
		this.zoomManager = zoomManager;
	}

	/**
	 * Wrong orientation means a track is drawn as a vertical or nearly vertical
	 * line. Such layout is rejected to avoid confusion with left/right driving
	 * directions
	 * 
	 * @return true is the positioning is not acceptable
	 */
	public boolean wrongOrientation() {
		if (segment.getOrientation() == Orientation.ANY) {
			final Point l = getPoints().getLastPoint();
			final Point f = getPoints().getFirstPoint();

			final double h = Math.abs(l.y - f.y);
			final double w = l.x - f.x;

			if (w <= 0) {
				return true;
			}

			final double angle = Math.atan(h / w);

			if (Math.toDegrees(angle) >= MAX_ANGLE) {
				return true;
			}
		}

		return false;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	private Ambit getAmbit() {
		return AmbitUtil.getSegmentAmbitMap(EmfUtil.getProject(segment)).get(segment);

	}

	@Override
	public void outlineShape(final Graphics g) {

		if (wrongOrientation()) {
			g.pushState();
			g.setLineStyle(SWT.LINE_DASH);
			g.setLineCap(SWT.CAP_FLAT);
			g.setLineDash(new int[] { 10, 5 });
			g.setForegroundColor(ColorConstants.red);
			super.outlineShape(g);
			g.popState();
			return;
		}

		if (ZoomLevels.isOutline(zoomManager)) {

			g.setForegroundColor(mixInterlockingColor(ColorConstants.black));
			g.setBackgroundColor(mixInterlockingColor(ColorConstants.black));
			setLineWidth(mixWidth(TrackConstants.TRACK_WIDTH * 6));

			if (segment.getStyle() != null) {

				final Style s = segment.getStyle();
				if (s.getForeground() != null && s.getForeground() instanceof Color) {
					g.setForegroundColor((Color) s.getForeground());
				}

				if (s.getLinewidth() != 0) {
					setLineWidth(s.getLinewidth());
				}

				if (s.getLinestyle() != 0) {
					g.setLineStyle(s.getLinestyle());
				}
			}
			super.outlineShape(g);
			return;
		} else if (ZoomLevels.isCoarse(zoomManager)) {
			setLineWidth(mixWidth(TrackConstants.TRACK_WIDTH * 2));
			g.setForegroundColor(mixInterlockingColor(ColorConstants.black));
			g.setBackgroundColor(mixInterlockingColor(ColorConstants.black));
		} else {
			setLineWidth(mixWidth(TrackConstants.TRACK_WIDTH));
		}

		g.pushState();

		if (segment != null) {
			g.setForegroundColor(mix(ColorConstants.gray));
			// if ((segment.getRole() & SegmentRole.JUNCTION_VALUE) ==
			// SegmentRole.JUNCTION_VALUE) {
			// g.setForegroundColor(mix(TrackColours.SegmentJunction));
			// if (segment.getPointrole() == PointRole.REVERSE) {
			// int width = mixWidth(g.getLineWidth());
			// g.pushState();
			// g.setLineWidth(width);
			// g.setForegroundColor(mix(ColorConstants.gray));
			// super.outlineShape(g);
			// g.popState();
			// g.setLineStyle(SWT.LINE_DOT);
			// g.setLineCap(SWT.CAP_FLAT);
			// }
			//
			// if ((segment.getRole() & SegmentRole.CROSS_A_VALUE) ==
			// SegmentRole.CROSS_A_VALUE) {
			// g.setForegroundColor(mix(TrackColours.SegmentCrossoverA));
			// } else if ((segment.getRole() & SegmentRole.CROSS_B_VALUE) ==
			// SegmentRole.CROSS_B_VALUE) {
			// g.setForegroundColor(mix(TrackColours.SegmentCrossoverB));
			// }
			// }

			Ambit ambit = getAmbit();
			if (ambit != null) {
				final Project project = EmfUtil.getProject(ambit);
				if (project == null) {
					ambit = null;
				} else {
					final List<Route> routes = AmbitUtil.getAmbitRoutes(ambit);
					if (routes == null || routes.isEmpty()) {

						if (!SubRouteUtil.hasSubRoutes(ambit)) {
							g.setLineStyle(SWT.LINE_DASH);
							g.setLineDash(new int[] { 6, 6 });
							g.setForegroundColor(ColorConstants.lightBlue);
							g.setLineWidth(TrackConstants.TRACK_WIDTH * 2);
						} else {
							g.setLineStyle(SWT.LINE_DASH);
							g.setLineDash(new int[] { 4, 4 });
							g.setForegroundColor(ColorConstants.darkBlue);
							g.setLineWidth(TrackConstants.TRACK_WIDTH * 2);
						}
					}
				}
			} else {
				g.setLineStyle(SWT.LINE_DASHDOT);
				g.setLineDash(new int[] { 8, 8 });
				g.setForegroundColor(ColorConstants.orange);
				g.setLineWidth(TrackConstants.TRACK_WIDTH * 2);
			}

			super.outlineShape(g);

			if (segment.getOrientation() == Orientation.LEFT || segment.getOrientation() == Orientation.RIGHT) {
				final int midindex = getPoints().size() / 2;
				final Point last = getPoints().getPoint(midindex);
				final Point prev = getPoints().getPoint(midindex - 1);

				final int midx = Math.abs(last.x + prev.x) / 2;
				final int midy = Math.abs(last.y + prev.y) / 2;

				double angle = 0;
				final double a = last.y - prev.y;
				final double b = last.x - prev.x;

				angle = Math.atan(a / b);

				if (b >= 0) {
					angle = angle + Math.PI / 2.0;
				} else {
					angle = angle - Math.PI / 2.0;
				}

				// if (b == 0)
				// angle = 0;
				// else if (a == 0 && prev.x > last.x)
				// angle = -Math.PI / 2.0;
				// else if (a == 0 && prev.x > last.x)
				// angle = -Math.PI / 2.0;
				// else if (prev.x > last.x)
				// angle = Math.atan(a / b) - Math.PI / 2.0;
				// else
				// angle = Math.atan(b / a) + Math.PI / 2.0;
				//

				if (segment.getOrientation() == Orientation.RIGHT) {
					angle = angle + Math.PI;
				}

				final int base = TrackConstants.TRACK_WIDTH * 4;
				final PointList path = new PointList();
				path.addPoint(midx, midy);
				path.addPoint(midx + base, midy + base * 4);
				path.addPoint(midx - base, midy + base * 4);
				FAUtils.rotate(path, new Point(midx, midy), angle);
				if (segment.getOrientation() == Orientation.RIGHT) {
					g.setBackgroundColor(ColorConstants.red);
				} else {
					g.setBackgroundColor(ColorConstants.blue);
				}

				g.fillPolygon(path);
				// g.drawString("" + angle, midx, midy);
			}

			if (segment != null && ZoomLevels.isNormal(zoomManager) && ExtensionUtil.isTrackSubRouteMarkersVisible(segment)) {

				/*
				 * Point f;
				 * 
				 * if (segment.getPointrole() == PointRole.REVERSE || getPoints().size() > 2) {
				 * f = getPoints().getMidpoint(); } else { f = getPoints().getFirstPoint(); }
				 * 
				 * // drawInterlockingFine(g, f);
				 * 
				 * if (ZoomLevels.isUltraFine(zoomManager)) {
				 * g.setBackgroundColor(ColorConstants.gray);
				 * 
				 * g.setForegroundColor(ColorConstants.gray); g.setFont(TrackFonts.FONT_TINY);
				 * g.drawString(segment.getLength() + "m", f.x + TrackConstants.TRACK_WIDTH * 2
				 * + 10, f.y); g.setForegroundColor(ColorConstants.black);
				 * g.setFont(TrackFonts.FONT_TINY); g.drawString( ((segment.getGradient() !=
				 * null && segment.getGradient().length() > 0) ? (" [" + segment.getGradient() +
				 * "]") : "") + ((segment.getSpeedlimit() != null &&
				 * segment.getSpeedlimit().length() > 0) ? (" @" + df.format(new
				 * Double(segment.getSpeedlimit()))) : ""), f.x + TrackConstants.TRACK_WIDTH *
				 * 2, f.y - TrackConstants.TRACK_WIDTH * 2 - 2);
				 * 
				 * }
				 */
				annotateMarkers(g);
			}
			g.popState();

			if (segment.getIntervals() != null && segment.getIntervals().size() > 0
					|| segment.getMarkers() != null && segment.getMarkers().size() > 0) {
				g.pushState();
				g.setLineCap(SWT.CAP_FLAT);
				final PointList displayPoints = getSmoothPoints(false);
				double totalLength = 0;
				for (int i = 0; i < displayPoints.size() - 1; i++) {
					final Point p0 = displayPoints.getPoint(i), p1 = displayPoints.getPoint(i + 1);
					totalLength += p0.getDistance(p1);
				}

				for (final HighlightedInterval interval : segment.getIntervals()) {
					Point lastPoint = null;
					final double from = interval.getFrom() * totalLength, to = interval.getTo() * totalLength;
					double curLength = 0;

					Point fromPoint = null, toPoint = null;
					for (int i = 0; i < displayPoints.size() - 1; i++) {
						Point curPoint = null;
						final Point p0 = displayPoints.getPoint(i), p1 = displayPoints.getPoint(i + 1);
						final double len = p0.getDistance(p1);
						if (fromPoint == null) {
							fromPoint = getPointBetween(from - curLength, p0, p1);
							if (fromPoint != null) {
								lastPoint = fromPoint;
							}
						}
						if (lastPoint != null) {
							toPoint = getPointBetween(to - curLength, p0, p1);
							if (toPoint != null) {
								curPoint = toPoint;
							} else {
								curPoint = p1;
							}
						}
						if (lastPoint != null && curPoint != null) {
							// applyStyle(g, null);
							// g.drawLine(lastPoint, curPoint);
							applyStyle(g, interval.getStyle());

							final Point pa = new Point(lastPoint.x, lastPoint.y + interval.getVoffset());
							final Point pb = new Point(curPoint.x, curPoint.y + interval.getVoffset());
							g.drawLine(pa, pb);

							// g.drawLine(lastPoint, curPoint);
							lastPoint = curPoint;
							if (toPoint != null) {
								break;
							}
						}

						curLength += len;
					}

					if (interval.getLabel() != null) {
						String label = interval.getLabel();
						g.setForegroundColor(ColorConstants.black);
						g.setFont(TrackFonts.FONT_TINY);
						if (label.endsWith("_")) {
							label = label.substring(0, label.length() - 1);
							// g.drawText(label, fromPoint.x - 20,
							// fromPoint.y + 3);

							g.drawText(label, fromPoint.x, fromPoint.y - 12);
						} else {
							// g.drawText(label, toPoint.x - 40, toPoint.y +
							// 3);

							g.drawText(label, toPoint.x - 20, toPoint.y - 12);
						}
					}
				}

				if (ExtensionUtil.isMarkerVisible(segment)) {
					for (final VisualMarker marker : segment.getMarkers()) {
						if (marker.getStyle() != null) {

							final Style s = marker.getStyle();
							if (s.getForeground() != null && s.getForeground() instanceof Color) {
								g.setForegroundColor((Color) s.getForeground());
							}

							if (s.getLinewidth() != 0) {
								g.setLineWidth(s.getLinewidth());
							}

							if (s.getLinestyle() != 0) {
								g.setLineStyle(s.getLinestyle());
							}
						} else {
							g.setForegroundColor(ColorConstants.red);
							g.setLineWidth(TrackConstants.TRACK_WIDTH + 2);
							g.setLineStyle(SWT.LINE_SOLID);
						}

						final double pos = marker.getPosition() * totalLength;
						double curLength = 0;
						for (int i = 0; i < displayPoints.size() - 1; i++) {
							final Point p0 = displayPoints.getPoint(i), p1 = displayPoints.getPoint(i + 1);
							final double len = p0.getDistance(p1);
							final Point curPoint = getPointBetween(pos - curLength, p0, p1);
							if (curPoint != null) {
								g.drawLine(curPoint.x - g.getLineWidth() / 2, curPoint.y, curPoint.x + g.getLineWidth() / 2 + 1,
										curPoint.y);
								if (marker.getLabel() != null) {
									final String label = marker.getLabel();
									g.setForegroundColor(ColorConstants.black);
									g.setFont(TrackFonts.FONT_TINY);
									g.drawText(label, curPoint.x - 5, curPoint.y - 8);
									if (marker.getOffsetLabel() != null) {
										g.drawText(marker.getOffsetLabel(), curPoint.x - 5, curPoint.y + 8);
									}
								}
								break;
							}

							curLength += len;
						}
					}
				}

				g.popState();
			}
		}
	}

	private void annotateMarkers(Graphics g) {
		final Point first = getPoints().getPoint(0);
		final Point last = getPoints().getPoint(getPoints().size() - 1);

		final String rightTag = ExtensionUtil.getString(segment.getTo(), SafecapConstants.EXT_NODE_TAG, "right");
		final String leftTag = ExtensionUtil.getString(segment.getFrom(), SafecapConstants.EXT_NODE_TAG, "left");

		g.pushState();

		g.setForegroundColor(ColorConstants.gray);

		if (rightTag != null) {
			g.drawText(rightTag, last.getTranslated(-10, -10));
		}

		if (leftTag != null) {
			g.drawText(leftTag, first.getTranslated(6, -10));
		}

		g.popState();
	}

	private Color mixInterlockingColor(Color xc) {
		final Ambit ambit = SegmentUtil.getSegmentAmbit(segment);

		if (ambit != null) {
			final String interlocking = ExtensionUtil.getString(ambit, SafecapConstants.EXT_LDL_GEN, "interlocking");
			if (interlocking != null && interlocking.length() > 0) {
				final String c = interlocking.substring(0, 1).toUpperCase();
				return TrackColours.interlocking[c.charAt(0) % TrackColours.interlocking.length];
			}
		}
		return xc;
	}

	private int mixWidth(int i) {
		if (segment.getStyle() != null) {
			final Style s = segment.getStyle();
			if (s.getLinewidth() != 0) {
				return s.getLinewidth();
			}
		}
		return i;
	}

	private Color mix(Color c) {
		if (segment.getStyle() != null) {
			final Style s = segment.getStyle();
			if (s.getForeground() != null && s.getForeground() instanceof Color) {
				return (Color) s.getForeground();
			}
		}
		return c;
	}

	private void applyStyle(Graphics g, Style style) {
		if (style != null) {

			final Style s = style;
			if (s.getForeground() != null && s.getForeground() instanceof Color) {
				g.setForegroundColor((Color) s.getForeground());
			}

			if (s.getLinewidth() != 0) {
				g.setLineWidth(s.getLinewidth());
			}

			if (s.getLinestyle() != 0) {
				g.setLineStyle(s.getLinestyle());
			}
		} else {
			g.setForegroundColor(ColorConstants.white);
			g.setLineWidth(TrackConstants.TRACK_WIDTH);
			g.setLineStyle(SWT.LINE_SOLID);
		}
	}

	private Point getPointBetween(double position, Point p0, Point p1) {
		final double len = p0.getDistance(p1);
		if (position >= 0 && position <= len) {
			double ratio;
			if (len > 0) {
				ratio = position / len;
			} else {
				return null;
			}

			final Point p = new Point();
			p.x = (int) Math.round(p0.x + (p1.x - p0.x) * ratio);
			p.y = (int) Math.round(p0.y + (p1.y - p0.y) * ratio);
			return p;
		}
		return null;
	}

	private void drawInterlockingFine(Graphics g, Point p) {
		final Ambit ambit = SegmentUtil.getSegmentAmbit(segment);

		if (ambit != null) {
			final String interlocking = ExtensionUtil.getString(ambit, SafecapConstants.EXT_LDL_GEN, "interlocking");
			if (interlocking != null && interlocking.length() > 0) {
				final String c = interlocking.substring(0, 1).toUpperCase();
				final Color bc = TrackColours.interlocking[c.charAt(0) % TrackColours.interlocking.length];
				g.setForegroundColor(bc);
				g.setFont(TrackFonts.FONT_TINY);
				g.drawString(c, p.x + 2, p.y - TrackConstants.TRACK_WIDTH);
			}
		}
	}

	private void drawInterlockingCoarse(Graphics g, Point p) {
		final Ambit ambit = SegmentUtil.getSegmentAmbit(segment);

		if (ambit != null) {
			final String interlocking = ExtensionUtil.getString(ambit, SafecapConstants.EXT_LDL_GEN, "interlocking");
			if (interlocking != null && interlocking.length() > 0) {
				final String c = interlocking.substring(0, 1).toUpperCase();
				final Color bc = TrackColours.interlocking[c.charAt(0) % TrackColours.interlocking.length];
				g.setBackgroundColor(bc);
				g.fillRectangle(p.x + 2, p.y - TrackConstants.TRACK_WIDTH - 10, 30, 30);
			}
		}
	}

	@Override
	public IFigure getToolTip() {
		if (ZoomLevels.isCoarse(zoomManager)) {
			return null;
		}

		final StringBuffer sb = new StringBuffer();

		sb.append("Segment: " + (segment.getLabel() != null && segment.getLabel().length() > 0 ? segment.getLabel() : "(no label)") + "\n");
		final Ambit ambit = getAmbit();
		if (ambit != null) {

			for (final String key : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_LDL_GEN)) {
				sb.append(key + " : " + ExtensionUtil.getString(ambit, SafecapConstants.EXT_LDL_GEN, key) + "\n");
			}

			if (ambit instanceof Section) {
				sb.append("Section: " + (ambit.getLabel() != null ? ambit.getLabel() : "(no label)") + "\n");
			} else {
				sb.append("Junction: " + (ambit.getLabel() != null ? ambit.getLabel() : "(no label)") + "\n");
			}
			sb.append("Ambience: " + ambit.getSegments() + "\n");

			sb.append("Segment length: " + segment.getLength() + "m\n");
			sb.append("Point role: " + segment.getPointrole().getLiteral() + "\n");
			if ((segment.getRole() & SegmentRole.CROSS_A_VALUE) == SegmentRole.CROSS_A_VALUE) {
				sb.append("Crossing role: A\n");
			} else if ((segment.getRole() & SegmentRole.CROSS_B_VALUE) == SegmentRole.CROSS_B_VALUE) {
				sb.append("Crossing role: B\n");
			}
			sb.append("Needs label: " + (SegmentUtil.needsLabel(segment) ? "yes\n" : "no\n"));

			final Map<String, List<String>> map = new HashMap<>();

			for (final Route r : AmbitUtil.getAmbitRoutes(ambit)) {
				final String orientation = ExtensionUtil.getString(ambit, SafecapConstants.EXT_ORIENTATION_DOMAIN, r.getLabel());

				List<String> list = map.get(orientation);
				if (list == null) {
					list = new ArrayList<>();
					map.put(orientation, list);
				}
				list.add(r.getLabel());

			}

			if (map.size() > 0) {
				sb.append("Routes over the ambit:\n");
				for (final String key : map.keySet()) {
					sb.append("    ");
					sb.append(key);
					sb.append(": ");
					sb.append(map.get(key).toString());
					sb.append("\n");
				}
			}

			sb.append("Sub route paths:\n");

			for (final String s : ExtensionUtil.getKeys(ambit, SafecapConstants.EXT_SUBROUTE_PATH)) {
				sb.append("    ");
				sb.append(s);
				sb.append("::");
				final String path = ExtensionUtil.getString(ambit, SafecapConstants.EXT_SUBROUTE_PATH, s);
				sb.append(path);
				sb.append("\n");
			}

			if (ambit != null && EmfUtil.getProject(ambit) != null) {
				final Collection<MergedAmbit> composedL = AmbitUtil.getAmbitToComposedMap(EmfUtil.getProject(ambit)).get(ambit);
				if (composedL != null && !composedL.isEmpty()) {
					for (final MergedAmbit composed : composedL) {
						sb.append("Child of " + composed.getLabel() + "\n");
						sb.append("    Siblings: " + composed.getAmbits().toString() + "\n");
						sb.append("    Parent sub routes: " + SubRouteUtil.getSubRoutes(composed) + "\n");
					}
				}
			}
		}

		final Label l = new Label();
		l.setFont(TrackFonts.FONT_SMALL);
		l.setOpaque(true);
		l.setBorder(null);
		l.setBackgroundColor(ColorConstants.white);
		l.setForegroundColor(ColorConstants.black);
		l.setText(sb.toString());

		return l;

	}

	// private Image getLabelImage(String label) {
	//
	// return ImageUtilities.createRotatedImageOfString("Label Name",
	// TrackFonts.FONT_SMALL,
	// ColorConstants.black,
	// ColorConstants.white );
	// }

}
