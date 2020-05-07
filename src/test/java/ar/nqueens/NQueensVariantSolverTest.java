package ar.nqueens;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

final class NQueensVariantSolverTest {
	static final class DiagonalIndexing {
		@Test
		void size1() {
			NQueensVariantSolver solver = new NQueensVariantSolver(1);
			assertEquals(0, solver.diagonalIndex(0, 0));
			assertEquals(0, solver.antiDiagonalIndex(0, 0));
		}

		@Test
		void size2() {
			NQueensVariantSolver solver = new NQueensVariantSolver(2);

			assertEquals(1, solver.diagonalIndex(0, 0));
			assertEquals(0, solver.antiDiagonalIndex(0, 0));

			assertEquals(2, solver.diagonalIndex(1, 0));
			assertEquals(1, solver.antiDiagonalIndex(1, 0));

			assertEquals(0, solver.diagonalIndex(0, 1));
			assertEquals(1, solver.antiDiagonalIndex(0, 1));

			assertEquals(1, solver.diagonalIndex(1, 1));
			assertEquals(2, solver.antiDiagonalIndex(1, 1));
		}
	}

	static final class Collinear {
		@Test
		void collinear() {
			/*
			 * We currently only bother testing cases that could potentially be called if the rest
			 * of the N Queens logic is working correctly - i.e. no same row/column, no mixed-up
			 * point orders, etc.
			 */
			assertTrue(NQueensVariantSolver.collinear(0, 0, 1, 1, 2, 2));
			assertFalse(NQueensVariantSolver.collinear(0, 0, 1, 1, 3, 2));
			assertTrue(NQueensVariantSolver.collinear(0, 0, 2, 1, 4, 2));
			assertFalse(NQueensVariantSolver.collinear(0, 0, 3, 1, 4, 2));
			assertTrue(NQueensVariantSolver.collinear(7, 0, 5, 1, 3, 2));
		}
	}

	static final class Solve {
		private void solveTest(int size, Optional<int[]> expected) {
			Optional<int[]> actual = NQueensVariantSolver.findSolution(size);
			assertEquals(expected.isPresent(), actual.isPresent());
			if (expected.isPresent())
				assertArrayEquals(expected.get(), actual.get());
		}

		@Test
		void trivialSizes() {
			solveTest(1, Optional.of(new int[] { 0 }));
			solveTest(2, Optional.empty());
			solveTest(3, Optional.empty());
		}

		/**
		 * The current implementation always returns the lexicographically first solution. We can
		 * use this to directly check the expected results for small sizes. This does admittedly
		 * make the tests slightly fragile, since the API only guarantees that an arbitrary solution
		 * is returned.
		 */
		@Test
		void smallSolutions() {
			solveTest(4, Optional.of(new int[] { 1, 3, 0, 2 }));
			solveTest(8, Optional.of(new int[] { 2, 4, 7, 3, 0, 6, 1, 5 }));
		}
	}
}
