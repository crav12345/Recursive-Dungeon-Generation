fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments at Run/Debug configuration
    println("Program arguments: ${args.joinToString()}")


}

/** Procedurally builds a game world with room objects on a 2D array */
private fun generateDungeon(n: Int, m: Int): Array<Array<Room>> {
    // Initialize our worldMap as a 2D array of Rooms.
    val worldMap = Array(n) { Array(m) {Room()}}

    // Place dungeon origin. In this case, it's at the center of the world.
    val origin = Room(
        arrayOf(
            Door(false, false, 0),
            Door(false, false, 1),
            Door(false, false, 2),
            Door(false, false, 3)
        ),
        arrayOf(n/2,m/2)
    )
    worldMap[origin.coordinates[0]][origin.coordinates[1]] = origin

    // Call the recursive pathify() method on our origin.
    pathify(origin, worldMap)

    // Send our new map back to main().
    return worldMap
}

/** Recursively snakes paths out of the walls of a room */
private fun pathify(currentRoom: Room, worldMap: Array<Array<Room>>) {
    // Iterate through every door in the room.
    for (i in 0 until currentRoom.doors.size) {
        // TODO: Check if this door can be opened.
        if (currentRoom.doors[i].canOpen(worldMap)) {
            // Flip a coin to see whether to open a door.
            if((0..1).random() == 1) {
                // Open the door.
                currentRoom.doors[i].isOpen = true

                // TODO: Place a room down if one doesn't already exist.
                // TODO: Recursive pathify() call on the new room.
            }
        }
    }
}