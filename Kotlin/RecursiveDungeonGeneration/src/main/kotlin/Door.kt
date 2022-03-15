class Door(var isOpen: Boolean, var direction: Int) {
    constructor(): this (
        false,
        0
    )

    fun canOpen(worldMap: Array<Array<Room>>): Boolean {
        // Variable to store our return value.
        var canOpen = false

        // Can't open if the door is already open.
        if (this.isOpen)
            canOpen = false
        // else {
            // TODO: Check if we're at the end of the matrix.
            // if (endOfMatrix) {
            //  canOpen = false
            // }
            // else
            //  canOpen = true
        // }

        // Return our result.
        return canOpen
    }
}