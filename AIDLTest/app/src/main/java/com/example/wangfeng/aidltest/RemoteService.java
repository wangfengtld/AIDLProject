package com.example.wangfeng.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfeng on 15/8/4.
 */
public class RemoteService extends Service {


  private List<Client> mClients = new ArrayList<>();

  // 通过IBinder查找Client
  private int findClient(IBinder token) {
    for (int i = 0; i < mClients.size(); i++) {
      if (mClients.get(i).mToken == token) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public IBinder onBind(Intent intent) {
    // Return the interface
    Log.d("message", "onBind" + intent.getAction());
    return mBinder;
  }


  private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
    @Override
    public void join(IBinder token, String name) throws RemoteException {
      int idx = findClient(token);
      if (idx >= 0) {
        Log.d("message", "already joined");
        return;
      }
      Log.d("message", name + "joined" + token.toString());
      Client client = new Client(token, name);
      // 注册客户端死掉的通知
      token.linkToDeath(client, 0);
      mClients.add(client);
    }

    @Override
    public void leave(IBinder token) throws RemoteException {
      int idx = findClient(token);
      if (idx < 0) {
        Log.d("message", "already left");
        return;
      }
      Log.d("message", token.toString() + "left");
      Client client = mClients.get(idx);
      mClients.remove(client);

      // 取消注册
      client.mToken.unlinkToDeath(client, 0);
    }

    @Override
    public List<String> getParticipators() throws RemoteException {
      return null;
    }
  };

  private final class Client implements IBinder.DeathRecipient {
    public final IBinder mToken;
    public final String  mName;

    public Client(IBinder token, String name) {
      mToken = token;
      mName = name;
    }

    @Override
    public void binderDied() {
      // 客户端死掉，执行此回调
      int index = mClients.indexOf(this);
      if (index < 0) {
        return;
      }

      Log.d("message", "client died: " + mName);
      mClients.remove(this);
    }
  }
}