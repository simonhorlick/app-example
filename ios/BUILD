load("/third_party/grpc/upstream/grpc", "objc_grpc_library")

ios_application(
  name = "ios-app",
  binary = "ios-app-binary",
  infoplist = "UrlGet/UrlGet-Info.plist",
)

objc_grpc_library(
  name = "test",
  services = ["test.proto"],
  other_messages = [],
  visibility = ["//visibility:public"],
)

objc_binary(
  name = "ios-app-binary",
  srcs = [
    "UrlGet/main.m",
  ],
  sdk_frameworks = [
    "SystemConfiguration", # used by grpc
  ],
  deps = [
        ":UrlGetClasses",
  ],
)

objc_library(
    name = "UrlGetClasses",
    srcs = [
        "UrlGet/AppDelegate.m",
        "UrlGet/UrlGetViewController.m",
    ],
    hdrs = glob(["UrlGet/*.h"]),
    deps = [
      ":test",
      "//third_party/grpc/upstream:grpc_client",
      "//third_party/grpc/upstream:rx_library",
    ],
    xibs = ["UrlGet/UrlGetViewController.xib"],
)

