import java.io.*;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Start {


    public static void main(String[] args) throws IOException, NotBoundException {

        Map<Character, Integer> operationCount = new HashMap<>();
        Client client = new Client("system.properties");
        client.getObject("rmi://localhost:6000/graph");
        Scanner scanner = new Scanner(System.in);
        int numOfRequests = scanner.nextInt();
        double writePercentage = scanner.nextInt() / 100.0;
        for (int i = 0; i < numOfRequests; i++) {
            Operation operation = client.getOperation(writePercentage);
            int count = operationCount.getOrDefault(operation.getType(), 0);
            operationCount.put(operation.getType(), count + 1);
            client.processOperation(operation);
        }
        for (Map.Entry<Character, Integer> entry : operationCount.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }


    }
}
