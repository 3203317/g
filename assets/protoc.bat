del ..\front\assets\js\*.js

protoc.exe --java_out=..\gws\src\main\java\ .\protobuf\*

protoc.exe --js_out=library=..\front\assets\js\emag,binary:. .\protobuf\*