@echo  %~dp0
cd %~dp0
protoc  ./protofile/*.proto  --java_out=./src/main/java/

pause 