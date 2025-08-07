# UdaSecurity

![UdaSecurity Logo](UdaSecurity.png)

Your company, **Udasecurity**, has created a home security application. This application tracks the status of sensors, monitors camera input, and changes the alarm state of the system based on inputs. Users can arm the system for when they’re home or away as well as disarm the system.

The wild success of your application means that you have to prepare to scale your software. You’ll need to write unit tests for all of the major operations the application performs so that you can safely make more changes in the future. You also need to use maven to streamline the build process and automate your tests and code analysis. More urgently, you need to make sure your application actually does what it’s supposed to do, and so one of writing thorough unit tests will help you find any bugs that already exist.

The image analysis service used by this application has proven popular as well, and another team wants to use it in their project. To accomplish this, you must separate the Image Service from the program and package it as a separate module to be included both in your own project and in other projects.

The end goal for this assignment is to split the project into multiple modules, refactor it to be unit-testable, write unit tests to cover all the main requirements for the Security portion of the application and fix any bugs that you find in the process. You’ll also update the build process to automatically run unit tests, perform static code analysis, and build the code into an executable jar file. 

## Section 1: Update `pom.xml` with Missing Dependencies
This app was initially built by including jar files for each dependency manually, but now we are modernizing the dependency management by using Maven to manage our dependencies and their versions for us. 

The project has already been moved into a maven file structure for you, but you’ll need to identify missing dependencies and add their artifacts to your pom.xml until you can run the project successfully. Look throughout the project directory in order to make sure you find and add all the appropriate dependencies. 

You'll know you've added all of the correct dependencies when the project runs without errors.

![starter pom.xml file](pom_xml.png)

## Section 2: Split the Image Service into its Own Project

Now that you have identified all the dependencies and can run the project, it’s time to split things up! Remember another team wants to use the Image Service in their project. To accomplish this, you must separate the Image Service from the program and package it as a separate module to be included both in your own project and in other projects

### Splitting Things Up

1. Use either maven or your IDE to create a new maven project that will be the parent project for the two modules you will be creating. 
1. Inside of the parent project, create one module for your Security Security and one module for your Image service. (*Note: Use one parent pom at the top level and one child pom for each module.*)
1. Move all components into their proper modules.
1. Update dependencies in the poms so that shared dependencies are in the parent pom, but unshared dependencies are in the child poms.
1. Use the pluginManagement tag in the parent pom to set the latest versions for the core plugins used by the maven lifecycle, such as the maven-compiler-plugin.
1. Create a module descriptor for each package. You will have to provide the correct `export` and `requires` statements in each of these descriptors.
1. Address transitive dependencies. Now that your project uses modules, you may need to open your packages to dependencies or explicitly include transitive dependencies that are required by your dependencies that do not declare them all in a `modules-info.java`. *(Note: In particular, some amazon SDK dependencies do not provide modular jars but may require your project to reference the libraries they use. You might need to add **`requires`** statement to your modules-info.java to reference those dependencies. See this discussion thread on modularization of *<a href="https://github.com/aws/aws-sdk-java-v2/issues/1869" target="_blank">*aws 2*</a>*.)*
1. Make sure the project still runs!

## Section 3: Write Unit Tests and Refactor Project to Support Unit Tests

In this section, you will write unit tests for the Security Service. Each of the requirements below should be verified by one or more unit tests. Make sure to put these unit tests in a new package that has the same name as the package containing the Security Service.

You do **NOT** need to test the Image Service or the Repository, so you should use `@Mock`s to help substitute dependencies and keep the scope of each unit test narrow. You should use JUnit 5 features like `@ParameterizedTest` and `ArgumentMatchers` to ensure your tests cover branching conditions.

Each of the requirements below should be verified by one or more unit tests. All of these test the **Security Service**, so make sure your tests don't depend on the implementation of the Repository or the Image Service. *Remember, you can use ****`Mocks`**** to replace these services in your unit tests.*

