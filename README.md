# Tetris
A fully playable version of Tetris that I made as a passion project while learning Java at University. 
The code is a bit rough around the edges, and there may be a pesky bug that I'm not sure I fixed at the time because it happened so 
rarely that I couldn't understand what was causing it: I say "may" because I can't seem to replicate it right now, so maybe I really did fix it.
Besides that, the game works as intended and is kind of amusing, giving me a newfound appreciation for a classic I didn't particularly care for.
The hardest part of the project was trying to figure out a way to rotate the blocks with a general algorithm that worked for every type of piece 
and orientation: a problem that I managed to solve with some basic operations on matrices, like inversions and swapping rows with columns. 
Maybe there was an easier way, but back then I couldn't find it, and to be honest, neither now.

The code is commented in Italian whenever possible.

LIST OF FILES:
Main.java
Menu.java
Coordinate.java
Pezzo.java
Tetris.java

INSTALL INSTRUCTIONS: 
Compile Main.java with "javac Main.java" and then execute it with "JAVA Main" on your command prompt. 
The program is compatible with Windows systems only.
