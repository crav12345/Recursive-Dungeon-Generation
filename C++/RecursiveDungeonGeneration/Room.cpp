#include "Room.h"

Room::Room() {
	for (int i = 0; i < sizeof(coordinates) / sizeof(int); i++) {
		coordinates[i] = -1;
	}

	for (int i = 0; i < sizeof(doors) / sizeof(Door); i++) {
		doors[i] = Door();
	}
}

Room::Room(int newCoordinates[2], Door newDoors[4]) {
	for (int i = 0; i < sizeof(coordinates) / sizeof(int); i++) {
		coordinates[i] = newCoordinates[i];
	}

	for (int i = 0; i < sizeof(doors) / sizeof(Door); i++) {
		doors[i] = newDoors[i];
	}
}