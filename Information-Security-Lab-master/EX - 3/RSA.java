import java.util.*;
import java.math.*;

public class RSA
{
	public boolean gcd_verify(BigInteger e, BigInteger phi)
	{
		BigInteger gcd_value = e.gcd(phi);
		System.out.println(gcd_value);
		if(gcd_value.compareTo(BigInteger.valueOf(1))==0)
			return true;
		else
			return false;
	}

	public BigInteger encryptkey(BigInteger phi)
	{
		BigInteger e;
		Scanner input = new Scanner(System.in);
		while(true)
		{
			System.out.println("Enter the value of e: ");
			e = input.nextBigInteger();
			if(gcd_verify(e,phi)==true)
			{
				System.out.println("e value is valid!");
				break;
			}
			else
				continue;
		}

		System.out.println("e:  "+e);

		return e;
	}

	public BigInteger decryptkey(BigInteger e, BigInteger phi)
	{
		BigInteger a=phi,b=e,q,r,t,t1,t2;
		t1=BigInteger.valueOf(0);
		t2=BigInteger.valueOf(1);
		while(!(b.equals(BigInteger.valueOf(0))))
		{
			q=a.divide(b);
			r=a.mod(b);
			t=t1.subtract(q.multiply(t2));
			a=b;
			b=r;
			t1=t2;
			t2=t;
		}
		if(t1.compareTo(BigInteger.valueOf(0))==-1)
		{
			t1=t1.add(phi);
		}
		System.out.println("d:  "+t1);
		return t1;
	}


	public BigInteger encrypt(BigInteger plain, BigInteger e, BigInteger n)
	{
		return plain.modPow(e, n);
	}

	public BigInteger decrypt(BigInteger cipher, BigInteger d, BigInteger n)
	{
		return cipher.modPow(d, n);
	}
	
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		RSA rsa = new RSA();

		String  alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		while(true)
		{
			System.out.println("Enter your choice:\n1-Key generation\n2-Encryption\n3-Decryption\n4-Exit");
			BigInteger n, e, phi, d;
			int choice = input.nextInt();
			if(choice==4)
			{
				break;
			}
			else
			{
				switch(choice)
				{
					case 1:
					{
						System.out.println("Enter the prime number (p):");
						BigInteger p = input.nextBigInteger();
						System.out.println("Enter the prime numer (q):");
						BigInteger q = input.nextBigInteger();
						
						n = p.multiply(q);
						
						BigInteger one = BigInteger.valueOf(1);
					
						phi = (p.subtract(one)).multiply((q.subtract(one)));

						System.out.println("n:  " + n);
						System.out.println("phi:  " + phi);
						System.out.println();

						e = rsa.encryptkey(phi);
						d = rsa.decryptkey(e, phi);
						
						break;
					}
					case 2:
					{
						System.out.println("Enter the plain text:");
						String pt = input.next().toUpperCase();
						System.out.println("Enter the multiplication of two prime numbers (n):");
						n = input.nextBigInteger();
						System.out.println("Enter the encryption key (e):");
						e = input.nextBigInteger();
						System.out.println("Cipher:");
						for(int i=0; i<pt.length(); i++)
						{
							BigInteger plain = BigInteger.valueOf(alpha.indexOf(pt.charAt(i)));
							System.out.print(rsa.encrypt(plain, e, n) + "\n");
						}
						break;
					}
					case 3:
					{
						System.out.println("Enter the number of characters:");
						int count = input.nextInt();

						BigInteger[] num_array = new BigInteger[count];
                        System.out.println("Enter the CT:");
						for(int i=0; i<count; i++)
						{
							num_array[i] = input.nextBigInteger();
						}

						System.out.println("Enter the multiplication of two prime numbers (n):");
						n = input.nextBigInteger();
						System.out.println("Enter the decryption key (d)");
						d = input.nextBigInteger();
						System.out.println("plain text: ");
						for(int i=0;i<count; i++)
						{
							System.out.print(alpha.charAt(rsa.decrypt(num_array[i], d, n).intValue()));
						}
						System.out.println();
						break;
					}
					default:
						System.out.println("Enter the valid input!");

				}
			}

		
		}
	}
}