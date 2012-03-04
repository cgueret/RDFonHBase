/**
 * 
 */
package nl.vu.rdfonhbase;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.hp.hpl.jena.graph.Node;

@PersistenceCapable
public class Triple {

	@Persistent
	private String subject = null;

	@Persistent
	private String predicate = null;

	@Persistent
	private String object = null;

	/**
	 * @param s
	 * @param p
	 * @param o
	 */
	public Triple(Node s, Node p, Node o) {
		subject = s.toString();
		predicate = p.toString();
		object = o.toString();
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the object
	 */
	public String getObject() {
		return object;
	}

	/**
	 * @return the predicate
	 */
	public String getPredicate() {
		return predicate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Triple [subject=");
		builder.append(subject);
		builder.append(", predicate=");
		builder.append(predicate);
		builder.append(", object=");
		builder.append(object);
		builder.append("]");
		return builder.toString();
	}
}
