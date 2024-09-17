# Arena Battle Simulation

Arena Battle Simulator is a turn-based game where teams of fighters battle in an arena. The arena is a grid with walls around and within it, forming a labyrinth. Fighters from each team move toward their closest enemy and attack until one team wins. The game prints the states of the battle to the terminal in real time, allowing the user to watch the action unfold.
Features

    Supports multiple teams and fighters.
    Fighters have various characteristics such as:
        Max HP: Maximum health points.
        Armor: Protection from enemy attack.
        Attack: Strength of attack.
        Damage: Amount of damage dealt per attack.
        Defense: Strength of defense.
    Fighters move intelligently, finding the shortest path to enemy fighters using a pathfinding algorithm.
    Arena has walls that block movement, creating strategic challenges.
    Real-time display of the battle progress.

Getting Started
Prerequisites

    Python 3.x or above

Installation

    Clone the repository:

    bash

git clone https://github.com/USERNAME/ArenaBattleSimulator.git](https://github.com/SD135642/Game-Simulation.git
cd Game-Simulation

Install any necessary dependencies:

bash

    Prepare your input files:
        arena.txt file: Defines the dimensions of the arena and its map.
        fighters.txt file: Defines fighters' names and fighting charateristics.
        teams.txt file: Defines the teams.

Example Input Files
fighters.txt

txt

IronMan
10 5 7 6 8
Hulk
15 4 6 12 8

arena.txt

txt

5 6
#####
##..#
#...#
#.#.#
#...#
#####

teams.txt

txt

Avengers 1
Hulk
Enemies 1
IronMan

Running the Game

To start the game, run the following command in the terminal:

bash

python main.py fighters.txt arena.txt teams.txt

The game will load the teams, fighters, and arena from the text files and start the simulation. You can watch as the fighters move, attack, and battle until one team wins.
Output

The game state will be printed to the terminal at each step, showing:

    The fighters' movements across the arena.
    Health points after attacks.
    Real-time updates of which fighter is attacking whom.
    When a fighter is eliminated.


Customization

You can modify the input files to:

    Add more teams or fighters.
    Change fighter stats to create diverse abilities.
    Redesign the arena layout to create different challenges (e.g., larger arenas, more labyrinths).

Future Enhancements

    Add special abilities or power-ups for fighters.
    Introduce more complex pathfinding algorithms.
    Implement a graphical user interface (GUI) for enhanced visual gameplay.
