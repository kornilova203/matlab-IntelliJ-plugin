# matlab-IntelliJ-plugin
Matlab syntax highlighting plugin for IntelliJ IDEA

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