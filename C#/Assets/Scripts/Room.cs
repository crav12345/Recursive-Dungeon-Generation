using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Room
{
    public Door[] doors { get; set; }
    public int[] coordinates { get; set; }

    public Room()
    {
        doors = new Door[4];
        coordinates = new int[] { 0, 0 };
    }

    public Room(Door[] newDoors, int[] newCoordinates)
    {
        doors = newDoors;
        coordinates = newCoordinates;
    }
}