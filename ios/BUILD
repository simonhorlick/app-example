load("/third_party/grpc/upstream/grpc", "objc_grpc_library")

objc_grpc_library(
  name = "helloworld",
  services = ["proto/helloworld.proto"],
  other_messages = [],
)

objc_binary(
    name = "BazelSample",
    srcs = [
        "src/main.m",
        "src/AppDelegate.m",
        "src/ViewController.m",
    ],
    hdrs = glob(["src/*.h"]),
    asset_catalogs = ["src/Images.xcassets/AppIcon.appiconset/Contents.json"],
    infoplist = "src/Info.plist",
    storyboards = ["src/Base.lproj/Main.storyboard"],
    sdk_frameworks = [
      "SystemConfiguration", # used by grpc
    ],
    deps = [
			":helloworld",
      "//third_party/grpc/upstream:grpc_client",
      "//third_party/grpc/upstream:rx_library",
    ],
)

# FIXME(simon): Use ios_application rule as the application-related attributes (infoplist, app_icon, resources, xibs, datamodels...) on this rule are deprecated.

