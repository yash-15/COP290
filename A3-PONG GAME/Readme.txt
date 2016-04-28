________________________________________________________

README FILE for Ping Pong: A Networked Multi-Player Game
________________________________________________________

DESIGNERS:
	
		Team SPYCODERS
		--------------
 
		Sauhard Gupta(2013ME10117) 
		Prabhu Prasad Panda(2013ME10859) 
		Yash Kumar Bansal(2013ME10742)
_______________________________________________________________________________________________________________________

1. DEPENDENCIES
---------------
		JRE 1.8 or higher
		java-json.jar

________________________________________________________________________________________________________________________

2. KEY COMBINATIONS:
--------------------

		The User needs only two key
			1. Left Arow key:- This is to move the paddle left.
			2. Right Arow key:- This is to move the paddle right.
		On pressing the appropriate key the position and velocity of the paddle are controlled. The pressing of the key gives a constant acceleration to the 			paddle, subject to a cut of speed. Once the key is released, the paddle decelerates rapidly till it stops or some other key is pressed. 
________________________________________________________________________________________________________________________

3. RULES
---------
			This is a networked multi-player Ping-Pong game that can be played on a desktop machine.

			>SERVER/CLIENT: 
			--------------	

			On running the application the player chooses between server or client. If the player chooses SERVER, he/she has enter his/her  ExportIP. If 				the player chooses CLIENT he/she has enter his/her ServerIP and Server Port.

			>BALLS:	
			-------	
			The players agree on the number of BALLS before the start of the game, which range from 1-4. The balls move in a random direction at the start 				of the game. The balls will appear at the centre of the board symmetrically and after the starting timer blows off. They will be directed 				arbitrarily towards the players	(but it is ensured that not more than one ball is directed towards any player). 

			>NUMBER OF PLAYERS: 
			-------------------			
			The game has maximum four players where each player guards his/her wall from the ball. 
 
			>NUMBER OF HUMANS & BOTS: 
			-------------------------
			This is decided by the number of player that number of player that join in the initial sever and can be maximum to 4. If less than 4 player 				join the initial server then the remaining player are computer players(designanted as BOTS).
	
			>LIVES: 
			-------
			The players agree on the number of LIVES before the start of the game, which range from 1-50. If the player choose x as the number of LIVES 				then if the ball touches the player's wall x times, the player is deemed as dead and his/her paddle is removed from the game board and just the 			wall remains.

			>SCORING:
			----------
			The number of remaining lives are visible on the status bar of each player. The lives reduces as soon the ball touches the wall of any player.

			>TYPES OF PLAYERS:
			-------------------
			Some players in the game can be manual(HUMANS), and others can be backed by the computer(BOTS).

			>WINNER:
			-------
			The player who remains alive at the end of the game is deemed as the winner.



			>CONNECTION:- 
			-------------
			Here is how different players play against each other;

				1. Single player: 
				   --------------
					If there's only one manual player, he/she has to play against computer players on his/her local machine.

				2. Multi-player:
				   -------------
					In this case, one player starts the game, and others join the game by providing the IP of the starting machine. The IP's of all 					machines involved in the game will be exchanged at this time. Once started, the game is completely peer-to-peer, meaning there 					is no central server.

			>TIMER:
			-------
			 In the beginning of the game in order to the allow every player to settle down, we will have a timer 3 ! 2 ! 1.

			>SPEED CONTROL OF THE BALL:
			---------------------------
			 The initial speed of the ball will be set by the player as Setting of the game. With every collision of the ball with the bat, the speed of 			 	 the ball increases by x%, where x can be changed by the players.

			>SEND BAT INFO TO THE OTHER MACHINES:
			-------------------------------------
			 Every time, the bat's co-ordinates, velocity or button pressed(left ,right or nothing) changes, this information is sent to other 					 local machines so that these machines could have the latest information about the bat.

			>CONNECTION OUT HANDLING:
			------------------------
			 If the connection of the control-ling machine goes out, the control is transferred to the machine with the next highest priority. This machine 			 resumes operation from the latest in-formation it has about the machine. So this highlights the importance of sending velocity and other 			 	 parameters apart form co-ordinates at every stage to other machines. Ideally only co-ordinates need to be transmitted for display in other 				 machines. But in order to handle the cases of connection failure, we need to send the entire information so that another machine can resume 				 the control operation.
	
This is a continuous action game even if a player misses the ball, the ball continues to move on the game board.
	
Here is how a simple 4-player Ping-Pong would look like:
/*** Board Configuration :
 * 					 (2) 
 * 	   //===========================\\
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	(3)||			(0,0)			||(1)
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   ||							||
 * 	   \\===========================//
 * 					 (0)
 * 
 * 		0 : DOWN  ;	 1 : RIGHT  ;  2 : UP  ;  3 : LEFT
 */
			Figure: Snap Of Game Board





















