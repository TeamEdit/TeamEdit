=====README=====
Audio Meta Edit
Group 11
Timothy Lesgaux, Benny Pimentel, Harrison Hammonds
Audio Meta Edit gives the user a GUI to alter the Artist, Title, Album, and Year ID3 metadata of audio files.

---Setup---
In addition to the JRE 1.8 and JavaFX libraries, Audio Meta Edit requires the JID3 library which is located here: https://blinkenlights.org/jid3/. Add this library by right clicking the project in Eclipse, selecting Properties > Java Build Path > Add External JARs and navigating to the location of "\jid3_0.46\dist\JID3.jar".

---File Manager---
User can navigate by selecting directories in the list or typing a file path in the top text field and pressing "Go".
User can select the audio file by choosing from the list or type file path into bottom text field and pressing "Open".
File paths may be direct or relative from the Java src directory; this was not hard-coded to begin at C: or root due to different OS of developers.

---Editor---
-Import-
User can select to import a "Song" or "Album".
If "Song" is selected, the user may select and import a single .mp3 file.
If "Album" is selected, the user may select and import a directory which contains .mp3 files.

Once a file is importing to the Editor, the user can select it from the list by double clicking. The user can change the metadata in the text fields in the bottom right section, after making changes in any text field hit Enter. Then apply changes with the "Apply" button. They can also play the song with the play button, then stop the song with the stop button.

--Known Bugs--
Due to ID3 byte coding of genre, user may run into trouble with certain genre types.
Program fails if the .mp3 file is open in another program due to read-lock.