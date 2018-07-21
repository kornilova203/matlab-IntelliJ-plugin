# Matlab support for IntelliJ
Matlab syntax highlighting plugin for JetBrains IDEs

![Screenshot of Matlab plugin](screenshots/screen.png)

https://plugins.jetbrains.com/plugin/10941-matlab-support

## Installation
 1. Open **Settings | Plugins** (Windows) or **Preferences | Plugins** (MacOS)
 2. Click **Browse repositories...**
 3. Type _Matlab Support_ in search bar
 4. Click **Install**

## Build
1. Install [Grammar-Kit](https://plugins.jetbrains.com/plugin/6606-grammar-kit) plugin
2. Open [Matlab.bnf](src/main/grammar/Matlab.bnf) and run `Generate Parser Code` action in context menu
3. Open [MatlabLexer.flex](src/main/grammar/MatlabLexer.flex) and run `Run JFlex Generator` action in context menu
4. Run `runIdea`
    ```bash
    # Linux
    ./gradlew runIdea

    # Windows
    gradlew.bat runIdea
    ```
