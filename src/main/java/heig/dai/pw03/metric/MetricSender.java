package heig.dai.pw03.metric;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MetricSender implements Runnable {

    private final DatagramSocket socket;
    private final InetSocketAddress group;
    private final Metric metric;

    @Override
    public void run() {
        try {
            log.debug("Sending metrics to server");
            // TODO compute metric
            var value = (int) (Math.random() * 100);
            // TODO properly format message
            String message = "metric{value=%.2d}".formatted(value);

            byte[] payload = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket datagram = new DatagramPacket(payload, payload.length, group);

            socket.send(datagram);
        } catch (IOException e) {
            log.error("Error while sending metrics", e);
            throw new UncheckedIOException(e);
        }
    }
}