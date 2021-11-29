import java.util.*;
import java.util.Map.Entry;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class PER
{
    public String getHEX_SHA256(String pt) throws NoSuchAlgorithmException
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] sha = md.digest(pt.getBytes(StandardCharsets.UTF_8));
            BigInteger n = new BigInteger(1, sha);
            StringBuilder hs = new StringBuilder(n.toString(16));
            while (hs.length()<32)
            {
                hs.insert(0,'0');
            }
            String ans = hs.toString();
            return ans;
        }
        catch(Exception ex)
        {
            System.out.println("Error!");
            return null;
        }
       
    }

    public static void main(String args[])
    {
        PER per = new PER();
        Scanner input = new Scanner(System.in);
        HashMap<String, String> dictionary = new HashMap<String, String>();

        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        try
        {
            for(int i=0; i<alpha.length(); i++)
            {
                for(int j=0; j<alpha.length(); j++)
                {
                    String s = alpha.charAt(i)+""+alpha.charAt(j);
                    String sha_256 = per.getHEX_SHA256(s);
                    dictionary.put(s, sha_256);
                }
            }
            loop: while(true)
            {
                System.out.println("-------------Password Recovery And Extraction-------------");
                System.out.println("Enter your choice 1 - Convert to SHA-256   2 - Dictionary Attack 3 - Exit");
                int choice = input.nextInt();
                input.nextLine();
                switch(choice)
                {
                    case 1:
                        System.out.println("Password: ");
                        String pass = input.nextLine();
                        System.out.println("Hash: " + per.getHEX_SHA256(pass));
                        break;
                    case 2:
                        System.out.println("Hash: ");
                        String h = input.nextLine();
                        for(Entry<String, String> entry: dictionary.entrySet()) 
                        {
                            if(entry.getValue().equals(h)) 
                            {
                                System.out.println(entry.getKey());
                                System.out.println("The password is " + entry.getKey());
                                break;
                            }
                        }
                        break;
                    
                    case 3:
                        System.out.println("Exiting......");
                        break loop;

                    default:
                        System.out.println("Enter valid input!");
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Error!");
        }
        input.close();
    }
}