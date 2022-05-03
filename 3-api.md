**Mobile Development 2021/22 Portfolio**
# API description

Your username: `C2043958`

Student ID: `C2043958`

_Complete the information above and then write your 300-word API description here.__

# Need to remove 54 words 

Minimal navigation was needed as users can accesss everything from the dashboard but `TabLayout` was utilised to allow event organisers to conveniently swipe between viewing event information and guests.

`Recyclerviews` are used frequently (e.g. Grid view of events in dashboard and list of attendees). Listview and gridview were avoided as `Recyclerview` offers better performance by default and greater developer control(https://www.thedroidsonroids.com/blog/difference-between-listview-recyclerview).Android Studio also indicates listView and gridView are legacy implying they will soon be deprecated.  When displaying pre-set elements e.g. creating a form I used `ScrollView` instead of `RecyclerView` as `RecyclerView` is intended for displaying large sets of data dynamically (https://developer.android.com/guide/topics/ui/layout/recyclerview), both are scrollable.
 
`Volley` was used to send and recieve data from a server that I created, volley is used to access the server API which is needed so event data across different devices can be synchronised.    

`Room` was used to persistently store event data, this reduced the required amount of API calls and made it easier to access data going between activities and fragments because rather than having bunding large amounts of data from API calls, just the event Id can be passed and then the Room database can be accessed from the new activity/fragment. Everytime the dashboard is accessed the `Room` database is refreshed using an API call to the server, ensuring up-to-date data. 

`Shared Preferences` - shared preferences are used to save user data, shared preferences are ideal because it saves data across sessions (https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples/), and only a small amount of data is stored and rarely changed, important because shared preferences use expensive operations (https://developer.android.com/reference/android/content/SharedPreferences)

`External Intents` were needed to conveniently allow users to send messages with event details via WhatsApp. `Deep Linking` allowed event-guests to access specific event data via a link within the app.

`Fragments` were used to seperate complexity from parent activities, and promoted code reusability for example the loading-screen fragment is used in several places. Fragments enabled easy transiting from loading fragments to data-displaying fragments when data was loading. Fragments aren't strictly required but using fragments allowed for better code modularity and seperation of concerns.  




