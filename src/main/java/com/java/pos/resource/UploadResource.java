package com.java.pos.resource;

import com.java.pos.model.Perfil;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Path("/api/upload")
public class UploadResource {


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public void upload(MultipartFormDataInput input) {

        Map<String, List<InputPart>> formData = input.getFormDataMap();
        List<InputPart> inputParts = formData.get("perfil-foto");

        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                String fotoBase64 = Base64.getEncoder().encodeToString(bytes);

                String mediaType = inputPart.getMediaType().getType() + "/" + inputPart.getMediaType().getSubtype();

                String especificacao = "data:" + mediaType + ";base64,";

                Perfil.update("foto = ?1 ", especificacao + fotoBase64);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
