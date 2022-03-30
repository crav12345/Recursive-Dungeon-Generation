class Door:
    def __init__(self, is_open, direction):
        self.is_open = is_open
        self.direction = direction

    def can_open(self, parent_room, world_map):
        # Stores return value.
        can_open = True

        # Can't open an already open door.
        if self.is_open:
            can_open = False
        # Check if we're at the end of the matrix based on door direction.
        else:
            if self.direction == 0:
                if parent_room.coordinates[1] < 1:
                    can_open = False
            elif self.direction == 1:
                if parent_room.coordinates[0] > len(world_map[0]) - 2:
                    can_open = False
            elif self.direction == 2:
                if parent_room.coordinates[1] > len(world_map) - 2:
                    can_open = False
            else:
                if parent_room.coordinates[0] < 1:
                    can_open = False

        # Return result.
        return can_open
