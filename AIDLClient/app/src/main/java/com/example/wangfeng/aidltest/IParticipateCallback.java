/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/wangfeng/Documents/workspace/java/AIDLProject/AIDLTest/app/src/main/aidl/com/example/wangfeng/aidltest/IParticipateCallback.aidl
 */
package com.example.wangfeng.aidltest;
// Declare any non-default types here with import statements

public interface IParticipateCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.wangfeng.aidltest.IParticipateCallback
{
private static final String DESCRIPTOR = "com.example.wangfeng.aidltest.IParticipateCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.wangfeng.aidltest.IParticipateCallback interface,
 * generating a proxy if needed.
 */
public static com.example.wangfeng.aidltest.IParticipateCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.wangfeng.aidltest.IParticipateCallback))) {
return ((com.example.wangfeng.aidltest.IParticipateCallback)iin);
}
return new com.example.wangfeng.aidltest.IParticipateCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_onParticipate:
{
data.enforceInterface(DESCRIPTOR);
String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
this.onParticipate(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.wangfeng.aidltest.IParticipateCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
// 用户加入或者离开的回调

@Override public void onParticipate(String name, boolean joinOrLeave) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeInt(((joinOrLeave)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_onParticipate, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onParticipate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
// 用户加入或者离开的回调

public void onParticipate(String name, boolean joinOrLeave) throws android.os.RemoteException;
}
