import java.net.*;
import java.util.*;
class Client
{
	public static void main(String[ ] args) throws Exception{
	try
	{
		DatagramSocket mySocket = new DatagramSocket( );
		loop: while(true)
		{
			InetAddress receiverHost = InetAddress.getLocalHost();
			int receiverPort = 5000;
			Scanner sc=new Scanner(System.in);
			System.out.println("\n\n1-Encryption\n2-Decryption\n3-Brute Force Attack\n4-Frequency analysis\n5-Exit");
			System.out.println("Enter your the choice: ");
			int choice = sc.nextInt();
			String text="";
			int key=0;
			switch(choice)
			{
				case 1:
				System.out.println("Enter plain text: ");
				sc.nextLine();
				text=sc.nextLine();
				System.out.println("Enter key: ");
				key=sc.nextInt();
				text=text+":"+String.valueOf(key)+":"+String.valueOf(choice);
				break;

				case 2:
				System.out.println("Enter cipher text: ");
				sc.nextLine();
				text=sc.nextLine();
				System.out.println("Enter key: ");
				key=sc.nextInt();
				text=text+":"+String.valueOf(key)+":"+String.valueOf(choice);
				break;

				case 3:
				System.out.println("Enter cipher text: ");
				sc.nextLine();
				text=sc.nextLine();
				text=text+":"+String.valueOf(key)+":"+String.valueOf(choice);
				break;

				case 4:
				System.out.println("Enter cipher text: ");
				sc.nextLine();
				text=sc.nextLine();
				text=text+":"+String.valueOf(key)+":"+String.valueOf(choice);
				break;

				case 5:
				break loop;

			}
			byte[] buffer = text.getBytes( );
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverHost, receiverPort);
			mySocket.send(packet);
		}
		mySocket.close( );
	}
	catch(Exception e)
	{ 
		e.printStackTrace( ); }
	}
}