package com.example.wangfeng.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wangfeng.aidltest.IParticipateCallback;
import com.example.wangfeng.aidltest.IRemoteService;
import com.example.wangfeng.aidltest.Product;


public class MainActivity extends Activity {

  private Button bindBtn, unbindBtn, callBtn;
  private ListView listView;
  private ArrayAdapter mAdapter;
  private IBinder mToken        = new Binder();
  private boolean isBindService = false;
  private boolean mIsJoin       = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bindBtn = (Button) findViewById(R.id.bind);
    unbindBtn = (Button) findViewById(R.id.unbind);
    callBtn = (Button) findViewById(R.id.calll);
    listView = (ListView) findViewById(R.id.listview);
    mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    listView.setAdapter(mAdapter);

    bindBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent("com.example.wangfeng.aidltest.IRemoteService");
//        intent.setAction("com.example.wangfeng.aidltest.IRemoteService");
        isBindService = bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
      }
    });
    unbindBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isBindService) {
          unbindService(mServiceConnection);
          try {
            mService.unregisterParticipateCallback(mParticipateCallback);
          } catch (RemoteException e) {
            e.printStackTrace();
          }
          isBindService = false;
        }
      }
    });
    callBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        callRemote();
      }
    });
  }

  private IRemoteService mService;

  private ServiceConnection mServiceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      Toast.makeText(MainActivity.this, "Service connected" + name, Toast.LENGTH_SHORT).show();
      mService = IRemoteService.Stub.asInterface(service);
      try {
        mService.registerParticipateCallback(mParticipateCallback);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
      isBindService = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      //特别注意，此方法只有service被后台杀死才会调用，主动调用unbindService不会调用此方法
      Toast.makeText(MainActivity.this, "Service disconnected" + name, Toast.LENGTH_SHORT).show();
      mService = null;
      isBindService = false;
    }
  };

  private void callRemote() {

    if (mService == null) {
      Toast.makeText(this, "Service is not available yet!", Toast.LENGTH_SHORT).show();
      return;
    }
    if (!isBindService) {
      Toast.makeText(this, "Service is unbind!", Toast.LENGTH_SHORT).show();
      return;
    }

    try {
      if (!mIsJoin) {
        String name = "Client:" + 1;
        mService.join(mToken, name);
        int num = 10;
        for (int i = 0; i < num; i++) {
          IBinder iBinder = new Binder();
          final String nameClient = "client:" + i;
          mService.join(iBinder, nameClient);
          Log.d("message", "当前加入人数：" + mService.getParticipators().size());
        }
        callBtn.setText("leave");
        mIsJoin = true;
      } else {
        mService.leave(mToken);
        Log.d("message", "当前加入人数：" + mService.getParticipators().size());
        callBtn.setText("join");
        mIsJoin = false;
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  private IParticipateCallback mParticipateCallback = new IParticipateCallback.Stub() {

    @Override
    public void onParticipate(Product product, boolean joinOrLeave) throws RemoteException {
      Log.d("message", "onParticipate  " + Thread.currentThread().getName());

      Message message = handler.obtainMessage();
      message.what = joinOrLeave ? 1 : 0;
      message.obj = product.getName();
      handler.sendMessage(message);
    }
  };

  private android.os.Handler handler = new android.os.Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 1) {
        mAdapter.add(msg.obj);
      } else {
        mAdapter.remove(msg.obj);
      }
      listView.setSelection(mAdapter.getCount() - 1);
    }
  };
}
