// Copyright 2015 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.bazel.example.android.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.okhttp.OkHttpChannelBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
  
  static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    logger.info("onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    logger.info("onCreateOptionsMenu");
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    logger.info("onOptionsItemSelected");
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_ping) {
      new GrpcTask().execute();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private class GrpcTask extends AsyncTask<Void, Void, String> {
      private String mHost = "192.168.99.100";
      private String mMessage;
      private int mPort = 50051;
      private ManagedChannel mChannel;

      @Override
      protected void onPreExecute() {
          logger.info("onPreExecute");
          //mHost = mHostEdit.getText().toString();
          //mMessage = mMessageEdit.getText().toString();
          //String portStr = mPortEdit.getText().toString();
          //mPort = TextUtils.isEmpty(portStr) ? 0 : Integer.valueOf(portStr);
          //mResultText.setText("");
      }

      private String sayHello(ManagedChannel channel) {
          logger.info("sayHello");
          GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
          HelloRequest message = HelloRequest.newBuilder().setName("").build();
          HelloReply reply = stub.sayHello(message);
          return "";
      }

      @Override
      protected String doInBackground(Void... nothing) {
          logger.info("doInBackground");
          try {
              mChannel = OkHttpChannelBuilder.forAddress(mHost, mPort)
                  .usePlaintext(true)
                  .build();
              Log.i("BazelSample", "Requesting greeting from server");
              return sayHello(mChannel);
          } catch (Exception e) {
              return "Failed... : " + e.getMessage();
          }
      }

      @Override
      protected void onPostExecute(String result) {
          logger.info("onPostExecute");
          try {
              mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
          }
          //mResultText.setText(result);
          //mSendButton.setEnabled(true);
      }
  }
}
