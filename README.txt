=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: YuhanL
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. File I/O

  File I/O enables persistent state between game runs. Frogger stores the frog's positions, the positions of the
  current obstacles, and the game state including lives and how close the user is to winning (number of frogs
  delivered).

  2. Inheritance and Subtyping

  Inheritance and subtyping is best exemplified in the implementation of the Strip objects, in which I used an abstract
  strip class and its implementations to model the game's streets, rivers, and lillypad aisle.
  While the strips share many of the same functions, as all of them contain linked lists that hold objects, and they
  must also regulate these objects. However, the land and water strips also differ in that the water strips
  must also account for the frog's movement. In addition, the frog dies differently when in contact with water versus
  land strips. Thus, using an abstract class allows for the inheritance of necessary methods and the addition of new
  ones. Furthermore, dynamic dispatch is applied since the strips are added to the same list (same methods are
  applied to all) in GameCourt.

  3. JUnit Tests

  JUnit tests are used to verify that the game state is functional, including checking the interactions between the
  frog and the obstacles, as well as the state of the game. JUnit tests can be applied to my project as the tests
  do not rely on the GUI component of my game.

  4. Collections

  Linked lists are used to model the obstacles in the strips. Linked lists work best because the objects in the
  game strips are one dimensional. Furthermore, the number of obstacles in the list is constantly changing
  in my implementation based on where they appear on the screen. Furthermore, linked lists are convenient for iterating
  through objects in order, which aligns with the implementation of the obstacles in my game.

  (I did not receive specific feedback about any concept implementations)

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Game Strips: Frogger is organized into strips that the frog must surpass.

    Strip: Abstract class with general methods for all strips, including functions to add and regulate
           obstacle objects in the strips' linked lists.
    Street: Extends strip class and is used to model roads with car objects.
    River: Extends strip class and is used to model the strips of water. This class must add additional methods
           because the water functions differently-- the frog must move when landing on water obstacles and the frog
           must die if it reaches the water without landing on a floatation object.
    WinStrip: Used to model the line of lillypads (end goals) at the top of the game.
              Since they are in the water, this class extends River.

  Obstacles: These are the objects that the frog must interact with or avoid in order to pass the strips.

    Obstacle: Parent class for obstacle objects
    Log: Obstacles in river strips.
    Lillypad: Obstacles in the WinStrip
    Car: Obstacles in streets

  Frog: Models the frog sprite that the user interacts with and that regulates the game state in
        terms of progress and ending.

  GameSaver: This class implements File I/O to save the state of the game, including the location of the frog and
             obstacles along with the lives and number of frogs delivered.

  FileLineIterator: Implemented from the Twitterbot homework, this class helps to read information from the file in
                    order to load an existing game state.

  GameCourt: This class holds the primary game logic and combines all other classes to form the game.

  Direction: Enum for arrow key controls

  RunFrogger: Main class that sets up the game and GUI


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

The implications of saving my game state was challenging because the obstacles in the strips are designed to appear
at even intervals based on the timer. Thus, it was difficult to bring back the state exactly when the user reopens
the game. In the end, the storage of the existing obstacles is correct. That is, obstacles on the screen
at the time in which the user closed the window are brought back to their places. However,
the strip also continues producing its own obstacles normally, resulting in a possible influx of obstacles.

My other main challenge involved handling the many types of objects in the game and how to approach the design
of the game to remain organized while incrementally adding features to build it up.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

Overall, there is a solid separation of functionality and private state is acceptably encapsulated. If given the chance,
I may not implement subtyping for obstacle objects. Although overriding the draw functions made applying the images
to obstacles more convenient, I felt that implementing subtyping for the strips was enough. Overall, the design
of the game is acceptable but can be streamlined since I have a better picture of how the different pieces fit together.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

Although the images were drawn by me on ProCreate, they were copied from Frogger's original design.
No other tutorials were used.