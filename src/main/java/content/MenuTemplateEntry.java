package content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuTemplateEntry {
    int order;
    String dishName;
    String price;
}
