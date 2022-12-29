using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DungeonGenerator : MonoBehaviour
{
    // Dungeon dimensions.
    [SerializeField] private int n;
    [SerializeField] private int m;

    // Start is called before the first frame update
    void Start()
    {
        // Generate and report the world.
        Room[,] worldMap = GenerateDungeon(n, m);
        PrintMap(worldMap);
    }

    // Generates data to spawn an NxM dungeon.
    public static Room[,] GenerateDungeon(int n, int m)
    {
        // Safety check to make sure map meets minimum dimension requirements.
        if (n < 1) n = 1;
        if (m < 1) m = 1;

        // Initialize world map array.
        Room[,] worldMap = new Room[n, m];

        // Instantiate origin room.
        Room origin = new Room(new Door[] { new Door(false, 'N'), new Door(false, 'E'), new Door(false, 'S'), new Door(false, 'W') }, new int[] { (int)Mathf.Floor(m / 2), (int)Mathf.Floor(n / 2) });

        // Place origin. In this case, we want it at the center of the map.
        worldMap[origin.coordinates[1], origin.coordinates[0]] = origin;

        // Recursively pathify map outward from the origin.
        Pathify(origin, worldMap, n, m);

        // Send our map's data back.
        return worldMap;
    }

    // Recursively snakes paths out of a room object.
    public static void Pathify(Room currentRoom, Room[,] worldMap, int n, int m)
    {
        foreach (Door currentDoor in currentRoom.doors)
        {
            if (currentDoor.CanOpen(currentRoom, n, m))
            {
                // Flip a coin to see whether to open a door.
                if (Random.Range(0, 2) == 1)
                {
                    int xNext = 0;
                    int yNext = 0;
                    bool[] newDoors = new bool[4];

                    // Open the door.
                    currentDoor.isOpen = true;

                    // Locate the next room and open a door there.
                    if (currentDoor.direction == 'N')
                    {
                        xNext = currentRoom.coordinates[0];
                        yNext = currentRoom.coordinates[1] - 1;
                        newDoors = new bool[] { false, false, true, false };
                    }
                    else if (currentDoor.direction == 'E')
                    {
                        xNext = currentRoom.coordinates[0] + 1;
                        yNext = currentRoom.coordinates[1];
                        newDoors = new bool[] { false, false, false, true };
                    }
                    else if (currentDoor.direction == 'S')
                    {
                        xNext = currentRoom.coordinates[0];
                        yNext = currentRoom.coordinates[1] + 1;
                        newDoors = new bool[] { true, false, false, false };
                    }
                    else
                    {
                        xNext = currentRoom.coordinates[0] - 1;
                        yNext = currentRoom.coordinates[1];
                        newDoors = new bool[] { false, true, false, false };
                    }

                    // Place room in adjacent spot if one isn't already there.
                    Room nextRoom = worldMap[yNext, xNext];

                    if (nextRoom == null)
                    {
                        Room newRoom = new Room
                        (
                            new Door[]
                            {
                                new Door(newDoors[0], 'N'),
                                new Door(newDoors[1], 'E'),
                                new Door(newDoors[2], 'S'),
                                new Door(newDoors[3], 'W')
                            },
                            new int[] { xNext, yNext }
                        );
                        worldMap[yNext, xNext] = newRoom;

                        // Recursive pathify() call on newly placed room.
                        Pathify(newRoom, worldMap, n, m);
                    }
                }
            }
        }
    }

    // Prints representation of a given 2D array to the console.
    public static void PrintMap(Room[,] worldMap)
    {
        string currentRow = "";
        int doorCounter = 0;

        for (int i = 0; i < worldMap.GetLength(0); i++)
        {
            for (int j = 0; j < worldMap.GetLength(1); j++)
            {
                if (worldMap[i, j] == null)
                {
                    currentRow += "<color=blue>0</color>";
                }
                else
                {
                    doorCounter = 0;
                    foreach (Door door in worldMap[i, j].doors)
                    {
                        if (door.isOpen == true)
                        {
                            doorCounter++;
                        }
                    }

                    if (doorCounter == 0)
                    {
                        currentRow += "<color=red>X</color>";
                    }
                    else if (doorCounter == 1)
                    {
                        currentRow += "<color=orange>X</color>";
                    }
                    else if (doorCounter == 2)
                    {
                        currentRow += "<color=yellow>X</color>";
                    }
                    else if (doorCounter == 3)
                    {
                        currentRow += "<color=green>X</color>";
                    }
                    else
                    {
                        currentRow += "<color=black>X</color>";
                    }
                }

                if (j == worldMap.GetLength(1) - 1)
                {
                    Debug.Log(currentRow);
                    currentRow = "";
                }
            }
        }
    }
}