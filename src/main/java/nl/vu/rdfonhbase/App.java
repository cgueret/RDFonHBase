package nl.vu.rdfonhbase;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.jena.graph.Node;

/**
 * Hello world!
 * 
 */
public class App {
	private static final int NB_TRIPLES = 50;
	private static final int NB_RESOURCES = 10;
	private static final int NB_PREDICATES = 5;

	public static void main(String[] args) {
		// Logger instance
		Logger logger = LoggerFactory.getLogger(App.class.getCanonicalName());

		// Triple store instance
		TripleStore tripleStore = TripleStore.getInstance();

		// Clean the current content of the store
		tripleStore.removeAll();

		// Put some triples
		Random random = new Random();
		Set<Node> resources = new HashSet<Node>();
		for (int i = 0; i < NB_TRIPLES; i++) {
			Node s = Node.createURI("http://example.com/resource" + random.nextInt(NB_RESOURCES));
			Node p = Node.createURI("http://example.com/predicate" + random.nextInt(NB_PREDICATES));
			Node o = null;
			if (random.nextFloat() > 0.7)
				o = Node.createLiteral("blah");
			else
				o = Node.createURI("http://example.com/resource" + random.nextInt(NB_RESOURCES));

			// Store the triple
			tripleStore.add(new Triple(s, p, o));

			// Keep a reference of the s for querying
			resources.add(s);
		}

		// Dump the content of the store
		logger.info("Content of the store");
		List<Triple> triples = tripleStore.getTriples(Node.ANY, Node.ANY, Node.ANY);
		for (Triple triple : triples)
			logger.info(triple.toString());

		// Dump description of two resources
		Node[] resourcesArray = (Node[]) resources.toArray(new Node[resources.size()]);
		for (int i = 0; i < NB_TRIPLES; i++) {
			Node resource = resourcesArray[0];
			logger.info("Description of " + resource);
			List<Triple> description = tripleStore.getTriples(resource, Node.ANY, Node.ANY);
			for (Triple triple : description)
				logger.info(triple.toString());
		}
	}
}
