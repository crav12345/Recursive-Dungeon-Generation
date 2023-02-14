#pragma once

#include "Door.h"

class Room
{
	public:
		int coordinates[2];
		Door doors[4];

		Room();

		Room(int newCoordinates[2], Door newDoors[4]);
};
