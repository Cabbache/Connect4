# Connect4
Play connect 4 with the minimax algorithm

### How it works ###
This implementation of the minimax algorithm works by looking ahead by a specific number of moves and after getting all destinies for each possible move, it would have the **double[] bs** array representing the score for each move. The index of the highest number is the number of the best slot that should be the next move.

### How far should it look ##
7 is recommended. The duration of the calculation of the next move grows exponentially with that number. If 1 is entered, then it can only be certain where to throw if it's the winning move. By 2 it can 'block' the user in basic situations and if it's 7, it realises that the first move should always be in slot 4. Any more than 8 can cause the computation to take too long and the game becomes unplayable. Theoretically, 42 would mean that the game would be played perfectly and that the user wouldn't get any more than a draw if they do not have the first move.

### Details on the algorithm ###
The method **percieve(,,,)** plays the most important role. It works recursively and is called for each possible branch by the **think(,,)** method. **percieve(,,,)** draws a tree where treminal nodes are possible ways of finishing the game. In the diagram, red terminal nodes represent victory by opponent, green terminal nodes represent victory by the algorithm and grey nodes are neutral. For each unique terminal colored node, **7^(depth-hyperLength) * p** is added to the score to that branch where depth is how deep further it should go, hyperLength is how deep it should look (entered by the user), 7 is the width of the game and p is 1 or -1 depending on which player is playing.
