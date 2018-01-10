# JerdApp
jerd (n): a journalism nerd

An Android mobile app for journalism students, professionals, and organizations to maintain and share sources.

Jerd allows journalists to take pictures, record audio, and jot down notes, and to choose to save them on their mobile devices and/or in the cloud. Collaborators can then download the material off the cloud using a password to transcribe or examine others' material.

This app was submitted for the 2016 Congressional App Challenge and was the CA-26th District winner. 

## Information
This app is a paused work-in-progress and was primarily created for self-development in programming skills/working with Android; other apps that have similar functionality already exist in the app store. Disclaimer: File safety is not yet guaranteed.

To test, download or clone this project and run with Android Studio. Camera and recording functions must be used on a physical phone.

Prerequisites:
* Internet Connection
* Android version 14+ (24+ preferred)

## Room for Improvement
* UI design - colors are often default and tacky; resolution of images and icons is low; some pages close the app when the "back" button is pressed rather than returning to the open drawer navigation; tools UI can be improved with better setup
* File privacy - currently, any file can be downloaded given that you entered the exact file name; a password should be implemented along with an easier title system
* Instructions page - instructions can be improved with images and examples rather than plain text

## Screenshots
### Home page
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/homepage.png?raw=true" align="left" height="20%" width="20%" >
Jerd's home page opens onto the essential tools: interview questions list, audio recorder, camera, and note taker.

### Make interview questions
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/makequestion.png?raw=true" align="left" height="20%" width="20%" >
Interview questions may be added with the pink floating button in the bottom right corner.

### Delete interview questions
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/deletequestion.png?raw=true" align="left" height="20%" width="20%" >
Interview questions can be deleted if you click on one in the list.

### Audio recorder
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/recordaudio.png" align="left" height="20%" width="20%" >
Audio can be recorded with the start and end buttons. The drop down menu indicates which project folder the file should be saved to. An option to title your recording will show after you end the recording.

### Note taker
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/titlefile.png?raw=true" align="left" height="20%" width="20%" >
Similar to the audio recorder, the note taker allows users to take notes in a text box, then save and title their file. The dropdown menu indicates which project folder the file should be saved to. 

### Drawer navigation menu
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/drawer.png?raw=true" align="left" height="20%" width="20%" >
Hitting the hamburger menu in the top left opens a drawer navigation menu on the lefthand side, where users can find and download files, see instructions, change their account, etc. "Tools" is the home page.

### My files
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/createproject.png?raw=true" align="left" height="20%" width="20%" >
Hitting "My Files" in the drawer menu gives this page, where project folders are located. Projects can be added with the pink floating button in the bottom right corner. This page interacts within the Android internal storage folder to establish new folders. Tapping a folder will open a page showing the files located within.

### Check files
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/downloadfile.png?raw=true" align="left" height="20%" width="20%" >
Hitting "Check Files" in the drawer menu gives this page, where you can download collaborators' files from the Firebase platform by tapping the pink floating button in the bottom right corner, then typing the exact text name of the file. This page then sends a download request to the Firebase platform for the file of that name.

### How to 
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/instructions.png?raw=true" align="left" height="20%" width="20%" >
Hitting "How To" in the drawer menu gives this page, where users can read instructions for use of the app. This is a static page.

### Settings
<img src="https://github.com/vickyjjj/JerdApp/blob/master/app/src/main/res/screenshots/settings.png?raw=true" align="left" height="20%" width="20%" >
Hitting "Settings" in the drawer menu gives this page, where users can change details about their account. This page interacts with the Firebase platform in order to achieve account functionality.
