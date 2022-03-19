class Door(var isOpen: Boolean, var direction: Int) {
    constructor(): this (
        false,
        0
    )

    fun canOpen(parentRoom: Room, worldMap: Array<Array<Room>>): Boolean {
        // Variable to store our return value.
        var canOpen = true

        // Can't open if the door is already open.
        if (this.isOpen)
            canOpen = false

        // Check if we're at the end of the matrix based on direction.
        else {
            // North
            if (this.direction == 0) {
                if (parentRoom.coordinates[1] < 1) {
                    canOpen = false
                }
            }
            // East
            else if (this.direction == 1) {
                if (parentRoom.coordinates[0] > worldMap[0].size - 2) {
                    canOpen = false
                }
            }
            // South
            else if (this.direction == 2) {
                if (parentRoom.coordinates[1] > worldMap.size - 2) {
                    canOpen = false
                }
            }
            // West
            else {
                if (parentRoom.coordinates[0] < 1) {
                    canOpen = false
                }
            }
        }

        // Return our result.
        return canOpen
    }
}