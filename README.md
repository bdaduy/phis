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

On the first run, phis_standalone and  phis_client will automatically create the "config" folder allowing ad hoc configuration (e.g, custom terminology, networking parameters, etc.)

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
3. Compiling Java code
- Navigate to project root folder ("phis"), execute command
```
mvn clean install
```
- Examine artifacts on "phis_standalone\target", "phis_server\target"
4. Compiling C# code
- Open "phis_client.sln" with Visual Studio
- Build -> Rebuild Solution
- Examine artifacts on "phis_client\bin"
### Development Notes
- Both deployment models used the NLP logics coded in the phis_standalone module. The phis_client user-interface (UI) used Windows Form App (C#.NET) technology, while phis_standalone ui used JavaFX technology.
- The client-server model may be less flexible, however, it is more secure to utilize confidential resources (e.g, patient and physician databases).
- The symetric encryption algorithm, Advanced Encryption Standard (AES), was used to encrypt messages between client and server. To change the encryption key, modify the "ChangeMe" value in PHISController.java (server) and in config/AppConfig.xml (client).
- The de-Identification solution used a mix of methods such as pattern-matching, dictionary-matching, and machine-learning divided into many independent [units or pipelines.](https://github.com/bdaduy/phis/blob/master/phis_standalone/src/main/java/edu/db/tool/deid/annotator/Annotator.java)
- The machine-learning approach used the conditional random field (CRF) classifier implemented by Stanford NLP Group. The CRF classifiers were trained on the [2014 I2b2 de-identification dataset](https://www.i2b2.org/NLP/DataSets/) and the UAB local dataset.
- PHIS allows user-defined dictionaries by dropping text files to corresponding folder on "config\custom_dict".
- PHIS might underperform on texts on different domains due to the effect of medical [sublanguage](https://en.wikipedia.org/wiki/Sublanguage#In_natural_language). In such situation, users can evolve phis by (1) develop meaningful local terminologies (2) re-train the machine-learning classifer on local data, and (3) Add more rules and ad hoc algorithms to the pipeline.

## Trouble Shooting
- Try to "Run as administrator" if the program cannot start.
- If you encounter OutOfMemoryError, try to increase maximum Java heap space (e.g,-Xmx1g)

## Disclaimer
- PHIS was developed for internal use. Some UAB-specific components were not included in the public release. Further uses of the tool in specific situations will need cautiously and comprehensively testing and adaptation. We hold no liability and accoutability for any damages or losses resulted from the uses of the software.

## References
- Developer: Duy Duc An Bui (PhD)
- Supervisor: James J. Cimino (MD)
- Related publications:
```
  Bui DDA, Wyatt M, Cimino JJ. The UAB Informatics Institute and 2016 CEGS N-GRID de-identification shared task challenge. J Biomed Inform. 2017;75S:S54-S61.
  Bui DDA, Redden DT and Cimino JJ. Is Multiclass Automatic Text De-Identification Worth the Effort?. Methods of Information in Medicine.2018; 57(04); pp.177-184.
```