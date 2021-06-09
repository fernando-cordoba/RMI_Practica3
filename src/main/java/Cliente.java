
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.NotSerializableException;
import java.io.WriteAbortedException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;
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
public class Cliente {

    /**
     *
     * @param args
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws IOException
     * @throws NotBoundException
     * @throws WriteAbortedException
     * @throws NotSerializableException
     * @throws ServerNotActiveException
     */
    public static void main(String args[]) throws RemoteException, MalformedURLException, IOException, NotBoundException, WriteAbortedException, NotSerializableException, ServerNotActiveException {

        String ip;
        List<Integer> o = new ArrayList<>();
        ip = JOptionPane.showInputDialog("Ingrese la direccion ip del Coordinador");
        Method_RMI ad = (Method_RMI) Naming.lookup("//" + ip + "/coordinador");
        
        final float interval = Float.parseFloat(JOptionPane.showInputDialog("Ingrese en minutos con que frecuencia se pedira la información al coordinador"));
        Runnable runnable
                = () -> {
                    try {
                        while (true) {
                            try {
                                

                                int cantMonitores = ad.iniClient();
                                JOptionPane.showMessageDialog(null, "Cantidad de monitores activos " + cantMonitores);
                                    
                                    if(cantMonitores > 0){
                                        String load =  ad.getLoadAVG();
                                        JOptionPane.showMessageDialog(null, "Ultima carga Obtenida : " + load);
                                    }else{
                                    
                                        JOptionPane.showMessageDialog(null, "Actualmente no hay monitores disponibles");
                                    }
    
                                    Thread.sleep((long) (interval * 60000));
                                
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
