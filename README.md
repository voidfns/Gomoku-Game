# Issues
problem:
printBoard was not printing the board after each player's turn

solution: 
since Gomoku class has 
```java
private final char[][] board = new char[WIDTH][WIDTH];
```
I did not create any getter for it. Instead of changing it, I have left it as placeholder. 
Added a new board char array and added the stones after each turn,
that way it's kept inside GameController and updated as game is played.
