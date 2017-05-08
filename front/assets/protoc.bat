protoc.exe --java_out=..\..\gws\src\main\java .\protobuf\*

protoc.exe --js_out=import_style=commonjs,binary:. .\protobuf\*