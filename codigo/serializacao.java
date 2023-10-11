import java.io.*;

public class Serializacao {
    public static void main(String[] args) {
        // Serialização
        Veiculo veiculo = new Veiculo("ABC123");
        try {
            FileOutputStream fileOut = new FileOutputStream("veiculo.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(veiculo);
            out.close();
            fileOut.close();
            System.out.println("Objeto Veiculo foi serializado em veiculo.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Deserialização
        Veiculo veiculoLido = null;
        try {
            FileInputStream fileIn = new FileInputStream("veiculo.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            veiculoLido = (Veiculo) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (veiculoLido != null) {
            System.out.println("Objeto Veiculo foi deserializado. Placa: " + veiculoLido.getPlaca());
        }
    }
}