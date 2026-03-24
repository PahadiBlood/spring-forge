package xyz.manojraw.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Service
public class NotificationClient {

    private final RestClient restClient;

    public String getLastNotification(Long sleepTime) {
        String response = restClient
                .get()
                .uri("/notifications?sleepTime={sleepTime}", sleepTime)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError, (req, res) -> {
                            HttpStatusCode status = res.getStatusCode();
                            if (status == HttpStatus.NOT_FOUND) {
                                System.out.println("Error not found " + res.getStatusText());
                            } else {
                                System.out.println("4xx error occur " + res.getStatusText());
                            }
                        })
                .onStatus(
                        HttpStatusCode::is5xxServerError, (req, res) -> {
                            System.out.println("5xx error " + res.getStatusText());

                        }
                )
                .body(String.class);


        return response;
    }

    public String getLastNotificationCallback(Exception e){
        e.printStackTrace();
        return "Fallback error";
    }

}
