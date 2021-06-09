
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.io.WriteAbortedException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ryukai
 */
public class Coordinador extends UnicastRemoteObject implements Method_RMI, Serializable {
    
    public List<Integer> CantMonit = new ArrayList<>();

    /**
     *
     */
    public List<Class> Lista = new ArrayList<>();

    /**
     *
     */
    public List<String> Listaaux = new ArrayList<>();

    public String valor;
    /**
     *
     * @throws RemoteException
     */
    public Coordinador() throws RemoteException {
        super();
    }

    /**
     *
     * @return @throws RemoteException
     * @throws IOException
     * @throws WriteAbortedException
     * @throws NotSerializableException
     */
    @Override
    public Class iniMonitor() throws RemoteException, IOException, WriteAbortedException, NotSerializableException {
        
        String address = InetAddress.getLocalHost().toString();
        String[] ad = address.split("/");
        float interval = Float.parseFloat(JOptionPane.showInputDialog("Ingrese el intervalo de medición del computador en minutos"));        
        Class obj = new Class((interval * 60000), ad[1], "");
        Lista.add(obj);
        return obj;
    }

    /**
     *
     * @param a
     * @throws RemoteException
     */
    @Override
    public void loadMonitor(Class a) throws RemoteException {
        int i = 0;
        
        Iterator<Class> nombreIterator = Lista.iterator();
        while (nombreIterator.hasNext()) {
            Class elemento = nombreIterator.next();
            if (elemento.IP.equals(a.IP)) {    
                valor = a.Loadavg;
                Lista.add(i, a);
                break;
            }
            i++;
        }        
        
    }

    /**
     *
     * @return @throws RemoteException
     * @throws IOException
     * @throws WriteAbortedException
     * @throws NotSerializableException
     * @throws ServerNotActiveException
     */
    @Override
    public int iniClient() throws RemoteException, IOException, WriteAbortedException, NotSerializableException, ServerNotActiveException{
        
        Monitor a = new Monitor();
        String host = "";        

            if(!(this.valor == null)){
            
             host = a.pingmonitor();
            }else{
            
                if(this.CantMonit.size()> 0){
               
                    JOptionPane.showMessageDialog(null, CantMonit.size());
                    this.CantMonit.add(0, (Listaaux.size()-1));
                }
                
            }
            
            if(!(host.equals(""))){
             
                if(!(Listaaux.contains(host))){
                Listaaux.add(host);      
                this.CantMonit.add(0, Listaaux.size());
                }
             }
;
        if (this.CantMonit.size() <= 0) {
            Lista.removeAll(Lista);            
        } else {

            for(int j=0; j < Lista.size(); j++){
                if(!Listaaux.contains(Lista.get(j).IP)){
                Lista.remove(j);
                break;
                }

            }
        }
        
        if(Lista.size()<=0){
        
            if(!(this.CantMonit.isEmpty())){
            this.valor = "";
            this.CantMonit.remove(0);
            }
        }

        int temp = 0;
        
        if(this.CantMonit.size()>0){
            
            temp = this.CantMonit.get(0);
        
        }
        return temp;
        
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public String getLoadAVG() throws RemoteException {
        
        return this.valor;


    }

    /**
     *
     * @param args
     * @throws RemoteException
     */
    public static void main(String args[]) throws RemoteException {
        
        try {
            
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("coordinador", new Coordinador());
            JOptionPane.showMessageDialog(null, "CORRIENDO....");
            
        } catch (RemoteException e) {
            
            System.out.println("");
        }
    }
    
}
