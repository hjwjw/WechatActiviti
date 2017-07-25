package com.hand.activiti.listener;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;


/**
 * Created by HJW on 2017/7/24 0024.
 */
public class SendTaskListener implements ExecutionListener {


    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        System.out.println("执行了发送消息操作" );

    }
}
