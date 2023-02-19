package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if (labels.containsKey(label)) {
			throw new RuntimeException("We cannot add a duplicate label for an instruction!");
		} else {
			labels.put(label, address);
		}
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) {
		/* We can get an error if we incorrectly store an address against a label
		 * Specifically, this will become a problem if we store an address for an index which is larger than the size
		 * of the ArrayList of Instructions 'program' in Machine.  If we attempt to jump to the index of this label
		 * and this is outside the size of 'program', a NullPointerException will be thrown.
		 */
		if (!labels.containsKey(label)) {
			throw new RuntimeException("The label " + label + " does not exist!");
		} else {
			return labels.get(label);
		}
	}

	/**
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet()
				.stream()
				.map(e -> e.getKey() + " -> " + e.getValue())
				.collect(Collectors.joining(","));
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Labels labels1 = (Labels) o;
		return Objects.equals(labels, labels1.labels);
	}

	@Override
	public int hashCode() {
		return Objects.hash(labels);
	}
}
