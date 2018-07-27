# Matlab support for IntelliJ
Matlab syntax highlighting plugin for JetBrains IDEs

![Screenshot of Matlab plugin](screenshots/screen.png)

https://plugins.jetbrains.com/plugin/10941-matlab-support

## Installation
 1. Open **Settings | Plugins** (Windows) or **Preferences | Plugins** (MacOS)
 2. Click **Browse repositories...**
 3. Type _Matlab Support_ in search bar
 4. Click **Install**
 
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
