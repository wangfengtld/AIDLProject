/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/wangfeng/Documents/workspace/java/AIDLTest/app/src/main/aidl/com/example/wangfeng/aidltest/IRemoteService.aidl
 */
package com.example.wangfeng.aidltest;
// Declare any non-default types here with import statements

public interface IRemoteService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.wangfeng.aidltest.IRemoteService
{
private static final String DESCRIPTOR = "com.example.wangfeng.aidltest.IRemoteService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.wangfeng.aidltest.IRemoteService interface,
 * generating a proxy if needed.
 */
public static com.example.wangfeng.aidltest.IRemoteService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.wangfeng.aidltest.IRemoteService))) {
return ((com.example.wangfeng.aidltest.IRemoteService)iin);
}
return new com.example.wangfeng.aidltest.IRemoteService.Stub.Proxy(obj);
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
case TRANSACTION_join:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
String _arg1;
_arg1 = data.readString();
this.join(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_leave:
{
data.enforceInterface(DESCRIPTOR);
android.os.IBinder _arg0;
_arg0 = data.readStrongBinder();
this.leave(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getParticipators:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<String> _result = this.getParticipators();
reply.writeNoException();
reply.writeStringList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.wangfeng.aidltest.IRemoteService
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
@Override public void join(android.os.IBinder token, String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_join, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void leave(android.os.IBinder token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder(token);
mRemote.transact(Stub.TRANSACTION_leave, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.List<String> getParticipators() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<String> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getParticipators, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArrayList();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_join = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_leave = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getParticipators = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void join(android.os.IBinder token, String name) throws android.os.RemoteException;
public void leave(android.os.IBinder token) throws android.os.RemoteException;
public java.util.List<String> getParticipators() throws android.os.RemoteException;
}
