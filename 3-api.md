**Mobile Development 2021/22 Portfolio**
# API description

Your username: `C2043958`

Student ID: `C2043958`

_Complete the information above and then write your 300-word API description here.__

`TabLayout` allowed event organisers to conveniently swipe between viewing event information and guests, no other navigation elements were needed because everything is accessible from the dashboard .

`RecyclerViews` are used frequently (e.g. Grid view of events in dashboard and list of attendees). Listview and gridview were avoided as `Recyclerview` offers better performance by default and greater developer control(https://www.thedroidsonroids.com/blog/difference-between-listview-recyclerview). When displaying pre-set elements e.g. creating a form I used `ScrollView` instead of `RecyclerView` as `RecyclerView` is intended for displaying large sets of data dynamically (https://developer.android.com/guide/topics/ui/layout/recyclerview), both are scrollable.
 
`Volley` is used to access the server API that I created so users can retrieve and update data from a shared-database.     

`Room` was used to persistently store event data, this reduced needed API calls and made data access easier going between activities and fragments, rather than bunding large amounts of data from API calls, just the event Id can be passed into fragments and then the Room database can be accessed from the new activity/fragment. `Room` event data is refreshed when accessing the dashboard so inter-session persistence isn't required.  

`SharedPreferences` are used to save user data, shared preferences are ideal because it saves data across sessions (https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples/), and only a small amount of data is stored and rarely changed, important because shared preferences use expensive operations (https://developer.android.com/reference/android/content/SharedPreferences)

`External Intents` were needed to conveniently allow users to send messages with event details via WhatsApp. `Deep Linking` allowed event-guests to access specific event data via a link within the app.

`Fragments` promoted code reusability e.g. the frequently used loading-screen fragment and enabled easy transiting from a loading segment to data-displaying segment when loading data. Activities could've been used but using fragments promoted better code modularity and seperation of concerns.  




