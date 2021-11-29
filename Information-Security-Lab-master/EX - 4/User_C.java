import java.net.*;
import java.io.*;
import java.util.Random;

public class User_C {
	public static int getRandomNumberUsingNextInt(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

	static int power(int x, int y, int p) {
		int res = 1; // Initialize result

		x = x % p; // Update x if it is more than or
		// equal to p

		while (y > 0) {
			// If y is odd, multiply x with result
			if (y % 2 == 1) {
				res = (res * x) % p;
			}

			// y must be even now
			y = y >> 1; // y = y/2
			x = (x * x) % p;
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		try {
			double alpha, yA, yB, key_B;
			int p;
			String Bstr;
			ServerSocket serverSocket = new ServerSocket(5555);
			System.out.println("Intruder Eve");
			Socket server = serverSocket.accept();
			System.out.println("Connected !!");
			DataInputStream in = new DataInputStream(server.getInputStream());
			yA = Double.parseDouble(in.readUTF());
			p = Integer.parseInt(in.readUTF());
			alpha = Double.parseDouble(in.readUTF());
			System.out.println("Recieved from Alice " + yA);

			int xc = getRandomNumberUsingNextInt(2, p); // private key of intruder
			double yC = ((Math.pow(alpha, xc)) % p); // public key of intruder
			Bstr = Double.toString(yC); // sends the public key to alice
			OutputStream outToclient = server.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToclient);
			out.writeUTF(Bstr);
			System.out.println("key between Eve and Alice: " + power((int) yA, (int) xc, (int) p)); // pub key of alice
																									// ^ priv key of
																									// intruder

			server = serverSocket.accept();
			System.out.println("Connected!!");
			in = new DataInputStream(server.getInputStream());
			yB = Double.parseDouble(in.readUTF());
			System.out.println("Received from Bob " + yB);

			int xd = getRandomNumberUsingNextInt(2, p);
			double yD = ((Math.pow(alpha, xd)) % p);

			Bstr = Double.toString(yD);
			outToclient = server.getOutputStream();
			out = new DataOutputStream(outToclient);
			out.writeUTF(Bstr);

			System.out.println("Key between Eve and Bob: " + power((int) yB, (int) xd, (int) p));

			server.close();
		} catch (SocketTimeoutException s) {
			System.out.println("Timed out!");
		} catch (IOException e) {
		}
	}
}

// digest function use to generate the hash value of the message
// messagedigest uses the hash function to generate the hash value of the message
// hash function is used to generate the hash value of the message by how 
// UTF - 8 encoding is used to convert the message into bytes and then the bytes are hashed using the hash function