del ..\front\assets\js\emag\*.js

protoc.exe --java_out=..\gws-protobuf-common\src\main\java\ .\protobuf\common.proto
protoc.exe --js_out=library=..\front\assets\js\emag\common,binary:. .\protobuf\common.proto

protoc.exe --java_out=..\gws-protobuf-user\src\main\java\ .\protobuf\model.user.proto
protoc.exe --js_out=library=..\front\assets\js\emag\user,binary:. .\protobuf\model.user.proto

protoc.exe --java_out=..\gws-protobuf-chat\src\main\java\ .\protobuf\model.chat.proto
protoc.exe --js_out=library=..\front\assets\js\emag\chat,binary:. .\protobuf\model.chat.proto

protoc.exe --java_out=..\gws-protobuf-group\src\main\java\ .\protobuf\model.group.proto
protoc.exe --js_out=library=..\front\assets\js\emag\group,binary:. .\protobuf\model.group.proto


