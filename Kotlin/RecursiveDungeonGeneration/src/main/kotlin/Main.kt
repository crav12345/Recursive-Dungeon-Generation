// Constants for color-coded output
const val TEXT_RED = "\u001B[31m"
const val TEXT_GREEN = "\u001B[32m"
const val TEXT_YELLOW = "\u001B[33m"
const val TEXT_PURPLE = "\u001B[35m"
const val TEXT_CYAN = "\u001B[36m"

/** Utilizes my recursive procedural generation algorithm to make a dungeon */
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
    for (i in worldMap.indices) {
        for (j in 0 until worldMap[0].size) {
            if (worldMap[i][j].exists) {
                var doorCounter = 0
                for (r in 0 until worldMap[i][j].doors.size) {
                    if (worldMap[i][j].doors[r].isOpen) {
                        doorCounter++
                    }
                }

                // Print an 'X' for each room. Color differs for number of open
                // doors.
                when (doorCounter) {
                    1 -> print(TEXT_GREEN + "X")
                    2 -> print(TEXT_CYAN + "X")
                    3 -> print(TEXT_YELLOW + "X")
                    else -> print(TEXT_PURPLE + "X")
                }
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
    // Used to identify location of next placed room.
    var xNext: Int
    var yNext: Int

    // Used to construct doors array for new rooms.
    var newDoors: Array<Boolean>

    // Iterate through every door in the room.
    for (i in 0 until currentRoom.doors.size) {
        // Check if this door can be opened.
        if (currentRoom.doors[i].canOpen(currentRoom, worldMap)) {
            // Flip a coin to see whether to open a door.
            if((0..1).random() == 1) {
                // Open the door.
                currentRoom.doors[i].isOpen = true

                // Locate next position and prepare to open a door there.
                when (i) {
                    0 -> {
                        xNext = currentRoom.coordinates[0]
                        yNext = currentRoom.coordinates[1] - 1
                        newDoors = arrayOf(false, false, true, false)
                    }
                    1 -> {
                        xNext = currentRoom.coordinates[0] + 1
                        yNext = currentRoom.coordinates[1]
                        newDoors = arrayOf(false, false, false, true)
                    }
                    2 -> {
                        xNext = currentRoom.coordinates[0]
                        yNext = currentRoom.coordinates[1] + 1
                        newDoors = arrayOf(true, false, false, false)
                    }
                    else -> {
                        xNext = currentRoom.coordinates[0] - 1
                        yNext = currentRoom.coordinates[1]
                        newDoors = arrayOf(false, true, false, false)
                    }
                }

                // If there is NOT already a room in the adjacent spot, place
                // one now.
                val nextPosition = worldMap[yNext][xNext]
                if (!nextPosition.exists){
                    val newRoom = Room(
                        arrayOf(
                            Door(newDoors[0], 0),
                            Door(newDoors[1], 1),
                            Door(newDoors[2], 2),
                            Door(newDoors[3], 3),
                        ),
                        arrayOf(
                            xNext,
                            yNext
                        ),
                        true
                    )
                    worldMap[yNext][xNext] = newRoom

                    // Recursive pathify call on newly placed room.
                    pathify(newRoom, worldMap)
                }
            }
        }
    }
}