package ru.practicum.gateway.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.ApiPathConstants;
import ru.practicum.shareit.item.model.CommentRequest;
import ru.practicum.shareit.item.model.Item;

@Service
public class ItemClient extends BaseClient {
    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + ApiPathConstants.ITEM_PATH))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> add(Item item, Long userId) {
        return post("", userId, item);
    }

    public ResponseEntity<Object> addComment(CommentRequest commentRequest, Long id, Long userId) {
        return post("/" + id + "/comment", userId, commentRequest);
    }

    public ResponseEntity<Object> update(Item item, Long id, Long userId) {
        return patch("/" + id, userId, item);
    }

    public void deleteItem(Long id) {
        delete(String.valueOf(id));
    }

    public ResponseEntity<Object> getItems(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> getItemByIdForUser(Long id, Long userId) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> search(String text) {
        return get("/search?text=" + text);
    }
}
