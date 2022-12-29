using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Door
{
    public bool isOpen { get; set; }
    public char direction { get; set; }

    public Door(bool newIsOpen, char newDirection)
    {
        isOpen = newIsOpen;
        direction = newDirection;
    }

    public bool CanOpen(Room parentRoom, int n, int m)
    {
        bool canOpen = true;

        // Can't open an already opened door.
        if (isOpen)
        {
            canOpen = false;
        }
        // Check if we're at the end of the map.
        else if (direction == 'N' && parentRoom.coordinates[1] < 1)
        {
            canOpen = false;
        }
        else if (direction == 'E' && parentRoom.coordinates[0] > m - 2)
        {
            canOpen = false;
        }
        else if (direction == 'S' && parentRoom.coordinates[1] > n - 2)
        {
            canOpen = false;
        }
        else
        {
            if (parentRoom.coordinates[0] < 1)
            {
                canOpen = false;
            }
        }

        return canOpen;
    }
}
