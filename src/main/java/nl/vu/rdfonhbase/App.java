package nl.vu.rdfonhbase;

import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.graph.Node;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		// Logger instance
		Logger logger = LoggerFactory.getLogger(App.class.getCanonicalName());
		logger.debug("Hello World!");

		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		PersistenceManager pm = pmf.getPersistenceManager();
		pm.checkConsistency();

		Node s = Node.createURI("http://example.com");
		Node p = Node.createURI("http://example.com/predicate");
		Node o = Node.createLiteral("test");

		Triple t = new Triple(s, p, o);
		pm.makePersistent(t);

		Query query = pm.newQuery(Triple.class);

		@SuppressWarnings("unchecked")
		List<Triple> c = (List<Triple>) query.execute();
		Iterator<Triple> iter = c.iterator();
		while (iter.hasNext()) {
			Triple triple = iter.next();
			System.out.println(triple);
		}
		query.closeAll();

	}
}
