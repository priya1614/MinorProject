Firebase features to be used:
Login using Firebase Authentication (https://firebase.google.com/products/auth)
Save and fetch data using Firestore (https://firebase.google.com/products/firestore)
Save and fetch images using Cloud Storage (https://firebase.google.com/products/storage)

Requirements for the app:

Intro: The app basically allows users to create categories, add their pictures in categories. Users can view the pictures in categories and in a timeline view.

Screen 1-2: Login and  Signup Screen
Allow users to login using email and password.
Allow users to Signup using Name, email, password, and upload a profile image.

Screen 3: Categories Screen
Here the user can see the list of categories in grid format
Category Image and Category Title
Click on Category to go to Category detail page
Add Category Button
User should be able to add a category 

Screen 4: Category Detail
Show list of images in Grid.
If an image is clicked show in full screen mode.
Delete Image (This also removes the image from timeline)
Add Image 
Take from camera or Gallery (Upload to Cloud Storage and save link in Firestore)

Screen 5: Timeline (This is view only)
Show images in one list according to the time they were uploaded, recent one comes to the top.
This timeline is irrelevant to category i.e. If I add image A in category X and then add image B in category Y then the timeline should show B then A.

Screen 6: Profile Screen
Show user Name,  Email, Profile Pic
Logout button
Allow change of Profile Pic
