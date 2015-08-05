// IRemoteService.aidl
package com.example.wangfeng.aidltest;

// Declare any non-default types here with import statements

interface IRemoteService {

  void join(IBinder token, String name);
  void leave(IBinder token);
  List<String> getParticipators();
}
