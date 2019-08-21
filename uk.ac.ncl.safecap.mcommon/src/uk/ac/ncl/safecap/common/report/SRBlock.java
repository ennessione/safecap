package uk.ac.ncl.safecap.common.report;

public class SRBlock extends SRContent {

	/**
	 * Add block with no title
	 */
	public SRBlock() {
	}

	/**
	 * Add block with given title
	 * 
	 * @param title title string
	 */
	public SRBlock(String title) {
		super.set(TITLE, title);
	}

}
