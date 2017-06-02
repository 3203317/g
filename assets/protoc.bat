del ..\front\assets\js\emag\*.js

protoc.exe --java_out=..\gws-protobuf-common\src\main\java\ .\protobuf\common.proto
protoc.exe --js_out=library=..\front\assets\js\emag\common,binary:. .\protobuf\common.proto