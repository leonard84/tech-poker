package com.github.leonard84.techpoker.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

import com.github.leonard84.techpoker.config.PokerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@RestController
public class QrController {
    private static final Logger LOG = LoggerFactory.getLogger(QrController.class);

    private final PokerProperties pokerProperties;

    public QrController(PokerProperties pokerProperties) {
        this.pokerProperties = pokerProperties;
    }

    @GetMapping(value = "/qr/{qrCode}/join.svg", produces = "image/svg+xml")
    public ResponseEntity<String> getJoinQrCodeSvg(@PathVariable("qrCode") String qrCode) {
        try {
            BitMatrix bitMatrix = getBitMatrix(qrCode, 16, 16);

            StringBuilder sbPath = new StringBuilder();
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BitArray row = new BitArray(width);
            for (int y = 0; y < height; ++y) {
                row = bitMatrix.getRow(y, row);
                for (int x = 0; x < width; ++x) {
                    if (row.get(x)) {
                        sbPath.append(" M").append(x).append(",").append(y).append("h1v1h-1z");
                    }
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"0 0 ")
                    .append(width).append(" ")
                    .append(height).append("\" stroke=\"none\">\n");
            sb.append("<style type=\"text/css\">\n");
            sb.append(".black {fill:#000000;}\n");
            sb.append("</style>\n");
            sb.append("<path class=\"black\"  d=\"").append(sbPath).append("\"/>\n");
            sb.append("</svg>\n");

            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(Duration.ofDays(365)))
                    .body(sb.toString());

        } catch (IllegalArgumentException uuidError) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (WriterException e) {
            LOG.error("Could not create QR code", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/qr/{qrCode}/join.png")
    public ResponseEntity<byte[]> getJoinQrCodePng(@PathVariable("qrCode") String qrCode) {
        try {
            BitMatrix bitMatrix = getBitMatrix(qrCode, 100, 100);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png",baos);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .cacheControl(CacheControl.maxAge(Duration.ofDays(365)))
                    .body(baos.toByteArray());

        } catch (IllegalArgumentException uuidError) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (WriterException | IOException e) {
            LOG.error("Could not create QR code", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private BitMatrix getBitMatrix(String qrCode, int width, int height) throws WriterException {
        // simple parameter validation with UUID parsing
        UUID uuid = UUID.fromString(qrCode);
        String joinUrl = pokerProperties.getExternalUrl() + "/join?gameId=" + uuid;

        // Code adapted from https://stackoverflow.com/a/60638350
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());

        BitMatrix bitMatrix = qrCodeWriter.encode(joinUrl, BarcodeFormat.QR_CODE, width, height, hints);
        return bitMatrix;
    }
}
