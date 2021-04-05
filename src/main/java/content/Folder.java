package content;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Folder {
    String name;
    List<DynamicBox> documents;
}
