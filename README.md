# Personal Health Information Scrubber (PHIS)

## Introduction
PHIS is an automatic de-identification software used to removing sensitive protected health information (PHI) from medical texts. Our PHI definition was based on HIPAA's Privacy Rule and Uzuner et al.' work on de-identification challenge. PHIS was derived from an internal research project at the Informatics Institute, University of Alabama at Birmingham (UAB).

## Screenshot:
![phis main screen](https://github.com/bdaduy/phis/blob/master/images/MainGui.png?raw=true)

### ![Download Latest Build](https://github.com/bdaduy/phis/blob/master/build/phis.exe?raw=true)

## Minimum Requirement
- Windows 7/10
- Java ([JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)/[JRE](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)) >1.8 
- PHIS was intended to be a portable software running on any Operating Systems installed with Java. The current revision has been tested on Windows platform. Future work is required to make it run on Linux or macOS. 

## Main Features
- Support manual and automatic PHI redaction using a computerized solution optimized for UAB's clinical texts.
- Support management of custom PHI terminology defined by Users.
![Add Lexicon](https://github.com/bdaduy/phis/blob/master/images/AddLexicon.png?raw=true) ![Lexicons](https://github.com/bdaduy/phis/blob/master/images/Lexicons.png?raw=true)
- Support interactive processing of a single document and batch processing of multiple documents.
![batch run screen](https://github.com/bdaduy/phis/blob/master/images/BatchRunGui.png?raw=true)
- Support two de-identification modes: "All PHI" AND "PHI for releasing limited dataset". In the second mode, the system ignores AGE, DATE, and ZIPCODE categories. [Link](https://privacyruleandresearch.nih.gov/pr_08.asp)
- With MIT license, It facilitates modification and adaptation to user-specific needs. 

## Developer's Quickstart Guide
1. Download and install JDK and Maven. Make sure PATH environment variable includes JDK and [Maven](https://maven.apache.org/install.html)
2. Download and extract phis project
3. Compile the source code:
   - Launch the Command Prompt in Windows
   - Navigate to project's root folder and execute
   ```
   mvn clean install
   ```
   - If compiled successfully, an executable ["phis.exe"](https://github.com/bdaduy/phis/blob/master/build/phis.exe?raw=true) will be generated in the 'target' folder.
4. Setup Development Environment
   - Download and install Netbeans or Eclipse IDE
   - Open/Import this as a Maven Project
   - Some development notes:
     - JavaFX technology was used to create graphic user interface. Suggested Entry: [AppFX.java](https://github.com/bdaduy/phis/blob/master/src/main/java/edu/db/tool/deid/app/fx/AppFX.java)
     - De-Identification solution used a mixed of Natural Language Processing (NLP) techniques: Conditional Random Field, Ahocorasick algorithm, Regular Expressions, and some ad hoc algorithms. They are organized in a pipeline structure. Suggested Entry: [AllAnnotator.java](https://github.com/bdaduy/phis/blob/master/src/main/java/edu/db/tool/deid/app/AllAnnotator.java)
     - Stanford NLP tool was used to train the conditional random field classifier. To train the CRF classifier, we used the [2014 I2b2 de-identification dataset](https://www.i2b2.org/NLP/DataSets/).
     - PHIS was optimized for UAB's clinical text with some localized characteristics. We don't include some UAB-specific methods in the public release source code for our institution security. Testing phis on a different [sublanguage](https://en.wikipedia.org/wiki/Sublanguage#In_natural_language) might not yield its best performance. Users can adapt the tool to best solving their NLP problems by (1) maintaining their own site-specific lexicons (2) re-training the machine-learning model using local data, and (3) Modifying the source code to add more rules and ad hoc algorithms.
     - We haven't had a plan to integrate external code submissions at this moment. Feel free to modify it to your own need.

## Trouble Shooting
- Try to "Run as administrator" if the program cannot trigger the main interface.

## Disclaimer
- The software was developed for internal use. Some UAB-specific components were not included in the public release. It holds no legal liability and no responsibility for using the software in other contexts.

## References
- Developer: Duy Duc An Bui (PhD)
- Supervisor: James J. Cimino (MD)
- Related publications:
```
  Bui DDA, Wyatt M, Cimino JJ. The UAB Informatics Institute and 2016 CEGS N-GRID de-identification shared task challenge. J Biomed Inform. 2017;75S:S54-S61.
```