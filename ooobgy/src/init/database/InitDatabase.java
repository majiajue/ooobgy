/**
 * @Author 周晓龙   frogcherry
 * @Email frogcherry@gmail.com
 * @CreateDate 2010-6-10
 */

package init.database;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class InitDatabase {
	public void createSchema() {
		Configuration cfg = new Configuration();
		cfg.configure("/hibernate.cfg.xml");
		SchemaExport doExport = new SchemaExport(cfg);
		doExport.create(true, true);
	}
}
