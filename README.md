# Tetris
A fully playable version of Tetris that I made as a passion project while learning Java at University. 


<b>Rules:</b><br/>
Rotate and turn descending blocks to right and left until they touch the floor or other blocks. If you obtain a row completely devoid of empty spaces, then it will dissappear and you will gain points. Otherwise the screen will become increasingly cluttered until you reach a game over.


<b>Comments:</b><br/>
The code is a bit rough around the edges, and there may be a pesky bug that I'm not sure I fixed at the time because it happened so rarely that I couldn't understand what was causing it: I say "may" because I can't seem to replicate it right now, so maybe I really did fix it.<br/>
Besides that, the game works as intended and is kind of amusing, giving me a newfound appreciation for a classic I didn't particularly care for.<br/>
The hardest part of the project was trying to figure out a way to rotate the blocks with a general algorithm that worked for every type of piece and orientation: a problem that I managed to solve with some basic operations on matrices, like inversions and swapping rows with columns. Maybe there was an easier way, but back then I couldn't find it, and to be honest, neither now.<br/>
The code is commented in Italian whenever possible.


<b>Install Instructions:</b></br> 
Compile Main.java with "javac Main.java" and then execute it with "JAVA Main" on your command prompt.<br/>
I've tested this program on Windows systems only: however it should work correctly on other systems if the setSize function of the view windows work exactly as they do on Windows.
