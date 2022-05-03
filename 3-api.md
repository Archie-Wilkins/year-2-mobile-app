**Mobile Development 2021/22 Portfolio**
# API description

Your username: `C2043958`

Student ID: `C2043958`

_Complete the information above and then write your 300-word API description here.__

### Navigation 
The app required minimal navigation, as the user can accesss everything from the dashboard but `TabLayout` was utilised to allow event organisers to conveniently swipe between viewng event information and guests.


### RecyclerViews
The app makes frequent use of `Recyclerviews` and does not utilise listview or gridview as these are both deprecated indicated by the fact they appear as legacy items within android studio. `Recyclerview` offers better performance by default and if needed greater control for the developer (https://www.thedroidsonroids.com/blog/difference-between-listview-recyclerview). 
For example I used the Recycler view `GridLayoutManager` to display data items in two coloumns. 

### Scroll Views 
When displaying pre-set elements e.g. creating a form I used scroll view instead of recycler view as Recycler view is better suited for displaying large sets of data dynamically (https://developer.android.com/guide/topics/ui/layout/recyclerview), even though both are scrollable.

### Use of On Click Methods 
Talk about using onClick methods 

### Data Management 
`Volley` was used frequently to send and recieve data from a server that I created. This was neccessary as multiple users need to be able to access the same data from different devices and this the easiest way to ensure synchronisity is to have the data push and pull to/from a single shared database.  

`Room` was used to persistently store event data, this reduced the required amount of API calls and made it easier to access data going between activities and fragments because rather than having to bundle entire data sets, just the event Id can be passed and then the Room database can be accessed from the new activity/fragment. 

`Shared Preferences` - shared preferences are used to save user login data, shared preferences are ideal because data is saved across sessions (https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples/), and only a small amount of data is stored and rarely changed, which is important because shared preferences use expesnive operations (https://developer.android.com/reference/android/content/SharedPreferences)



External Intents needed to allow users to send messages with event information via WhatsApp 

Activities - 

Fragments - 

Deep linking - 

