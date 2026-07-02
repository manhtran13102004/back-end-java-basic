package com.hocviec.shared.config;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hocviec.shared.exception.AppException;
import com.hocviec.shared.exception.ErrorCode;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.body() != null) {
            try (InputStream bodyIs = response.body().asInputStream()) {
                JsonNode jsonNode = objectMapper.readTree(bodyIs);
                
                if (jsonNode != null && jsonNode.has("code")) {
                    int responseCode = jsonNode.get("code").asInt();
                    
                    // 🌟 DUYỆT TÌM: Tìm xem cái số code này ứng với ErrorCode nào trong Enum chung
                    for (ErrorCode errorCode : ErrorCode.values()) {
                        if (errorCode.getCode() == responseCode) {
                            // Tìm thấy phát là ném luôn AppException với ErrorCode chuẩn đó!
                            return new AppException(errorCode);
                        }
                    }
                }
            } catch (Exception e) {
                // Lỗi đọc dòng
            }
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}