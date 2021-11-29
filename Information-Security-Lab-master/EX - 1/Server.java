import java.net.*;
import java.util.*;
class Server
{
	private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	public static String decrypt(String cipherText, int shiftKey) 
	{
		cipherText = cipherText.toLowerCase();
		String plainText = "";
		for (int i = 0; i < cipherText.length(); i++) 
			{
				int charPosition = ALPHABET.indexOf(cipherText.charAt(i));
				int keyVal = (charPosition - shiftKey) % 26;
				if (keyVal < 0) 
				{
					keyVal = ALPHABET.length() + keyVal;
				}
				char replaceVal = ALPHABET.charAt(keyVal);
				plainText += replaceVal;
			}
		return plainText;
	}

	public static String encrypt(String plainText, int shiftKey) 
	{
		plainText = plainText.toLowerCase();
		String cipherText = "";
		for (int i = 0; i < plainText.length(); i++) 
		{
			int charPosition = ALPHABET.indexOf(plainText.charAt(i));
			int keyVal = (shiftKey + charPosition) % 26;
			char replaceVal = ALPHABET.charAt(keyVal);
			cipherText += replaceVal;
		}
		return cipherText;
	}	


		public static void brute(String text)
		{
			int key=1;
			for (int i=1; i<26;i++)
			{
				String plainText = "";
				for (int j = 0; j < text.length(); j++) 
				{
					int charPosition = ALPHABET.indexOf(text.charAt(j));
					int keyVal = (charPosition - i) % 26;
					if (keyVal < 0) 
					{
						keyVal = ALPHABET.length() + keyVal;
					}
					char replaceVal = ALPHABET.charAt(keyVal);
					plainText += replaceVal;
				}
				System.out.println("Key: "+key+"  Text: "+ plainText);
				key++;
		}
	}

	public static char freq(String text)
	{
		char freq_char =' ';
		int max_count=0;
		for(int i=0;i<text.length();i++)
		{
			char temp = text.charAt(i);
			
			int curr_count = 0;
			for(int j=0 ; j<text.length();j++)
			{
				if(temp == text.charAt(j))
				{
					curr_count++;
				}
			}
			if(curr_count>max_count) 
			{
				max_count = curr_count;
				freq_char = temp;
			}
		}
		return freq_char;
	}

	public static void frequen(String text)
	{
		char freq_occured = freq(text);
		Scanner sc=new Scanner(System.in);
		char[] fr={'e','t','a','o','i'};
		System.out.println("Frequently occurred cipher letter: "+freq_occured);
		for(int i=0;i<fr.length;i++)
		{
			int temp = ALPHABET.indexOf(freq_occured);
			int key = (temp-ALPHABET.indexOf(fr[i]))%26;
			if (key<0)
			{
				key=key+26;
			}
			String result = decrypt(text,key);
			System.out.println("Plain Text: " + result + " Key : "+ key);
			System.out.println("Is this wanted Plain text? (Y/N)");
			String n = sc.nextLine();
			if(n.toLowerCase().charAt(0)=='n')
			{
				continue;
			}
			else
				break;
		}
	}




	public static void main(String[ ] args) throws Exception
	{
		try
		{
			loop: while(true)
			{
			int MAX_LEN = 100;
			int localPortNum = 5000;
			DatagramSocket mySocket = new DatagramSocket(localPortNum);
			byte[] buffer = new byte[MAX_LEN];
			DatagramPacket packet = new DatagramPacket(buffer, MAX_LEN);
			mySocket.receive(packet);
			String message = new String(buffer); 
			message=message.trim();
			String arr[] = message.split(":");
			String text = arr[0].toLowerCase();
			int key = Integer.parseInt(arr[1]);
			int choice = Integer.parseInt(arr[2]);
			switch(choice)
			{
				case 1:
				System.out.println("Plain text: "+text);
				System.out.println("Cipher text: "+encrypt(text,key));
				System.out.println("\n");
				break;

				case 2:
				System.out.println("Cipher text: "+text);
				System.out.println("Plain text: "+decrypt(text,key));
				System.out.println("\n");
				break;

				case 3:
				System.out.println("Cipher text: "+text);
				brute(text);
				System.out.println("\n");
				break;

				case 4:
				System.out.println("Cipher text: "+text);
				frequen(text);
				System.out.println("\n");
				break;

				case 5:
				break loop;
			}
			mySocket.close( );
		}
		}
		catch(Exception e)
		{
			e.printStackTrace( );
		}
	}
}

