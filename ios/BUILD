ios_application(
  name = "app",
  binary = ":app-binary",
  infoplist = "src/app-Info.plist",
  storyboards = ["src/Base.lproj/Main.storyboard"],
  asset_catalogs = ["src/Images.xcassets/AppIcon.appiconset/Contents.json"],
)

objc_binary(
  name = "app-binary",
  srcs = [
    "src/main.m",
    "src/AppDelegate.m",
    "src/ViewController.m",
  ],
  hdrs = glob(["src/*.h"]),
  sdk_frameworks = [
    "SystemConfiguration", # used by grpc
  ],
  deps = [
    "//proto:helloworld",
    "//third_party/grpc/upstream:grpc_client",
    "//third_party/grpc/upstream:rx_library",
  ],
)

