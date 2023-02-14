#include "Door.h"

Door::Door() {
	isOpen = false;
	direction = '0';
}

Door::Door(bool newIsOpen, char newDirection) {
	isOpen = newIsOpen;
	direction = newDirection;
}

void Door::setOpen(bool newIsOpen) {
    isOpen = newIsOpen;
}

bool Door::getOpen() {
    return isOpen;
}

bool Door::canOpen(int coordinates[2], int n, int m) {
	bool canOpen = true;

    if (isOpen)
    {
        canOpen = false;
    }
    else if (direction == 'N' && coordinates[1] < 1)
    {
        canOpen = false;
    }
    else if (direction == 'E' && coordinates[0] > m - 2)
    {
        canOpen = false;
    }
    else if (direction == 'S' && coordinates[1] > n - 2)
    {
        canOpen = false;
    }
    else
    {
        if (coordinates[0] < 1)
        {
            canOpen = false;
        }
    }

	return canOpen;
}
