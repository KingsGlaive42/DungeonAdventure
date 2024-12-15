# Dungeon Adventure

Welcome to **Dungeon Adventure**, a turn-based RPG game developed as part of a group project at the University of Washington Tacoma. Navigate through a randomly generated dungeon, collect the four Pillars of OOP, and battle enemies to escape victoriously!

---

## Table of Contents
- [Features](#features)
- [How to Play](#how-to-play)
- [Design Overview](#design-overview)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Contributors](#contributors)

---

## Features

- **Randomly Generated Dungeon**: Each playthrough offers a unique experience with randomly placed items, monsters, and dungeon layout.
- **Hero Selection**: Choose from multiple hero classes, each with unique stats and special abilities:
  - **Warrior**: Excels in melee combat with a special skill, *Crushing Blow*.
  - **[Other Classes]**: (List other classes with a brief description of their skills and stats.)
- **Turn-Based Combat**: Engage in strategic battles against enemies with varying difficulty levels.
- **Item Collection**: Discover potions, weapons, and the four Pillars of OOP hidden throughout the dungeon.
- **SQLite Integration**: Store and retrieve character and monster stats from a database.

---

## How to Play

1. **Start the Game**: Launch the game and select your hero class.
2. **Navigate the Dungeon**: Use on-screen commands to move through the dungeon.
3. **Combat**: Encounter enemies in random rooms and engage in turn-based combat.
4. **Collect the Pillars**: Find the four Pillars of OOP scattered throughout the dungeon.
5. **Escape**: Reach the exit with all four Pillars to win the game!

---

## Design Overview

The game is built using the **Model-View-Controller (MVC)** design pattern:

- **Model**: Manages game data, including hero and monster stats, inventory, and dungeon layout.
- **View**: Displays the game state to the player, handling user interface elements.
- **Controller**: Processes user input, updates the model, and refreshes the view.

**Additional Features**:
- **Database Integration**: Character stats are stored in a SQLite database, enabling easy management and extensibility.
- **Enhanced Gameplay**: Unique skills for hero subclasses add depth and strategy to combat.

---

## Technologies Used

- **Java**: Core programming language for game logic and UI.
- **SQLite**: Database for managing character and monster statistics.
- **Object-Oriented Programming (OOP)**: Utilized for clean, modular, and extensible code design.

---

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/dungeon-adventure.git
   cd dungeon-adventure
   ```
2. **Set Up the Environment**:
   - Install [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html).
   - Install SQLite or ensure it is included in your development environment.
3. **Run the Application**:
   - Compile the source code:
     ```bash
     javac -d bin src/*.java
     ```
   - Run the game:
     ```bash
     java -cp bin Main
     ```

## Contributors

- **Thomas Le** ([GitHub Profile](https://github.com/KingsGlaiver42)): Implemented Core Combat Mechanics, Hero Classes, Combat to Dungeon Transition,  and more.
- **Jayden Fausto**: ([GitHub Profile](https://github.com/NinjaPanda351)): Implemented Dungeon Traversal, GameStates, Save and Load Files, randomized Dungeon Generation, and more.
- **Aileen Rosas**: ([GitHub Profile](https://github.com/aileenrosasd)): Implemented Dungeon Monsters, connecting SQLite Database, Inventory, and more.

---

Feel free to explore the repository and leave feedback or suggestions!

