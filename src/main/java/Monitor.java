
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.io.WriteAbortedException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import static java.rmi.server.RemoteServer.getClientHost;
import java.rmi.server.ServerNotActiveException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Monitor implements Serializable{
    
    Monitor() throws RemoteException{
    }
    

    /**
     *
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    public String pingmonitor() throws RemoteException, ServerNotActiveException{
        Coordinador apuntador = new Coordinador();
        
        if(Thread.currentThread().isAlive()){
        String host = getClientHost();
        
        if(!host.isEmpty()){
            
            return host;

        }
        }
        return "";
    }

    /**
     *
     * @param args
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws IOException
     * @throws NotBoundException
     * @throws WriteAbortedException
     * @throws NotSerializableException
     */
    public static void main(String args[]) throws RemoteException, MalformedURLException, IOException, NotBoundException, WriteAbortedException, NotSerializableException {

        String ip;
        ip = JOptionPane.showInputDialog("Ingrese la direccion ip del coordinador");
        Method_RMI ad = (Method_RMI) Naming.lookup("//" + ip + "/coordinador");

        Class datos = ad.iniMonitor();
        final float intervalo;
        intervalo = datos.interv;
        Runnable runnable
                = () -> {
                    try {
                        while (true) {
                            try {
                                
                                Process result = Runtime.getRuntime().exec("cat /proc/loadavg");
                                BufferedReader stdInput = new BufferedReader(new InputStreamReader(result.getInputStream()));
                                StringBuilder res = new StringBuilder(80);
                                String s = null;
                                while ((s = stdInput.readLine()) != null) {
                                    res.append(s);
                                }
                                JOptionPane.showMessageDialog(null, res);
                                datos.Loadavg = res.toString();
                                ad.loadMonitor(datos); 
                                Thread.sleep((long) intervalo);
                            } catch (IOException e) {
                            }
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                };

        runnable.run();
        
        
       

    }

}
