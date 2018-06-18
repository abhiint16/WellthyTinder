# WellthyTinder

Documentation (Wellthy Tinder)

Best Practices

• Used MVVM (model-view-viewmodel) to make project structured (hence anyone can easily undertand the code), easily testable, easily maintainable in future, very less technical debt as it will be loosely coupled. 
• Used DataBinding with MVVM to connect view with the viewmodel.
• Implemented “Package By Component” way of structuring the project.
• Commented wherever required.


Product description
The project starts with MainActivity.kt which initializes the ViewPager and Tabs. A CustomViewPager has been made just to nullify the fragment change on swiping. Then the adapter has been set to the ViewPage. The adapter decides the fragments as per the position.

There are total 3 fragments.
• Connect

This is the main fragment where all the magic happens. We will talk about this fragment in detail after this section.


• Chat

This fragment just shows a blank page with a textview at center.


• Discover

This fragment is same as “Chat”.



Connect Fragment
This fragment uses a custom view named “TinderCardStack” to make stack of cards which can be swiped off the screen with the drag. We will talk about the custom view in detail later, for now lets see how this fragment works.
ConnectFrag has an ArrayAdapter of String type to inflate the individual cards with numbers (0,1,2…..). ConnectFrag uses ViewModel to get the data. ConnectFrag calls a function of it’s ViewModel to load the data. I am  here using a Json file that is stored in “asset folder” to get the data for the ArrayAdapter. 
I am using Json file just to make the ConnectFrag ready for production as we can anytime replace the statis Json with dynamic json file coming from the network.
Now to convert the Json file into String, I am using a utility class named “JsonToString”. 
I am using a ConnectRepository which calls the JsonToString and then work on the returned String to store the required data into an ArrayList of String.
After getting the data from Repository, ConnectFrag sets the adapter.


Custom View or TinderCardStack
TinderCardStack  extends RelativeLayout. After initialization it first makes a collection of FrameLayout(initially it will initialise the collection with 4 FrameLayout) and then takes help of another class named TinderCardStackAnimator and passes the collection of FrameLayout to it. 
TinderCardStackAnimator work on the very basic collection of Frame Layout and give the individual FrameLayout its dimension. In addition to the dimension, it also sets margin to each FrameLayout. It uses TinderCardUtil.kt to add and remove pixels and hence set the margin around the FrameLlayout. 
After all the procedure, collection comes back to TinderCardStack where we use DragGestureDetector class on it. By using this class we will come to know about any drag over screen and hence we can move the card accordingly. As already stated that we have used CustomViewPager just to nullify the effect of swipe for ViewPager, it was used coz we didn’t want the swipe to take effect on fragments rather we want all the drag motion to work only on the Cards of the Stack so as to give it Tinder like effect.
Now the DragGestureDetector provides some callback functions which will be used to know the the drag position and direction. Once the distance and direction is known, we will use a class named DefaultStackEventListener.kt which will return Boolean value according to whether the position is greater or less than the threshold value (I am providing 300 as threshold value initially). Threshold value is amount of drag has been done to any card. Threshold value is static but position is dynamic value of drag. I will use this Boolean value to know whether to retain the card in the stack or remove it from the screen. 
