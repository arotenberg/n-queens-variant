# n-queens-variant

I was already reasonably familiar with the standard _N_ Queens problem before working on this problem.

My first thought with this problem was to see whether the no-three-in-a-line constraint restricts the possible solutions in a way that guarantees some interesting global/algebraic property that I could take advantage of with a clever algorithm. However, I was unable to deduce anything interesting. (I did find [a related Wikipedia article](https://en.wikipedia.org/wiki/No-three-in-line_problem).) After giving up on that, I decided to just modify a standard _N_ Queens approach.

There are a couple classic solutions I'm familiar with for the standard _N_ Queens problem. The approach I went with is a straightforward row-oriented backtracking. This simple strategy is nonetheless reasonably efficient because it encodes one of the problem constraints (no two queens in the same row) directly into the structure of the search, and the backtracking prunes out much of the search space early.

Another approach I'm aware of is reducing to the Exact Cover problem and solving with Dancing Links. However, with the extra constraints required to enforce the no-three-in-a-line rule in the variant of the problem here, it was not obvious to me that Dancing Links would outperform the simple backtracking.

I considered a few different options for implementing the `isValidPlacement()` check efficiently. I thought about maintaining some kind of set of `(slope, offset)` pairs to keep track of which lines are already occupied for the three-in-a-line check. This is similar to an optimization that can be done for standard _N_ Queens where _O_(_N_) extra space is used to track currently occupied columns, diagonals, and anti-diagonals to avoid having to iterate over the search stack for these checks. However, I could not think of an efficient way to check whether a given position intersects any of the occupied lines without just iterating over all the occupied lines, which would defeat the point. (A space-partitioning tree or similar computational geometry data structure might be able to help out here?) Ultimately, I was unable to come up with anything that was obviously more efficient than just checking all previous pairs in the search stack. I did implement the column/diagonal/anti-diagonal optimization, since it allows the more expensive three-in-a-line check to be short-circuited in many cases.

I used a recursive function to implement the backtracking. If we were expecting input sizes of _N_ = 1000 or more, I would want to switch to a heap-allocated stack to avoid the possibility of stack overflow. In practice, however, my implementation will take an unreasonable amount of time to run long before stack overflow becomes a serious concern.

I wrote the code for Java 8 compatibility, since this version of Java is still widely deployed. An idiomatic Java 14 version would be identical except for possibly replacing a couple local variable types with `var`.

This program is not necessarily very representative of my software design style for larger projects. There are only two classes in `src/main/java` and one test class in `src/test/java`, because the problem didn't seem to call for more. When I'm not writing little programs with tight inner loops, I tend to use a lot more wrapper types, Guava immutable collections, etc.
