# Tiger-Eye
a cool framework and api to be used in competiton and module development. This was created to replace the old project 'FRC-Framework'.

This project was inspired by [this awesome project](https://github.com/Open-RIO/ToastAPI), so big thank to Jaci.

Keep in mind that this readme is temporary, and will be modified when this project is deemed stable and ready. Currently, this project is not ready for public release.

## How to import

Add JitPack to your gradle repository:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Then, add the library as a dependency:

```gradle
dependencies {
   compile 'com.github.Tiger-team-2679:Tiger-Eye:<release>'
}
```

## Setting up
### dependencies
Before you start, make sure you have the GradleRio plugin included in your project.
The simplest way to do that is to add it to your plugins' list:

```gradle
plugins {
    id "edu.wpi.first.GradleRIO" version "<version>"
}
```

### configuration
Now we have to tell java to start by executing our library's code.
We will do that by editing the "Main-Class" attribute in the manifest:

```gradle
def robotManifest = {
    attributes 'Main-Class': 'org.team2679.TigerEye.core.Bootstrap'
}

jar.doFirst {
    from configurations.compile.collect { it.isDirectory() ? it : zipTree(it) }
    manifest robotManifest
}
```

### creating our main class
In order to run our code, we should provide the library with a main class to run. 
In order to make our main class we need to do two things.
We will start by creating a main class, that inherits from a class called "Setup".
example code:
```java
import org.team2679.TigerEye.core.Setup;

public class Main extends Setup {

    @Override
    public void preinit() {
        // This code will run before the wpilib startup
    }

    @Override
    public void init() {
        // This code will run after the wpilib startup .
    }
}
```
After creating the main class, we should tell the library were it is located.
by creating a file called "tiger.properties" in our resources folder, with the following content:
```
setup_class = <location of the main class>
```
## advancet concepts
Now that we have our main class running, we could start using some advancet features in our library.
### Tiger class
no content yet

### Logger
The logger is one of the most important parts of our library, with the logger we can notify the user about sagnificant events and debug our code more easily.

We have two options for using the logger, we could create a new logger instance
```java
Logger logger = new Logger("Subsystems");
```
or we could use the logger already included in the Tiger class:
```java
Logger logger =  org.team2679.TigerEye.core.Tiger.log();
```
We reccomend using the first methos, because it allows to differentiate between two loggers by reading the logger's name from the output pipeline.

### Notifier
no content yet

### State Tracker
no content yet

## Todo
* try to remove uneccesary replacements for wpilib from the source code.
* try to reduce setup effort
* add logger output example to the readme
* add information about the state tracker, notifier, and tiger class to the readme

