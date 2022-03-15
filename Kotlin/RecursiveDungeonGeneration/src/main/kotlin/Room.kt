class Room(var doors: Array<Door>, var coordinates: Array<Int>) {
    constructor(): this (
        arrayOf(Door(), Door(), Door(), Door()),
        arrayOf(0, 0)
    )
}