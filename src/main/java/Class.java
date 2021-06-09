
import java.io.Serializable;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ryukai
 */
public class Class implements Serializable {
    
    float interv;
    String IP;
    String Loadavg;
    
    Class(float inte, String ip, String loadavg) throws RemoteException{
        
        this.interv = inte;
        this.IP = ip;
        this.Loadavg = loadavg;
    }
    Class() throws RemoteException{
    }
}
