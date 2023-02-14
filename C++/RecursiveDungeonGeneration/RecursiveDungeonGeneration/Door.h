#pragma once

class Door
{
	public:
		bool isOpen;
		char direction;

		Door();

		Door(bool isOpen, char direction);

		void setOpen(bool newIsOpen);

		bool getOpen();

		bool canOpen(int coordinates[2], int n, int m);
};