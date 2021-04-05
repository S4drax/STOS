package content;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Directory {
    Map<String, Folder> folderMap;
}
