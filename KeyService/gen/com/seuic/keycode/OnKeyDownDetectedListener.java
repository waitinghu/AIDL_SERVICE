/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\KeyService\\src\\com\\seuic\\keycode\\OnKeyDownDetectedListener.aidl
 */
package com.seuic.keycode;
public interface OnKeyDownDetectedListener extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.seuic.keycode.OnKeyDownDetectedListener
{
private static final java.lang.String DESCRIPTOR = "com.seuic.keycode.OnKeyDownDetectedListener";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.seuic.keycode.OnKeyDownDetectedListener interface,
 * generating a proxy if needed.
 */
public static com.seuic.keycode.OnKeyDownDetectedListener asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.seuic.keycode.OnKeyDownDetectedListener))) {
return ((com.seuic.keycode.OnKeyDownDetectedListener)iin);
}
return new com.seuic.keycode.OnKeyDownDetectedListener.Stub.Proxy(obj);
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
case TRANSACTION_onKeyDownDetected:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.onKeyDownDetected(_arg0, _arg1);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.seuic.keycode.OnKeyDownDetectedListener
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
@Override public void onKeyDownDetected(int keyCode, int times) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(keyCode);
_data.writeInt(times);
mRemote.transact(Stub.TRANSACTION_onKeyDownDetected, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_onKeyDownDetected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void onKeyDownDetected(int keyCode, int times) throws android.os.RemoteException;
}
