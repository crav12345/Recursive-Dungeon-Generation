const val TEXT_RED = "\u001B[31m"
const val TEXT_GREEN = "\u001B[32m"
const val TEXT_YELLOW = "\u001B[33m"
const val TEXT_PURPLE = "\u001B[35m"
const val TEXT_CYAN = "\u001B[36m"

fun main(args: Array<String>) {
    // Program arguments at Run/Debug configuration
    println("Program arguments: ${args.joinToString()}")

    // Get number of rows, 'n', from user.
    print("Enter world height 'n' as an Int: ")
    val n = readLine()!!.toInt()

    // Get number of columns, 'm', from user.
    print("Enter world length 'm' as an Int: ")
    val m = readLine()!!.toInt()

    println("Generating Dungeon!")

    val worldMap = generateDungeon(n, m)

    printMap(worldMap)
}

/** Prints values of a provided matrix of Room objects */
private fun printMap(worldMap: Array<Array<Room>>) {
    for (i in 0 until worldMap.size) {
        for (j in 0 until worldMap[0].size) {
            if (worldMap[i][j].exists) {
                var doorCounter = 0
                for (r in 0 until worldMap[i][j].doors.size) {
                    if (worldMap[i][j].doors[r].isOpen) {
                        doorCounter++
                    }
                }

                if (doorCounter == 1)
                    print(TEXT_GREEN + "X")
                else if (doorCounter == 2)
                    print(TEXT_CYAN + "X")
                else if (doorCounter == 3)
                    print(TEXT_YELLOW + "X")
                else
                    print(TEXT_PURPLE + "X")
            }
            else {
                print(TEXT_RED + "0")
            }
        }
        println()
    }
}

/** Procedurally builds a game world with room objects on a 2D array */
private fun generateDungeon(n: Int, m: Int): Array<Array<Room>> {
    // Initialize our worldMap as a 2D array of Rooms.
    val worldMap = Array(n) { Array(m) {Room()}}

    // Place dungeon origin. In this case, it's at the center of the world.
    val origin = Room(
        arrayOf(
            Door(false, 0),
            Door(false, 1),
            Door(false, 2),
            Door(false, 3)
        ),
        arrayOf(m/2, n/2),
        true
    )
    worldMap[origin.coordinates[1]][origin.coordinates[0]] = origin

    // Call the recursive pathify() method on our origin.
    pathify(origin, worldMap)

    // Send our new map back to main().
    return worldMap
}

/** Recursively snakes paths out of the walls of a room */
private fun pathify(currentRoom: Room, worldMap: Array<Array<Room>>) {
    // Iterate through every door in the room.
    for (i in 0 until currentRoom.doors.size) {
        // Check if this door can be opened.
        if (currentRoom.doors[i].canOpen(currentRoom, worldMap)) {
            // Flip a coin to see whether to open a door.
            if((0..1).random() == 1) {
                // Open the door.
                currentRoom.doors[i].isOpen = true

                // Place a room down if one doesn't already exist.
                if (i == 0) {
                    val nextPosition = worldMap[currentRoom.coordinates[1] - 1][currentRoom.coordinates[0]]
                    if (!nextPosition.exists) {
                        val newRoom = Room(
                            arrayOf(
                                Door(false, 0),
                                Door(false, 1),
                                Door(true, 2),
                                Door(false, 3)
                            ),
                            arrayOf(
                                currentRoom.coordinates[0],
                                currentRoom.coordinates[1] - 1
                            ),
                            true
                        )
                        worldMap[currentRoom.coordinates[1] - 1][currentRoom.coordinates[0]] = newRoom

                        // Recursive pathify() call on the new room.
                        pathify(newRoom, worldMap)
                    }
                } else if (i == 1) {
                    val nextPosition = worldMap[currentRoom.coordinates[1]][currentRoom.coordinates[0] + 1]
                    if (!nextPosition.exists) {
                        val newRoom = Room(
                            arrayOf(
                                Door(false, 0),
                                Door(false, 1),
                                Door(false, 2),
                                Door(true, 3)
                            ),
                            arrayOf(
                                currentRoom.coordinates[0] + 1,
                                currentRoom.coordinates[1]
                            ),
                            true
                        )
                        worldMap[currentRoom.coordinates[1]][currentRoom.coordinates[0] + 1] = newRoom

                        // Recursive pathify() call on the new room.
                        pathify(newRoom, worldMap)
                    }
                }
                else if (i == 2) {
                    val nextPosition = worldMap[currentRoom.coordinates[1] + 1][currentRoom.coordinates[0]]
                    if (!nextPosition.exists) {
                        val newRoom = Room(
                            arrayOf(
                                Door(true, 0),
                                Door(false, 1),
                                Door(false, 2),
                                Door(false, 3)
                            ),
                            arrayOf(
                                currentRoom.coordinates[0],
                                currentRoom.coordinates[1] + 1
                            ),
                            true
                        )
                        worldMap[currentRoom.coordinates[1] + 1][currentRoom.coordinates[0]] = newRoom

                        // Recursive pathify() call on the new room.
                        pathify(newRoom, worldMap)
                    }
                }
                else {
                    val nextPosition = worldMap[currentRoom.coordinates[1]][currentRoom.coordinates[0] - 1]
                    if (!nextPosition.exists) {
                        val newRoom = Room(
                            arrayOf(
                                Door(false, 0),
                                Door(true, 1),
                                Door(false, 2),
                                Door(false, 3)
                            ),
                            arrayOf(
                                currentRoom.coordinates[0] - 1,
                                currentRoom.coordinates[1]
                            ),
                            true
                        )
                        worldMap[currentRoom.coordinates[1]][currentRoom.coordinates[0] - 1] = newRoom

                        // Recursive pathify() call on the new room.
                        pathify(newRoom, worldMap)
                    }
                }
            }
        }
    }
}