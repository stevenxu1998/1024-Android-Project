# Android-Project
## Description
1024 puzzle game controlled by hand gestures through the motion sensors inside an andriod device
The game ends on 2 conditions:
1. No more possible for the user to make (i.e. they lose the game)
2. When the user gets a block with the number 1024 (i.e. they win the game)

## Rules of the game 
Pretty much the same rules as the 2048 puzzle game except the user must get to only 1024 to win the game

1. The game will automically generate blocks with number 2 and 4
2. Only blocks with the same number can be combined into 1 block with double that same number (i.e. block with 2 + block with 2 = block with 4)
3. Blocks can be moved in all 4 directions if there are no walls/blocks surrounding them
4. If there are walls/blocks with a different number in the way, then the block will stay in the same position 
5. Moving the phone up will cause all the blocks to move up
6. Moving the phone down will cause all the blocks to move down
7. Moving the phone right will cause all the blocks to move right
8. Moving the phone left will cause all the blocks to move left


## Programming Concepts/Design Patterns used
- Finite state machine
- Object Oriented Design 
- Phone Emulator
- UML class diagram
