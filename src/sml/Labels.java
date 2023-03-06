package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents the structure we use to store labels and the addresses of the instructions with those labels
 * in the program.
 *
 * This class consists of methods for the interaction with this data structure, such as the addition of new pairs
 * address-label pairs and the retrieval of an address given a label.
 *
 * @author lhickley
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 * Throws an error if it sees a duplicate label coming in, as that would corrupt a program.
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
	 * @throws RuntimeException if the label does not exist
	 */
	public int getAddress(String label) {
		/* We can get an error if we incorrectly store an address against a label
		 * Specifically, this will become a problem if we store an address for an index which is larger than the size
		 * of the ArrayList of Instructions 'program' in Machine.  If we attempt to jump to the index of this label
		 * and this is outside the size of 'program', a NullPointerException will be thrown.
		 */
		if (!labels.containsKey(label)) {
			throw new RuntimeException("The label " + label + " does not exist!  Please check the file containing the instruction set.");
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
				.collect(Collectors.joining(", ", "[", "]"));
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}

	/**
	 * Returns whether this object is equal to another object.
	 * Two Labels objects are equal if and only if their labels maps are equal.
	 *
	 * @param o the object to compare to
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Labels labels1 = (Labels) o;
		return Objects.equals(labels, labels1.labels);
	}

	/**
	 * Returns a hash code for this object.
	 * The hash code is generated using the labels map.
	 * @return the hash code for this object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels);
	}
}
