package com.sjsu.webmart.model.notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ckempaiah
 * Date: 8/9/12
 * Time: 10:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractMessageObservable implements MessageObservable {

    private List<MessageObserver> observers= new ArrayList<MessageObserver>();

    @Override
    public void addObserver(MessageObserver notification) {
        observers.add(notification);
    }

    @Override
    public void deleteObserver(MessageObserver notification) {
        observers.remove(notification);
    }

    @Override
    public void notifyObservers(Object args) {

        for (MessageObserver observer: observers){
            observer.update(this, args);
        }
    }
}
