# ThreadCheckArray

ThreadCheckArray is a command-line Java program that searches for a subset of
integer values whose sum equals a user-supplied target. It uses two worker
threads to search complementary parts of the recursive search tree.

## Requirements

- A Java Development Kit (JDK) with `javac`, `java`, and `javadoc` available on
  `PATH`.
- At least one integer input value. The current recursive implementation
  expects a non-empty list.

## Build and run

From the project root, compile the sources and run the application:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out src\SharedData.java src\ThreadCheckArray.java src\TestThreadCheckArray.java
java -cp out TestThreadCheckArray
```

The program prompts for the number of values, each value, and the target sum.
If a solution is found, it prints the input indexes, values, and a `C` row of
flags: `1` means the corresponding value is included in the reported subset;
`0` means it is not. If no solution is found, it prints `Sorry`.

For example, with values `3, 5, 7` and target `8`, one possible solution is
`3 + 5`. The corresponding flags are `1, 1, 0`.

## Recursive multithreaded algorithm

The problem is to determine whether there is a subset of the input values
whose sum is the target. For a current search prefix and remaining target, the
recursive method explores two choices for the last value in that prefix:

1. Include the value, subtracting it from the remaining target.
2. Exclude the value, leaving the remaining target unchanged.

When one value remains, the recursion checks whether it completes the remaining
sum (or whether the remaining sum is zero). The recursion stops exploring when
a worker observes that a solution has already been found.

The application starts two `ThreadCheckArray` workers. For lists with more than
one value, `thread1` starts with the final value included, while `thread2`
starts with it excluded. Each worker then explores its branch recursively.
Searching every possible subset takes **O(2^n)** time in the worst case and
**O(n)** recursion-stack space, where `n` is the number of input values. The
two workers divide the initial search branches; they do not change the
worst-case complexity.

### Shared state and synchronization

Both workers refer to the same `SharedData` instance:

- The input list and target sum are shared for reading.
- Each worker builds its own `boolean[]` solution flags.
- The shared `flag` indicates that a worker has found a solution.
- Accesses that check or update the shared found flag, read initial shared
  values in the worker constructor, and publish the result flags synchronize
  on the `SharedData` object.
- The main thread joins both workers before inspecting the result.

The input list is not modified during the search. Synchronization around the
shared found flag lets workers notice a completed search and avoid further
work.

## Data structure

The input collection is an `ArrayList<Integer>` throughout the application.
`TestThreadCheckArray` appends values as they are read, `SharedData` stores the
list, and `ThreadCheckArray` accesses elements by index with `get()` and
checks the list length with `size()`.

The solution markers remain `boolean[]`; they are not converted to a list.
Both shared and worker result arrays are initialized to the input list's
`size()`, so result index `i` corresponds to input index `i`.

## Javadoc

The Java classes and their public APIs include Javadoc comments for their
purpose, parameters, return values, and shared state. Generic type names in
Javadoc use `{@code ...}` where necessary, preventing type parameters such as
`<Integer>` from being interpreted as unknown HTML tags.

Generate and validate the API documentation from the project root:

```powershell
javadoc -Xdoclint:all -d docs src\SharedData.java src\ThreadCheckArray.java src\TestThreadCheckArray.java
```

Open `docs\index.html` in a browser to browse the generated documentation.

## Authors and Git collaboration

Individual author names and GitHub identities are not specified in the source
files or project materials currently available in this repository. Add the
actual contributors and their agreed roles here rather than attributing work
to unverified identities.

Typical collaboration responsibilities for this project are:

| Git collaboration role | Responsibility |
| --- | --- |
| Author / implementer | Develops the algorithm or application changes and submits them in a branch or pull request. |
| Reviewer | Reviews the change for correctness, clarity, and consistency with the project requirements. |
| Integrator / maintainer | Merges approved changes into the shared project branch and keeps the project buildable. |

These are role descriptions, not claims about specific individuals or
contributors to this repository.

## License

This project is designated as being under the **Braude College Academic
License**. The license text and its specific terms are not included in the
repository; consult the applicable Braude College or course materials for the
authoritative license and permissions.