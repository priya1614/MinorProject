
PROJECT

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



Screenshots

![Screenshot from 2020-05-10 19-20-17](https://user-images.githubusercontent.com/60710081/81501457-1dee7b80-92f6-11ea-9bc3-52414339b4c7.png)
![Screenshot from 2020-05-10 19-23-49](https://user-images.githubusercontent.com/60710081/81501516-6c9c1580-92f6-11ea-80ed-0661864b42a6.png)
![Screenshot from 2020-05-10 19-24-13](https://user-images.githubusercontent.com/60710081/81501521-74f45080-92f6-11ea-83cd-db0b16c30578.png)
![Screenshot from 2020-05-10 19-26-11](https://user-images.githubusercontent.com/60710081/81501526-7cb3f500-92f6-11ea-8b6d-a219f034ba7d.png)
![Screenshot from 2020-05-10 19-24-41](https://user-images.githubusercontent.com/60710081/81501527-7cb3f500-92f6-11ea-93ec-217d8a46f3ce.png)
![Screenshot from 2020-05-10 19-25-23](https://user-images.githubusercontent.com/60710081/81501529-8178a900-92f6-11ea-8d3a-5c6d62ac0c76.png)

