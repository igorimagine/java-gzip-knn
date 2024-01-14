package practice.gzipknn1.main;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
@Service
public class Logic {

    @PostConstruct
    void logic() {
        log.info("--- START ---");
        final var str = "Hello, World!";
        final var compressed = compress(str);
        final var decompressed = decompress(compressed);
        log.info(decompressed);
        log.info("--- END ---");
    }

    private byte[] compress(String str) {
        try (final var out = new ByteArrayOutputStream(); final var gzip = new GZIPOutputStream(out)) {
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
            gzip.finish();
            return out.toByteArray();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    private String decompress(byte[] buf) {
        try (final var gzip = new GZIPInputStream(new ByteArrayInputStream(buf)); final var stringWriter = new StringWriter()) {
            IOUtils.copy(gzip, stringWriter, StandardCharsets.UTF_8);
            return stringWriter.toString();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return null;
        }
    }
}