You should also write interfaces to describe the necessary behaviors of your dependencies to make them easier to Mock. We’re already using a SecurityRepository interface, but we have no interface to describe the behavior of our Image Service. Create an interface that makes it easy to test our application regardless of whether we’re using the `AwsImageService` or `FakeImageService`. 

### *Optional Stand Out Task:* Connect Your Project to the AWS Image Recognition Library

> Once you have created an interface for your image service, complete the steps described in the AwsImageService to create credentials and provide them in a properties file for your application. Change the ImageService implementation class in the CatpointGui class to use the AwsImageService instead of the FakeImageService. Try submitting different types of images and see what comes back!

![Image of the Gui](gui_1.png)

**While you are writing tests, it's possible you may need to refactor the application in order to make all of the requirements testable.** For example, parts of the business logic may be contained in the GUI or repository classes. You may have to move this logic into the security service to be tested.

Remember, a failing unit test could mean one of two things:

1. Your unit test is faulty.
1. Something in your program isn't working.

Some of these requirements might not be properly implemented, so, even if you write the correct unit test, it might still fail. You will fix any faulty or missing requirements in the next section.

#### Application Requirements to Test:

1. If alarm is armed *and* a sensor becomes activated, put the system into pending alarm status.
1. If alarm is armed *and* a sensor becomes activated *and* the system is already pending alarm, set off the alarm.
1. If pending alarm *and* all sensors are inactive, return to no alarm state.
1. If alarm is active, change in sensor state should not affect the alarm state.
1. If a sensor is activated *while* already active *and* the system is in pending state, change it to alarm state.
1. If a sensor is deactivated *while* already inactive, make no changes to the alarm state.
1. If the camera image contains a cat *while* the system is armed-home, put the system into alarm status.
1. If the camera image does not contain a cat, change the status to no alarm *as long as* the sensors are not active.
1. If the system is disarmed, set the status to no alarm.
1. If the system is armed, reset all sensors to inactive.
1. If the system is armed-home *while* the camera shows a cat, set the alarm status to alarm.

*Reminder*: If you find yourself relying on the behavior of another service, you may want to consider using a Mock!

## Section 4: Fix Any Bugs You Find With Your Unit Tests!
As we stated in the previous section, a failing unit test could mean one of two things:
 1. Your unit test is faulty.
 2. Something in your program isn't working.

Your task is to make sure all of the Application Requirements are properly implemented. Some requirements may not be performed and some bugs may produce unexpected behavior.  After you fix any broken requirements, all of your unit tests should pass!

### Application Requirements to Test:
 1. If alarm is armed _and_ a sensor becomes activated, put the system into pending alarm status.
 2. If alarm is armed _and_ a sensor becomes activated _and_ the system is already pending alarm, set off the alarm.
 3. If pending alarm _and_ all sensors are inactive, return to no alarm state.
 4. If alarm is active, change in sensor state should not affect the alarm state.
 5. If a sensor is activated _while_ already active _and_ the system is in pending state, change it to alarm state.
 6. If a sensor is deactivated _while_ already inactive, make no changes to the alarm state.
 7. If the camera image contains a cat _while_ the system is armed-home, put the system into alarm status.
 8. If the camera image does not contain a cat, change the status to no alarm _as long as_ the sensors are not active.
 9. If the system is disarmed, set the status to no alarm.
 10. If the system is armed, reset all sensors to inactive.
 11. If the system is armed-home _while_ the camera shows a cat, set the alarm status to alarm.

![Image of the Gui](gui_2.png)

## Section 5: Check Unit Test Coverage
Use IntelliJ to check code coverage. Our goal is to cover everything in the Security Service. Other teams will be maintaining our Image Service so we’ll focus strictly on the behavior of the Security Service. 

![code coverage](code_coverage_1.png)

**Your goal is to provide full coverage of all methods that implement the application requirements.** You don’t need to test trivial methods like getters or setters, but you do need to make sure that all the lines in your other methods are reachable by the unit tests.

![code coverage](code_coverage_2.png)

### *Optional Stand Out Task:* Integration Tests

