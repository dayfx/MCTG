package server.utils;


import server.http.Method;
import server.server.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;

public class RequestBuilder {
    public server.server.Request buildRequest(BufferedReader bufferedReader) throws IOException {
        Request request = new Request();
        String line = bufferedReader.readLine();

        if (line != null) {
            String[] splitFirstLine = line.split(" ");

            request.setMethod(getMethod(splitFirstLine[0]));
            setPathname(request, splitFirstLine[1]);

            line = bufferedReader.readLine();
            while (!line.isEmpty()) {
                request.getHeaderMap().ingest(line);
                if(line.startsWith("Authorization: ")){
                    request.setToken(line);
                }
                if (line.startsWith("Content-Type: ")) {
                    request.setContentType(line.split(": ")[1]);  // store content type in request
                }
                line = bufferedReader.readLine();
            }

            if (request.getHeaderMap().getContentLength() > 0) {
                char[] charBuffer = new char[request.getHeaderMap().getContentLength()];
                bufferedReader.read(charBuffer, 0, request.getHeaderMap().getContentLength());

                request.setBody(new String(charBuffer));
            }
        }

        return request;
    }

    private Method getMethod(String methodString) {
        return Method.valueOf(methodString.toUpperCase(Locale.ROOT));
    }

    private void setPathname(Request request, String path){
        Boolean hasParams = path.indexOf("?") != -1;

        if (hasParams) {
            String[] pathParts =  path.split("\\?");
            request.setPathname(pathParts[0]);
            request.setParams(pathParts[1]);
        }
        else
        {
            request.setPathname(path);
            request.setParams(null);
        }
    }
}
