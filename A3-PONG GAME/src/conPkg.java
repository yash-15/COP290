

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class conPkg {
	Socket socket;
	BufferedReader bfReader;
	PrintWriter ptWriter;
	
	public conPkg() {
	
	}
	public conPkg(Socket socket,BufferedReader bfReader,PrintWriter ptWriter)
	{
		this.socket=socket;
		this.bfReader=bfReader;
		this.ptWriter=ptWriter;
	}
}
