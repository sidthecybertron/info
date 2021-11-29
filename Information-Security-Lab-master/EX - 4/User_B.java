import java.net.*;
import java.io.*;
import java.util.*;

public class User_B {
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

	static boolean isPrime(int n) {

		for (int i = 2; i * i <= n; i = i + 1) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}

	static void findPrimefactors(HashSet<Integer> s, int n) {
		for (int i = 2; i <= n; i = i + 1) {

			while (n % i == 0) {
				s.add(i);
				n = n / i;
			}
		}

	}

	static int findPrimitive(int n) {
		HashSet<Integer> s = new HashSet<Integer>();

		if (isPrime(n) == false) {
			return -1;
		}

		int phi = n - 1;

		findPrimefactors(s, phi);

		for (int r = 2; r <= phi; r++) {

			boolean flag = false;
			for (Integer a : s) {
				if (power(r, phi / (a), n) == 1) {
					flag = true;
					break;
				}
			}

			if (flag == false) {
				System.out.print(r + " ");
			}
		}
		return -1;
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

	public static void main(String[] args) {
		try {
			String global_val, global_alpha, global_choice, result;
			String serverName = "localhost";
			int p, choice;
			Scanner ip = new Scanner(System.in);
			System.out.println("User Alice");
			System.out.println("Enter the prime number (p): ");
			p = ip.nextInt();
			boolean res = isPrime(p);
			if (!res) {
				System.out.println("Please, Enter a Prime Number!");
				System.out.println("Enter the prime number (p): ");
				p = ip.nextInt();

			}
			int alpha;
			System.out.println("Primitive roots are: \n");
			findPrimitive(p);
			System.out.println("\nEnter the value of alpha (a): ");
			alpha = ip.nextInt();
			System.out.println("1.Key Exchange and Discrete Log\n2.Man in the Middle Attack\nEnter the choice: ");
			choice = ip.nextInt();

			int Xb = getRandomNumberUsingNextInt(2, p);
			double key_A, Yb, pubA;

			Socket client = new Socket(serverName, 8888);
			System.out.println("Connected...");
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			global_val = Integer.toString(p);
			out.writeUTF(global_val);
			global_alpha = Integer.toString(alpha);
			out.writeUTF(global_alpha);
			global_choice = Integer.toString(choice);
			out.writeUTF(global_choice);

			if (choice == 1) {
				Yb = ((Math.pow(alpha, Xb)) % p);
				result = Double.toString(Yb);
				out.writeUTF(result);
				System.out.println("User B : Private Key Xb= " + Xb);
				DataInputStream in = new DataInputStream(client.getInputStream());
				pubA = Double.parseDouble(in.readUTF());
				System.out.println("User B : Public Key Yb= " + Yb);
				System.out.println("Public Key of User A (received) is : " + pubA);
				System.out.println("Key to perform Symmetric Encryption = " + power((int) pubA, (int) Xb, (int) p));
				System.out.println("Discrete Log: xB using yB = " + getdiscretekey((int) Yb, (int) alpha, p));
				System.out.println("Discrete Log: xB using yA = " + getdiscretekey((int) pubA, (int) alpha, p));

				client.close();

			}

			if (choice == 2) {
				client.close();
				client = new Socket(serverName, 5555);
				double yA = ((Math.pow(alpha, Xb)) % p);
				OutputStream outToclient = client.getOutputStream();
				out = new DataOutputStream(outToclient);
				String Bstr = Double.toString(yA);
				out.writeUTF(Bstr);
				out.writeUTF(global_val);
				out.writeUTF(global_alpha);
				System.out.println("Sent " + yA);
				// send
				DataInputStream in = new DataInputStream(client.getInputStream());
				double yC = Double.parseDouble(in.readUTF());

				System.out.println("Received " + yC);
				System.out.println("key: " + power((int) yC, (int) Xb, (int) p));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}