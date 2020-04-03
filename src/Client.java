import Graph_Service.GraphInterface;

import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.Random;

public class Client {

    private static char[] operations = {'Q', 'A', 'D'};
    private final int MAX = 10;
    GraphInterface remoteObject;
    private Properties properties;

    public Client(String propFileName) throws IOException {
        readSystemProperties(propFileName);
    }

    void getObject(String globalName) throws RemoteException, NotBoundException, MalformedURLException {
//        String remoteHostName = properties.getProperty("GSP.server")+":"+properties.getProperty("GSP.server.port");
//        int remotePort = Integer.parseInt(properties.getProperty("GSP.rmiregistry.port"));
//        Registry reg = LocateRegistry.getRegistry(remoteHostName, remotePort);
        remoteObject = (GraphInterface) Naming.lookup(globalName);
    }

    Operation getOperation(double writePercentage) {
        double random = Math.random();
        if (random <= writePercentage) {
            random = Math.random();

            return random >= 0.5 ? buildOperation(operations[1]) : buildOperation(operations[2]);
        } else {
            return buildOperation(operations[0]);
        }


    }

    private void readSystemProperties(String propFileName) throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
        inputStream.close();
    }

    public void processOperation(Operation op) throws RemoteException {
        switch (op.getType()) {
            case 'Q':
                int result = remoteObject.query(op.getNode1(), op.getNode2());
                System.out.println("Query (" + op.getNode1() + " , " + op.getNode2() + " ) = " + result);
                break;

            case 'A':
                remoteObject.add(op.getNode1(), op.getNode2());
                System.out.println("Add ( " + op.getNode1() + " , " + op.getNode2() + ") ");

                break;
            case 'D':
                remoteObject.delete(op.getNode1(), op.getNode2());
                System.out.println("delete ( " + op.getNode1() + " , " + op.getNode2() + ") ");

                break;

        }
    }

    private Operation buildOperation(char operation) {
        Operation op = new Operation();
        op.setType(operation);
        Random random = new Random();
        op.setNode1(random.nextInt(MAX));
        op.setNode2(random.nextInt(MAX));
        return op;
    }

    public void readOperationsFromFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bf.readLine()) != null) {
            String[] operationParts = line.split("\\s");
            Operation op = new Operation(operationParts[0].charAt(0), Integer.parseInt(operationParts[1]), Integer.parseInt(operationParts[1]));
            this.processOperation(op);
        }
    }

}