> Create a FakeSecurityRepository class that works just like the `PretendDatabaseSecurityRepository` class (except without the property files). Create a second test class called `SecurityServiceIntegrationTest.java` and write methods that test our requirements as integration tests.

> These tests can call service methods and then use JUnit Assertions to verify that the values you retrieve after performing operations are the expected values.

## Section 6: Build the Application into an Executable JAR
Update your `pom.xml` to use a maven plugin that allows you to compile your application into an executable JAR. Confirm that you can run the program by running the jar file. Execute the Maven goal that builds the JAR and start the application from the command line.

Submit a screenshot titled `executable_jar.png` that shows you running the executable jar from the command line and the application launching. Use the command `java -jar [yourjarname]` to run it.

![creating a jar file](jar.png)

## Section 7: Add Static Analysis to Build
Add a Reporting tag to your pom that contains the `spotbugs-maven-plugin` and use it to generate a `spotbugs.html` report in your project’s `/target/site` directory. 

You should fix any of the errors it finds that are High priority. You are welcome, though not required, to address any other errors you find as well!

### Project Submission

For your submission, please submit the following:
- Completed project code should be uploaded either to GitHub or a .zip file. Make sure to include the entire project folder.

### Double-Check the Rubric
Make sure you have completed all the rubric items [here](https://review.udacity.com/#!/rubrics/3010/view).

### Submit your Project

You can submit your project by uploading a zip file or selecting your GitHub repo.


<br/>


## Description:

The project is a home security application that tracks the status of sensors, monitors camera input, and changes the alarm state of the system based on inputs. Users can arm the system for when they’re home or away as well as disarm the system.

## Files Used

_None_


## Date created

**7/24/2025**

## Udacity Mentors Who Have Answered Questions In _Knowledge_ To Help With Project

- JULIANO

## Credits
###### References used while making project

A, Noyan. “How Do I Run My Program after Splitting It into Modules?” _Knowledge_, Udacity, 2021, knowledge.udacity.com/questions/483315. Accessed 24 Jul. 2025.

å, 梁. “What to Export?” _Knowledge_, Udacity, 2022, knowledge.udacity.com/questions/881462. Accessed 30 Jul. 2025.

audrey. “How to Use MigLayout in IntelliJ?” _Stack Overflow_, 17 Apr. 2018, stackoverflow.com/questions/49887811/how-to-use-miglayout-in-intellij. Accessed 28 Jul. 2025.

“AWS Java SDK Example.” _Telnyx Developer_, Telnyx, developers.telnyx.com/docs/cloud-storage/sdk/java. Accessed 30 Jul. 2025.

ayush. “Udasecurity: Splitting the App.” _Knowledge_, Udacity, 2022, knowledge.udacity.com/questions/911834. Accessed 25 Jul. 2025.

Azaw. “ Spilt the Security Service and Image Service into Modules in NEW Project?” _Knowledge_, Udacity, 2022, knowledge.udacity.com/questions/838167. Accessed 25 Jul. 2025.

Casey, Jerome LacosteJohn. “Usage.” _Apache Maven Project_, 29 Jan. 2006, maven.apache.org/plugins/maven-deploy-plugin/usage.html#:~:text=To%20enable%20this%20mojo%20to,a%20set%20of%20related%20projects. Accessed 1 Aug. 2025.

Casey, Jerome LacosteJohn. “Usage.” Apache Maven Project, 29 Jan. 2006, maven.apache.org/plugins/maven-deploy-plugin/usage.html#:~:text=To%20enable%20this%20mojo%20to,section%20similar%20to%20the%20following:. Accessed 1 Aug. 2025.

“Class JFileChooser.” _JFileChooser (Java Platform SE 6)_, 19 Nov. 2015, docs.oracle.com/javase/6/docs/api/javax/swing/JFileChooser.html. Accessed 5 Aug. 2025.

“Class JFileChooser.” _JFileChooser (Java Platform SE 8)_, 15 July 2025, docs.oracle.com/javase/8/docs/api/javax/swing/JFileChooser.html. Accessed 5 Aug. 2025.

Cvguntur. “Understanding Apache Maven – Part 6 – POM Reference.” _C. V. Guntur_, 9 June 2020, cguntur.me/2020/06/20/understanding-apache-maven-part-6/#:~:text=A%20distributionManagement%20section%20is%20used,current%20project%20can%20inherit%20from. Accessed 1 Aug. 2025.

D, Dao Anh. “BUILD FAILURE in Section 6: Build the Application into an Executable JAR.” _Knowledge_, Udacity, 2022, knowledge.udacity.com/questions/854204. Accessed 6 Aug. 2025.

“Deploy:Deploy.” _Apache Maven Deploy Plugin – Deploy:Deploy_, maven.apache.org/plugins/maven-deploy-plugin/deploy-mojo.html#:~:text=. Accessed 1 Aug. 2025.

dnim. “IntelliJ IDEA: ‘Indexed Maven Repositories’ List - How to Add Remote Maven Repository in This List?” Edited by Shawn Chin and Om-nom-nom, _Stack Overflow_, 16 Nov. 2011, stackoverflow.com/questions/8150535/intellij-idea-indexed-maven-repositories-list-how-to-add-remote-maven-repos/58556678. Accessed 28 Jul. 2025.

fudefite. “Using Miglayout with Netbeans.” _Stack Overflow_, 11 Dec. 2009, stackoverflow.com/questions/1891400/using-miglayout-with-netbeans. Accessed 28 Jul. 2025.

GuruKulki, and dinox0r. “How to Know the Which Archetype an Existing Maven Project Is Built On?” _Stack Overflow_, 14 Nov. 2013, stackoverflow.com/questions/19983017/how-to-know-the-which-archetype-an-existing-maven-project-is-built-on. Accessed 25 Jul. 2025.

H, Kevin. “Use the pluginManagement Tag in the Parent Pom to Set the Latest Versions for the Core Plugins Used by the Maven Lifecycle.” _Knowledge_, Udacity, 2022, knowledge.udacity.com/questions/898185. Accessed 30 Jul. 2025.

“Index of /ICS4U1/Unit4-Graphics/Miglayout/11.0.” _Index of /ICS4U1/UNIT4-Graphics/Miglayout/11.0_, quarkphysics.ca/ICS4U1/unit4-graphics/miglayout/11.0/. Accessed 28 Jul. 2025.

“Introduction to Remote Repositories.” _Introduction to Remote Repositories | Learn Git Ebook (CLI Edition)_, www.git-tower.com/learn/git/ebook/en/command-line/remote-repositories/introduction#:~:text=Local%20repositories%20reside%20on%20the,or%20on%20a%20local%20network. Accessed 28 Jul. 2025.

“Iterating over Enum Values in Java.” _GeeksforGeeks_, GeeksforGeeks, 11 July 2025, www.geeksforgeeks.org/java/iterating-over-enum-values-in-java/. Accessed 3 Aug. 2025.

James, and Gabriel Belingueres. “How to Find out Which Dependencies Need to Be Included?” _Stack Overflow_, 24 Nov. 2011, stackoverflow.com/questions/8262540/how-to-find-out-which-dependencies-need-to-be-included. Accessed 24 Jul. 2025.

“jar-with-dependencies.” _Apache Maven Project_, 7 Feb. 2011, maven.apache.org/plugins/maven-assembly-plugin/descriptor-refs.html#jar-with-dependencies. Accessed 7 Aug. 2025.

Jason, and HJW. “What Archetype to Choose for a Simple Java Project.” Edited by Mustafa Özçetin and Rogerdpack, _Stack Overflow_, 23 Aug. 2011, stackoverflow.com/questions/7158348/what-archetype-to-choose-for-a-simple-java-project. Accessed 25 Jul. 2025.

Jenkov, Jakob. “Java Modules.” _Jenkov.Com Tech & Media Labs - Resources for Developers, IT Architects and Technopreneurs_, jenkov.com/tutorials/java/modules.html. Accessed 30 Jul. 2025.

“JUnit » 4.13.2.” _MVN REPOSITORY_, mvnrepository.com/artifact/junit/junit/4.13.2. Accessed 30 Jul. 2025.

“JUnit Jupiter API » 5.7.0.” _MVN REPOSITORY_, mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.7.0. Accessed 25 Jul. 2025.

“JUnit Jupiter Engine » 5.7.0.” _MVN REPOSITORY_, mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine/5.7.0. Accessed 25 Jul. 2025.

Lundberg, Dennis. “Make The Jar Executable.” _Apache Maven Project_, 1 Jan. 2008, maven.apache.org/shared/maven-archiver/examples/classpath.html. Accessed 7 Aug. 2025.

“Maven Error: ‘Deployment Failed: Repository Element Was Not Specified in the POM inside distributionManagement Element or in -DaltDeploymentRepository=id.’” _Salesforce_, 2 Mar. 2024, help.salesforce.com/s/articleView?id=001122595&type=1. Accessed 1 Aug. 2025.

“Maven. Repositories.” _IntelliJ IDEA Help_, IntelliJ IDEA, 23 Oct. 2024, www.jetbrains.com/help/idea/maven-repositories.html. Accessed 28 Jul. 2025.

“Maven.” _Apache Maven Project_, maven.apache.org/ref/3.6.3/maven-model/maven.html#class_distributionManagement. Accessed 1 Aug. 2025.

McCalley, Austin. “Google GSON Dependency Not Found.” _Stack Overflow_, 8 Jan. 2017, stackoverflow.com/questions/41539209/google-gson-dependency-not-found/41539256. Accessed 28 Jul. 2025.

Meyer, Nick. “A For-Loop to Iterate over an Enum in Java.” Edited by Mateen Ulhaq, _Stack Overflow_, 9 July 2009, stackoverflow.com/questions/1104975/a-for-loop-to-iterate-over-an-enum-in-java. Accessed 3 Aug. 2025.

“Mig Layout.” _Java Graphics - MigLayout_, quarkphysics.ca/ICS4U1/unit4-graphics/MigLayout.html. Accessed 28 Jul. 2025.

Mirchevski, Bruno. “17 Maven Commands and Options [Cheat Sheet].” _VPSServer.Com_, 27 Sept. 2023, www.vpsserver.com/maven-commands-and-options/. Accessed 24 Jul. 2025.

“MLA Works Cited: Electronic Sources (Web Publications).” _MLA Works Cited: Electronic Sources - Purdue OWL® - Purdue University_, owl.purdue.edu/owl/research_and_citation/mla_style/mla_formatting_and_style_guide/mla_works_cited_electronic_sources.html. Accessed 24 Jul. 2025.

“Mockito Core » 3.6.0.” _MVN REPOSITORY_, mvnrepository.com/artifact/org.mockito/mockito-core/3.6.0. Accessed 25 Jul. 2025.

“Mockito JUnit Jupiter » 3.6.0.” _MVN REPOSITORY_, mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter/3.6.0. Accessed 2 Aug. 2025.

“Multi-Module Project With Spring Boot.” _GeeksforGeeks_, GeeksforGeeks, 23 July 2025, www.geeksforgeeks.org/java/multi-module-project-with-spring-boot/. Accessed 25 Jul. 2025.

“Net.Miginfocom.Swing.MigLayout Maven / Gradle / Ivy.” _JAR Search and Dependency Download from the Maven Repository_, jar-download.com/artifacts/com.miglayout/miglayout-swing/5.2/source-code/net/miginfocom/swing/MigLayout.java. Accessed 28 Jul. 2025.

nihilogist. “Maven: Local versus Remote Repository.” Edited by Gijs Overvliet, _Stack Overflow_, 5 Jan. 2012, stackoverflow.com/questions/8746042/maven-local-versus-remote-repository. Accessed 28 Jul. 2025.

“Online Compiler and Debugger for C/C++.” _GDB Online Debugger_, www.onlinegdb.com/. Accessed 24 Jul. 2025. 

rohit. “Efficient Way to Read an Image File Using Java.” Edited by Tasos P., _Stack Overflow_, 11 July 2020, stackoverflow.com/questions/62852645/efficient-way-to-read-an-image-file-using-java. Accessed 5 Aug. 2025.

S, Nikolai. “Enhanced Switch Blocks Are Not Supported at Language Level 12.” _Knowledge_, Udacity, 2022, knowledge.udacity.com/questions/784400. Accessed 25 Jul. 2025.

S, Shouvik. “I Am Not Able to Create a Jar File, When Trying to Build Security Module.” _Knowledge_, Udacity, 2024, knowledge.udacity.com/questions/1022201. Accessed 24 Jul. 2025.

“Software.Amazon.Awssdk.Auth.Credentials.AwsBasicCredentials Maven / Gradle / Ivy.” _JAR Search and Dependency Download from the Maven Repository_, jar-download.com/artifacts/software.amazon.awssdk/bundle/2.0.0-preview-13/source-code/software/amazon/awssdk/auth/credentials/AwsBasicCredentials.java. Accessed 30 Jul. 2025.

“Software/Amazon/Awssdk/Auth/2.9.9.” _Central Repository: Software/Amazon/Awssdk/Auth/2.9.9_, repo.maven.apache.org/maven2/software/amazon/awssdk/auth/2.9.9/. Accessed 28 Jul. 2025.

Song, Yitong. “Unable to See Maven Tab IntelliJ IDEA 2024.3 (Ultimate Edition).” _JETBRAINS_, 18 Nov. 2024, intellij-support.jetbrains.com/hc/en-us/community/posts/22715417398034-Unable-to-see-maven-tab-IntelliJ-IDEA-2024-3-Ultimate-Edition. Accessed 24 Jul. 2025.

“surefire:test.” _Apache Maven Project_, maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html. Accessed 7 Aug. 2025.

Szczukocki, Denis. “Multi-Module Project with Maven.” _Baeldung_, 11 May 2024, www.baeldung.com/maven-multi-module. Accessed 25 Jul. 2025.

Tanner, David E. “How to Configure Maven Setting.Xml and Pom.Xml to Deploy a Snapshot.” _MIT Wiki Service_, 15 Sept. 2009, wikis.mit.edu/confluence/display/devtools/How+to+configure+Maven+setting.xml+and+pom.xml+to+deploy+a+snapshot#:~:text=$%7BrenderedContent%7D-,Overview,my%20snapshot%20repository. Accessed 1 Aug. 2025.

UAnjali. “Udacity/Cd0384-Java-Application-Deployment-Projectstarter.” _GitHub_, 2021, github.com/udacity/cd0384-java-application-deployment-projectstarter. Accessed 24 Jul. 2025.

“Udacity Git Commit Message Style Guide.” _Udacity Nanodegree Style Guide_, udacity.github.io/git-styleguide/. Accessed 24 Jul. 2025.

Walter, Newton. “Mastering the Module-Info.Java File in Java.” _Chronon Systems_, 7 Mar. 2023, chrononsystems.com/blog/module-info-java-file/. Accessed 30 Jul. 2025.

Wikituby. “Deployment Failed: Repository Element Was Not Specified in the POM inside distributionManagement.” _YouTube_, YouTube, 1 May 2023, www.youtube.com/watch?v=eGpJ36NKcCE. Accessed 1 Aug. 2025.

zoran119. “New Lines inside Paragraph in README.Md.” Edited by Yivi, _Stack Overflow_, 4 July 2014, stackoverflow.com/questions/24575680/new-lines-inside-paragraph-in-readme-md. Accessed 24 Jul. 2025.

Zyl, Jason van, and Brian Fox. “Introduction to Repositories.” _Apache Maven Project_, Maven, 13 May 2008, maven.apache.org/guides/introduction/introduction-to-repositories.html. Accessed 28 Jul. 2025.

Zyl, Jason van. “Setting up Multiple Repositories.” _Apache Maven Project_, 12 Oct. 2005, maven.apache.org/guides/mini/guide-multiple-repositories.html. Accessed 1 Aug. 2025.
