package ar.nqueens;

import java.util.*;

/**
 * Simple command line interface for {@link NQueensVariantSolver}.
 */
public final class Main {
	private Main() {}

	private static final double NANOS_PER_SECOND = 1_000_000_000.0;

	public static void main(String[] args) {
		// SuppressWarnings is fine here, it's just stdin...
		@SuppressWarnings("resource")
		Scanner stdin = new Scanner(System.in);

		OptionalInt n = OptionalInt.empty();
		while (!n.isPresent()) {
			System.out.print("Enter N value: ");
			String nLine = stdin.nextLine();
			n = parseN(nLine);
			if (!n.isPresent())
				System.out.println("Not a valid N value. Try again.");
		}

		System.out.println("Solving...");
		long startTime = System.nanoTime();
		Optional<int[]> maybeSolution = NQueensVariantSolver.findSolution(n.getAsInt());
		long endTime = System.nanoTime();

		printSolution(maybeSolution);

		double elapsedSeconds = (endTime - startTime) / NANOS_PER_SECOND;
		System.out.format("Solver took %.2f seconds.%n", elapsedSeconds);
	}

	private static OptionalInt parseN(String nLine) {
		int n;
		try {
			n = Integer.parseInt(nLine);
		} catch (NumberFormatException ex) {
			return OptionalInt.empty();
		}

		if (n < 1)
			return OptionalInt.empty();
		return OptionalInt.of(n);
	}

	private static void printSolution(Optional<int[]> maybeSolution) {
		if (!maybeSolution.isPresent()) {
			System.out.println("No solution exists for this N.");
			return;
		}

		System.out.println("Solution found for this N:");
		int[] solution = maybeSolution.get();
		int n = solution.length;
		for (int row = 0; row < n; row++) {
			System.out.print("    ");
			int solutionCol = solution[row];
			for (int col = 0; col < n; col++)
				System.out.print((col == solutionCol) ? "Q " : ". ");
			System.out.println();
		}
	}
}
