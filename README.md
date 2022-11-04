# CMPUT 301
### Team Name: shell379
### Team Members (and ccids):
* Alphonso Dineros (alphonso)
* Elsa Xiaoyi Chen (xiaoyi9)
* Joshusa Wong (jyw2)
* Muhammad Fiaz (mfiaz)
* Yi Meng Wang (yimeng2)
* Zong Lin Yu (zonglin1)

### Running Instructions:
1. Install the APK
2. Use as desired!

### Assumptions:
1. This app assumes that a stable internet connection is available to the user
2. This app is built to run on Android Oreo and above

### Comments:
1. Due to the usage of LiveData objects, testing was done heavily through the use of UI testing. This is primarily due to the fact that LiveData objects requires threading, which is not available in regular JUnit tests
2. Please note that when adding an ingredient to a recipe, to check a checkbox, the AMOUNT MUST BE PROVIDED
3. UML and CRC Cards are under the wiki

### Known issues:
1. Currently, when the user is in the middle of creating a new Recipe and has already filled out some of its fields, then goes to add some ingredients to the recipe (which takes them to another fragment), their progress is lost upon returning from the ingredient selection screen and the fields that they had previously filled out are reset. The previously selected ingredients are also overrriden by the most recent selection (writing instead of appending). We were not able to fix this issue in time for the half-way checkpoint, but we will ensure that we get it fixed by the final project submission date. Adding and removing ingredients in an existing recipe does however work as intended.

### Acknowledgements and References:
1. CMPUT301 Lecture Lab Code was used in the development of this project
2. Group members used their own prior CMPUT301 Assignment/Lab code in the development of this project
2. Additional citations have been provided at several points throughout the codebase
