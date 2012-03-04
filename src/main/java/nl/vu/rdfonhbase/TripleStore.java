package nl.vu.rdfonhbase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.hp.hpl.jena.graph.Node;

public class TripleStore {
	// Singleton instance
	private static TripleStore instance = null;
	// Connection with the persistence layer
	private final PersistenceManager persistenceManager;

	/**
	 * Constructor
	 */
	protected TripleStore() {
		PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
		persistenceManager = pmf.getPersistenceManager();
		persistenceManager.checkConsistency();
	}

	/**
	 * Singleton creator
	 * 
	 * @return
	 */
	public static TripleStore getInstance() {
		if (instance == null)
			instance = new TripleStore();
		return instance;
	}

	/**
	 * Clean the content of the store
	 */
	@SuppressWarnings("unchecked")
	public void removeAll() {
		Query query = persistenceManager.newQuery(Triple.class);
		List<Triple> triples = (List<Triple>) query.execute();
		persistenceManager.deletePersistentAll(triples);
		query.closeAll();
	}

	/**
	 * Add a new triple to the store (duplicates are allowed)
	 * 
	 * @param t
	 *            the triple to add
	 */
	public void add(Triple t) {
		persistenceManager.makePersistent(t);
	}

	/**
	 * @param s
	 * @param p
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Triple> getTriples(Node s, Node p, Node o) {
		// Array for the results
		List<Triple> res = new ArrayList<Triple>();

		// Prepare the query
		Query query = persistenceManager.newQuery(Triple.class);

		// Set filtering conditions based on s,p,o
		StringBuilder builder = new StringBuilder();
		if (!s.equals(Node.ANY))
			builder.append("subject == '" + s.toString() + "' &&");
		if (!p.equals(Node.ANY))
			builder.append("predicate == '" + p.toString() + "' &&");
		if (!o.equals(Node.ANY))
			if (o.isLiteral())
				builder.append("object == '" + o.toString() + "' &&");
			else
				builder.append("object == \"" + o.toString() + "\" &&");
		if (builder.length() > 0) {
			// Remove the last &&
			builder.delete(builder.length() - 2, builder.length());
			// Set the filter
			query.setFilter(builder.toString());
		}

		// Get the results and push them to the array
		List<Triple> triples = (List<Triple>) query.execute();
		Iterator<Triple> iter = triples.iterator();
		while (iter.hasNext())
			res.add(persistenceManager.detachCopy(iter.next()));
		query.closeAll();

		return res;
	}

}
