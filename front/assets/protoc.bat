protoc.exe --java_out=..\..\gws\src\main\java .\protobuf\*

protoc.exe --js_out=library=.\protobuf\emag,binary:. .\protobuf\*