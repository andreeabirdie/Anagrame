import domain.Clasament;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void enableStart() throws RemoteException;
    void disableStart() throws RemoteException;
    void newRound(Integer id, String s, Integer points) throws RemoteException;
    void finalClasament(Clasament c) throws RemoteException;
}
