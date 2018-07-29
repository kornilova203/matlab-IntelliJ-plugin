# Matlab support for IntelliJ
Matlab syntax highlighting plugin for JetBrains IDEs.

[![Build Status](https://travis-ci.com/kornilova-l/matlab-IntelliJ-plugin.svg?branch=master)](https://travis-ci.com/kornilova-l/matlab-IntelliJ-plugin)

![Screenshot of Matlab plugin](screenshots/screen.png)

https://plugins.jetbrains.com/plugin/10941-matlab-support

## Installation
 1. Open **Settings | Plugins** (Windows) or **Preferences | Plugins** (MacOS)
 2. Click **Browse repositories...**
 3. Type _Matlab Support_ in search bar
 4. Click **Install**

## Running matlab files from IDE
 1. Right click on .m file in files tree.
 2. Choose **Run 'helloWorld.m'**.
 3. If you do it for the first time you'll see an error message telling you that interpreter is not specified.
    1. To fix it open **Run | Edit configuration...** choose created configuration and specify path to `matlab` executable.  
    Tip: you can specify path to any other program that runs .m files like [Octave].
    
 4. You can save generated run configuration so it will not be removed.
 To do it open **Run | Edit configuration...** choose the configuration and click **Save** icon.

Note: currently `Program arguments` field in configuration is ignored.
 
## Development

For easier development install [Grammar-Kit](https://plugins.jetbrains.com/plugin/6606-grammar-kit) plugin.

### Run IntelliJ IDEA with the plugin
```bash
# Linux
./gradlew runIde

# Windows
gradlew.bat runIde
```

### Build jar with the plugin
```bash
# Linux
./gradlew buildPlugin

# Windows
gradlew.bat buildPlugin
```
Jar will be outputted to `build/distributions` directory.


 [Octave]: https://www.gnu.org/software/octave/
