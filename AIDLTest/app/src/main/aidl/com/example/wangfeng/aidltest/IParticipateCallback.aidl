// IParticipateCallback.aidl
package com.example.wangfeng.aidltest;

// Declare any non-default types here with import statements
import com.example.wangfeng.aidltest.Product;

interface IParticipateCallback {
    // 用户加入或者离开的回调
    void onParticipate(inout Product product, boolean joinOrLeave);
}
