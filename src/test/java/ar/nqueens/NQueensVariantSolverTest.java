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
	}

	static final class Collinear {
		@Test
		void collinear() {
			assertTrue(NQueensVariantSolver.collinear(0, 0, 2, 1, 4, 2));
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
		void size1() {
			solveTest(1, Optional.of(new int[] { 0 }));
		}
	}
}
