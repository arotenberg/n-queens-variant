package ar.nqueens;

import java.util.Optional;

public final class NQueensVariantSolver {
	/**
	 * Find and return an arbitrary solution to the "<var>N</var>-queens with no three in a line"
	 * problem.
	 *
	 * @param n
	 *            The value <var>N</var> from the problem description: the number of queens to place
	 *            and the side length of the board.
	 * @return An arbitrary solution for the given <var>N</var>, represented as an array of the
	 *         0-indexed file (column) on which to place the queen for each rank (row); or
	 *         {@code Optional.empty()} if no solution exists for the given <var>N</var>.
	 */
	public static Optional<int[]> findSolution(int n) {
		NQueensVariantSolver solver = new NQueensVariantSolver(n);
		boolean result = solver.findSolutionRecursive(0);
		return result ? Optional.of(solver.solutionStack) : Optional.empty();
	}

	private final int[] solutionStack;
	private final boolean[] occupiedColumns, occupiedDiagonals, occupiedAntiDiagonals;

	// Several methods package-private instead of private for unit test access.

	NQueensVariantSolver(int n) {
		solutionStack = new int[n];
		occupiedColumns = new boolean[n];
		int diagonalsCount = 2 * n - 1;
		occupiedDiagonals = new boolean[diagonalsCount];
		occupiedAntiDiagonals = new boolean[diagonalsCount];
	}

	/**
	 * Top-left to bottom-right diagonals.
	 */
	int diagonalIndex(int row, int col) {
		int n = solutionStack.length;
		return row - col + n - 1;
	}

	/**
	 * Top-right to bottom-left diagonals.
	 */
	int antiDiagonalIndex(int row, int col) {
		return row + col;
	}

	private boolean findSolutionRecursive(int row) {
		int n = solutionStack.length;
		if (row == n)
			return true;

		for (int col = 0; col < n; col++) {
			if (isValidPlacement(row, col)) {
				solutionStack[row] = col;
				occupiedColumns[col] = true;
				occupiedDiagonals[diagonalIndex(row, col)] = true;
				occupiedAntiDiagonals[antiDiagonalIndex(row, col)] = true;

				if (findSolutionRecursive(row + 1))
					return true;

				occupiedColumns[col] = false;
				occupiedDiagonals[diagonalIndex(row, col)] = false;
				occupiedAntiDiagonals[antiDiagonalIndex(row, col)] = false;
			}
		}

		return false;
	}

	private boolean isValidPlacement(int row, int col) {
		if (occupiedColumns[col] || occupiedDiagonals[diagonalIndex(row, col)]
				|| occupiedAntiDiagonals[antiDiagonalIndex(row, col)])
			return false;

		// Three-in-a-line check
		for (int prevRow = 0; prevRow < row; prevRow++) {
			int prevCol = solutionStack[prevRow];
			for (int prevRow2 = 0; prevRow2 < prevRow; prevRow2++) {
				int prevCol2 = solutionStack[prevRow2];
				if (collinear(prevCol2, prevRow2, prevCol, prevRow, col, row))
					return false;
			}
		}

		return true;
	}

	static boolean collinear(int x1, int y1, int x2, int y2, int x3, int y3) {
		/*
		 * The three points are collinear if and only if the 3-D cross product
		 * 
		 * (p2 - p1) x (p3 - p1)
		 * 
		 * has zero magnitude. Credit for this trick: https://math.stackexchange.com/a/947591
		 */
		return (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1) == 0;
	}
}
