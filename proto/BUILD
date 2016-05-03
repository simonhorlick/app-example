load("/third_party/grpc/grpc_proto", "proto_library")
load("/third_party/grpc/upstream/grpc", "objc_grpc_library")

proto_library(
  name = "helloworld",
  src = "helloworld.proto",
  has_service = True,
  generate_java = True,
  generate_android = True,
  generate_cc = False,
  visibility = ["//visibility:public"],
)

objc_grpc_library(
  name = "helloworld",
  services = ["helloworld.proto"],
  other_messages = [],
  visibility = ["//visibility:public"],
)

