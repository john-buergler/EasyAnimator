# Animator
**Posn**: A positon class created for the functionality of this model. Includes two integers that represent x and y coordinates in a cartesian plane. The
purpose of this implmentation is to render the shapes that include a Posn value on a scene. The x and y coordinates will represent pixels within an animated scene.

**Shape**: The custom-created instance of a Shape object in this animator. Properties of the Shapes in this animator include integer height, integer width, 
Color color, String ID, Posn position, and ShapeType type. Height and width represent the pixel height and width values for the shape objects that
would be rendered, along with color representing the color. The shape ID is unique to each shape object as enforced by an exception, and its purpose is to be able
to identifiy which unique shape is being called to perform a specific action. ShapeType is whether a shape is circular or rectangular, again to distinguish what type of
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

**buildScene**: Must be called before the animation to establish the size of the scene along with the animation's duration in ticks. It is being assumed here that there
will be a scene creation for the animation that has specific dimensions. As for the duration, it is necessary to initialize the amount of ticks that the animation will
go on for in order to create our shapesPerTick field that is included in the model. This field is a sort of "Timeline" for the animation. When it comes to rendering
the animation, it will run through this List of Lists. Each list will represent a tick and as it works its way through the timeline it will render the shapes that are included in each tick. This is the heart of the animation.

**addShape**: Adds a shape with given specifications along with a unique ShapeID. This ShapeID will be utilized often to tell the model which shape to perform
a certain functionality on.

**deleteShape**: This removes a shape from both the shape list and the entire timeline as a whole so that it no longer exists.

**disappearShape**: Say the user does not want a shape in the scene for a certain amount of time. Disappear shape allows the user to remove the shape on screen for
a certain amount of ticks. This will help visualize what removing the shape would look like.

**moveShape**: This allows the user to specify a specific shape that they want to move from a given start position (Posn) to an end position (Posn). This happens
smoothly over the given time interval, startTime (int) to endTime(int). A rate is calculated within the method with integer division.

**changeColor**: Simialar to moveShape, however the functionality changes the color of the shape from one color to the next using RGB values that change over time.
All the user has to do is input the start color, the end color, the start time, the end time, and the ShapeID that they want to change the color.

**changeSize**: Allows the user to change the dimensions of the Shape with the given ShapeID over a period of time.
