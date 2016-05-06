/*
 *
 * Copyright 2015, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

#import "UrlGetViewController.h"

#import <GRPCClient/GRPCCall.h>
#import <GRPCClient/GRPCCall+Tests.h>
#import <ProtoRPC/ProtoMethod.h>
#import <Helloworld.pbrpc.h>
#import <RxLibrary/GRXWriter+Immediate.h>
#import <RxLibrary/GRXWriteable.h>

@implementation UrlGetViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    NSString * const kRemoteHost = @"192.168.99.100:50051";
    
    // FIXME(simon): Enable TLS.
    [GRPCCall useInsecureConnectionsForHost:kRemoteHost];
    
    // Create the service.
    HLWGreeter *service = [[HLWGreeter alloc] initWithHost:kRemoteHost];

    // Allocate a request object.
    HLWHelloRequest *request = [[HLWHelloRequest alloc] init];
    request.name = @"Simon";

    // Call the RPC with the request.
    [service sayHelloWithRequest:request handler:^(HLWHelloReply *response, NSError *error) {
        if (response) {
            NSLog(@"Finished successfully with response:\n%@", response);
        } else if (error) {
            NSLog(@"Finished with error: %@", error);
        }
    }];
}

- (IBAction)getUrl:(id)sender {
    NSURL *url = [NSURL URLWithString:self.urlTextField.text];
    NSData *data = [NSData dataWithContentsOfURL:url];
    self.urlContentTextView.text = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
}

@end
