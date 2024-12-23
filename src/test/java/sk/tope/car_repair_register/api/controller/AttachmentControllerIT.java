package sk.tope.car_repair_register.api.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import sk.tope.car_repair_register.api.service.so.AttachmentSo;
import sk.tope.car_repair_register.common.TestBase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AttachmentControllerIT extends TestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentControllerIT.class);

    MockMultipartFile mockMultipartFile =
            new MockMultipartFile("multipartFile",
                    "plaintext.txt",
                    "text/plain", "Lorem ipsum dolor sit amet".getBytes());


    @Test
    public void testUploadAttachment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/attachment/")
                        .file(mockMultipartFile)
                        .param("repairLogId", "1003")
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isNotNull();
        AttachmentSo result = asJsonObject(mvcResult.getResponse().getContentAsString(), AttachmentSo.class);
        assertThat(result.id()).isNotNull();
        assertThat(result.name()).isEqualTo(mockMultipartFile.getOriginalFilename());
        assertThat(result.mimeType()).isEqualTo(mockMultipartFile.getContentType());
        assertThat(result.created()).isNotNull();
        assertThat(result.modified()).isNull();

        mockMvc.perform(multipart("/attachment/")
                        .file(mockMultipartFile)
                        .param("repairLogId", "999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDownloadAttachment() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/attachment/")
                        .file(mockMultipartFile)
                        .param("repairLogId", "1003")
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();
        AttachmentSo uploadedAttachment = asJsonObject(mvcResult.getResponse().getContentAsString(), AttachmentSo.class);


        MvcResult fileMvcResult = mockMvc.perform(get("/attachment/" + uploadedAttachment.id())
                .with(getUser())).andExpect(status().isOk()).andReturn();

        assertThat(fileMvcResult.getResponse().getContentType()).isEqualTo(mockMultipartFile.getContentType());
        assertThat(fileMvcResult.getResponse().getContentAsByteArray()).isEqualTo(mockMultipartFile.getBytes());
    }

    @Test
    public void testDeleteRepairLog() throws Exception {
        MvcResult mvcResult = mockMvc.perform(multipart("/attachment/")
                        .file(mockMultipartFile)
                        .param("repairLogId", "1003")
                        .with(getUser()))
                .andExpect(status().isCreated()).andReturn();
        AttachmentSo uploadedAttachment = asJsonObject(mvcResult.getResponse().getContentAsString(), AttachmentSo.class);

        mockMvc.perform(delete("/attachment/" + uploadedAttachment.id())
                        .with(getUser()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/attachment/" + uploadedAttachment.id())
                        .with(getUser()))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/attachment/999")
                        .with(getUser()))
                .andExpect(status().isNotFound());
    }
}
