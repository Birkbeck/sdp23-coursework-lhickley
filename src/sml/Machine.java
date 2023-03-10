package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * Represents the machine, the context in which programs run.
 * <p>
 * An instance contains 8 registers and methods to access and change them.
 *
 */
public final class Machine {

	private final Labels labels = new Labels();

	private final List<Instruction> program = new ArrayList<>();

	private final Registers registers;

	// The program counter; it contains the index (in program)
	// of the next instruction to be executed.
	private int programCounter = 0;

	public Machine(Registers registers) {
		this.registers = registers;
	}

	/**
	 * Execute the program in program, beginning at instruction 0.
	 * Precondition: the program and its labels have been stored properly.
	 */
	public void execute() {
		programCounter = 0;
		registers.clear();
		while (programCounter < program.size()) {
			Instruction ins = program.get(programCounter);
			int programCounterUpdate = ins.execute(this);
			programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
				? programCounter + 1
				: programCounterUpdate;
		}
	}

	public Labels getLabels() {
		return this.labels;
	}

	public List<Instruction> getProgram() {
		return this.program;
	}

	public Registers getRegisters() {
		return this.registers;
	}

	/**
	 * String representation of the program under execution.
	 *
	 * @return pretty formatted version of the code.
	 */
	@Override
	public String toString() {
		return program.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Compares this Machine object with another object for equality.
	 * @param o the object to compare
	 * @return true if the object being compared is an instance of Machine and all of its fields match those of this object,
	 * otherwise false
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Machine m) {
			return Objects.equals(this.labels, m.labels)
					&& Objects.equals(this.program, m.program)
					&& Objects.equals(this.registers, m.registers)
					&& this.programCounter == m.programCounter;
		}
		return false;
	}

	/**
	 * Returns a hash code value for the Machine object based on the hash codes of its components:
	 * labels, program, registers, and programCounter.
	 * @return an int value representing the hash code of the Machine object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
