@echo off
setlocal

REM Get the full path of the directory where the batch file is located
set "batch_dir=%~dp0"

REM Build the full path to the input file relative to the batch file directory
set "input_dir=%batch_dir%..\..\..\src\test\resource"

REM Run the Java program with the input file path as an argument
java -jar "%batch_dir%\softwareProduct.jar" "%input_dir%"

endlocal
