/**
 * Prints a dungeon of height 'n' and length 'm'.
 *
 * @param {number} n - the height of the matrix
 * @param {number} m - the length of the matrix
 */
function printDungeon(n, m) {
  let parent = document.getElementById("worldMap")
  let worldMap = generateDungeon(n, m)

  if (parent.firstChild) {
      removeAllChildNodes(parent)
  }

  for (let i = 0; i < worldMap.length; i++) {
    let tag = document.createElement("p")

    for (let j = 0; j < worldMap[i].length; j++) {
      let container = document.createElement("span")
      let string = ""

      if (worldMap[i][j]) {
        let doorCounter = 0
        string = "X"

        for (let d = 0; d < worldMap[i][j].doors.length; d++) {
          if (worldMap[i][j].doors[d].isOpen) {
            doorCounter++
          }
        }

        if (doorCounter == 1) {
          container.style.color = "red"
        } else if (doorCounter == 2) {
          container.style.color = "orange"
        } else if (doorCounter == 3) {
          container.style.color = "green"
        } else {
          container.style.color = "pink"
        }
      }
      else {
        string = "O"
        container.style.color = "blue"
      }
      let text = document.createTextNode(string)
      container.appendChild(text);
      tag.appendChild(container)
    }
    parent.appendChild(tag)
  }
}

/**
 * Removes all children nodes from an HTML element.
 *
 * @param {element} parent - the parent HTML element
 */
function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

/**
 * Creates a matrix of Room objects of height 'n' and length 'm'. The matrix
 * represents the map of a dungeon.
 *
 * @param {number} n - the height of the map
 * @param {number} m - the length of the map
 */
function generateDungeon(n, m) {
  // Prevent page from refreshing.
  event.preventDefault()

  // Safety check to make sure n and m are non-zero.
  if (n < 1) {
    n = 1
  }
  if (m < 1) {
    m = 1
  }

  // Create worldMap matrix. Arrays in JS are dynamic, so setting length with n
  // and m is not best practice.
  let worldMap = new Array()
  for (let i = 0; i < n; i++) {
    worldMap[i] = new Array()
    for (let j = 0; j < m; j++) {
      worldMap[i][j] = undefined
    }
  }

  // Set our origin.
  let origin = new Room(
    [
      new Door(false, 0),
      new Door(false, 1),
      new Door(false, 2),
      new Door(false, 3)
    ],
    [Math.floor(m/2), Math.floor(n/2)],
    true
  )
  worldMap[origin.coordinates[1]][origin.coordinates[0]] = origin

  // Recursively map paths out of the origin.
  pathify(origin, worldMap, n, m)

  // Send our new map back.
  return worldMap
}

/**
 * Recursively snakes paths out of the walls of a room object.
 *
 * @param {Room} currentRoom - room having paths added to it
 * @param {Array} worldMap - matrix representing the dungeon
 */
 function pathify(currentRoom, worldMap, n, m) {
   // Iterate through every door in the room.
   for (let i = 0; i < currentRoom.doors.length; i++) {
     // Check if this door can be opened.
     if (currentRoom.doors[i].canOpen(currentRoom, n, m)) {
       // Flip a coin to see whether to open a door.
       if (Math.floor(Math.random() * 2) == 1) {
         // Open the door.
         currentRoom.doors[i].isOpen = true

         // Locate the next position and prepare to open a door there.
         if (i == 0) {
           var xNext = currentRoom.coordinates[0]
           var yNext = currentRoom.coordinates[1] - 1
           var newDoors = [false, false, true, false]
         } else if (i == 1) {
           var xNext = currentRoom.coordinates[0] + 1
           var yNext = currentRoom.coordinates[1]
           var newDoors = [false, false, false, true]
         } else if (i == 2) {
           var xNext = currentRoom.coordinates[0]
           var yNext = currentRoom.coordinates[1] + 1
           var newDoors = [true, false, false, false]
         }
         else {
           var xNext = currentRoom.coordinates[0] - 1
           var yNext = currentRoom.coordinates[1]
           var newDoors = [false, true, false, false]
         }

         // If there is NOT already a room in the adjacent spot, place one now.
         let nextPosition = worldMap[yNext][xNext]
         if (nextPosition == undefined) {
           let newRoom = new Room(
             [
               new Door(newDoors[0], 0),
               new Door(newDoors[1], 1),
               new Door(newDoors[2], 2),
               new Door(newDoors[3], 3)
             ],
             [xNext, yNext],
             true
           )
           worldMap[yNext][xNext] = newRoom

           // Recursive pathify call on newly placed room.
           pathify(newRoom, worldMap, n, m)
         }
       }
     }
   }
 }
