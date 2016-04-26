import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionPackage {
	Socket socket;
	BufferedReader bfReader;
	PrintWriter ptWriter;
	
	public ConnectionPackage() {
	
	}
	public ConnectionPackage(Socket socket,BufferedReader bfReader,PrintWriter ptWriter)
	{
		this.socket=socket;
		this.bfReader=bfReader;
		this.ptWriter=ptWriter;
	}
}
