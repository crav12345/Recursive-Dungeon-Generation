# Recursive-Dungeon-Generation
This repository contains implementations of a recursive algorithm, authored by Christopher Ravosa, for generating dungeons on a matrix. The general idea with this algorithm is that a dungeon is created by snaking paths out of a starting room known as the "origin" of the dungeon. For a more detailed overview of the algorithm, see the below sections.

## Algorithm Summary
The algorithm works by placing a "room" object in an arbitrary position on a two-dimensional matrix. The algorithm then flips a coin on each door associated with the initial room object to determine whether it will open and attach the current room to another room in that direction. In the event another room is placed, the new room is instantiated with an open door connecting it to the previous room. Before the rest of the doors in the initial room are handled, the algorithm recursively calls itself on the new room, handling the new room's doors first. This process continues until there are no more opportunities for a room to instantiate off of an existing room. This can mean the path has reached the end of the map, that another room already exists in the desired pathing direction, or that the algorithm determined not to place a room in a given direction at all. 

In all cases, the coin flip occurs prior to a room being placed or a connection being established between a new room and a pre-existing room. This means that simply because two rooms are adjacent to each other, does not mean they will always connect. This creates a maze-like structure and ensures that all rooms are reachable from the initial room placement known as the "origin".

There are many modifications which can be made to this algorithm. For example, the algorithm can be modified to work on a three-dimensional array of rooms with six doors. The fifth and sixth doors would exist on the floor and ceiling, effectively creating the potential for stairwells to exist. This modification allows seperate floors to exist in the dungeon. Other modifications include requirements regarding a minimum number of rooms or how frequently a room can be instantiated in a specific direction.

The documentation of this repository, however, will exclusively discuss dungeon generation using this algorithm on a two-dimensional matrix. Check back in the future to see implementations of this algorithm in different languages and with different modifications.

## Visualized Example

<p align="center">
  <img
       src="https://piskel-imgstore-b.appspot.com/img/26852594-a49e-11ec-bfc6-3d5395cde8f1.gif"
       alt="Christopher Ravosa's Recursive Dungeon Generation algorithm visualized in a gif"
  />
</p>

## Pseudocode
The algorithm makes use of a _Room_ class. _Room_ defines an object containing an array of four (4) booleans. Each boolean represents one (1) of the four (4) doors which may exist on each of the walls of a standard room. Each index of the _doors_ array must be associated with one of the cardinal directions. For example, 0 -> North, 1 -> East, 2 -> South, 3 -> West. If a boolean in the _doors_ array is _true_, that means the associated door exists and attaches this instance of _Room_ to another instance of _Room_ in the direction associated with that index of the _doors_ array.

This algorithm generates a dungeon (game world) on a matrix containing instances of the _Room_ class. The matrix can be characterized by the dimensions _n x m_, where _n_ is row length and _m_ is column length. This algorithm works on both square matrices and matrices where row and column lengths differ. Included below is a pseudocode representation of this algorithm before any modifications have been made for generating more diverse dungeons.

**_GenerateDungeon(Int n, Int m)_** <br />
&nbsp;&nbsp;&nbsp;_instantiate the matrix, worldMap, which will be returned_ <br />
&nbsp;&nbsp;&nbsp;_place a room, origin, in the matrix_ <br />
&nbsp;&nbsp;&nbsp;_pathify(origin)_ <br />
&nbsp;&nbsp;&nbsp;_return worldMap_ <br />

**_pathify(Object room)_** <br />
&nbsp;&nbsp;&nbsp;_for each door in this room_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_generate a percentage x between 0 and 100_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_if x >= 50_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_set this door to true to open it_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_if the adjacent spot in that direction on worldMap does NOT have a room_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_place a room, newRoom, on that index of the matrix_ <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_pathify(newRoom)_ <br />
