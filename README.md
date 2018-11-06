# Personal Health Information Scrubber (PHIS)

## Introduction
Personal Health Information Scrubber, or PHIS, is a de-identification software helped to remove individual-identifiable protected health information (PHI) (e.g, patient names, SSN, address, etc. ) from clinical texts. Our PHI model is based on an extended definition of [HIPAA Privacy Rule](https://www.hhs.gov/sites/default/files/hipaa-simplification-201303.pdf) which has two models: Safe Harbor (SH) and Limited Data Set (LDS). The SH model included 18 HIPAA-recommended identifiers for patients, their relatives, employers, extended to cover physicians and providers. the LDS model keeps information such as Age, Date, Zip code. LDS model retains more information for research, however, data recipients need to sign Data Use Agreement (DUA) with providers to promise additional data protection mechanisms. PHIS was originally developed at the Informatics Institute, University of Alabama at Birmingham (UAB) and released to public under MIT license.

The majority code of the solution used Java technology. Only the thin client of the client-server model was implemented using C#.NET.

## Basic Features
- Standalone and client-server deployment modes.
- Manual and automated de-identification.
- Management of custom PHI terminology defined by Users.
- Batch processing of multiple documents (standalone version only).
- Two de-identification modes: "Safe Harbor" AND "Limited Data Set". 

## Latest Build
- [phis_standalone.exe](https://github.com/bdaduy/phis/releases/download/v1.0/phis_standalone.exe)
- [phis_standalone.jar](https://github.com/bdaduy/phis/releases/download/v1.0/phis_standalone.jar)
- [phis_client.exe](https://github.com/bdaduy/phis/releases/download/v1.0/phis_client.exe)
- [phis_server.jar](https://github.com/bdaduy/phis/releases/download/v1.0/phis_server.jar)
- [source code](https://github.com/bdaduy/phis/archive/v1.0.zip)

## How To Run
### Minimum Runtime Requirement
- Windows 7 or later / MacOS
- Java 1.8 or later

### Standalone Deployment
On Shell, execute command:
```
java -jar phis_standalone.jar
```
On a Windows machine:
```
open phis_standalone.exe
```
![phis standalone](https://github.com/bdaduy/phis/blob/master/images/phis_standalone.png?raw=true)

### Client-server deployment
To start phis client on a Windows machine:
```
open phis_client.exe
```
![phis client](https://github.com/bdaduy/phis/blob/master/images/phis_client.png?raw=true)

To start phis_server from shell, execute command:
```
java -jar phis_server.jar
```
![phis server](https://github.com/bdaduy/phis/blob/master/images/phis_server.png?raw=true)

## Developer's Quickstart Guide
### Development Recommendation
- JDK 1.8 or later
- Apache Maven
- Visual Studio Community 2017
- Netbeans / Eclipse
### Compiling
1. Clone/Download the source code at https://github.com/bdaduy/phis.git
2. Test if JDK and Maven were imported to environment variables.
```
java -version
mvn -version
```
3. Compiling Java
   - Navigate to project root folder ("phis") and execute command
```
mvn clean install
```
   - Examine artifacts on "phis_standalone\target", "phis_server\target"
4. Compiling C#
   - Open "phis_client.sln" with Visual Studio
   - Build -> Rebuild Solution
   - Examine artifacts on "phis_client\bin"
5. Development Notes
   - JavaFX technology was used to create graphic user interface. Suggested Entry: [AppFX.java](https://github.com/bdaduy/phis/blob/master/src/main/java/edu/db/tool/deid/app/fx/AppFX.java)
   - De-Identification solution used a mixed of Natural Language Processing (NLP) techniques: Conditional Random Field, Ahocorasick algorithm, Regular Expressions, and some ad hoc algorithms. They are organized in a pipeline structure. Suggested Entry: [AllAnnotator.java](https://github.com/bdaduy/phis/blob/master/src/main/java/edu/db/tool/deid/app/AllAnnotator.java)
   - Stanford NLP tool was used to train the conditional random field classifier. To train the CRF classifier, we used the [2014 I2b2 de-identification dataset](https://www.i2b2.org/NLP/DataSets/).
   - PHIS was optimized for UAB's clinical text with some localized characteristics. We don't include some UAB-specific methods in the public release source code for our institution security. Testing phis on a different [sublanguage](https://en.wikipedia.org/wiki/Sublanguage#In_natural_language) might not yield its best performance. Users can adapt the tool to best solving their NLP problems by (1) maintaining their own site-specific lexicons (2) re-training the machine-learning model using local data, and (3) Modifying the source code to add more rules and ad hoc algorithms.
   - We haven't had a plan to integrate external code submissions at this moment. Feel free to modify it to your own need.

## Trouble Shooting
- Try to "Run as administrator" if the program cannot trigger the main interface.
- If you encounter OutOfMemoryError, try to increase maximum Java heap space (e.g,-Xmx1g)

## Disclaimer
- The software was developed for internal use. Some UAB-specific components were not included in the public release. It holds no legal liability and no responsibility for using the software in other contexts.

## References
- Developer: Duy Duc An Bui (PhD)
- Supervisor: James J. Cimino (MD)
- Related publications:
```
  Bui DDA, Wyatt M, Cimino JJ. The UAB Informatics Institute and 2016 CEGS N-GRID de-identification shared task challenge. J Biomed Inform. 2017;75S:S54-S61.
  Bui DDA, Redden DT and Cimino JJ. Is Multiclass Automatic Text De-Identification Worth the Effort?. Methods of Information in Medicine.2018; 57(04); pp.177-184.
```