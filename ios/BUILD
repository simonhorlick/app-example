ios_application(
  name = "app",
  binary = ":BazelSample_binary",
  bundles = [
  #  ":PrenotCalculatorResources"
  ],
  #datamodels = glob(["PrenotCalculator/DataModel.xcdatamodeld/**"]),
  infoplist = "src/Info.plist",
  storyboards = ["src/Base.lproj/Main.storyboard"],
  asset_catalogs = ["src/Images.xcassets/AppIcon.appiconset/Contents.json"],
  #visibility = ["//visibility:public"],
  #xibs = ["PrenotCalculator/CalculatorViewController.xib"],
)

#objc_bundle_library(
#  name = "BazelSampleResources",
#  resources = glob(["Resources/**"]),
#)

objc_binary(
  name = "BazelSample_binary",
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

# FIXME(simon): Use ios_application rule as the application-related attributes (infoplist, app_icon, resources, xibs, datamodels...) on this rule are deprecated.

