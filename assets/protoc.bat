del ..\front\assets\js\emag\*.js

protoc.exe --java_out=..\gws-protobuf-common\src\main\java\ .\protobuf\common.proto
protoc.exe --js_out=library=..\front\assets\js\emag\common,binary:. .\protobuf\common.proto



protoc.exe --java_out=..\gws-protobuf-user\src\main\java\ .\protobuf\method.user.login.proto .\protobuf\method.user.logout.proto .\protobuf\method.user.get.proto .\protobuf\model.user.proto
protoc.exe --js_out=library=..\front\assets\js\emag\user,binary:. .\protobuf\method.user.login.proto .\protobuf\method.user.logout.proto .\protobuf\method.user.get.proto .\protobuf\model.user.proto