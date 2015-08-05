package com.example.wangfeng.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wangfeng.aidltest.IRemoteService;


public class MainActivity extends Activity {

  private Button bindBtn, unbindBtn, callBtn;
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

    try {
      if (!mIsJoin) {
        String name = "Client:" + 1;
        mService.join(mToken, name);
        callBtn.setText("leave");
        mIsJoin = true;
      } else {
        mService.leave(mToken);
        callBtn.setText("join");
        mIsJoin = false;
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }
}
