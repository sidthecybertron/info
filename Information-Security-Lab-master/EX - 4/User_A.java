import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class User_A {
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

	public static int getdiscretekey(int y, int a, int p) {

		long start = System.currentTimeMillis();
		int i = 0;
		for (i = 1; i < p; i++) {
			int x = power(a, i, p);
			System.out.println(i + "\t" + x + "\t" + y);
			if (x == y) {
				return i;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("The function takes " + (end - start) + "ms");
		return -1;
	}

	public static void main(String[] args) throws IOException {
		try {
			double alpha, Ya, yB, key_B, pubB;
			int p, choice;
			String Bstr;
			String serverName = "localhost";

			ServerSocket serverSocket = new ServerSocket(8888);
			System.out.println("User Bob");
			Socket server = serverSocket.accept();
			System.out.println("Connected !!");
			DataInputStream in = new DataInputStream(server.getInputStream());
			p = Integer.parseInt(in.readUTF());

			System.out.println("P = " + p);

			int Xa = getRandomNumberUsingNextInt(2, p);
			System.out.println("User A : Private Key Xa= " + Xa);
			alpha = Integer.parseInt(in.readUTF());
			System.out.println("Alpha = " + alpha);
			choice = Integer.parseInt(in.readUTF());

			if (choice == 1) {
				Ya = ((Math.pow(alpha, Xa)) % p);
				System.out.println("User A : Public Key Ya= " + Ya);
				Bstr = Double.toString(Ya);
				pubB = Double.parseDouble(in.readUTF());
				OutputStream outToclient = server.getOutputStream();
				DataOutputStream out = new DataOutputStream(outToclient);
				out.writeUTF(Bstr);
				System.out.println("Public Key of User B (received) is : " + pubB);
				System.out.println("Key to perform Symmetric Encryption = " + power((int) pubB, (int) Xa, (int) p));

				System.out.println("Discrete Log: XA using yA = " + getdiscretekey((int) Ya, (int) alpha, p));
				System.out.println("Discrete Log: XA using yB = " + getdiscretekey((int) pubB, (int) alpha, p));

			}
			if (choice == 2) {
				server.close();
				try {
					TimeUnit.SECONDS.sleep(1);
					Socket client = new Socket(serverName, 5555);
					System.out.println("connected");
					// send
					yB = ((Math.pow(alpha, Xa)) % p);
					OutputStream outToclient = client.getOutputStream();
					DataOutputStream out = new DataOutputStream(outToclient);
					Bstr = Double.toString(yB);
					out.writeUTF(Bstr);
					System.out.println("Sent " + yB);
					in = new DataInputStream(client.getInputStream());

					// recieve
					double yD = Double.parseDouble(in.readUTF());
					System.out.println("Received " + yD);

					System.out.println("key: " + power((int) yD, (int) Xa, (int) p));

				} catch (Exception e) {
				}

			}
		} catch (SocketTimeoutException s) {
			System.out.println("Timed out!");
		} catch (IOException e) {
		}
	}
}