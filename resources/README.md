# Animator

Changes from Assignment 5 to take note of:

1. For our AnimatorGraphicsView class which we previously had handling the timer to render the 
animation, we designed a controller to handle that aspect of the application. We created a 
controller interface specified for this type of view and implemented the interface to control the animation.
2. We cleaned up our main method. We previously had our main method throwing a lot of errors
with bad command line input, but we changed it to have display more hints. 
3. We also added a method to our JPanel. This method, called restartTick(), is used to restart the state of 
the animation to it's first tick.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Posn**: A positon class created for the functionality of this model. Includes two integers that represent x and y coordinates in a cartesian plane. The
purpose of this implmentation is to render the shapes that include a Posn value on a scene. The x and y coordinates will represent pixels within an animated scene.

**Shape**: The custom-created instance of a Shape object in this animator. Properties of the Shapes in this animator include integer height, integer width, 
Color color, String ID, Posn position, and ShapeType type. Height and width represent the pixel height and width values for the shape objects that
would be rendered, along with color representing the color. The shape ID is unique to each shape object as enforced by an exception, and its purpose is to be able
to identify which unique shape is being called to perform a specific action. ShapeType is whether a shape is circular or rectangular, again to distinguish what type of
shape to render on the screen with the given characteristics in the future.

**Oval and Rect** are implementations of the Shape interface to represent rendered images within the scene. The methods of either shape are abstracted into a AbstractShape
class which mainly holds getters for the model to use when getting information about a specific shape. Along with the getters, the AbstractShape class provides
functionalities changeColor, changeShapeDimension, setPos, and moveShape. These methods are generally self explanatory, offering the ability to change the color, the 
height and width dimensions, and the position either by a certain rate (moveShape) or to set the Shape's Posn (setPos).

**Animator Model**: This is the Model for the Easy Animator. It has fields:
1. **sceneHeight** representing the height of the animation scene.
2. **sceneWidth** representing the width of the animation scene.
3. **shapes** holds all shapes that are available to the user to transform as they please.
4. **shapesPerTick** The animation timeline per tick for the animation.

Functionalities of the model include:
1. buildScene
2. addShape
3. deleteShape
4. disappearShape
5. moveShape
6. changeColor
7. changeSize

**buildScene**: Must be called before the animation to establish the size of the scene. It is being assumed here that there
will be a scene creation for the animation that has specific dimensions. When it comes to rendering the animation, this
will set the size of the canvas.

**addShape**: Adds a shape with given specifications along with a unique ShapeID. This ShapeID will be utilized often to tell the model which shape to perform
a certain functionality on. This method also has a startOfLife and endOfLife 

**deleteShape**: This removes a shape from both the shape list and the entire timeline as a whole so that it no longer exists.

**moveShape**: This allows the user to specify a specific shape that they want to move from a given start position (Posn) to an end position (Posn). This happens
smoothly over the given time interval, startTime (int) to endTime(int). A rate is calculated within the method with integer division.

**changeColor**: Similar to moveShape, however the functionality changes the color of the shape from one color to the next using RGB values that change over time.
All the user has to do is input the start color, the end color, the start time, the end time, and the ShapeID that they want to change the color.

**changeSize**: Allows the user to change the dimensions of the Shape with the given ShapeID over a period of time.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**IViewInterface**

The IView renders a visual for the animation in a given format. There are three options for what type of view the animation should be outputted as. These three views
are contained within their own respective classes. Under the view directory. The views are as follows:

**AnimatorGraphicsView** represents the graphical view of the animation, rendering the shapes that are provided by the user's instructions.
This simply creates a frame by frame rendering of the animation and paints a JPanel for each tick in the shapesPerTick field of the model.
A user constructs this view by creating an instance whilst providing an AnimatorModel and a integer representing the speed. The speed is calculated
in ticks per second for the animation.

**AnimatorTextView** Is a text rendering of the animation which gives text-based specifics on the shapes being added and how those respective
shapes move over the duration of the animation. There is no visual to this view, it is purely to get a sense of the animation without watching
the shapes actually move. This view takes in an AnimatorModel and a Speed, similar to the graphics view above, but it also takes in a 
fileName input. This filename input either overwrites a given file with the animation that is being generated or, if no such file exists,
it will create one.

**AnimatorSVGView** In case the user would like to view the animation in the SVG format, this view takes the instructions provided by the user
and then generates a properly formatted SVG file to reproduce the animation. The user must provide an AnimatorModel, a fileName, and speed
similar to above.

**AnimatorInteractiveView** This view is an extension of the AnimatorGraphicsView, with added interactivity. The panel is relatively
self-explanatory, with a play to play the animation, a pause to pause, a toggle loopback function, a restart, and a speed setting
function to change the speed in ticks per second.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
**InteractiveViewInterface**
This interface is designed to be a key factor in connecting the view and controller of this application.
It offers a method which allows for a view to add listeners to itself, allowing controllers to be made aware
when actions in the user interface happen. The interactive view class implemented this interface and used
its singular method to add a controller so that when any of the buttons were pressed the controller would know.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
**AnimatorControllers**

For this application we designed two specific interfaces to use as controllers. One interface, the
AnimatorInteractiveController is used for interactive functionality. This includes play, pause, loop, etc.
and works only with view that have interactive functionality. The other controller interface is AnimatorVisualController and 
this interface is used to control views that are displayed graphically but don't include any 
interactive features.

**AnimatorInteractiveControllerImpl**
This class is an implementation of the interactive controller interface and serves to control interactive
animations by the use of a timer. This class takes in a model and a view so that 
it can control the animation. It also takes in a speed so that it knows how fast to render the animation.
Specific features are handled by starting or stopping the timer, restarting it, etc.

**AnimatorVisualControllerImpl**
This class is an implementation of the visual controller interface as serves much like the other controller class
by having a timer control the animation. This class takes in a model, view, and a speed, so it can control what to render and how fast to do it. 
However, since this class doesn't have interactive features it only starts the timer as its main function.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
