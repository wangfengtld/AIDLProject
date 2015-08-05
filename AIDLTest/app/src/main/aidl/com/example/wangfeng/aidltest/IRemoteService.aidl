// IRemoteService.aidl
package com.example.wangfeng.aidltest;

import com.example.wangfeng.aidltest.IParticipateCallback;
// Declare any non-default types here with import statements

interface IRemoteService {

  void registerParticipateCallback(IParticipateCallback cb);
  void unregisterParticipateCallback(IParticipateCallback cb);
  void join(IBinder token, String name);
  void leave(IBinder token);
  List<String> getParticipators();
}
