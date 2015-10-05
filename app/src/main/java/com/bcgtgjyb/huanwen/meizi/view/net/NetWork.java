package com.bcgtgjyb.huanwen.meizi.view.net;

/**
 * Created by Administrator on 2015/10/4.
 */
public class NetWork  {
    private boolean net=false;
    private static NetWork netWork;

   private NetWork(){

   }

    public static NetWork getInstance(){
        if(netWork==null){
            netWork=new NetWork();
        }
        return netWork;
    }

    public boolean isNet() {
        return net;
    }

    public void setNet(boolean net) {
        this.net = net;
    }
}
