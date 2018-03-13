package ttt;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TTTServer {
	public static void main(String[] args) throws IOException {
		int registryPort = 1099;
		    
		System.out.println("Main OK");
		
		try {
			
			TTT game = new TTT();
            System.out.println("After create");
            
            Registry reg = LocateRegistry.createRegistry(registryPort);
            reg.rebind("TTTServer", game);
            

            System.out.println("TTT server ready");

            System.out.println("Awaiting connections");
            System.out.println("Press enter to shutdown");
            System.in.read();
            System.exit(0);
            
            
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
