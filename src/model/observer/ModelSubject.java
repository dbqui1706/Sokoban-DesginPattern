package model.observer;

public interface ModelSubject {
    public void addModelObserver(ModelObserver modelObserver);
    public void removeModelObserver(ModelObserver modelObserver);
    public void notifyModelObserver();
}
