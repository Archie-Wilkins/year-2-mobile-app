**Mobile Development 2021/22 Portfolio**
# API description

Your username: `C2043958`

Student ID: `C2043958`

_Complete the information above and then write your 300-word API description here.__


`RecyclerViews` are used frequently (e.g. Grid view of events in dashboard and list of attendees). Listview and gridview were avoided as `Recyclerview` offers better performance by default and greater developer control(https://www.thedroidsonroids.com/blog/difference-between-listview-recyclerview). When displaying pre-set elements e.g. creating a form I used `ScrollView` instead of `RecyclerView` as `RecyclerView` is intended for displaying large sets of data dynamically (https://developer.android.com/guide/topics/ui/layout/recyclerview), both are scrollable.
      

`Room` was used to persistently store event data, this reduced needed API calls and made data access easier going between activities and fragments, rather than bunding large amounts of data from API calls, just the event Id can be passed into fragments and then the Room database can be accessed from the new activity/fragment. `Room` event data is refreshed when accessing the dashboard so inter-session persistence isn't required.   


`SharedPreferences` are used to save user data, shared preferences are ideal because it saves data across sessions (https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples/), and only a small amount of data is stored and rarely changed, important because shared preferences use expensive operations (https://developer.android.com/reference/android/content/SharedPreferences)


`Fragments` promoted code reusability e.g. the frequently used loading-screen fragment and enabled easy transiting from a loading segment to data-displaying segment when loading data. `Activities` alone could've been used as navigation elements (e.g. `BottomNavigationView`) that would have to be unnecessarily reloaded on each new activity werne't used. However, fragments promoted better code modularity and seperation of concerns. 

`TabLayout` was used to allow users to easily pivot between two views (event information and event attendees). Other navigation options are available such as `BottomNavigationView` and `Navigation Drawer`, but I'd argue these would make it harder to quickly flick back and forth between the two views. If I had several screens the user needed to access then these options would be more suitable. 

<!-- 
Formative comments from Sandy

– Make sure you adhere to the word limit. One of the marking criteria is "conciseness". Part of the challenge of the excerise is to be concise and selective about what you cover. So do try and get your word count down.

– The strongest parts of this document is where you are able to offer the reader some insight into where alternatives existed, and why you went with the solution you went with over other options. You do this well, for example with the `RecyclerView' discussion. In other places, you have just stated what you have done e.g., with Volley or `TabLayout`. Remember, there's no expectation that this section is exhaustive; it's not necessary to include every API choice. What you want to do is focus on the most important/critical choices that you've made. Preferably, with these choices, you'll be able to offer the reader some insight into what the alternatives were and what the technical merits of your choice were. I think you can improve this by covering fewer things, but in more detail and giving more insight into the competing options.

– Thanks for `backticking` your class names, makes it much clearer for me!

-->
