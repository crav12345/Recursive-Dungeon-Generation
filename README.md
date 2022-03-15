# Recursive-Dungeon-Generation
This repository contains implementations of a recursive algorithm, authored by Christopher Ravosa, for generating dungeons on a matrix.

## Pseudocode
The algorithm makes use of a _Room_ class. _Room_ defines an object containing an array of four (4) booleans. Each boolean represents one (1) of the four (4) doors which may exist on each of the walls of a standard room. Each index of the _doors_ array must be associated with one of the cardinal directions. For example, 0 -> North, 1 -> East, 2 -> South, 3 -> West. If a boolean in the _doors_ array is _true_, that means the associated door exists and attaches this instance of _Room_ to another instance of _Room_ in the direction associated with that index of the _doors_ array.

This algorithm generates a dungeon (game world) on a matrix containing instances of the _Room_ class. The matrix can be characterized by the dimensions _n x m_, where _n_ is row length and _m_ is column length. This algorithm works on both square matrices and matrices where row and column lengths differ. Included below is a pseudocode representation of this algorithm before any modifications have been made for generating more diverse dungeons.

**_GenerateDungeon(Int n, Int m)_** <br />
&nbsp;&nbsp;&nbsp;_instantiate the matrix, worldMap, which will be returned_
&nbsp;&nbsp;&nbsp;_place a room, origin, in the matrix_ <br />
&nbsp;&nbsp;&nbsp;_pathify(origin)_ <br />
&nbsp;&nbsp;&nbsp;_return worldMap_

**_pathify(Object room)_** <br />
&nbsp;&nbsp;&nbsp;_for each door in this room_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_generate a percentage x between 0 and 100_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_if x >= 50_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_set this door to true to open it_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_if the adjacent spot in that direction on worldMap does NOT have a room_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_place a room, newRoom, on that index of the matrix_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_pathify(newRoom)_ <br />
