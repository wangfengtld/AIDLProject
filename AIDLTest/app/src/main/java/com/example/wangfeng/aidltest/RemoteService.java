package com.example.wangfeng.aidltest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangfeng on 15/8/4.
 */
public class RemoteService extends Service {


  MySingleThreadExecutor pool = new MySingleThreadExecutor();
  private RemoteCallbackList<IParticipateCallback> mCallbacks = new RemoteCallbackList<>();
  private List<Client>                             mClients   = new ArrayList<>();

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public IBinder onBind(Intent intent) {
    // Return the interface
    Log.d("message", "onBind " + intent.getAction());
    return mBinder;
  }


  private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
    @Override
    public void registerParticipateCallback(IParticipateCallback cb) throws RemoteException {
      Log.d("message", cb.toString() + "客户端注册进入");
      mCallbacks.register(cb);
      Log.d("message", "共有 " + mCallbacks.getRegisteredCallbackCount() + " 个客户端");
    }

    @Override
    public void unregisterParticipateCallback(IParticipateCallback cb) throws RemoteException {
      Log.d("message", cb.toString() + "退出");
      mCallbacks.unregister(cb);
      Log.d("message", "移除后共有 " + mCallbacks.getRegisteredCallbackCount() + " 个客户端");
    }

    @Override
    public void join(IBinder token, String name) throws RemoteException {
      // 通知client加入
      int idx = findClient(token);
      if (idx >= 0) {
        Log.d("message", "already joined");
        return;
      }
      Log.d("message", name + "joined" + token.toString());
      Client client = new Client(token, name);
      Product product = new Product();
      product.setName(client.getmName());
      product.setId(2);
      product.setDesc(client.getmName());

      notifyParticipate(product, true);
      mClients.add(client);
    }

    @Override
    public void leave(IBinder token) throws RemoteException {
      // 通知client离开
      int idx = findClient(token);
      if (idx < 0) {
        Log.d("message", "already left");
        return;
      }
      Log.d("message", token.toString() + "left");
      Client client = mClients.get(idx);
      Product product = new Product();
      product.setName(client.getmName());
      product.setId(2);
      product.setDesc(client.getmName());

      notifyParticipate(product, false);
      mClients.remove(client);
    }

    @Override
    public List<String> getParticipators() throws RemoteException {
      List<String> list = new ArrayList<>();
      for (Client mClient : mClients) {
        list.add(mClient.getmName());
      }
      return list;
    }
  };

  // 通过IBinder查找Client
  private int findClient(IBinder token) {
    for (int i = 0; i < mClients.size(); i++) {
      if (mClients.get(i).mToken == token) {
        return i;
      }
    }
    return -1;
  }

  private void notifyParticipate(final Product product, final boolean joinOrLeave) {
    final int len = mCallbacks.beginBroadcast();
    for (int i = 0; i < len; i++) {
      final IParticipateCallback iParticipateCallback = mCallbacks.getBroadcastItem(i);
      // 通知回调
//        new Thread(new Runnable() {
//          @Override
//          public void run() {
//
//            try {
//              Thread.sleep(1000);
//              iParticipateCallback.onParticipate(name, joinOrLeave);
//            } catch (RemoteException e) {
//              e.printStackTrace();
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//            }
//          }
//        }).start();
      pool.execute(new Runnable() {
        @Override
        public void run() {

          try {
            Thread.sleep(1000);
            iParticipateCallback.onParticipate(product, joinOrLeave);
          } catch (RemoteException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
    }
    mCallbacks.finishBroadcast();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    // 取消掉所有的回调
    pool.shutdownNow();
    mCallbacks.kill();
    mClients = null;
    Log.d("message", "service destroy!");
  }

  private final class Client {
    public final IBinder mToken;
    public final String  mName;

    public Client(IBinder token, String name) {
      mToken = token;
      mName = name;
    }

    public IBinder getmToken() {
      return mToken;
    }

    public String getmName() {
      return mName;
    }
  }
}