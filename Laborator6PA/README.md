# Compulsory  
  
For Compulsory I created a shape drawer. It can draw the following shapes:  
* Circles (You can specify the colour and the radius)  
* Regular Polygons (You can specify the colour, the size, the number of edges and the rotation)  
  
I used JavaFX for the GUI.  
  
# Optional  
  
For Optional I extended the program implemented for Compulsory:  
* You can add outline to any shape, with custom colour and width  
* You can erase shapes, using the Eraser tool  
* You can Free Draw. The program will try to match what you've drawn with a pre-defined shape. It can identify lines, circles and triangles so far.  
  
Other changes:  
* Changed some names  
  
# Bonus  
  
For Bonus I once again extended the program, implementing two main features:  
* Layers  
* Action Logger  
  
The layers provide a multitude of different ways to modify the drawn shapes:  
* Any layer can be deleted by pressing (Delete) or (Backspace)  
* Any layer can be moved using (Numpad 4 -> Left) (Numpad 8 -> Up) (Numpad 6 -> Right) (Numpad 2 -> Down)  
* Any layer besides free-drawn shapes can be resized (Numpad + -> Increase size) (Numpad - -> Decrease Size)  
* Any layer besides free-drawn shapes can be rotated (Numpad * -> Rotate to the right) (Numpad / -> Rotate to the left)  
All of the actions listed above can be amplified by holding (Ctrl) down  
  
The Action Logger records the last 20 actions the user has taken, and can undo/redo them:  
* You can undo your last action using (Ctrl + Z)  
* You can redo your last undone action using (Ctrl + Y)  
  
# Planned  
  
* Improve shape recognition  
* Enable changing colours of fill/stroke using layers  
* Enable copy/paste of shapes/images  
* Enable using all the Numpad actions with the mouse (move images by dragging them etc.)