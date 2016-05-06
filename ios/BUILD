ios_application(
  name = "ios-app",
  binary = "ios-app-binary",
  infoplist = "UrlGet/UrlGet-Info.plist",
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
    #  "//proto:helloworld",
      "//third_party/grpc/upstream:grpc_client",
      "//third_party/grpc/upstream:rx_library",
    ],
    xibs = ["UrlGet/UrlGetViewController.xib"],
)

