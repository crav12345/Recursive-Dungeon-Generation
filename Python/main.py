import random
import math
from room import Room
from door import Door

# ANSI escape code colors for output.
TEXT_BLACK = "\u001b[30m"
TEXT_RED = "\u001b[31m"
TEXT_GREEN = "\u001b[32m"
TEXT_YELLOW = "\u001b[33m"
TEXT_BLUE = "\u001b[34m"
TEXT_MAGENTA = "\u001b[35m"
TEXT_CYAN = "\u001b[36m"
TEXT_WHITE = "\u001b[37m"


def main():
    # Get world dimensions, n and m, from the user.
    n = int(input("Enter world height 'n' as an Int: "))
    m = int(input("Enter world length 'm' as an Int: "))

    # Generate and report the world map.
    print("Generating dungeon!")
    world_map = generate_dungeon(n, m)
    print_map(world_map)


def generate_dungeon(n, m):
    # Used to initialize world map matrix and ensure na and m are non-zero.
    world_n = n
    world_m = m

    # Safety check to make sure n and m are non-zero.
    if n < 1:
        world_n = 1
    if m < 1:
        world_m = 1

    # Initialize worldMap matrix.
    world_map = [[Room(None, None) for _ in range(world_m)] for _ in range(world_n)]

    # Place dungeon origin. In this case, in the center of the map.
    origin = Room(
        [Door(False, 0), Door(False, 1), Door(False, 2), Door(False, 3)],
        [math.floor(m/2), math.floor(n/2)]
    )
    world_map[origin.coordinates[1]][origin.coordinates[0]] = origin

    # Call the recursive pathify() method on our origin.
    pathify(origin, world_map)

    # Send world map back to main().
    return world_map


def pathify(current_room, world_map):
    # Iterate through every door in the room.
    for door in current_room.doors:
        # Check if this door can be opened.
        if door.can_open(current_room, world_map):
            # Flip a coin to see whether to open the door.
            if random.randint(0, 1) == 1:
                # Open the door.
                door.is_open = True

                # Locate next position and prepare to open a door there.
                if door.direction == 0:
                    x_next = current_room.coordinates[0]
                    y_next = current_room.coordinates[1] - 1
                    new_doors = [False, False, True, False]
                elif door.direction == 1:
                    x_next = current_room.coordinates[0] + 1
                    y_next = current_room.coordinates[1]
                    new_doors = [False, False, False, True]
                elif door.direction == 2:
                    x_next = current_room.coordinates[0]
                    y_next = current_room.coordinates[1] + 1
                    new_doors = [True, False, False, False]
                else:
                    x_next = current_room.coordinates[0] - 1
                    y_next = current_room.coordinates[1]
                    new_doors = [False, True, False, False]

                # If there isn't already a room in the adjacent spot, place one.
                next_position = world_map[y_next][x_next]
                if next_position.doors is None:
                    new_room = Room(
                        [
                            Door(new_doors[0], 0),
                            Door(new_doors[1], 1),
                            Door(new_doors[2], 2),
                            Door(new_doors[3], 3)
                        ],
                        [x_next, y_next]
                    )
                    world_map[y_next][x_next] = new_room

                    # Recursive pathify() call on newly placed room.
                    pathify(new_room, world_map)


def print_map(world_map):
    for row in world_map:
        for col in row:
            if col.doors is None:
                print(TEXT_MAGENTA + "0", end="")
            else:
                door_counter = 0
                for door in col.doors:
                    if door.is_open is True:
                        door_counter += 1

                if door_counter == 1:
                    print(TEXT_RED + "X", end="")
                elif door_counter == 2:
                    print(TEXT_YELLOW + "X", end="")
                elif door_counter == 3:
                    print(TEXT_GREEN + "X", end="")
                else:
                    print(TEXT_CYAN + "X", end="")

        print()


if __name__ == '__main__':
    main()
