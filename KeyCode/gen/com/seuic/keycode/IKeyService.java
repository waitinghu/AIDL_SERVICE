/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\KeyCode\\src\\com\\seuic\\keycode\\IKeyService.aidl
 */
package com.seuic.keycode;
public interface IKeyService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.seuic.keycode.IKeyService
{
private static final java.lang.String DESCRIPTOR = "com.seuic.keycode.IKeyService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.seuic.keycode.IKeyService interface,
 * generating a proxy if needed.
 */
public static com.seuic.keycode.IKeyService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.seuic.keycode.IKeyService))) {
return ((com.seuic.keycode.IKeyService)iin);
}
return new com.seuic.keycode.IKeyService.Stub.Proxy(obj);
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
case TRANSACTION_registListener:
{
data.enforceInterface(DESCRIPTOR);
com.seuic.keycode.OnKeyDownDetectedListener _arg0;
_arg0 = com.seuic.keycode.OnKeyDownDetectedListener.Stub.asInterface(data.readStrongBinder());
this.registListener(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_startRecord:
{
data.enforceInterface(DESCRIPTOR);
this.startRecord();
reply.writeNoException();
return true;
}
case TRANSACTION_stopRecord:
{
data.enforceInterface(DESCRIPTOR);
this.stopRecord();
reply.writeNoException();
return true;
}
case TRANSACTION_isRcord:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isRcord();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getAllKeyCode:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.getAllKeyCode();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_getAllKeyNum:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.getAllKeyNum();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.seuic.keycode.IKeyService
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
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void registListener(com.seuic.keycode.OnKeyDownDetectedListener listener) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((listener!=null))?(listener.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registListener, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void startRecord() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startRecord, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopRecord() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopRecord, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean isRcord() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isRcord, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int[] getAllKeyCode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAllKeyCode, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int[] getAllKeyNum() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAllKeyNum, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_registListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_startRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_stopRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isRcord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getAllKeyCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getAllKeyNum = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
public void registListener(com.seuic.keycode.OnKeyDownDetectedListener listener) throws android.os.RemoteException;
public void startRecord() throws android.os.RemoteException;
public void stopRecord() throws android.os.RemoteException;
public boolean isRcord() throws android.os.RemoteException;
public int[] getAllKeyCode() throws android.os.RemoteException;
public int[] getAllKeyNum() throws android.os.RemoteException;
}
