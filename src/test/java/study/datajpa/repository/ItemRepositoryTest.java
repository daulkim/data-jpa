package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save() {
        // GenerateValue -> persist 될 때 들어감
        Item item = new Item("A");
        // Before override isNew() : pk -> null X isNew() -> false => merge 동작 select 날림 -> 없음 -> insert
        itemRepository.save(item);
    }
}