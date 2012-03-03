/**
 * 
 */
package nl.vu.rdfonhbase;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.hp.hpl.jena.graph.Node;

/**
 * @author Christophe Guéret <christophe.gueret@gmail.com>
 * 
 */
@PersistenceCapable
public class Triple {

	@Persistent
	private Node subject = null;

	@Persistent
	private Node predicate = null;

	@Persistent
	private Node object = null;

	/**
	 * @param s
	 * @param p
	 * @param o
	 */
	public Triple(Node s, Node p, Node o) {
		subject = s;
		predicate = p;
		object = o;
	}

	/**
	 * @return the subject
	 */
	public Node getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(Node subject) {
		this.subject = subject;
	}

	/**
	 * @return the object
	 */
	public Node getObject() {
		return object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(Node object) {
		this.object = object;
	}

	/**
	 * @return the predicate
	 */
	public Node getPredicate() {
		return predicate;
	}

	/**
	 * @param predicate
	 *            the predicate to set
	 */
	public void setPredicate(Node predicate) {
		this.predicate = predicate;
	}
}
