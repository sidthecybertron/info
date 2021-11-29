import java.util.*;
import java.math.BigInteger;
public class RSA
{
    public static BigInteger one = BigInteger.valueOf(1);
    public static BigInteger zero = BigInteger.valueOf(0);
    public static String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public BigInteger encryptKey(BigInteger phi)
    {
        BigInteger e;
        Scanner input = new Scanner(System.in);
        while(true)
        {
            System.out.println("Enter the value of e: ");
            e = input.nextBigInteger();
            if(e.gcd(phi).equals(one))
            {
                break;
            }
        }
        return e;
    }

    public BigInteger decryptKey(BigInteger e, BigInteger phi)
    {
        BigInteger a = phi, b = e, q, r, t, t1, t2;
        t1 = zero;
        t2 = one;
        while(!(b.equals(zero)))
        {
            q = a.divide(b);
            r = a.mod(b);
            t = t1.subtract(q.multiply(t2));
            a = b;
            b = r;
            t1 = t2;
            t2 = t;
        }
        if(t1.compareTo(zero)==-1)
        {
            t1 = t1.add(phi);
        }
        return t1;
    }
    
    public BigInteger encrypt(BigInteger e, BigInteger n, BigInteger m)
    {
        BigInteger ct = m.modPow(e, n);
        return ct;
    }

    public BigInteger decrypt(BigInteger d, BigInteger n, BigInteger m)
    {
        BigInteger pt = m.modPow(d, n);
        return pt;
    }

    public static void main(String args[])
    {
        Scanner input = new Scanner(System.in);
        RSA rsa = new RSA();
        while(true)
        {
            BigInteger p , q, n, phi, e, d;
            System.out.println("\t-------------19IT082 - RSA-------------\t");
            System.out.println("Enter your choice: 1 - Key Generation 2-Encryption 3-Decryption 4-Exit");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) 
            {
                case 1:
                    System.out.println("Enter the prime numbers 'p' and 'q':");
                    p = input.nextBigInteger();
                    q = input.nextBigInteger();
                    n = p.multiply(q);
                    phi = p.subtract(one).multiply(q.subtract(one));
                    e = rsa.encryptKey(phi);
                    d = rsa.decryptKey(e, phi);
                    System.out.println("p: "+p+"\n"+"q: "+q+"\n"+"n: "+n+"\n"+"phi: "+phi+"\n"+"e: "+e+"\n"+"d: "+d+"\n");
                    break;
                
                case 2:
                    System.out.println("Enter plain text:");
                    String pt = input.nextLine().toUpperCase();
                    System.out.println("Enter the value of 'e', 'n' :");
                    e = input.nextBigInteger();
                    n = input.nextBigInteger();
                    System.out.println("Cipher text:-");
                    for(int i=0; i<pt.length(); i++)
                    {
                        BigInteger m = BigInteger.valueOf(alpha.indexOf(pt.charAt(i)));
                        System.out.println(rsa.encrypt(e, n, m));
                    }
                    break;
                case 3:
                    System.out.println("Enter the value of 'd', 'n' :");
                    d = input.nextBigInteger();
                    n = input.nextBigInteger();
                    System.out.println("Enter length of the cipher text:");
                    int size = input.nextInt();
                    System.out.println("Enter the cipher text: ");
                    pt = "";
                    for(int i=0; i<size; i++)
                    {
                        BigInteger m = input.nextBigInteger();
                        m = rsa.decrypt(d, n, m).mod(BigInteger.valueOf(26));
                        pt += Character.toString(alpha.charAt(m.intValue()));
                    }
                    System.out.println("Plain text:"+pt);
                    break;
                default:
                    break;
            }
        }

    }
}