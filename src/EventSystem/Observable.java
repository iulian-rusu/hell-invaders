package EventSystem;

import EventSystem.Events.GameEvent;

import java.util.ArrayList;

public abstract class Observable {
    private ArrayList<Observer> allObservers;

    public void AddObserver(Observer o){
        if(allObservers==null){
            allObservers=new ArrayList<>();
        }
        allObservers.add(o);
    }

    public void RemoveObserver(Observer o){
        allObservers.remove(o);
    }

    public void NotifyAllObservers(GameEvent e){
        if(allObservers==null)
            return;
        for(Observer o:allObservers){
            o.OnNotify(e);
        }
    }
}
