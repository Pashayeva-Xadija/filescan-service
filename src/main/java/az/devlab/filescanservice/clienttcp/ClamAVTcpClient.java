package az.devlab.filescanservice.clienttcp;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ClamAVTcpClient {

    private final String host;
    private final int port;
    private final int timeoutMs;


    public ClamAVTcpClient(String host, int port, int timeoutMs) {
        this.host = host;
        this.port = port;
        this.timeoutMs = timeoutMs;
    }


    public String scan(byte[] data) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            socket.setSoTimeout(timeoutMs);

            try (OutputStream out = new BufferedOutputStream(socket.getOutputStream());
                 InputStream in  = new BufferedInputStream(socket.getInputStream())) {

                out.write("zINSTREAM\0".getBytes());
                out.flush();

                final int CHUNK = 8192;
                int off = 0;
                while (off < data.length) {
                    int len = Math.min(CHUNK, data.length - off);
                    out.write(ByteBuffer.allocate(4).putInt(len).array());
                    out.write(data, off, len);
                    off += len;
                }
                out.write(ByteBuffer.allocate(4).putInt(0).array());
                out.flush();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int b;
                while ((b = in.read()) != -1) {
                    if (b == '\n') break;
                    bos.write(b);
                }
                return bos.toString();
            }
        }
    }
}
