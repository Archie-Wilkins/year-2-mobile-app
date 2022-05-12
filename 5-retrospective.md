**Mobile Development 2021/22 Portfolio**
# Retrospective

Your username: `C2043958`

Student ID: `C2043958`

_Complete the information above and then write your 200-word Retrospective here.__

During development the MVP shifted from focusing on creating sharable images with event details, to focusing on allowing users to invite others to events and respond to invitations. This appeared more valuable, and I was having difficulties configuring the server to allow image uploading. 

With more time I'd like to allow users to upload images so they can be used to create sharable ticket-style images including event-details. The dashboard currently has calendar-icons where user-uploaded event-thumbnails would've gone.      

On reflection, more time should've been spent wireframing to prevent additional code refactoring. For example, using design software like Figma could've saved me refactoring the `activity_external_event_view screen` several times while I decided how to allow users to respond to events. 

In future I'd utilise `ViewModel` to share data between fragments (https://developer.android.com/guide/fragments/communicate). E.g. when adding attendees I intended the form to be a fragment to allow easy dupliaction and refactoring but I was unaware data could be passed between fragments. My solution involved placing the add attendees form in the parent activity, submitting data to the database and then reloading the fragment (https://git.cardiff.ac.uk/c2043958/mobile-assessment/-/blob/main/4-application/app/src/main/java/com/example/myapplication/ViewEventInfomation/event_details_attendeesinfo.java), which is performance inefficient. 


<!-- 
Comments from Sandy

– This will be tricky, but if you're able to, try and make more reference to specific technical differences you might have if you were starting from scratch. For example is there some other part of the Android API that, in retrospect, might have been useful for you? It'd be good to see this kind of technical decision-making coming through.

– I'd probably recommend being less negative about your code organisation, though I can see the benefits of doing that better, to make room to talk about other technical options.

- If there's any space for more details on your sharable images that would be good. Would these be auto-generated from event data, for example?

-->
