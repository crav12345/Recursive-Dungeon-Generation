/**
* Implementation of a recursive algorithm for generating a map on a matrix.
* 
* A 2D vector array containing Room objects is recursively pathified until all
* available Door objects in each Room pathed have been exhausted. Please note,
* this implementation is a work in progress. It will continue to be iterated
* upon as improvements are discovered and as polish is finalized.
* 
*/

// TODO: Get rid of circular dependencies in other versions.
// TODO: Should be able to get rid of coordinates altogether.
// TODO: newDoors and newCoords should also just be arrays in other versions.
// TODO: Pathify should really return something, not just stop.
// TODO: Memory cleanup.
// TODO: Shouldn't just set dungeon[n][m] to currentRoom, it's sloppy.

#include <iostream>
#include <vector>
#include "Room.h"
using namespace std;

vector<vector<Room>> generateDungeon(int n, int m);
void pathify(Room &currentRoom, vector<vector<Room>> &dungeon, int n, int m);
void printMap(vector<vector<Room>> dungeon);

int main()
{
	int n;
	int m;
	vector<vector<Room>> dungeon;

	cout << "Enter world height, 'N' as an int: ";
	cin >> n;
	cout << "Enter world length, 'M', as an int: ";
	cin >> m;

	dungeon = generateDungeon(n, m);

	printMap(dungeon);

	return 0;
}

vector<vector<Room>> generateDungeon(int n, int m) {
	if (n < 1)
		n = 1;
	if (m < 1)
		m = 1;

	int originCoords[] = { (int)floor(m / 2), (int)floor(n / 2) };

	Door originDoors[] = {
		Door(false, 'N'),
		Door(false, 'E'),
		Door(false, 'S'),
		Door(false, 'W')
	};

	Room origin = Room(originCoords, originDoors);

	vector<vector<Room>> dungeon;

	dungeon.resize(n, vector<Room>(m, Room()));

	dungeon[origin.coordinates[1]][origin.coordinates[0]] = origin;

	pathify(origin, dungeon, n, m);

	return dungeon;
}

void pathify(Room &currentRoom, vector<vector<Room>> &dungeon, int n, int m) {
	for (Door &door : currentRoom.doors) {
		if (door.canOpen(currentRoom.coordinates, n, m)) {
			if (rand() % 2 == 1) {
				int xNext = 0;
				int yNext = 0;
				Door newDoors[4];

				door.setOpen(true);

				if (door.direction == 'N') {
					xNext = currentRoom.coordinates[0];
					yNext = currentRoom.coordinates[1] - 1;
					
					newDoors[0] = Door(false, 'N');
					newDoors[1] = Door(false, 'E');
					newDoors[2] = Door(true, 'S');
					newDoors[3] = Door(false, 'W');
				}
				else if (door.direction == 'E') {
					xNext = currentRoom.coordinates[0] + 1;
					yNext = currentRoom.coordinates[1];

					newDoors[0] = Door(false, 'N');
					newDoors[1] = Door(false, 'E');
					newDoors[2] = Door(false, 'S');
					newDoors[3] = Door(true, 'W');
				}
				else if (door.direction == 'S') {
					xNext = currentRoom.coordinates[0];
					yNext = currentRoom.coordinates[1] + 1;

					newDoors[0] = Door(true, 'N');
					newDoors[1] = Door(false, 'E');
					newDoors[2] = Door(false, 'S');
					newDoors[3] = Door(false, 'W');
				}
				else {
					xNext = currentRoom.coordinates[0] - 1;
					yNext = currentRoom.coordinates[1];

					newDoors[0] = Door(false, 'N');
					newDoors[1] = Door(true, 'E');
					newDoors[2] = Door(false, 'S');
					newDoors[3] = Door(false, 'W');
				}

				dungeon[currentRoom.coordinates[1]][currentRoom.coordinates[0]] = currentRoom;
				Room nextRoom = dungeon[yNext][xNext];

				if (nextRoom.coordinates[0] == -1) {
					int newCoords[] = { xNext, yNext };

					Room newRoom = Room(newCoords, newDoors);

					dungeon[yNext][xNext] = newRoom;

					pathify(newRoom, dungeon, n, m);
				}
			}
		}
	}
}

void printMap(vector<vector<Room>> dungeon) {
	for (vector<Room> row : dungeon) {
		for (Room room : row) {
			if (room.coordinates[0] == -1) {
				cout << "\u001b[34m0\u001b[37m";
			}
			else {
				int doorCounter = 0;

				for (Door door : room.doors) {
					if (door.getOpen()) {
						doorCounter++;
					}
				}

				if (doorCounter == 4)
					cout << "\u001b[32mX\u001b[37m";
				else if (doorCounter == 3)
					cout << "\u001b[33mX\u001b[37m";
				else if (doorCounter == 2)
					cout << "\u001b[35mX\u001b[37m";
				else if (doorCounter == 1)
					cout << "\u001b[31mX\u001b[37m";
				else
					cout << "\u001b[37mX\u001b[37m";
			}
		}

		cout << "\n";
	}
}
