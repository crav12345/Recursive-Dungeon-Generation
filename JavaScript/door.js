class Door {
  constructor(isOpen, direction) {
    this.isOpen = isOpen
    this.direction = direction
  }

  canOpen(parentRoom, worldMap) {
    let canOpen = true

    // Can't open an already open door.
    if (this.isOpen) {
      canOpen = false
    }
    // Check if we're at the end of the matrix.
    else {
      if (this.direction == 0) {
        if (parentRoom.coordinates[1] < 1) {
          canOpen = false
        }
      }
      else if (this.direction == 1) {
        if (parentRoom.coordinates[0] < worldMap[0].length - 2) {
          canOpen = false
        }
      }
      else if (this.direction == 2) {
        if (parentRoom.coordinates[1] > worldMap.length - 2) {
          canOpen = false
        }
      }
      else {
        if (parentRoom.coordinates[0] < 1) {
          canOpen = false
        }
      }
    }

    return canOpen
  }
}
