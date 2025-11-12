package az.devlab.filescanservice.clientrest;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ClamAVRestClient {

    private final String scanUrl;
    private final int timeoutMs;
    private final RestTemplate rest = new RestTemplate();

    public ClamAVRestClient(String scanUrl, int timeoutMs) {
        this.scanUrl = scanUrl;
        this.timeoutMs = timeoutMs;
    }

    public String scan(byte[] data) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResourceEx(data, "upload.bin"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<String> resp =
                rest.postForEntity(scanUrl, new HttpEntity<>(body, headers), String.class);
        return resp.getBody();
    }

    static class ByteArrayResourceEx extends org.springframework.core.io.ByteArrayResource {
        private final String filename;
        ByteArrayResourceEx(byte[] bytes, String filename) {
            super(bytes);
            this.filename = filename;
        }
        @Override public String getFilename() { return filename; }
    }
}
