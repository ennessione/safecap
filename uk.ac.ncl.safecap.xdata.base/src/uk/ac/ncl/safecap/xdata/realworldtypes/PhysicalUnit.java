package uk.ac.ncl.safecap.xdata.realworldtypes;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({ PTAmpere.class, PTCandela.class, PTKelvin.class, PTKilogram.class, PTMetre.class, PTMole.class, PTSecond.class })
public abstract class PhysicalUnit implements Comparable<PhysicalUnit> {
	/**
	 * Short tag for a unit, i.e., m for metre
	 * 
	 * @return
	 */
	public abstract String getTag();

	/**
	 * Full name of a unit, i.e., metre
	 * 
	 * @return
	 */
	public abstract String getName();

	@Override
	public int compareTo(PhysicalUnit arg0) {
		return getTag().compareTo(arg0.getTag());
	}
}
