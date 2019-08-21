package uk.ac.ncl.safecap.xdata.verification.db;

import java.sql.SQLException;

import uk.ac.ncl.safecap.common.MD5Checksum;
import uk.ac.ncl.safecap.db.common.DBUtils;
import uk.ac.ncl.safecap.db.properties.PropertyQuery;
import uk.ac.ncl.safecap.xdata.verification.PredicateDefinition;

public class InvariantQuery {
	private static final String TYPE = "inv";
	
	public static boolean needsSaving(PredicateDefinition predicate) {
		try {
			if (predicate.getFormula().empty())
				return false;
			String hash = MD5Checksum.getMD5TextChecksum(predicate.getFormula().content());
			PropertyQuery pquery = new PropertyQuery(DBUtils.getConnection());
			return pquery.findProperties(TYPE, hash).isEmpty();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean save(PredicateDefinition p) {
		try {
			if (needsSaving(p)) {
				PropertyQuery pquery = new PropertyQuery(DBUtils.getConnection());
				String hash = MD5Checksum.getMD5TextChecksum(p.getFormula().content());
				pquery.addProperty(TYPE, 
						p.getKey().content(), 
						p.getId().content(), 
						p.getComment().content(), 
						p.getSemiFormal().content(), 
						p.getFormula().content(),
						hash,
						p.getReports().size());
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
